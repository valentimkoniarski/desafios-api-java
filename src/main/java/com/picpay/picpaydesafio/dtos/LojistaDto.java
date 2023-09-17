package com.picpay.picpaydesafio.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LojistaDto {
    private BigDecimal saldo;

    public LojistaDto(BigDecimal saldo) {
        this.saldo = saldo;
    }

}
