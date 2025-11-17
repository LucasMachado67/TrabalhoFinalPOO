package trabalho.arquivos.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaInicial extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneInicio;

	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public TelaInicial() {
		setTitle("Inicio");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 608, 402);
		contentPaneInicio = new JPanel();
		contentPaneInicio.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPaneInicio);
		contentPaneInicio.setLayout(null);
		
		JButton btnInserir = new JButton("Inserir Arquivo");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
                InserirNovo novaJanela = new InserirNovo();
                novaJanela.setVisible(true);

                // Fecha a tela atual (opcional)
                dispose();
				
			}
		});
		btnInserir.setBounds(366, 182, 143, 62);
		contentPaneInicio.add(btnInserir);
		
		JButton btnListar = new JButton("Ver arquivos");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ListaArquivos novaJanela = new ListaArquivos();
				novaJanela.setVisible(true);
				
				dispose();
			}
		});
		btnListar.setBounds(147, 182, 143, 62);
		contentPaneInicio.add(btnListar);

	}
}
