package locadora.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import locadora.fabrica.DadosJogo;
import locadora.fabrica.FabricaJogo;
import locadora.fabrica.FabricaJogoDigital;
import locadora.fabrica.FabricaJogoFisico;
import locadora.servico.Locadora;
import locadora.servico.LocadoraProxy;
import locadora.servico.ServicoLocadora;

/**
 * Janela principal (View).
 *
 * Organiza as três telas em abas e as registra como observadoras da
 * {@link Locadora}. A separação segue o padrão MVC:
 *  - Model:     classes em modelo/, fabrica/, estado/, contrato/, servico/
 *  - View:      os painéis (CatalogoPanel, AlugarPanel, AlugueisPanel)
 *  - Controller: os listeners de ação + o LocadoraProxy (intermediário)
 */
public class MainWindow extends JFrame {

    private final Locadora locadora = new Locadora();
    private final ServicoLocadora servico = new LocadoraProxy(locadora);

    public MainWindow() {
        setTitle("Locadora de Jogos - Padrões de Projeto");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(860, 560);
        setLocationRelativeTo(null);

        CatalogoPanel catalogoPanel = new CatalogoPanel(locadora);
        AlugarPanel alugarPanel = new AlugarPanel(locadora, servico);
        AlugueisPanel alugueisPanel = new AlugueisPanel(locadora);

        // Observer: as views observam a Locadora (Subject)
        locadora.adicionarObservador(catalogoPanel);
        locadora.adicionarObservador(alugarPanel);
        locadora.adicionarObservador(alugueisPanel);

        popularDadosIniciais();

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Catálogo", catalogoPanel);
        abas.addTab("Alugar", alugarPanel);
        abas.addTab("Aluguéis", alugueisPanel);

        JLabel cabecalho = new JLabel(
                "Factory Method · State · Decorator · Proxy · Observer (MVC)",
                SwingConstants.CENTER);
        cabecalho.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(cabecalho, BorderLayout.NORTH);
        add(abas, BorderLayout.CENTER);
    }

    private void popularDadosIniciais() {
        FabricaJogo fisica = new FabricaJogoFisico();
        FabricaJogo digital = new FabricaJogoDigital();

        locadora.adicionarJogo(fisica.criarJogo(new DadosJogo("The Legend of Zelda", "Aventura", 12.0, 10, 3)));
        locadora.adicionarJogo(fisica.criarJogo(new DadosJogo("FIFA 26", "Esporte", 10.0, 0, 5)));
        locadora.adicionarJogo(digital.criarJogo(new DadosJogo("GTA VI", "Ação", 15.0, 18, 0)));
        locadora.adicionarJogo(digital.criarJogo(new DadosJogo("Minecraft", "Sandbox", 6.0, 10, 0)));
    }
}
