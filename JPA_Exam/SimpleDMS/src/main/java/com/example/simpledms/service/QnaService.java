package com.example.simpledms.service;

import com.example.simpledms.model.Qna;
import com.example.simpledms.repository.QnaRepository;
import com.example.simpledms.repository.QnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.modelexam.service
 * fileName : DeptService
 * author : kangtaegyung
 * date : 2022/10/12
 * description : Qna 서비스 클래스
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2022/10/12         kangtaegyung          최초 생성
 */
// springboot 프레임워크에 객체를 생성함 : 싱글톤 유형
@Service
public class QnaService {

    @Autowired
    QnaRepository qnaRepository; // 샘플데이터 DB에 접근하는 객체

    public Page<Qna> findAll(Pageable pageable) {
        Page<Qna> page = qnaRepository.findAll(pageable);

        return page;
    }

    public Optional<Qna> findById(int dno) {
        Optional<Qna> optionalQna = qnaRepository.findById(dno);

        return optionalQna;
    }

    public Qna save(Qna qna) {

        Qna qna2 = qnaRepository.save(qna);

        return qna2;
    }

    public boolean removeById(int qno) {

        if (qnaRepository.existsById(qno)) {
            qnaRepository.deleteById(qno);
            return true;
        }
        return false;
    }

    public void removeAll() {

        qnaRepository.deleteAll();
    }

    //    question like 검색
    public Page<Qna> findAllByQuestionContaining(String question, Pageable pageable) {

        Page<Qna> qna = qnaRepository.findAllByQuestionContaining(question, pageable);

        return qna;
    }

    //    question like 검색
    public Page<Qna> findAllByQuestionerContaining(String question, Pageable pageable) {

        Page<Qna> qna = qnaRepository.findAllByQuestionerContaining(question, pageable);

        return qna;
    }

}






















