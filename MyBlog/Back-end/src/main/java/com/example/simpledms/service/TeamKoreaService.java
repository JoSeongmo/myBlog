package com.example.simpledms.service;

import com.example.simpledms.model.TeamKorea ;

import com.example.simpledms.repository.TeamKoreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.jpaexam.service.exam01
 * fileName : TeamKorea Service
 * author : ds
 * date : 2022-10-20
 * description : 부서 업무 서비스 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Service
public class TeamKoreaService {

    @Autowired
    TeamKoreaRepository teamKoreaRepository; // JPA CRUD 함수가 있는 인터페이스
    

    //    전체 조회 함수 findAll() ->(변경) findAll(Pageable pageable)
    public Page<TeamKorea > findAll(Pageable pageable) {
        Page<TeamKorea > page = teamKoreaRepository.findAll(pageable);

        return page;
    }

    //    전체 삭제 함수
    public void removeAll() {
        teamKoreaRepository.deleteAll(); // 전체 삭제
    }

    //   부서 정보 저장/수정 함수
    public TeamKorea  save(TeamKorea  teamKorea) {

        TeamKorea  teamKorea2 = teamKoreaRepository.save(teamKorea);

        return teamKorea2;
    }

    //    부서번호로 조회하는 함수
    public Optional<TeamKorea> findById(int pid) {
//        findById(기본키속성)
        Optional<TeamKorea> optionalTeamKorea  = teamKoreaRepository.findById(pid);
//
////        데이터 가공
//        TeamKorea  teamKorea = optionalTeamKorea .get();
//        teamKorea.setDname(teamKorea.getDname() + "_해킹");
//
////        가공 데이터를 다시 옵셔널 넣기
//        optionalTeamKorea  = Optional.ofNullable(teamKorea);

        return optionalTeamKorea ;
    }

    // 부서번호(rid)로 삭제하는 함수
    public boolean removeById(int rid) {
//        existsById(기본키) 있으면 삭제 실행 + true 리턴
        if(teamKoreaRepository.existsById(rid) == true) {
            teamKoreaRepository.deleteById(rid);
            return true;
        }

//        없으면 그냥 false 리턴
        return false;
    }

    //    email like 검색 함수( 페이징 처리 추가 )
    public Page<TeamKorea > findAllByKoreanNameContaining(String  koreanName, Pageable pageable) {
        Page<TeamKorea > page = teamKoreaRepository.findAllByKoreanNameContaining(koreanName, pageable);

        return page;
    }
    public Page<TeamKorea > findAllByPositionContaining(String position, Pageable pageable) {
        Page<TeamKorea > page = teamKoreaRepository.findAllByPositionContaining(position, pageable);

        return page;
    }
    public Page<TeamKorea > findAllByEnglishNameContaining(String englishName, Pageable pageable) {
        Page<TeamKorea > page = teamKoreaRepository.findAllByEnglishNameContaining(englishName, pageable);

        return page;
    }
    public Page<TeamKorea > findAllByBirthContaining(String birth, Pageable pageable) {
        Page<TeamKorea > page = teamKoreaRepository.findAllByBirthContaining(birth, pageable);

        return page;
    }
    public Page<TeamKorea > findAllByAffiliatedTeamContaining(String AffiliatedTeam, Pageable pageable) {
        Page<TeamKorea > page = teamKoreaRepository.findAllByAffiliatedTeamContaining(AffiliatedTeam, pageable);

        return page;
    }
    public Page<TeamKorea > findPidContain(Integer pid, Pageable pageable) {
        Page<TeamKorea > page = teamKoreaRepository.findPidContain(pid, pageable);

        return page;
    }


}









