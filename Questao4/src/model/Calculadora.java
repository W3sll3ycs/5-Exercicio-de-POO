package model;

public class Calculadora {
    public static final OperacaoMatematica SOMA = (a, b) -> a + b;

    public static final OperacaoMatematica SUBTRACAO = (a, b) -> a - b;

    public static final OperacaoMatematica MULTIPLICACAO = (a, b) -> a * b;

    public static final OperacaoMatematica DIVISAO = (a, b) -> {
        if (b == 0) {
            throw new ArithmeticException("Divisão por zero não é permitida.");
        }
        return a / b;
    };

    private Calculadora() {
    }
}
