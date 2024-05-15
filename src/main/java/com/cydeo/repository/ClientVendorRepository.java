package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import com.cydeo.enums.ClientVendorType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {

    List<ClientVendor> findAllByCompany_Title(String companyTitle, Pageable pageable);

    List<ClientVendor> findAllByCompany_TitleAndClientVendorType(
            String companyTitle,
            ClientVendorType clientVendorType,
            Pageable page
    );
}
