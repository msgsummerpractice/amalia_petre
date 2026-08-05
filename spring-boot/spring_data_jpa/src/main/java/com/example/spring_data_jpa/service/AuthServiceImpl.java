package com.example.spring_data_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ott.OneTimeTokenAuthenticationToken;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.ott.GenerateOneTimeTokenRequest;
import org.springframework.security.authentication.ott.OneTimeTokenService;
import org.springframework.security.authentication.ott.OneTimeToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.spring_data_jpa.configuration.JwtTokenProvider;
import com.example.spring_data_jpa.model.OttVerifyRequest;
import com.example.spring_data_jpa.model.Role;
import com.example.spring_data_jpa.model.SignInRequest;
import com.example.spring_data_jpa.model.SignUpRequest;
import com.example.spring_data_jpa.service.AuthService;
import com.example.spring_data_jpa.model.SignInResponse;
import com.example.spring_data_jpa.model.User;
import com.example.spring_data_jpa.repository.RoleRepository;
import com.example.spring_data_jpa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    private OneTimeTokenService oneTimeTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public SignInResponse login(SignInRequest loginDto) {

        final var unauthenticatedToken = UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getUsername(),
                loginDto.getPassword());

        authenticationManager.authenticate(unauthenticatedToken);

        OneTimeToken ott = oneTimeTokenService.generate(new GenerateOneTimeTokenRequest(loginDto.getUsername()));

        System.out.println("==========================================");
        System.out.println("GENERATED OTT PIN: " + ott.getTokenValue());
        System.out.println("==========================================");

        SignInResponse response = new SignInResponse();
        response.setMfaRequired(true);
        response.setAccessToken(null);
        return response;
    }

    @Override
    public SignInResponse verifyOtt(OttVerifyRequest verifyDto) {
        System.out.println("Verifying OTT for user: " + verifyDto.getUsername() + " with token: " + verifyDto.getToken());
        OneTimeTokenAuthenticationToken ottAuthenticationToken = new OneTimeTokenAuthenticationToken(verifyDto.getToken());

        OneTimeToken consumed = oneTimeTokenService.consume(ottAuthenticationToken);

        if (consumed == null || !consumed.getUsername().equals(verifyDto.getUsername())) {
            throw new RuntimeException("Invalid or expired one-time token.(PIN)");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(verifyDto.getUsername());

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(userDetails, userDetails.getAuthorities(), null);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();

        SignInResponse response = new SignInResponse();
        response.setMfaRequired(false);
        response.setAccessToken(jwtTokenProvider.generateToken(authentication));
        response.setTokenType("Bearer");
        response.setExpiresInSeconds(jwtTokenProvider.getExpiresInSeconds());
        response.setRoles(roles);

        return response;
    }

    @Override
    public String register(SignUpRequest registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }
        Role userRole = roleRepository.findByName("ROLE_USER");

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
