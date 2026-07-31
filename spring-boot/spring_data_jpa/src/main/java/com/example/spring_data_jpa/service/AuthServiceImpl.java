package com.example.spring_data_jpa.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.spring_data_jpa.configuration.JwtTokenProvider;
import com.example.spring_data_jpa.model.Role;
import com.example.spring_data_jpa.model.SignInRequest;
import com.example.spring_data_jpa.model.SignUpRequest;
import com.example.spring_data_jpa.service.AuthService;
import com.example.spring_data_jpa.model.SignInResponse;
import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.repository.RoleRepository;
import com.example.spring_data_jpa.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public SignInResponse login(SignInRequest loginDto) {

        // unauthenticated username and password token
        final var unauthenticatedToken = UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(), loginDto.getPassword());

        // authenticated token
        final var authentication = authenticationManager.authenticate(unauthenticatedToken);
        // extract username from the authenticated token

        (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User userDetails) {
            String username = userDetails.getUsername();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        SignInResponse authResponseDto = new SignInResponse();
        authResponseDto.setAccessToken(token);
        authResponseDto.setExpiresInSeconds(jwtTokenProvider.getExpiresInSeconds());
        return authResponseDto;
    }

    @Override
    public String register(SignUpRequest registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        Role userRole=roleRepository.findByName("ROLE_USER");
        

        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setRole(userRole); // Set the role to ROLE_USER
        userRepository.save(user);

        return "User registered successfully!";
    }
}
