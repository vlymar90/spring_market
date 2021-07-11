package com.lymar.gb.my_market.repository;

import com.lymar.gb.my_market.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByLogin(String username);
    List<Users> findAllByPhone(String phone);
}
