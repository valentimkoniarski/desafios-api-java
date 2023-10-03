package com.picpay.picpaydesafio.controllers;

import com.picpay.picpaydesafio.auth.TokenDto;
import com.picpay.picpaydesafio.auth.TokenService;
import com.picpay.picpaydesafio.dtos.LoginDto;
import com.picpay.picpaydesafio.dtos.UsuarioDto;
import com.picpay.picpaydesafio.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/usuarios")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;
    private final AuthenticationManager authManager;

    @PostMapping("/autenticar")
    public ResponseEntity<TokenDto> authentication(@RequestBody @Valid final LoginDto loginDto) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken loginData = loginDto.convert();
        final Authentication authentication = authManager.authenticate(loginData);
        final String token = tokenService.createToken(authentication);
        return ResponseEntity.ok(new TokenDto(token, "Bearer"));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrarUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
        usuarioService.registrarUsuario(usuarioDto);
        return ResponseEntity.ok().build();
    }



}
