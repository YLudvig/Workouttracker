package com.workouttracker.workouttracker.Util;
import com.workouttracker.workouttracker.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.workouttracker.workouttracker.DTOs.AuthRequest;
import com.workouttracker.workouttracker.DTOs.AuthResponse;
import com.workouttracker.workouttracker.DTOs.UserDTO;
import com.workouttracker.workouttracker.model.User;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest request){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtTokenProvider.generateToken(authentication.getName());
        
        return new AuthResponse(token);
    }

    public Optional<UserDTO> register(String username, String password, String email) {

        if (userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already in use!");
        } else if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("username already in use");
        }
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(encodedPassword);
        newUser.setRole("User");
        userRepository.save(newUser);

        UserDTO userDTO = new UserDTO(newUser.getUsername());

        System.out.println("Användare lyckades skapas");
        return Optional.of(userDTO);
    }

}
