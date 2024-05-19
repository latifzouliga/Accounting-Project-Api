package com.cydeo.repository;

import com.cydeo.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(nativeQuery = true,
            value = """
                    SELECT * FROM products p
                    JOIN categories ct ON p.category_id = ct.id
                    JOIN companies c ON ct.company_id = c.id
                    WHERE c.title = :title
                    """)
    List<Product> findAllByCompanyTitle(@Param("title") String companyTitle, Pageable pageable);
}
