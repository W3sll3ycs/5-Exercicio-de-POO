import model.Smartphone;
import model.AparelhoTelefonico;
import model.NavegadorInternet;
import model.ReprodutorMusical;

void main(String[] args) {
    Smartphone smartphone = new Smartphone();

    System.out.println("=== Telefone ===");
    smartphone.ligar("(83) 99999-0000");
    smartphone.atender();

    System.out.println("\n=== Navegador ===");
    smartphone.exibirPagina("https://www.ifpb.edu.br/campus/cajazeiras");
    smartphone.adicionarNovaAba();
    smartphone.exibirPagina("https://www.google.com");

    System.out.println("\n=== Reprodutor Musical ===");
    smartphone.tocar("Música Exemplo");
    smartphone.pausar();
    smartphone.pausar();

    System.out.println("\n=== Uso via interfaces ===");
    AparelhoTelefonico telefone = smartphone;
    telefone.ligar("190");
    ReprodutorMusical player = smartphone;
    player.tocar("Outra Música");
}
