package com.infoa.educationms.service;

import com.infoa.educationms.entities.UserDetailsImpl;
import com.infoa.educationms.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accountNumber) throws UsernameNotFoundException {
        com.infoa.educationms.entities.User user = userRepository
                .findByAccountNumber(accountNumber) // 👈 注意是 accountNumber
                .orElseThrow(() -> new UsernameNotFoundException("账号不存在: " + accountNumber));

        return new UserDetailsImpl(user);
    }
}

