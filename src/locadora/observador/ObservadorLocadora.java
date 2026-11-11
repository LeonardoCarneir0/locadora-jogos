package locadora.observador;

/**
 * Interface do padrão Observer.
 *
 * As telas (Views) implementam esta interface e se registram na
 * {@link locadora.servico.Locadora} (Subject). Quando o catálogo ou a lista
 * de aluguéis muda, a Locadora chama {@link #atualizar()} em cada observador,
 * e cada tela se redesenha automaticamente.
 */
public interface ObservadorLocadora {

    void atualizar();
}
