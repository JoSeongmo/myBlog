package com.example.simpledms.controller;

import com.example.simpledms.model.Dept;
import com.example.simpledms.model.Emp;
import com.example.simpledms.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * packageName : com.example.modelexam.controller
 * fileName : EmpController
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
public class EmpController {

    @Autowired
    EmpService empService;

    @GetMapping("/emp")
    public ResponseEntity<Object> getDeptAll(@RequestParam(required = false) String ename,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size
    ) {

        try {

//            페이지 변수 저장
            Pageable pageable = PageRequest.of(page, size);

//            List<Dept> list = Collections.emptyList();
            Page<Emp> empPage;

//            dname 이 없으면 전체 검색 실행
            if (ename.equals("")) {
                empPage = empService.findAll(pageable);
            }
//            dname 에 검색어가 있으면 like 검색 실행
            else {
                empPage = empService.findAllByEnameContaining(ename, pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("emp", empPage.getContent());
            response.put("currentPage", empPage.getNumber());
            response.put("totalItems", empPage.getTotalElements());
            response.put("totalPages", empPage.getTotalPages());

            if (empPage.isEmpty() == false) {
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

    @GetMapping("/emp/{eno}")
    public ResponseEntity<Object> getEmpId(@PathVariable int eno) {

        try {
            Optional<Emp> optionalEmp = empService.findById(eno);

            if (optionalEmp.isPresent()) {
//                성공
                return new ResponseEntity<>(optionalEmp.get(), HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
//            서버 에러
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/emp")
    public ResponseEntity<Object> createEmp(@RequestBody Emp emp) {

        try {
            Emp emp2 = empService.save(emp);

            return new ResponseEntity<>(emp2, HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러가 났을경우 : INTERNAL_SERVER_ERROR 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/emp/{eno}")
    public ResponseEntity<Object> updateEmp(@PathVariable int eno, @RequestBody Emp emp) {

        try {
            Emp emp2 = empService.save(emp);

            return new ResponseEntity<>(emp2, HttpStatus.OK);

        } catch (Exception e) {
//            DB 에러가 났을경우 : INTERNAL_SERVER_ERROR 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/emp/deletion/{eno}")
    public ResponseEntity<Object> deleteEmp(@PathVariable int eno) {

//        프론트엔드 쪽으로 상태정보를 보내줌
        try {
            boolean bSuccess = empService.removeById(eno);

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

    @DeleteMapping("/emp/all")
    public ResponseEntity<Object> deleteAll() {

//        프론트엔드 쪽으로 상태정보를 보내줌
        try {
            empService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러가 날경우
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

















