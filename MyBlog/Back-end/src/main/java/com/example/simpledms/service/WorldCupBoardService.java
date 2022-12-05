package com.example.simpledms.service;

import com.example.simpledms.model.WorldCupBoard;
import com.example.simpledms.repository.WorldCupBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.jpaexam.service.exam01
 * fileName : WorldCupBoardService
 * author : ds
 * date : 2022-10-20
 * description : 부서 업무 서비스 클래스
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Service
public class WorldCupBoardService {

    @Autowired
    WorldCupBoardRepository worldCupBoardRepository; // JPA CRUD 함수가 있는 인터페이스

    //    전체 조회 함수( 페이징 처리 )
    public Page<WorldCupBoard> findAll(Pageable pageable) {
        Page<WorldCupBoard> page = worldCupBoardRepository.findAll(pageable);

        return page;
    }

    //    전체 삭제 함수
    public void removeAll() {
        worldCupBoardRepository.deleteAll(); // 전체 삭제
    }

    //   부서 정보 저장/수정 함수
    public WorldCupBoard save(WorldCupBoard worldCupBoard) {

        WorldCupBoard worldCupBoard2 = worldCupBoardRepository.save(worldCupBoard);

        return worldCupBoard2;
    }

    //    부서번호로 조회하는 함수
    public Optional<WorldCupBoard> findById(int id) {
//        findById(기본키속성)
        Optional<WorldCupBoard> optionalWorldCupBoard = worldCupBoardRepository.findById(id);

        return optionalWorldCupBoard;
    }

    // 부서번호(id)로 삭제하는 함수
    public boolean removeById(int id) {
//        existsById(기본키) 있으면 삭제 실행 + true 리턴
        if(worldCupBoardRepository.existsById(id) == true) {
            worldCupBoardRepository.deleteById(id);
            return true;
        }

//        없으면 그냥 false 리턴
        return false;
    }

    //    question(질문) like 검색 함수 ( 페이징 처리 )
    public Page<WorldCupBoard> findAllByNicknameContaining(String nickname, Pageable pageable) {
        Page<WorldCupBoard> page = worldCupBoardRepository.findAllByNicknameContaining(nickname, pageable);

        return page;
    }

    //    questioner(질문자) like 검색 함수 ( 페이징 처리 )
    public Page<WorldCupBoard> findAllByTitleContaining(String title, Pageable pageable) {
        Page<WorldCupBoard> page = worldCupBoardRepository.findAllByTitleContaining(title, pageable);

        return page;
    }
    public Page<WorldCupBoard> findAllByContentContaining(String content, Pageable pageable) {
        Page<WorldCupBoard> page = worldCupBoardRepository.findAllByContentContaining(content, pageable);

        return page;
    }

    public Page<WorldCupBoard> findidContain(Integer id, Pageable pageable) {
        Page<WorldCupBoard> page = worldCupBoardRepository.findidContain(id, pageable);

        return page;
    }
}









