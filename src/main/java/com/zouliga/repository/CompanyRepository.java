package com.zouliga.repository;

import com.zouliga.entity.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT c FROM Company c WHERE c.title <> 'CYDEO'")
    List<Company> findAllCompanies(Pageable pageable);

//    Optional<Company> findAllByTitle(String companyTitle);
    Optional<Company> findByTitle(String companyTitle);


}
