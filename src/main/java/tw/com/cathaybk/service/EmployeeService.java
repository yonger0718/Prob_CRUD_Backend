package tw.com.cathaybk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.cathaybk.entity.Employee;
import tw.com.cathaybk.dao.EmployeeDAO;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    public Employee saveEmployee(Employee employee) {
        return employeeDAO.save(employee);
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();
        employeeDAO.findAll().forEach(employees::add);
        return employees;
    }

    public Employee getEmployee(Long id) {
        return employeeDAO.findById(id).orElseThrow();
    }

    public void deleteEmployee(Long id) {
        employeeDAO.deleteById(id);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        Employee emp = employeeDAO.findById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        emp.setEmployeeName(employee.getEmployeeName());
        emp.setEmployeeEmail(employee.getEmployeeEmail());
        emp.setEmployeePID(employee.getEmployeePID());
        emp.setEmployeePhoneNumber(employee.getEmployeePhoneNumber());
        emp.setEmployeeAddress(employee.getEmployeeAddress());
        emp.setEmployeeGender(employee.getEmployeeGender());
        return employeeDAO.save(emp); //try to use return employeeDAO.save(employee) but fail -> PID duplicate
    }
}
