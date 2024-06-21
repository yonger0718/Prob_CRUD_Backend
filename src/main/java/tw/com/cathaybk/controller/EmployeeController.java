package tw.com.cathaybk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.com.cathaybk.dto.EmployeeDTO;
import tw.com.cathaybk.entity.Employee;
import tw.com.cathaybk.service.EmployeeService;

import java.util.List;

@Tag(name = "員工操作API",
    description = "使用此API可以新增員工、搜尋員工、更新員工、刪除員工")
@RestController()
//@CrossOrigin(origins = {"https://yonger-cathay.web.app",
//    "https://myapp-fujxotx5xq-de.a.run.app",
//    "https://yonger-cathay.firebaseapp.com"})
@RequestMapping("/api/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping()
    @Operation(summary = "新增員工")
    public ResponseEntity saveEmployee(@RequestBody EmployeeDTO employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping()
    @Operation(summary = "搜尋所有員工資訊")
    public ResponseEntity<List<Employee>> getEmployees() {
        return employeeService.getEmployees();
        // TODO 幫前端改寫為簡單版的VO
    }

    @GetMapping("/{id}")
    @Operation(summary = "根據id搜尋員工")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "刪除員工")
    public ResponseEntity deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    @PostMapping("/{id}")
    @Operation(summary = "更新員工資料")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employee) {
        return employeeService.updateEmployee(id, employee);
    }
}
