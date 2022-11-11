package com.example.simpledms.service;

import com.example.simpledms.model.Dept;
import com.example.simpledms.repository.DeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.modelexam.service
 * fileName : DeptService
 * author : kangtaegyung
 * date : 2022/10/12
 * description : 부서 업무 서비스 클래스
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/10/12         kangtaegyung          최초 생성
 */
// springboot 프레임워크에 객체를 생성함 : 싱글톤 유형
@Service
public class DeptService {

    @Autowired
    DeptRepository deptRepostory; // 샘플데이터 DB에 접근하는 객체

    public Page<Dept> findAll(Pageable pageable) {
        Page<Dept> page = deptRepostory.findAll(pageable);

        return page;
    }

    public Optional<Dept> findById(int dno) {
        Optional<Dept> optionalDept = deptRepostory.findById(dno);

        return optionalDept;
    }

    public Dept save(Dept dept) {

        Dept dept2 = deptRepostory.save(dept);

        return dept2;
    }

    public boolean removeById(int id) {

        if (deptRepostory.existsById(id)) {
            deptRepostory.deleteById(id);
            return true;
        }
        return false;
    }

    public void removeAll() {

        deptRepostory.deleteAll();
    }

    //    dname like 검색
    public Page<Dept> findAllByDnameContaining(String dname, Pageable pageable) {

        Page<Dept> page = deptRepostory.findAllByDnameContaining(dname, pageable);

        return page;
    }

}






















