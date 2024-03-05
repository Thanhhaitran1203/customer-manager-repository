package com.customer.controller;

import com.customer.model.Customer;
import com.customer.model.Province;
import com.customer.service.ICustomerService;
import com.customer.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IProvinceService provinceService;

    @ModelAttribute("provinces")
    public Iterable<Province> listProvinces() {
        return provinceService.findAll();
    }

//    @GetMapping("")
//    public ModelAndView listCustomer() {
//        ModelAndView modelAndView = new ModelAndView("/customer/list");
//        Iterable<Customer> customers = customerService.findAll();
//        modelAndView.addObject("customers", customers);
//        return modelAndView;
//    }
    @GetMapping()
    public ModelAndView listCustomer(Pageable pageable){
        pageable = PageRequest.of(0,5);
        Page<Customer> customers = customerService.findAll2(pageable);
        System.out.println(customers);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }
    @PostMapping("/search")
public ModelAndView listCustomers(@RequestParam("search") Optional<String> search, Pageable pageable){
    Page<Customer> customers;
    pageable = PageRequest.ofSize(5);
    if(search.isPresent()){
        customers = customerService.findAllByNameContaining(search.get(), pageable);
    } else {
        customers = customerService.findAll2(pageable);
    }
    ModelAndView modelAndView = new ModelAndView("/customer/list");
    modelAndView.addObject("customers", customers);
    return modelAndView;
}

    @GetMapping("/page")
    public ModelAndView listCustomerPage(@RequestParam int page,Pageable pageable){
        pageable = PageRequest.of(page,5);
        Page<Customer> customers = customerService.findAll2(pageable);
        System.out.println(customers);
        ModelAndView modelAndView = new ModelAndView("/customer/list");
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/customer/create");
        modelAndView.addObject("customer", new Customer());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("customer") Customer customer,
                         RedirectAttributes redirectAttributes) {
        customerService.save(customer);
        redirectAttributes.addFlashAttribute("message", "Create new customer successfully");
        return "redirect:/customers";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Customer> customer = customerService.findById(id);
        if (customer.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/customer/update");
            modelAndView.addObject("customer", customer.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("customer") Customer customer,
                         RedirectAttributes redirect) {
        customerService.save(customer);
        redirect.addFlashAttribute("message", "Update customer successfully");
        return "redirect:/customers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect) {
        customerService.remove(id);
        redirect.addFlashAttribute("message", "Delete customer successfully");
        return "redirect:/customers";
    }
}