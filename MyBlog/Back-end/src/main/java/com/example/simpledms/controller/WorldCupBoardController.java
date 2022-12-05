package com.example.simpledms.controller;

import com.example.simpledms.model.WorldCupBoard;
import com.example.simpledms.service.WorldCupBoardService;
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
 * packageName : com.example.jpaexam.controller.exam07
 * fileName : WorldCupBoard07Controller
 * author : ds
 * date : 2022-10-21
 * description : 부서 컨트롤러 쿼리 메소드
 * 요약 :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-21         ds          최초 생성
 */
@Slf4j
// CORS 보안 : 기본적으로 한사이트에서 포트를 달리 사용못함
// @CrossOrigin(허용할_사이트주소(Vue 사이트주소:포트)) : CORS 보안을 허용해주는 어노테이션
//@CrossOrigin(origins = "http://localhost")
@RestController
@RequestMapping("/api")
public class WorldCupBoardController {

    @Autowired
    WorldCupBoardService worldCupBoardService; // @Autowired : 스프링부트가 가동될때 생성된 객체를 하나 받아오기

//    frontend url(쿼리스트링방식) : ? 매개변수 전송방식 사용했으면 ------> backend @RequestParam
//    frontend url(파라메터방식) : /{} 매개변수 전송방식 사용했으면 ------> backend @PathVariable
    @GetMapping("/worldCupBoard")
    public ResponseEntity<Object> getWorldCupBoardAll(@RequestParam String searchSelect,
                                            @RequestParam(required = false) String searchKeyword,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "3") int size)
    {

        try {
//            Pageable 객체 정의 ( page, size 값 설정 )
            Pageable pageable = PageRequest.of(page, size);

            Page<WorldCupBoard> worldCupBoardPage;

//            Page 객체 정의
            if( searchSelect.equals("닉네임")) {
                worldCupBoardPage = worldCupBoardService.findAllByNicknameContaining(searchKeyword, pageable);
            } else if (searchSelect.equals("제목")) {
                worldCupBoardPage = worldCupBoardService.findAllByTitleContaining(searchKeyword, pageable);
            } else if (searchSelect.equals("내용")) {
                worldCupBoardPage = worldCupBoardService.findAllByContentContaining(searchKeyword, pageable);
            } else  {
                try {
                    worldCupBoardPage = worldCupBoardService.findidContain(Integer.parseInt(searchKeyword), pageable);
                } catch (Exception e) {
                    worldCupBoardPage = worldCupBoardService.findAllByNicknameContaining(searchKeyword, pageable);
                }


            }

            //            맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("worldCupBoard", worldCupBoardPage.getContent());
            response.put("currentPage", worldCupBoardPage.getNumber());
            response.put("totalItems", worldCupBoardPage.getTotalElements());
            response.put("totalPages", worldCupBoardPage.getTotalPages());

            if (worldCupBoardPage.isEmpty() == false) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            // 서버에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/worldCupBoard/all")
    public ResponseEntity<Object> removeAll() {

        try {
            worldCupBoardService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/worldCupBoard")
    public ResponseEntity<Object> createWorldCupBoard(@RequestBody WorldCupBoard worldCupBoard) {

        try {
            WorldCupBoard worldCupBoard2 = worldCupBoardService.save(worldCupBoard);

            return new ResponseEntity<>(worldCupBoard2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/worldCupBoard/{id}")
    public ResponseEntity<Object> getWorldCupBoardId(@PathVariable int id) {

        try {
            Optional<WorldCupBoard> optionalWorldCupBoard = worldCupBoardService.findById(id);

            if (optionalWorldCupBoard.isPresent() == true) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(optionalWorldCupBoard.get(), HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            // 서버에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/worldCupBoard/{id}")
    public ResponseEntity<Object> updateWorldCupBoard(@PathVariable int id,
                                            @RequestBody WorldCupBoard worldCupBoard) {

        try {
            WorldCupBoard worldCupBoard2 = worldCupBoardService.save(worldCupBoard);

            return new ResponseEntity<>(worldCupBoard2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping ("/worldCupBoard/deletion/{id}")
    public ResponseEntity<Object> deleteWorldCupBoard(@PathVariable int id) {

        try {
            boolean bSuccess = worldCupBoardService.removeById(id);

            if (bSuccess == true) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
//                데이터 없음 메세지 전송(클라이언트)
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            // 서버에러 발생 메세지 전송(클라이언트)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}










