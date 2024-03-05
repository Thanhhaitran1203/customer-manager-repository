package com.customer.service;

import com.customer.model.Customer;
import com.customer.model.Province;
import com.customer.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private ICustomerRepository iCustomerRepository;

    @Override
    public Iterable<Customer> findAll() {
        return iCustomerRepository.findAll();
    }
    public Page<Customer> findAll2(Pageable pageable) {
        return iCustomerRepository.findAll(pageable);
    }

    @Override
    public void save(Customer customer) {
        iCustomerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return iCustomerRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iCustomerRepository.deleteById(id);
    }

    @Override
    public Iterable<Customer> findAllByProvince(Province province) {
        return iCustomerRepository.findAllByProvince(province);
    }
    @Override
    public Page<Customer> findAllByName(Pageable pageable, String name) {
        return iCustomerRepository.findAllByFirstNameContaining(pageable, name);
    }
    @Override
    public Page<Customer> findAllByNameContaining(String name, Pageable pageable){
        return iCustomerRepository.findAllByFirstNameContaining(pageable, name);
    }
}