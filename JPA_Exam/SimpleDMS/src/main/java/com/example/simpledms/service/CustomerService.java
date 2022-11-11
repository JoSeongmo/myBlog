package com.example.simpledms.service;

import com.example.simpledms.model.Customer;
import com.example.simpledms.model.Emp;
import com.example.simpledms.repository.CustomerRepository;
import com.example.simpledms.repository.EmpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * packageName : com.example.modelexam.service
 * fileName : EmpService
 * author : kangtaegyung
 * date : 2022/10/12
 * description : 고객 서비스 클래스
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/10/12         kangtaegyung          최초 생성
 */
// springboot 프레임워크에 객체를 생성함 : 싱글톤 유형
@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository; // 샘플데이터 DB에 접근하는 객체

    public Page<Customer> findAll(Pageable pageable) {
        Page<Customer> page = customerRepository.findAll(pageable);

        return page;
    }

    public Optional<Customer> findById(int cid) {
        Optional<Customer> optionalCustomer = customerRepository.findById(cid);

        return optionalCustomer;
    }

    public Customer save(Customer customer) {

        Customer customer2 = customerRepository.save(customer);

        return customer2;
    }

    public boolean removeById(int cid) {

        if (customerRepository.existsById(cid)) {
            customerRepository.deleteById(cid);
            return true;
        }
        return false;
    }

    public void removeAll() {

        customerRepository.deleteAll();
    }

    //    email like 검색
    public Page<Customer> findAllByEmailContaining(String email, Pageable pageable) {

        Page<Customer> page = customerRepository.findAllByEmailContaining(email, pageable);

        return page;
    }

}






















