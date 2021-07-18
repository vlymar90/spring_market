package com.lymar.gb.my_market.service;

import com.lymar.gb.my_market.entity.Users;
import com.lymar.gb.my_market.repository.UserRepository;
import com.vaadin.flow.router.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public Users findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с идентификатором %s не найден", id)));
    }

    public Users saveUser(String phone, String login, String password, String email, String name, String secondName, String lastName) {
        Users user = new Users();
        user.setPhone(phone);
        user.setLogin(login);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        user.setName(name);
        user.setSecond_name(secondName);
        user.setLast_name(lastName);
        user.setRole("USER");
        return userRepository.save(user);
    }

    public List<Users> findUsers(String phone) {
        if(phone == "") {
            return userRepository.findAll();
        }
        return userRepository.findAllByPhone(phone);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(Users users) {
        userRepository.save(users);
    }
}
