package com.picpay.picpaydesafio.exceptions;

public class TransferenciaValidacoesException extends RuntimeException {

    public TransferenciaValidacoesException(String message) {
        super(message);
    }

    public static class SaldoNaoPodeSerNegativoException extends TransferenciaValidacoesException {
        public SaldoNaoPodeSerNegativoException() {
            super("Saldo não pode ser negativo");
        }
    }

    public static class LojistaNaoPodeTransferirException extends TransferenciaValidacoesException {
        public LojistaNaoPodeTransferirException() {
            super("Lojista não pode transferir");
        }
    }


}
