package com.example.simpledms.service;

import com.example.simpledms.dto.response.UserRoleDto;
import com.example.simpledms.model.User;
import com.example.simpledms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName : com.example.simpledms.service
 * fileName : UserService
 * author : ds
 * date : 2022-11-30
 * description :
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2022-11-30         ds          최초 생성
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository; // JPA CRUD 함수가 있는 인터페이스

    //    전체 조회 함수 findAll() ->(변경) findAll(Pageable pageable)
    public Page<User> findAll(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);

        return page;
    }

    //    전체 삭제 함수
    public void removeAll() {
        userRepository.deleteAll(); // 전체 삭제
    }

    //   부서 정보 저장/수정 함수
    public User save(User user) {

        User user2 = userRepository.save(user);

        return user2;
    }

    //    부서번호로 조회하는 함수
    public Optional<User> findById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        return optionalUser;
    }

    // 부서번호(dno)로 삭제하는 함수
    public boolean removeById(long id) {
        if(userRepository.existsById(id) == true) {
            userRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    //    usename like 검색 함수( 페이징 처리 추가 )
    public Page<UserRoleDto> findAllByUsernameContaining(String usename, Pageable pageable) {
        Page<UserRoleDto> page = userRepository.findAllByUsernameContaining(usename, pageable);

        return page;
    }
}









