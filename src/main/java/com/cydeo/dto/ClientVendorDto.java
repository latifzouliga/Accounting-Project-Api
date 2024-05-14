package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientVendorDto {
        private Long id;
        private String clientVendorName;
        private String phone;
        private String website;
        private ClientVendorType clientVendorType;
        private AddressDto address;
        private CompanyDto company;

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        private boolean hasInvoice;
}
