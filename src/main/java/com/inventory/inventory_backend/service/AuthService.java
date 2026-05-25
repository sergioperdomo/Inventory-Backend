package com.inventory.inventory_backend.service;


import com.inventory.inventory_backend.dto.LoginRequest;
import com.inventory.inventory_backend.dto.LoginResponse;
import com.inventory.inventory_backend.entity.User;
import com.inventory.inventory_backend.exception.BusinessException;
import com.inventory.inventory_backend.repository.UserRepository;
import com.inventory.inventory_backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException("Credenciales inválidas");
        }

        if (!user.getActive()) {
            throw new BusinessException("Usuario inactivo");
        }

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new LoginResponse(token, user.getUsername(), user.getRole().name());
    }
}
