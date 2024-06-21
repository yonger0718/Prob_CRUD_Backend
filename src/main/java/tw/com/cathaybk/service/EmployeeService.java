package tw.com.cathaybk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tw.com.cathaybk.dto.EmployeeDTO;
import tw.com.cathaybk.entity.Employee;
import tw.com.cathaybk.dao.EmployeeDAO;
import tw.com.cathaybk.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    /**
     * 儲存資料，回傳員工資訊
     *
     * @param employee
     * @return Result
     */
    public ResponseEntity saveEmployee(EmployeeDTO employee) {
        Employee emp = new Employee();
        BeanUtils.copyProperties(employee, emp);
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        employeeDAO.save(emp);
        log.info("員工添加成功 id: {}", emp.getEmployeeId());
        return ResponseEntity.ok(emp);
    }

    /**
     * 取得所有員工資料，會自動分頁
     *
     * @return Result of Pages
     */
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok((employeeDAO.findAll()));
    }

    /**
     * 一個通用method，找到則回傳員工資料，失敗進入ExceptionHandler
     * @param id
     * @return Employee
     */
    private Employee getEmployeeById(Long id) {
        log.info("取得員工資料 id: {}", id);
        return employeeDAO.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    /**
     * 根據id取得員工資料
     *
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
    public ResponseEntity deleteEmployee(Long id) {
        if(!employeeDAO.existsById(id)) {
            log.info("查無員工資料 id: {}", id);
            throw new UserNotFoundException("User Not Found");
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
}
