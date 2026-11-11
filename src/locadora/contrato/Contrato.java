package locadora.contrato;

/**
 * Interface do padrão Decorator.
 *
 * Define o contrato de um aluguel: seu preço total e sua descrição.
 * Tanto o aluguel básico quanto os extras ("decoradores") implementam
 * esta mesma interface, permitindo "embrulhar" um no outro.
 */
public interface Contrato {

    double calcularPreco();

    String getDescricao();
}
