package tw.com.cathaybk.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.com.cathaybk.entity.Employee;

@Repository
public interface EmployeeDAO extends CrudRepository<Employee, Long> {
}
