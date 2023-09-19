package com.picpay.picpaydesafio.repositories;

import com.picpay.picpaydesafio.entities.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {




}
