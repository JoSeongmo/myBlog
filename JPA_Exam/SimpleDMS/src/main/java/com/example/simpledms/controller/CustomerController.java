package com.example.simpledms.controller;

import com.example.simpledms.model.Customer;
import com.example.simpledms.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * packageName : com.example.modelexam.controller
 * fileName : CustomerController
 * author : kangtaegyung
 * date : 2022/10/12
 * description : 사원 컨트롤러
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/10/12         kangtaegyung          최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customer")
    public ResponseEntity<Object> getCustomerAll(@RequestParam(required = false) String email,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size
    ) {

        try {
//            페이지 변수 저장
            Pageable pageable = PageRequest.of(page, size);

//            List<Dept> list = Collections.customertyList();
            Page<Customer> customerPage = customerService.findAllByEmailContaining(email, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("customer", customerPage.getContent());
            response.put("currentPage", customerPage.getNumber());
            response.put("totalItems", customerPage.getTotalElements());
            response.put("totalPages", customerPage.getTotalPages());

            if (customerPage.isEmpty() == false) {
//                성공
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
//            서버 에러
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/customer/{cid}")
    public ResponseEntity<Object> getCustomerId(@PathVariable int cid) {

        try {
            Optional<Customer> optionalCustomer = customerService.findById(cid);

            if (optionalCustomer.isPresent()) {
//                성공
                return new ResponseEntity<>(optionalCustomer.get(), HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
//            서버 에러
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<Object> createCustomer(@RequestBody Customer customer) {

        try {
            Customer customer2 = customerService.save(customer);

            return new ResponseEntity<>(customer2, HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러가 났을경우 : INTERNAL_SERVER_ERROR 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/customer/{cid}")
    public ResponseEntity<Object> updateCustomer(@PathVariable int cid, @RequestBody Customer customer) {

        try {
            Customer customer2 = customerService.save(customer);

            return new ResponseEntity<>(customer2, HttpStatus.OK);

        } catch (Exception e) {
//            DB 에러가 났을경우 : INTERNAL_SERVER_ERROR 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/customer/deletion/{cid}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable int cid) {

//        프론트엔드 쪽으로 상태정보를 보내줌
        try {
            boolean bSuccess = customerService.removeById(cid);

            if (bSuccess == true) {
//                delete 문이 성공했을 경우
                return new ResponseEntity<>(HttpStatus.OK);
            }
//            delete 실패했을 경우( 0건 삭제가 될경우 )
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
//            DB 에러가 날경우
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/customer/all")
    public ResponseEntity<Object> deleteAll() {

//        프론트엔드 쪽으로 상태정보를 보내줌
        try {
            customerService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러가 날경우
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

















