package com.picpay.picpaydesafio.repositories;

import com.picpay.picpaydesafio.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
@Transactional
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM clientes WHERE usuario_id = ?1) THEN TRUE ELSE FALSE END", nativeQuery = true)
    boolean findClienteByUsuarioId(Long usuarioId);

    @Modifying
    @Query(value = "UPDATE clientes SET saldo =+ saldo + ?1 WHERE usuario_id = ?2", nativeQuery = true)
    void updateSaldoClienteRecebedor(BigDecimal saldo, Long usuarioId);

    @Modifying
    @Query(value = "UPDATE clientes SET saldo =+ saldo - ?1 WHERE usuario_id = ?2", nativeQuery = true)
    void updateSaldoClienteTransferidor(BigDecimal saldo, Long usuarioId);

}
