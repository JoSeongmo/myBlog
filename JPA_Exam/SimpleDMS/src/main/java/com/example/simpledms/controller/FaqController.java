package com.example.simpledms.controller;

import com.example.simpledms.model.Dept;
import com.example.simpledms.model.Faq;
import com.example.simpledms.service.FaqService;
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
 * fileName : FaqController
 * author : kangtaegyung
 * date : 2022/10/12
 * description : 부서 컨트롤러
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/10/12         kangtaegyung          최초 생성
 */
@Slf4j
//@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/api")
public class FaqController {

    @Autowired
    FaqService faqService;

    @GetMapping("/faq")
    public ResponseEntity<Object> getFaqAll(@RequestParam(required = false) String title,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int size
    ) {

        try {

//            페이지 변수 저장
            Pageable pageable = PageRequest.of(page, size);

            Page<Faq> deptPage;

//            dname 이 없으면 전체 검색 실행
            if (title.equals("")) {
                deptPage = faqService.findAll(pageable);
            }
//            dname 에 검색어가 있으면 like 검색 실행
            else {
                deptPage = faqService.findAllByTitleContaining(title, pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("faq", deptPage.getContent());
            response.put("currentPage", deptPage.getNumber());
            response.put("totalItems", deptPage.getTotalElements());
            response.put("totalPages", deptPage.getTotalPages());

            if (deptPage.isEmpty() == false) {
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

    @GetMapping("/faq/{no}")
    public ResponseEntity<Object> getFaqId(@PathVariable int no) {

        try {
            Optional<Faq> optionalFaq = faqService.findById(no);

            if (optionalFaq.isPresent()) {
//                성공
                return new ResponseEntity<>(optionalFaq.get(), HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
//            서버 에러
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/faq")
    public ResponseEntity<Object> createFaq(@RequestBody Faq faq) {

        try {
            Faq faq2 = faqService.save(faq);

            return new ResponseEntity<>(faq2, HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러가 났을경우 : INTERNAL_SERVER_ERROR 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/faq/{no}")
    public ResponseEntity<Object> updateFaq(@PathVariable int no, @RequestBody Faq faq) {

        try {
            Faq faq2 = faqService.save(faq);

            return new ResponseEntity<>(faq2, HttpStatus.OK);

        } catch (Exception e) {
//            DB 에러가 났을경우 : INTERNAL_SERVER_ERROR 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/faq/deletion/{no}")
    public ResponseEntity<Object> deleteFaq(@PathVariable int no) {

//        프론트엔드 쪽으로 상태정보를 보내줌
        try {
            boolean bSuccess = faqService.removeById(no);

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

    @DeleteMapping("/faq/all")
    public ResponseEntity<Object> deleteAll() {

//        프론트엔드 쪽으로 상태정보를 보내줌
        try {
            faqService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러가 날경우
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

















