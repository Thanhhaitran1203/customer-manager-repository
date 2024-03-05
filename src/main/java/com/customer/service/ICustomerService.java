package com.customer.service;

import com.customer.model.Customer;
import com.customer.model.Province;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICustomerService extends IGenerateService<Customer>{
    Iterable<Customer> findAllByProvince(Province province);

    Page<Customer> findAll2(Pageable pageable);
    Page<Customer> findAllByName(Pageable pageable, String name);

    Page<Customer> findAllByNameContaining(String name, Pageable pageable);
}
