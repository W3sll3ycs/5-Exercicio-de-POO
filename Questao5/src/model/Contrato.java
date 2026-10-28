package model;

public class Contrato implements Imprimivel {

    private final String cliente;

    public Contrato(String cliente) {
        this.cliente = cliente;
    }

    @Override
    public void imprimir() {
        System.out.printf("Imprimindo contrato do cliente %s%n", cliente);
    }
}