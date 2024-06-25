package tw.com.cathaybk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.cathaybk.entity.Employee;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Long> {
}
