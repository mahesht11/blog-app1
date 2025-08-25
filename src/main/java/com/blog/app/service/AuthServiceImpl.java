package com.blog.app.service;

import com.blog.app.dto.LoginDto;
import com.blog.app.dto.RegisterDto;
import com.blog.app.entity.Role;
import com.blog.app.entity.User;
import com.blog.app.exceptions.BlogApiException;
import com.blog.app.repository.RoleRepository;
import com.blog.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl {


    private AuthenticationManager manager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthenticationManager manager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String login(LoginDto loginDto){
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "User login successfully!";
    }

    public String register(RegisterDto registerDto){
        if(userRepository.existsByUsername(registerDto.username())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "User name is already exist!");
        }

        if(userRepository.existsByEmail(registerDto.email())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already exist!");
        }

        User user = new User();
        user.setName(registerDto.name());
        user.setUsername(registerDto.username());
        user.setEmail(registerDto.email());
        user.setPassword(registerDto.password());

        Set<Role> role = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        role.add(userRole);
        user.setRoles(role);

        userRepository.save(user);

        return "USer registered successfully!";
    }

}
