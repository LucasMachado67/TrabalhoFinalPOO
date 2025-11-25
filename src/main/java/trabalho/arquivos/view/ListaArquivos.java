package trabalho.arquivos.view;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.JLabel;
/**
 * Tela de listagem de mídias cadastradas no sistema.
 * <p>
 * Permite visualizar, editar, excluir, renomear e mover arquivos de mídia,
 * além de aplicar filtros por tipo, categoria e ordenação por título ou duração.
 * </p>
 */
public class ListaArquivos extends JFrame {

	private static final long serialVersionUID = 1L;
	/** Painel principal da tela */
    private JPanel contentPane;

    /** Tabela que exibe as mídias */
    private JTable tableArquivos;

    /** Lista interna das mídias carregadas */
    private List<Midia> arquivosTpoo;

    /** Gerenciador de mídias */
    private GerenciadorMidia gerenciador = new GerenciadorMidia();

    /**
     * Ponto de entrada da aplicação para teste da tela.
     * <p>
     * Cria e exibe a tela em uma thread segura de GUI.
     * </p>
     * 
     * @param args argumentos da linha de comando (não utilizados)
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
     * Construtor da tela de listagem de mídias.
     * <p>
     * Inicializa a tabela, botões de ações (editar, excluir, renomear, mover, executar)
     * e filtros de tipo, categoria e ordenação.
     * </p>
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
		//Excluir midia do sistema
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
		// Volta para a tela inicial do programa
		JButton btnVoltar = new JButton("Home");
		btnVoltar.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInicial telaInicial = new TelaInicial();
				telaInicial.setVisible(true);
				
				dispose();
			}
		});
		btnVoltar.setBounds(31, 10, 77, 64);
		contentPane.add(btnVoltar);
		//Renomeia o arquivo original selecionado e altera as informações dentro do .tpoo
		JButton btnRenomear = new JButton("Renomear");
		btnRenomear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int modelIndex = tableArquivos.convertRowIndexToModel(tableArquivos.getSelectedRow());
				Midia midia = arquivosTpoo.get(modelIndex);
				String novoNome = JOptionPane.showInputDialog("Digite o nome desejado!");
				if(novoNome != null) {
					gerenciador.renomearMidia(midia ,novoNome);
					
				}
				atualizarTabela();
			}
		});
		btnRenomear.setBounds(31, 392, 102, 20);
		contentPane.add(btnRenomear);
		//Move o arquivo de midia original e altera as informações dentro do .tpoo
		JButton btnMover = new JButton("Mover Arquivo");
		btnMover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int modelIndex = tableArquivos.convertRowIndexToModel(tableArquivos.getSelectedRow());
				Midia midia = arquivosTpoo.get(modelIndex);
				
				JFileChooser chooser = new JFileChooser(new File(midia.getLocal()));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int retorno = chooser.showOpenDialog(null);
				
				if (retorno == JFileChooser.APPROVE_OPTION) {
				    File pastaDestino = chooser.getSelectedFile();
				    
				    gerenciador.moverMidia(midia, pastaDestino.getAbsolutePath());
				    atualizarTabela();
				}
			}
		});
		btnMover.setBounds(140, 392, 148, 20);
		contentPane.add(btnMover);
		
		JComboBox<String> comboBoxFiltroTipo = new JComboBox<>();
		comboBoxFiltroTipo.addItem("Selecione...");
		comboBoxFiltroTipo.addItem("Filme");
		comboBoxFiltroTipo.addItem("Música");
		comboBoxFiltroTipo.addItem("Livro");
		
		JComboBox<String> comboBoxOrdenar = new JComboBox<>();
		comboBoxOrdenar.addItem("Selecione...");
		comboBoxOrdenar.addItem("Duração");
		comboBoxOrdenar.addItem("Título");
		comboBoxOrdenar.setSelectedItem("Selecione...");
		
		JComboBox<String> comboBoxCategoriaFiltro = new JComboBox<>();
		comboBoxCategoriaFiltro.setBounds(263, 47, 113, 27);
		contentPane.add(comboBoxCategoriaFiltro);
		comboBoxCategoriaFiltro.addItem("Todas");
		comboBoxCategoriaFiltro.setSelectedItem("Todas");
		comboBoxCategoriaFiltro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarFiltros(comboBoxFiltroTipo, comboBoxCategoriaFiltro, comboBoxOrdenar);
			}
		});
		
		comboBoxFiltroTipo.setBounds(140, 47, 113, 27);
		contentPane.add(comboBoxFiltroTipo);
		
		
		comboBoxOrdenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarFiltros(comboBoxFiltroTipo, comboBoxCategoriaFiltro, comboBoxOrdenar);
			}
		});
		comboBoxOrdenar.setBounds(386, 47, 113, 27);
		contentPane.add(comboBoxOrdenar);
		
		JButton btnLimparFiltros = new JButton("Limpar");
		btnLimparFiltros.setBounds(578, 54, 84, 20);
		btnLimparFiltros.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        comboBoxFiltroTipo.setSelectedIndex(0);
		        comboBoxCategoriaFiltro.removeAllItems();
		        comboBoxCategoriaFiltro.addItem("Todas");
		        comboBoxCategoriaFiltro.setSelectedIndex(0);
		        comboBoxOrdenar.setSelectedIndex(0);
		        atualizarTabela();
		    }
		});
		contentPane.add(btnLimparFiltros);

		JLabel lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(140, 27, 69, 20);
		contentPane.add(lblTipo);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(263, 27, 77, 20);
		contentPane.add(lblCategoria);
		
		JLabel lblOrdenacao = new JLabel("Ordenacao");
		lblOrdenacao.setBounds(386, 27, 77, 20);
		contentPane.add(lblOrdenacao);
		
		JButton btnExecutar = new JButton("Executar");
		btnExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int modelIndex = tableArquivos.convertRowIndexToModel(tableArquivos.getSelectedRow());
					Midia midia = arquivosTpoo.get(modelIndex);
					File arquivo = new File(midia.getLocal());
					
					if(Desktop.isDesktopSupported()) {
						Desktop.getDesktop().open(arquivo);
					}
				} catch (IOException e1) {				
					e1.printStackTrace();
				}
			}
		});
		btnExecutar.setBounds(380, 392, 84, 20);
		contentPane.add(btnExecutar);
		
		comboBoxFiltroTipo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {   	
		    	atualizarCategorias(comboBoxFiltroTipo, comboBoxCategoriaFiltro);
		    	aplicarFiltros(comboBoxFiltroTipo, comboBoxCategoriaFiltro, comboBoxOrdenar);
		    }
		});
	}
	
	/**
     * Atualiza a tabela exibindo todas as mídias do gerenciador.
     */
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
	/**
     * Atualiza a tabela exibindo uma lista específica de mídias.
     * 
     * @param lista lista de mídias a ser exibida
     */
	public void atualizarTabelaComLista(List<Midia> lista) {

	    DefaultTableModel modelo = (DefaultTableModel) tableArquivos.getModel();
	    modelo.setRowCount(0);

	    for (Midia m : lista) {
	        modelo.addRow(new Object[]{
	                m.getTipo(),
	                m.getTitulo(),
	                m.getTamanho(),
	                m.getCategoria(),
	                m.getDuracao()
	        });
	    }
	}
	/**
     * Retorna a lista de categorias possíveis para livros e filmes.
     * 
     * @return lista de categorias (Aventura, Ação, Romance, Suspense, Terror)
     */
	private List<String> categoriasLivroEFilme(){
		List<String> lista = new ArrayList<>();
		
		lista.add("Aventura");
		lista.add("Ação");
		lista.add("Romance");
		lista.add("Suspense");
		lista.add("Terror");
		
		return lista;
	}	
	 /**
     * Retorna a lista de categorias possíveis para músicas.
     * 
     * @return lista de categorias (POP, Jazz, Rock, Eletrônica, Indie)
     */
	private List<String> categoriasMusica(){
		List<String> lista = new ArrayList<>();
		
		lista.add("POP");
		lista.add("Jazz");
		lista.add("Rock");
		lista.add("Eletrônica");
		lista.add("Indie");
		
		return lista;
	}
	/**
     * Atualiza os itens do comboBox de categorias com base no tipo selecionado.
     * 
     * @param comboBoxFiltroTipo comboBox de seleção de tipo
     * @param comboBoxCategoriaFiltro comboBox de categorias
     */
	private void atualizarCategorias(JComboBox<String> comboBoxFiltroTipo, JComboBox<String> comboBoxCategoriaFiltro) {

	    String tipo = (String) comboBoxFiltroTipo.getSelectedItem();

	    comboBoxCategoriaFiltro.removeAllItems();
	    comboBoxCategoriaFiltro.addItem("Todas");

	    if (tipo == null || tipo.equals("Selecione...")) {
	        return;
	    }

	    if (tipo.equals("Filme") || tipo.equals("Livro")) {
	        for (String c : categoriasLivroEFilme()) {
	            comboBoxCategoriaFiltro.addItem(c);
	        }
	    } else if (tipo.equals("Música")) {
	        for (String c : categoriasMusica()) {
	            comboBoxCategoriaFiltro.addItem(c);
	        }
	    }
	}
	 /**
     * Aplica filtros de tipo, categoria e ordenação na tabela de mídias.
     * 
     * @param comboBoxFiltroTipo comboBox de seleção de tipo
     * @param comboBoxCategoriaFiltro comboBox de categoria
     * @param comboBoxOrdenar comboBox de ordenação (Título ou Duração)
     */
	private void aplicarFiltros(
	        JComboBox<String> comboBoxFiltroTipo,
	        JComboBox<String> comboBoxCategoriaFiltro,
	        JComboBox<String> comboBoxOrdenar) {
	    
	    String tipo = (String) comboBoxFiltroTipo.getSelectedItem();
	    String categoria = (String) comboBoxCategoriaFiltro.getSelectedItem();
	    String ordenar = (String) comboBoxOrdenar.getSelectedItem();

	    // Começa carregando tudo
	    List<Midia> lista = gerenciador.listarMidias();

	    // FILTRAR POR TIPO
	    if (tipo != null && !tipo.equals("Selecione...")) {
	        lista = gerenciador.listarPorFormato(tipo, lista);
	    }

	    // FILTRAR POR CATEGORIA
	    if (categoria != null && !categoria.equals("Todas")) {
	    	lista =  gerenciador.listarPorCategoria(categoria, lista);
	    }

	    // ORDENAR
	    if (ordenar != null && !ordenar.equals("Selecione...")) {
	        switch (ordenar) {
	            case "Duração":
	            	lista = gerenciador.ordenarPorDuracao(lista);
	                break;
	            case "Título":
	            	lista = gerenciador.ordenarPorTitulo(lista);
	                break;
	        }
	    }

	    // EXIBIR NA TABELA
	    atualizarTabelaComLista(lista);
	}
}
