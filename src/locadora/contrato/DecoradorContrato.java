package locadora.contrato;

/**
 * Decorador abstrato do padrão Decorator.
 *
 * Mantém uma referência ao {@link Contrato} que está sendo "decorado" e
 * repassa as chamadas para ele. As subclasses acrescentam o próprio extra.
 */
public abstract class DecoradorContrato implements Contrato {

    protected final Contrato contrato;

    public DecoradorContrato(Contrato contrato) {
        this.contrato = contrato;
    }
}
