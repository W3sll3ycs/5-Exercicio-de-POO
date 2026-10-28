import java.util.List;
import model.RelatorioFinanceiro;
import model.Contrato;
import model.EnviavelPorEmail;
import model.Imprimivel;

void main(String[] args) {
    RelatorioFinanceiro relatorioQ1 = new RelatorioFinanceiro("1º trimestre", 15000.50);
    RelatorioFinanceiro relatorioQ2 = new RelatorioFinanceiro("2º trimestre", 18250.00);
    Contrato contratoAna = new Contrato("Ana Souza");
    Contrato contratoLoja = new Contrato("Loja Central");

    List<Imprimivel> paraImprimir = List.of(relatorioQ1, contratoAna, relatorioQ2, contratoLoja);

    List<EnviavelPorEmail> paraEnviar = List.of(relatorioQ1, relatorioQ2);

    System.out.println("=== Impressão ===");
    paraImprimir.forEach(Imprimivel::imprimir);

    System.out.println();
    System.out.println("=== Envio por e-mail ===");
    paraEnviar.forEach(doc -> doc.enviar("financeiro@empresa.com"));
}

