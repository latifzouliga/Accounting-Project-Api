package com.zouliga.repository;


import com.zouliga.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCompany_Title(String companyTitle, Sort sort);

    @Query(
            nativeQuery = true,
            value = """
                    SELECT count(p) FROM categories ct
                    JOIN products p ON ct.id = p.category_id
                    JOIN companies cm ON ct.company_id = cm.id
                    WHERE cm.title = :title AND ct.description = :description
                    """
    )
    Long countCategoryProducts(@Param("title") String companyTitle,
                               @Param("description") String categoryDescription);

}
