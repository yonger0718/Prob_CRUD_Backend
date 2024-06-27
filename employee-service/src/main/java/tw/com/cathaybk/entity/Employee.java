package tw.com.cathaybk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee  implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;
    @Column(name = "iamge_url")
    private String imgUrl;
}
