package com.example.simpledms.controller;

import com.example.simpledms.model.TeamKorea;
import com.example.simpledms.service.TeamKoreaService;
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
 * fileName : TeamKorea07Controller
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

@RestController
@RequestMapping("/api")
public class TeamKoreaController {

    @Autowired
    TeamKoreaService teamKoreaService;

    @GetMapping("/teamKorea")
    public ResponseEntity<Object> getTeamKoreaAll(@RequestParam String searchSelect,
                                                  @RequestParam(required = false) String searchKeyword,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "3") int size
    ) {

        try {

            Pageable pageable = PageRequest.of(page, size);

            Page<TeamKorea> teamKoreaPage;


//            Page 객체 정의
            if( searchSelect.equals("한글이름")) {
                teamKoreaPage = teamKoreaService.findAllByKoreanNameContaining(searchKeyword, pageable);
            } else if (searchSelect.equals("포지션")) {
                teamKoreaPage = teamKoreaService.findAllByPositionContaining(searchKeyword, pageable);
            } else if (searchSelect.equals("영문이름")) {
                teamKoreaPage = teamKoreaService.findAllByEnglishNameContaining(searchKeyword, pageable);
            } else if (searchSelect.equals("생년월일")) {
                teamKoreaPage = teamKoreaService.findAllByBirthContaining(searchKeyword, pageable);
            } else if (searchSelect.equals("소속팀")) {
                teamKoreaPage = teamKoreaService.findAllByAffiliatedTeamContaining(searchKeyword, pageable);
            } else {
                try {
                    teamKoreaPage = teamKoreaService.findPidContain(Integer.parseInt(searchKeyword), pageable);
                } catch (Exception e) {
                    teamKoreaPage = teamKoreaService.findAllByKoreanNameContaining(searchKeyword, pageable);
                }

            }
//            맵 자료구조에 넣어서 전송
            Map<String, Object> response = new HashMap<>();
            response.put("teamKorea", teamKoreaPage.getContent());
            response.put("currentPage", teamKoreaPage.getNumber());
            response.put("totalItems", teamKoreaPage.getTotalElements());
            response.put("totalPages", teamKoreaPage.getTotalPages());

            if (teamKoreaPage.isEmpty() == false) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/teamKorea/all")
    public ResponseEntity<Object> removeAll() {

        try {
            teamKoreaService.removeAll();

            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/teamKorea")
    public ResponseEntity<Object> createTeamKorea(@RequestBody TeamKorea teamKorea) {

        try {
            TeamKorea teamKorea2 = teamKoreaService.save(teamKorea);

            return new ResponseEntity<>(teamKorea2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/teamKorea/{pid}")
    public ResponseEntity<Object> getTeamKoreaId(@PathVariable int pid) {

        try {
            Optional<TeamKorea> optionalTeamKorea = teamKoreaService.findById(pid);

            if (optionalTeamKorea.isPresent() == true) {
//                데이터 + 성공 메세지 전송
                return new ResponseEntity<>(optionalTeamKorea.get(), HttpStatus.OK);
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

    @PutMapping("/teamKorea/{pid}")
    public ResponseEntity<Object> updateTeamKorea(@PathVariable int pid,
                                            @RequestBody TeamKorea teamKorea) {

        try {
            TeamKorea teamKorea2 = teamKoreaService.save(teamKorea);

            return new ResponseEntity<>(teamKorea2, HttpStatus.OK);

        } catch (Exception e) {
            log.debug(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping ("/teamKorea/deletion/{pid}")
    public ResponseEntity<Object> deleteTeamKorea(@PathVariable int pid) {

        try {
            boolean bSuccess = teamKoreaService.removeById(pid);

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










