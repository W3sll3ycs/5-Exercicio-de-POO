import model.Calculadora;

void main() {
    double a = 10;
    double b = 4;

    System.out.println("a = " + a + " | b = " + b);
    System.out.println("Soma: " + Calculadora.SOMA.executar(a, b));
    System.out.println("Subtração: " + Calculadora.SUBTRACAO.executar(a, b));
    System.out.println("Multiplicação: " + Calculadora.MULTIPLICACAO.executar(a, b));
    System.out.println("Divisão: " + Calculadora.DIVISAO.executar(a, b));

    try {
        Calculadora.DIVISAO.executar(a, 0);
    } catch (ArithmeticException e) {
        System.out.println("Erro: " + e.getMessage());
    }

}