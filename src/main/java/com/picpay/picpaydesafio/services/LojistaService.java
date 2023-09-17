package com.picpay.picpaydesafio.services;

import com.picpay.picpaydesafio.dtos.LojistaDto;
import com.picpay.picpaydesafio.entities.Lojista;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.LojistaRepository;
import com.picpay.picpaydesafio.validacoes.lojista.SaldoInicialZero;
import com.picpay.picpaydesafio.validacoes.lojista.SomenteUmLojistaPorUsuario;
import com.picpay.picpaydesafio.validacoes.lojista.ValidacaoCriacaoDoLojista;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class LojistaService {

    private final LojistaRepository lojistaRepository;

    private final UsuarioService usuarioService;

    private final ModelMapper modelMapper;

    private final List<ValidacaoCriacaoDoLojista> validacoes;

    public LojistaService(LojistaRepository lojistaRepository,
                          UsuarioService usuarioService,
                          ModelMapper modelMapper,
                          List<ValidacaoCriacaoDoLojista> validacoes) {
        this.lojistaRepository = lojistaRepository;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
        this.validacoes = validacoes;
    }

    public void salvar(HttpServletRequest request) {
        final LojistaDto lojistaDto = new LojistaDto(BigDecimal.ZERO);
        final Lojista lojista = modelMapper.map(lojistaDto, Lojista.class);
        setterUsuario(request, lojista);
        lojistaRepository.save(lojista);
    }

    private void setterUsuario(HttpServletRequest request, Lojista lojista) {
        Usuario usuario = usuarioService.getUserByToken(request);
        lojista.setUsuario(usuario);
    }

    private List<ValidacaoCriacaoDoLojista> setValidacoesDeCricao(List<ValidacaoCriacaoDoLojista> validacoes) {
        validacoes.add(new SaldoInicialZero());
        validacoes.add(new SomenteUmLojistaPorUsuario(lojistaRepository));
        return validacoes;
    }

    private void validacoes(Lojista lojista) {
        validacoes.forEach(validacao -> validacao.validacao(lojista));
    }

}
