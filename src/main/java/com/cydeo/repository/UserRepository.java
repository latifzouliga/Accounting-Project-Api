package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    List<User> findAllByCompany_Title(String company, Pageable pageable);
    List<User> findAllByCompany_Title(String company);

    @Query(value = "SELECT * FROM users WHERE role_id = 2", nativeQuery = true)
    List<User> findAllAdmins(Pageable pageable);
    List<User> findAllByCompany_TitleAndRole_Description(String companyTile, String role);

}
