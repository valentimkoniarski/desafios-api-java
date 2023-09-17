package com.picpay.picpaydesafio.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClienteDto {
    private BigDecimal saldo;

    public ClienteDto(BigDecimal saldo) {
        this.saldo = saldo;
    }

}
