package com.example.simpledms.repository;


import com.example.simpledms.model.TeamKorea;
import com.example.simpledms.model.WorldCupBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.jpaexam.repository
 * fileName : DeptRepository
 * author : ds
 * date : 2022-10-20
 * description : JPA CRUD를 위한 인터페이스(==DAO)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */

@Repository
public interface WorldCupBoardRepository extends JpaRepository<WorldCupBoard, Integer> {
//    question 조회하는 like 검색 함수
//    1) 쿼리메소드 방식으로 함수 정의
    Page<WorldCupBoard> findAllByNicknameContaining(String nickname, Pageable pageable);
    Page<WorldCupBoard> findAllByTitleContaining(String title, Pageable pageable);
    Page<WorldCupBoard> findAllByContentContaining(String content, Pageable pageable);

    @Query(value = "select w.*" +
            "from tb_worldcupboard w " +
            "where id like %:id%",
            nativeQuery = true)
    Page<WorldCupBoard> findidContain(@Param("id") Integer id, Pageable pageable);
}









