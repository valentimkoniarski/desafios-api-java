package com.picpay.picpaydesafio.repositories;

import com.picpay.picpaydesafio.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM clientes WHERE usuario_id = ?1) THEN TRUE ELSE FALSE END", nativeQuery = true)
    boolean findClienteByUsuarioId(Long usuarioId);
}
