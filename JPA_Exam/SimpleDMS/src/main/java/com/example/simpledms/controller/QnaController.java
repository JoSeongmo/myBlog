package com.example.simpledms.controller;

import com.example.simpledms.model.Faq;
import com.example.simpledms.model.Qna;
import com.example.simpledms.service.FaqService;
import com.example.simpledms.service.QnaService;
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
 * fileName : QnaController
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
public class QnaController {

    @Autowired
    QnaService qnaService;

    @GetMapping("/qna")
    public ResponseEntity<Object> getQnaAll(@RequestParam String searchSelect,
                                                @RequestParam(required = false) String searchKeyword,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int size
    ) {

        try {

//            페이지 변수 저장
            Pageable pageable = PageRequest.of(page, size);

            Page<Qna> qnaPage;

            if (searchSelect.equals("question")) {
                qnaPage = qnaService.findAllByQuestionContaining(searchKeyword, pageable);
            }
            else {
                qnaPage = qnaService.findAllByQuestionerContaining(searchKeyword, pageable);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("qna", qnaPage.getContent());
            response.put("currentPage", qnaPage.getNumber());
            response.put("totalItems", qnaPage.getTotalElements());
            response.put("totalPages", qnaPage.getTotalPages());

            if (qnaPage.isEmpty() == false) {
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

    @GetMapping("/qna/{qno}")
    public ResponseEntity<Object> getQnaId(@PathVariable int qno) {

        try {
            Optional<Qna> optionalQna = qnaService.findById(qno);

            if (optionalQna.isPresent()) {
//                성공
                return new ResponseEntity<>(optionalQna.get(), HttpStatus.OK);
            } else {
//                데이터 없음
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
//            서버 에러
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/qna")
    public ResponseEntity<Object> createQna(@RequestBody Qna qna) {

        try {
            Qna qna2 = qnaService.save(qna);

            return new ResponseEntity<>(qna2, HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러가 났을경우 : INTERNAL_SERVER_ERROR 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/qna/{qno}")
    public ResponseEntity<Object> updateQna(@PathVariable int qno, @RequestBody Qna qna) {

        try {
            Qna qna2 = qnaService.save(qna);

            return new ResponseEntity<>(qna2, HttpStatus.OK);

        } catch (Exception e) {
//            DB 에러가 났을경우 : INTERNAL_SERVER_ERROR 프론트엔드로 전송
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/qna/deletion/{qno}")
    public ResponseEntity<Object> deleteQna(@PathVariable int qno) {

//        프론트엔드 쪽으로 상태정보를 보내줌
        try {
            boolean bSuccess = qnaService.removeById(qno);

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

    @DeleteMapping("/qna/all")
    public ResponseEntity<Object> deleteAll() {

//        프론트엔드 쪽으로 상태정보를 보내줌
        try {
            qnaService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
//            DB 에러가 날경우
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

















