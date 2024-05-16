package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import com.cydeo.enums.ClientVendorType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {

    List<ClientVendor> findAllByCompany_Title(String companyTitle, Pageable pageable);

    List<ClientVendor> findAllByCompany_TitleAndClientVendorType(
            String companyTitle,
            ClientVendorType clientVendorType,
            Pageable page
    );


    @Query(nativeQuery = true,
            value = """
            SELECT count(*)
            FROM clients_vendors cv
            JOIN invoices i on cv.id = i.client_vendor_id
            JOIN companies c on cv.company_id = c.id
            WHERE c.title = :companyTitle AND cv.client_vendor_name = :clientVendorName
            """)
    Long  countClientVendorInvoices(@Param("companyTitle") String companyTitle,
                                    @Param("clientVendorName") String clientVendorName);


}
