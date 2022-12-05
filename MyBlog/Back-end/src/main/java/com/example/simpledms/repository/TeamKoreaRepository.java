package com.example.simpledms.repository;



import com.example.simpledms.model.TeamKorea;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * packageName : com.example.jpaexam.repository
 * fileName : FileDbRepository
 * author : ds
 * date : 2022-10-20
 * description : JPA CRUD를 위한 인터페이스(==DAO)
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-10-20         ds          최초 생성
 */
@Repository
public interface TeamKoreaRepository extends JpaRepository<TeamKorea, Integer> {

    Page<TeamKorea> findAllByKoreanNameContaining(String  koreanName, Pageable pageable);

    Page<TeamKorea> findAllByPositionContaining(String position, Pageable pageable);
    Page<TeamKorea> findAllByEnglishNameContaining(String englishName, Pageable pageable);
    Page<TeamKorea> findAllByBirthContaining(String birth, Pageable pageable);
    Page<TeamKorea> findAllByAffiliatedTeamContaining(String AffiliatedTeam, Pageable pageable);

    @Query(value = "select t.*" +
            "from tb_teamkorea t " +
            "where pid like %:pid%",
            nativeQuery = true)
    Page<TeamKorea> findPidContain(@Param("pid") Integer pid, Pageable pageable);

}









