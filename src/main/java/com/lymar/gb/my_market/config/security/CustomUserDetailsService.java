package com.lymar.gb.my_market.config.security;

import com.lymar.gb.my_market.entity.Users;
import com.lymar.gb.my_market.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> users = userRepository.findByLogin(username);
        if(users.isPresent()) {
            return new CustomPrincipal(users.get());
        }
        throw new UsernameNotFoundException("Пользователь не найден");
    }
}
