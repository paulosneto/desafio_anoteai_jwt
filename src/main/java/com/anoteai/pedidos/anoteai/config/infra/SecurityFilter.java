package com.anoteai.pedidos.anoteai.config.infra;

import com.anoteai.pedidos.anoteai.repositories.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    // Injeção de dependencia do TokenService
    @Autowired
    TokenService tokenService;

    // Injeção de dependencia do UsersRepository
    @Autowired
    UsersRepository usersRepository;

    // Filtro interno para poder tratar o token recebido na requisição
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Recupera o token enviado
        var token = this.recoveryToken(request);

        // Se o token for diferente de null faz a validação para devolver a requisição apenas o token válido
        if(token != null){
            // Passa o token informado para ser validado pala classe "TokenService"
            var login = tokenService.validateToken(token);
            UserDetails user = usersRepository.findByLogin(login);

            // Salva o contexto do usuario o token de autenticacao
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }

        // Faz a chamada do proximo filtro
        filterChain.doFilter(request, response);
    }

    // Trata o token enviado na requisição apagando o
    // inicio padrão deixando somento o token válido
    private String recoveryToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
