package tw.com.cathaybk.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable {
    private static final long SerialVersionUID = 1L;
    private String employeeName;
    private String employeeEmail;
    private String employeePID;
    private String employeePhoneNumber;
    private String employeeAddress;
    private char employeeGender;
}
