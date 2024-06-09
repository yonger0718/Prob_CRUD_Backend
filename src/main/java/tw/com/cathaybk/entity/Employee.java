package tw.com.cathaybk.entity;

import jakarta.persistence.*;
import lombok.*;


@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long employeeId;
    @Column(name = "name")
    private String employeeName;
    @Column(name = "email")
    private String employeeEmail;
    @Column(name = "pid", unique = true, length = 10)
    private String employeePID;
    @Column(name = "phone")
    private String employeePhoneNumber;
    @Column(name = "address")
    private String employeeAddress;
    @Column(name = "gender")
    private char employeeGender;
}
