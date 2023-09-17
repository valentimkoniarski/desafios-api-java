package com.picpay.picpaydesafio.repositories;

import com.picpay.picpaydesafio.entities.Lojista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LojistaRepository extends JpaRepository<Lojista, Long> {

    @Query(value = "SELECT CASE WHEN EXISTS (SELECT * FROM lojistas WHERE usuario_id = ?1) THEN TRUE ELSE FALSE END", nativeQuery = true)
    boolean findLojistaByUsuarioId(Long usuarioId);

}
