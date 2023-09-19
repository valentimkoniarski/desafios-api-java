package com.picpay.picpaydesafio.services;

import com.picpay.picpaydesafio.auth.TokenService;
import com.picpay.picpaydesafio.auth.TokenUtils;
import com.picpay.picpaydesafio.dtos.UsuarioDto;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final ModelMapper modelMapper;

    private final TokenService tokenService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Usuario não encontrado com email %s", email)));
    }

    public void registrarUsuario(UsuarioDto usuarioDto) {
        criptografarSenha(usuarioDto);
        final Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);
        usuarioRepository.save(usuario);
    }

    private void criptografarSenha(UsuarioDto usuarioDto) {
        usuarioDto.setSenha(bCryptPasswordEncoder.encode(usuarioDto.getSenha()));
    }

    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Usuario não encontrado com id %s", id)));
    }

    public Usuario getUserByToken(HttpServletRequest request) {
        Long idUser = getIdUserByToken(request);
        Usuario usuario = usuarioRepository.findById(idUser).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Usuario não encontrado com id %s", idUser)));
        return usuario;
    }

    public Long getIdUserByToken(HttpServletRequest request) {
        String token = TokenUtils.getToken(request);
        return tokenService.getIdUser(token);
    }

}
