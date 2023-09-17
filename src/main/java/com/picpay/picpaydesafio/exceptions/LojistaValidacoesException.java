package com.picpay.picpaydesafio.exceptions;

public class LojistaValidacoesException extends RuntimeException {

    public LojistaValidacoesException(String message) {
        super(message);
    }

    public static class SaldoInicialInvalidoException extends LojistaValidacoesException {
        public SaldoInicialInvalidoException() {
            super("O saldo inicial do lojista deve ser zero");
        }
    }

    public static class LojistaExistenteException extends LojistaValidacoesException {
        public LojistaExistenteException() {
            super("Já existe um lojista para o usuário informado");
        }
    }

}

