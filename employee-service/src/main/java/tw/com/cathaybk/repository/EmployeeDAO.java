package tw.com.cathaybk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tw.com.cathaybk.entity.Employee;

import java.time.LocalDateTime;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {

    @Modifying
    @Query("update Employee e set e.imgUrl = ?2 , e.updateTime = ?3 where e.employeeId = ?1")
    @Transactional
    Integer updateImageByEmployeeID(Long employeeId, String imgUrl, LocalDateTime now);
}
