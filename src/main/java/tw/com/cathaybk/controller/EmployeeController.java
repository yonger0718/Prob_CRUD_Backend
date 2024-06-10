package tw.com.cathaybk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tw.com.cathaybk.entity.Employee;
import tw.com.cathaybk.service.EmployeeService;
import tw.com.cathaybk.utils.Result;

import java.util.List;

@Tag(name = "員工操作API",
    description = "使用此API可以新增員工、搜尋員工、更新員工、刪除員工")
@RestController()
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    @Operation(summary = "新增員工")
    public Result saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping()
    @Operation(summary = "搜尋員工")
    public Result<List<Employee>> getEmployees() {
        return employeeService.getEmployees();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根據id搜尋員工")
    public Result<Employee> getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除員工")
    public Result<Employee> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    @PostMapping("/{id}")
    @Operation(summary = "更新員工資料")
    public Result<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
        //TODO 考慮改為傳入DTO, 這樣傳入employee時可以不用帶入id，可能導致出現 update error
    }
}
