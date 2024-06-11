package tw.com.cathaybk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tw.com.cathaybk.dto.EmployeeDTO;
import tw.com.cathaybk.entity.Employee;
import tw.com.cathaybk.dao.EmployeeDAO;
import tw.com.cathaybk.exception.UserNotFoundException;
import tw.com.cathaybk.utils.Result;

import java.time.LocalDateTime;


@Service
@Slf4j
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    /**
     * 儲存資料，回傳員工資訊
     * @param employee
     * @return Result
     */
    public Result<Employee> saveEmployee(EmployeeDTO employee) {
        Employee emp = new Employee();
        BeanUtils.copyProperties(employee, emp);
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        employeeDAO.save(emp);
        log.info("員工添加成功 id: {}", emp.getEmployeeId());
        return Result.success(emp);
    }

    /**
     * 取得所有員工資料，會自動分頁
     * @param pageable
     * @return Result of Pages
     */
    public Result<Page<Employee>> getEmployees(Pageable pageable) {
        return Result.success(employeeDAO.findAll(pageable));
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
     * @param id
     * @return Result
     */
    public Result<Employee> getEmployee(Long id) {
        return Result.success(getEmployeeById(id));
    }

    /**
     * 刪除員工
     * @param id
     * @return Result
     */
    public Result deleteEmployee(Long id) {
        if(!employeeDAO.existsById(id)) {
            log.info("查無員工資料 id: {}", id);
            throw new UserNotFoundException("User Not Found");
        }
        log.info("員工資料刪除成功 id: {}", id);
        employeeDAO.deleteById(id);
        return Result.success();
    }

    /**
     * 更改員工資料
     * @param id
     * @param employee
     * @return Result，如果失敗則會進入Result.error(Exception)
     */
    public Result<Employee> updateEmployee(Long id, EmployeeDTO employee) {
        Employee emp = getEmployeeById(id);
        BeanUtils.copyProperties(employee, emp);
        emp.setUpdateTime(LocalDateTime.now());
        log.info("員工資料更新成功 id: {}", emp.getEmployeeId());
        return Result.success(employeeDAO.save(emp)); //try to use return employeeDAO.save(employee) but fail -> PID duplicate
    }
}
