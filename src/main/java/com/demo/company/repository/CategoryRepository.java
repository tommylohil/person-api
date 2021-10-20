package com.demo.company.repository;

import com.demo.company.entity.Category;
import com.demo.company.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CategoryRepository extends MongoRepository<Category, String>, EmployeeCustomRepository {

	Page<Category> findByMarkForDeleteFalse(Pageable pageable);

}
