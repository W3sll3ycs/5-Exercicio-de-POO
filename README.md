# Interfaces em Java

## 1. Métodos com corpo em interfaces

### A partir de qual versão?

Desde o **Java 8** (2014), as interfaces podem ter métodos `default` e `static` com corpo. No **Java 9** vieram também os métodos `private` (e `private static`), usados para reaproveitar código entre os métodos default e static da própria interface.

### Utilidade dos métodos `default`

Um método `default` traz uma **implementação padrão** que as classes herdam automaticamente, mas podem sobrescrever se quiserem.

**Evolução de interfaces sem quebrar código existente.** Antes do Java 8, adicionar um método abstrato a uma interface obrigava todas as classes que a implementavam a mudar. Com `default`, a interface ganha métodos novos e as implementações antigas continuam compilando. Foi assim que o Java adicionou `forEach` em `Iterable` e `stream()` em `Collection` sem quebrar milhões de classes.

```java
public interface Imprimivel {
    void imprimir();

    default void imprimirVezes(int n) {
        for (int i = 0; i < n; i++) {
            imprimir();
        }
    }
}
```

**Reuso de código comum.** O comportamento compartilhado fica na interface, sem exigir uma classe base abstrata. Como uma classe só estende uma superclasse, mas implementa várias interfaces, isso dá uma forma limitada de "herança múltipla de comportamento".

> Se duas interfaces tiverem o mesmo método `default`, a classe que implementa as duas é obrigada a sobrescrevê-lo, para resolver o conflito (pode chamar `InterfaceA.super.metodo()`).

### Utilidade dos métodos `static`

Um método `static` pertence à **interface em si**, não às instâncias, e não é herdado pelas classes que a implementam. Costuma servir para:

- **Métodos utilitários** relacionados ao tipo, que antes ficavam em classes auxiliares como `Collections` ou `Arrays`.
- **Fábricas (factory methods)**, como `List.of(...)` e `Comparator.naturalOrder()`.

```java
public interface OperacaoMatematica {
    double executar(double a, double b);

    static OperacaoMatematica soma() {
        return (a, b) -> a + b;
    }
}

// uso: OperacaoMatematica.soma().executar(2, 3);
```

| Característica | `default` | `static` |
|---|---|---|
| Herdado pelas classes | Sim | Não |
| Pode ser sobrescrito | Sim | Não |
| Chamado por | Instância | `NomeDaInterface.metodo()` |
| Uso típico | Comportamento padrão, evolução de API | Utilitários e fábricas |

---

## 2. Interfaces, desacoplamento e manutenção

### Como as interfaces ajudam

Uma interface define um **contrato** (o que deve ser feito) sem dizer **como** é feito. Quem usa o contrato depende só dele, não da implementação. Isso traz várias vantagens:

- **Baixo acoplamento.** O código cliente não conhece a classe concreta, então mudar a implementação não exige mudar quem a usa.
- **Substituição fácil.** Dá para trocar uma implementação por outra (outro banco de dados, outro serviço de pagamento) sem mexer na lógica de negócio.
- **Testabilidade.** Nos testes, a implementação real é trocada por um *mock* ou *fake*, sem depender de banco, rede ou serviços externos.
- **Extensibilidade (princípio aberto/fechado).** Para adicionar um novo comportamento, cria-se uma nova classe que implementa a interface, sem alterar o código já existente.
- **Trabalho em paralelo.** Equipes combinam a interface primeiro e desenvolvem cada lado de forma independente.
- **Inversão de dependência (o "D" do SOLID).** Módulos de alto nível dependem de abstrações, não de detalhes.

### Exemplo prático: envio de notificações

Imagine um sistema de e-commerce que avisa o cliente quando o pedido é enviado.

**Com classe concreta (acoplado):**

```java
public class PedidoService {
    private EmailSender sender = new EmailSender(); // preso ao e-mail

    public void enviarPedido(Pedido p) {
        // ...lógica do pedido...
        sender.enviarEmail(p.getCliente().getEmail(), "Seu pedido saiu!");
    }
}
```

Se amanhã a empresa quiser avisar por **SMS** ou **WhatsApp**, será preciso alterar o `PedidoService`. Testar a classe também exige enviar e-mails de verdade, ou manipular o código para evitar isso.

**Com interface (desacoplado):**

```java
public interface Notificador {
    void notificar(Cliente cliente, String mensagem);
}

public class NotificadorEmail implements Notificador {
    @Override
    public void notificar(Cliente c, String msg) { /* envia e-mail */ }
}

public class NotificadorSms implements Notificador {
    @Override
    public void notificar(Cliente c, String msg) { /* envia SMS */ }
}

public class PedidoService {
    private final Notificador notificador;

    public PedidoService(Notificador notificador) { // injeção por construtor
        this.notificador = notificador;
    }

    public void enviarPedido(Pedido p) {
        // ...lógica do pedido...
        notificador.notificar(p.getCliente(), "Seu pedido saiu!");
    }
}
```

**O que melhorou:**

- Para trocar e-mail por SMS, basta passar outra implementação no construtor. O `PedidoService` não muda.
- Para adicionar WhatsApp, cria-se `NotificadorWhatsApp` e nada mais é alterado.
- Nos testes, usa-se um `NotificadorFalso` que só registra as chamadas, sem enviar nada.
- Dá para combinar canais com um `NotificadorComposto` que implementa `Notificador` e repassa a mensagem a uma lista de outros notificadores.

Esse padrão aparece em vários cenários reais: acesso a dados (`Repositorio` com implementações para MySQL, MongoDB ou memória), meios de pagamento (cartão, Pix, boleto) e geração de logs (arquivo, console, serviço remoto). Em todos eles, a regra de negócio fica estável enquanto os detalhes técnicos podem mudar.
