package tw.com.cathaybk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.com.cathaybk.dto.EmployeeDTO;
import tw.com.cathaybk.entity.Employee;
import tw.com.cathaybk.service.EmployeeService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Tag(name = "員工操作API",
    description = "使用此API可以新增員工、搜尋員工、更新員工、刪除員工")
@RestController()
//@CrossOrigin(origins = {"https://yonger-cathay.web.app",
//    "https://myapp-fujxotx5xq-de.a.run.app",
//    "https://yonger-cathay.firebaseapp.com"})
@RequestMapping("/api/employee")
@Slf4j
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping()
    @Operation(summary = "新增員工")
    public ResponseEntity<Employee> saveEmployee(
            @RequestPart(value = "employee", required = true) EmployeeDTO employee,
            @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {
        ResponseEntity<Employee> employeeResponseEntity = employeeService.saveEmployee(employee); //先save才有id
        if(file != null) {
            employeeService.uploadEmployeeImage(Objects.requireNonNull(employeeResponseEntity.getBody())
                    .getEmployeeId(), file);
        }
        return employeeResponseEntity;
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
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新員工資料")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @RequestPart(value = "employee", required = true) EmployeeDTO employee,
            @RequestPart(value = "image", required = false) MultipartFile file) throws IOException {
        ResponseEntity<Employee> res;
        if(employeeService.getEmployeeById(id) == null) {
            res = employeeService.saveEmployee(employee);
        }
        else {
            res = employeeService.updateEmployee(id, employee);
        }

        if(file != null) {
            employeeService.uploadEmployeeImage(Objects.requireNonNull(res.getBody().getEmployeeId()), file);
        }
        return res;
    }

    @PostMapping("/upload/{id}")
    @Operation(summary = "上傳員工圖片")
    public ResponseEntity<Void> uploadEmployeeImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        try {
            employeeService.uploadEmployeeImage(id, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().build();
    }
}
