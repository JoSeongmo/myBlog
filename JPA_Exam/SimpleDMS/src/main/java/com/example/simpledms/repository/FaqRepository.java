package com.example.simpledms.repository;

import com.example.simpledms.model.Faq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName : com.example.jpaexam.repository
 * fileName : DeptRepostory
 * author : kangtaegyung
 * date : 2022/10/16
 * description : JPA CRUD 인터페이스 ( DAO 역할 )
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/10/16         kangtaegyung          최초 생성
 */
@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer> {

//    title like 검색
    Page<Faq> findAllByTitleContaining(String title, Pageable pageable);
}
