package com.picpay.picpaydesafio.configs;

import com.picpay.picpaydesafio.auth.TokenService;
import com.picpay.picpaydesafio.interceptors.UsuarioInterceptor;
import com.picpay.picpaydesafio.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AppConfig(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UsuarioInterceptor(tokenService, usuarioRepository));
    }
}

