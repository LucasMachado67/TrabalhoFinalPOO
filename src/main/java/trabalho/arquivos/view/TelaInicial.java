package trabalho.arquivos.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

/**
 * Tela inicial do sistema de gerenciamento de mídias.
 * <p>
 * Permite ao usuário escolher entre inserir um novo arquivo de mídia ou 
 * visualizar os arquivos existentes.
 * </p>
 * <p>
 * Esta tela é a primeira janela que é aberta quando o programa é iniciado.
 * </p>
 */
public class TelaInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneInicio;

	/**
     * Ponto de entrada da aplicação.
     * <p>
     * Cria e exibe a tela inicial em uma thread segura de GUI.
     * </p>
     * 
     * @param args argumentos da linha de comando (não utilizados)
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaInicial frame = new TelaInicial();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	 /**
     * Construtor da tela inicial.
     * <p>
     * Configura a janela, cria os botões "Inserir Arquivo" e "Ver arquivos" e define
     * suas ações.
     * </p>
     */
	public TelaInicial() {
		setTitle("Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 608, 402);
		contentPaneInicio = new JPanel();
		contentPaneInicio.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneInicio);
		contentPaneInicio.setLayout(null);
		// Botão para abrir a tela de inserção de novos arquivos
		JButton btnInserir = new JButton("Inserir Arquivo");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
                InserirNovo novaJanela = new InserirNovo();
                novaJanela.setVisible(true);

                // Fecha a tela atual
                dispose();
				
			}
		});
		btnInserir.setBounds(347, 182, 143, 62);
		contentPaneInicio.add(btnInserir);
		
		// Botão para abrir a tela de listagem de arquivos
		JButton btnListar = new JButton("Ver arquivos");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ListaArquivos novaJanela = new ListaArquivos();
				novaJanela.setVisible(true);
				
				dispose();
			}
		});
		btnListar.setBounds(91, 182, 143, 62);
		contentPaneInicio.add(btnListar);
		
		JLabel lblHome = new JLabel("Gerenciador de arquivos TPOO");
		lblHome.setHorizontalAlignment(SwingConstants.CENTER);
		lblHome.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblHome.setBounds(91, 50, 399, 62);
		contentPaneInicio.add(lblHome);

	}
}
