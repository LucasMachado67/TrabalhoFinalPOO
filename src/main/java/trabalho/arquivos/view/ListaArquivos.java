package trabalho.arquivos.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import trabalho.arquivos.classes.Midia;
import trabalho.arquivos.controllers.GerenciadorMidia;

import javax.swing.JTable;
import javax.swing.JButton;

public class ListaArquivos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableArquivos;
	private List<Midia> arquivosTpoo;
	private GerenciadorMidia gerenciador = new GerenciadorMidia();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaArquivos frame = new ListaArquivos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 */
	public ListaArquivos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 676, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*
		 * Jtable model precisa de um modelo para colocar dentro do JTable
		 */
		DefaultTableModel modelo = new DefaultTableModel();
		modelo.addColumn("Tipo");
		modelo.addColumn("Título");
		modelo.addColumn("Tamanho");
		modelo.addColumn("Categoria");
		modelo.addColumn("Duração");
		
		//Adicionando o modelo específicado
		tableArquivos = new JTable(modelo);
		tableArquivos.setBounds(10, 84, 642, 298);
		
		JScrollPane scroll = new JScrollPane(tableArquivos);
		scroll.setBounds(31, 84, 642, 298);

		contentPane.add(scroll);
		atualizarTabela();
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableArquivos.getSelectedRow();

		        if (linhaSelecionada == -1) {
		            JOptionPane.showMessageDialog(null, "Selecione um arquivo para editar!");
		            return;
		        }
		        
				int modelIndex = tableArquivos.convertRowIndexToModel(tableArquivos.getSelectedRow());
				Midia midia = arquivosTpoo.get(modelIndex);
				
				//passando o gerenciador e a tela listaArquivos para conseguir atualizar a lista de arquivos depois de editar, de dentro da tela EditarArquivo
				EditarArquivo telaEdicao = new EditarArquivo(midia,gerenciador, ListaArquivos.this);
				telaEdicao.setVisible(true);
				
			}
		});
		btnEditar.setBounds(568, 392, 84, 20);
		contentPane.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int modelIndex = tableArquivos.convertRowIndexToModel(tableArquivos.getSelectedRow());
				Midia midia = arquivosTpoo.get(modelIndex);
				gerenciador.removerMidia(midia);
				atualizarTabela();
				
			}
		});
		btnExcluir.setBounds(474, 392, 84, 20);
		contentPane.add(btnExcluir);
		
		JButton btnVoltar = new JButton("Início");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInicial telaInicial = new TelaInicial();
				telaInicial.setVisible(true);
				
				dispose();
			}
		});
		btnVoltar.setBounds(31, 10, 114, 33);
		contentPane.add(btnVoltar);
	}
	public void atualizarTabela() {
	    DefaultTableModel modelo = (DefaultTableModel) tableArquivos.getModel();
	    modelo.setRowCount(0);

	    arquivosTpoo = gerenciador.listarMidias();

	    for (Midia m : arquivosTpoo) {
	        modelo.addRow(new Object[]{
	                m.getTipo(),
	                m.getTitulo(),
	                m.getTamanho(),
	                m.getCategoria(),
	                m.getDuracao()
	        });
	    }
	}
}
