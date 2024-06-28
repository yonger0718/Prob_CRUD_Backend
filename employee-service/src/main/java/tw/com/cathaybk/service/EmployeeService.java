package tw.com.cathaybk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import tw.com.cathaybk.dto.EmployeeDTO;
import tw.com.cathaybk.entity.Employee;
import tw.com.cathaybk.exception.UploadFailureException;
import tw.com.cathaybk.repository.EmployeeDAO;
import tw.com.cathaybk.exception.UserNotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    private final RestTemplate restTemplate;

    @Value("${util-service.host}")
    private String utilServiceHost;

    /**
     * 儲存資料，回傳員工資訊
     *
     * @param employee
     * @return ResponseEntity.ok(emp)
     */
    public ResponseEntity<Employee> saveEmployee(EmployeeDTO employee) {
        Employee emp = new Employee();
        BeanUtils.copyProperties(employee, emp);
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        employeeDAO.save(emp);
        log.info("員工添加成功 id: {}", emp.getEmployeeId());
        return ResponseEntity.ok(emp);
    }

    /**
     * 取得所有員工資料
     * @return List of Employee
     */
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok((employeeDAO.findAll()));
    }

    /**
     * 一個通用method，找到則回傳員工資料，失敗時進入ExceptionHandler
     * @param id
     * @return Employee
     */
    public Employee getEmployeeById(Long id) {
        log.info("取得員工資料 id: {}", id);
        return employeeDAO.findById(id).orElseThrow(() -> new UserNotFoundException("找不到該用戶"));
    }

    /**
     * 根據id取得員工資料
     * @param id
     * @return Result
     */
    public ResponseEntity<Employee> getEmployee(Long id) {
        return ResponseEntity.ok(getEmployeeById(id));
    }

    /**
     * 刪除員工
     *
     * @param id
     * @return Result
     */
    public ResponseEntity<Void> deleteEmployee(Long id) {
        if(!employeeDAO.existsById(id)) {
            log.info("查無員工資料 id: {}", id);
            throw new UserNotFoundException("找不到該用戶");
        }
        log.info("員工資料刪除成功 id: {}", id);
        employeeDAO.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 更改員工資料
     *
     * @param id
     * @param employee
     * @return Result，如果失敗則會進入Result.error(Exception)
     */
    public ResponseEntity<Employee> updateEmployee(Long id, EmployeeDTO employee) {
        Employee emp = getEmployeeById(id);
        BeanUtils.copyProperties(employee, emp);
        emp.setUpdateTime(LocalDateTime.now());
        log.info("員工資料更新成功 id: {}", emp.getEmployeeId());
        return ResponseEntity.ok(employeeDAO.save(emp)); //try to use return employeeDAO.save(employee) but fail -> PID duplicate
    }

    public void uploadEmployeeImage(Long id, MultipartFile file) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(file.getBytes()) {

            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> imgUrl = restTemplate.postForEntity(
                utilServiceHost + "/api/upload/employee/" + id,
                requestEntity,
                String.class);
        if(imgUrl.getStatusCode().is2xxSuccessful()) {
            log.info("員工圖片上傳成功 url: {}", imgUrl.getBody());
            employeeDAO.updateImageByEmployeeID(id, imgUrl.getBody(), LocalDateTime.now());
        }
        else {
            throw new UploadFailureException("員工圖片上傳失敗");
        }
    }
}
