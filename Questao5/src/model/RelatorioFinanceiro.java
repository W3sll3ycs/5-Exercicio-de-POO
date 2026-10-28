package model;

public class RelatorioFinanceiro implements Imprimivel, EnviavelPorEmail {

    private final String periodo;
    private final double saldo;

    public RelatorioFinanceiro(String periodo, double saldo) {
        this.periodo = periodo;
        this.saldo = saldo;
    }

    @Override
    public void imprimir() {
        System.out.printf("Imprimindo relatório financeiro de %s (saldo: R$ %.2f)%n", periodo, saldo);
    }

    @Override
    public void enviar(String email) {
        System.out.printf("Enviando relatório financeiro de %s para %s%n", periodo, email);
    }
}
