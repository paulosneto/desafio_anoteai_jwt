package com.anoteai.pedidos.anoteai.controllers;

import com.anoteai.pedidos.anoteai.config.infra.TokenService;
import com.anoteai.pedidos.anoteai.domain.Users;
import com.anoteai.pedidos.anoteai.dto.AuthenticationDTO;
import com.anoteai.pedidos.anoteai.dto.LoginResponseDTO;
import com.anoteai.pedidos.anoteai.dto.RegisterDTO;
import com.anoteai.pedidos.anoteai.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    // Injeções de dependências para cada classe abaixo
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private TokenService tokenService;

    // Requisição para
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO){
        var userNamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());

        // Faz a comparação encode com a senha passada
        var auth = this.authenticationManager.authenticate(userNamePassword);

        // pega o token atraves do objeto principal
        var token = tokenService.generateToken((Users) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO registerDTO){
        if(this.usersRepository.findByLogin(registerDTO.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        Users readUser = new Users(registerDTO.login(), encryptedPassword, registerDTO.role());

        this.usersRepository.save(readUser);

        return  ResponseEntity.ok().build();
    }

}

