package com.picpay.picpaydesafio.services;

import com.picpay.picpaydesafio.entities.Cliente;
import com.picpay.picpaydesafio.entities.Usuario;
import com.picpay.picpaydesafio.repositories.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    private ClienteService clienteService;

    @MockBean
    private HttpServletRequest request = new MockHttpServletRequest();

    @Mock
    private HttpSession session = new MockHttpSession();

    @Spy
    private ModelMapper modelMapper;

    private final static Long USER_ID = 1L;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Usuario usuario = new Usuario();
        usuario.setId(USER_ID);

        HttpSession session = request.getSession();
        session.setAttribute("usuarioLogado", usuario);

        clienteService = new ClienteService(modelMapper, clienteRepository, request);
    }

    @Test
    void deveSalvarCliente() {
        when(clienteRepository.findClienteByUsuarioId(USER_ID)).thenReturn(false);
        clienteService.salvar();
        verify(clienteRepository, atLeastOnce()).save(any(Cliente.class));
    }

}
