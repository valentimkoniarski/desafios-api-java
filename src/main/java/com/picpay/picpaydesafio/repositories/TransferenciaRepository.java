package com.picpay.picpaydesafio.repositories;

import com.picpay.picpaydesafio.entities.Transferencia;
import com.picpay.picpaydesafio.validacoes.transferencia.SaldoAtualizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
@Transactional
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    @Modifying
    @Query(value = "UPDATE clientes SET saldo =+ saldo + ?1 WHERE usuario_id = ?2", nativeQuery = true)
    void updateSaldoClienteRecebedor(BigDecimal saldo, Long usuarioId);

    @Modifying
    @Query(value = "UPDATE lojistas SET saldo =+ saldo + ?1 WHERE usuario_id =?2", nativeQuery = true)
    void updateSaldoLojistaRecebedor(BigDecimal saldo, Long usuarioId);

    @Modifying
    @Query(value = "UPDATE clientes SET saldo =+ saldo - ?1 WHERE usuario_id = ?2", nativeQuery = true)
    void updateSaldoClienteTransferidor(BigDecimal saldo, Long usuarioId);

}
