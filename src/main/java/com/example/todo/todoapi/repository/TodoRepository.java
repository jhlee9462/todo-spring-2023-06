package com.example.todo.todoapi.repository;

import com.example.todo.todoapi.entity.Todo;
import com.example.todo.userapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, String> {

    // 특정 회원의 할 일 목록 리턴
    @Query("SELECT t FROM Todo t WHERE t.user = :user")
    List<Todo> findAllByUser(User user);

    // 회원이 작성한 일정의 개수를 리턴
    @Query("select count(*) from Todo t where t.user = :user")
    int countByUser(User user);

}
