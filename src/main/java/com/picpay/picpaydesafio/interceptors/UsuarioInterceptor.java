package com.picpay.picpaydesafio.interceptors;

import com.picpay.picpaydesafio.auth.TokenService;
import com.picpay.picpaydesafio.auth.TokenUtils;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class UsuarioInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioInterceptor(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Usuario usuario = getUserByToken(request);
        HttpSession session = request.getSession();
        session.setAttribute("usuarioLogado", usuario);
        return true;
    }

    public Usuario getUserByToken(HttpServletRequest request) {
        Long idUser = getIdUserByToken(request);
        return usuarioRepository.findById(idUser).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Usuario não encontrado com id %s", idUser)));
    }

    public Long getIdUserByToken(HttpServletRequest request) {
        String token = TokenUtils.getToken(request);
        return tokenService.getIdUser(token);
    }
}
