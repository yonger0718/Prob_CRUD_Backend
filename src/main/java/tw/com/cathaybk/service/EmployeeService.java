package tw.com.cathaybk.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.cathaybk.entity.Employee;
import tw.com.cathaybk.dao.EmployeeDAO;
import tw.com.cathaybk.exception.UserNotFoundException;
import tw.com.cathaybk.utils.Result;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public Result<Employee> saveEmployee(Employee employee) {
        employeeDAO.save(employee); //TODO 因為新增也是自增，不會需要帶入employeeID -> 可能還是拿DTO好
        return Result.success(employee);
    }

    public Result<List<Employee>> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeeDAO.findAll().forEach(employees::add);
        return Result.success(employees);
    }

    private Employee getEmployeeById(Long id) {
        return employeeDAO.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));
    }

    public Result<Employee> getEmployee(Long id) {
        return Result.success(getEmployeeById(id));
    }

    public Result<Employee> deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeDAO.deleteById(id);
        return Result.success(employee);
    }

    public Result<Employee> updateEmployee(Long id, Employee employee) {
        Employee emp = getEmployeeById(id);
        BeanUtils.copyProperties(employee, emp);
        emp.setEmployeeId(id); //TODO 考慮用DTO取代掉這一段，便可以不用在封裝時傳入id
        return Result.success(employeeDAO.save(emp)); //try to use return employeeDAO.save(employee) but fail -> PID duplicate
    }
}
