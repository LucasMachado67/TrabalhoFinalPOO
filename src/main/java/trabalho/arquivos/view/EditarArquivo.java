package trabalho.arquivos.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import trabalho.arquivos.classes.Filme;
import trabalho.arquivos.classes.Livro;
import trabalho.arquivos.classes.Midia;
import trabalho.arquivos.classes.Musica;
import trabalho.arquivos.controllers.GerenciadorMidia;

/**
 * Tela para editar uma mídia existente no sistema.
 * <p>
 * Permite ao usuário alterar informações de filmes, músicas ou livros, incluindo
 * título, categoria, atributos específicos (idioma, artista ou autores) e caminho do arquivo.
 * </p>
 * <p>
 * Esta tela deve ser aberta a partir de uma {@link ListaArquivos} ou de outra
 * interface que possua referência ao objeto {@link GerenciadorMidia}.
 * </p>
 */
public class EditarArquivo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/** Mídia a ser editada */
	private Midia midia;
	 /** Campo de texto para o local do arquivo */
    private JTextField textFieldLocal;

    /** Campo de texto para o título da mídia */
    private JTextField textFieldTitulo;

    /** Campo de texto para atributos específicos (idioma, artista, autores) */
    private JTextField textFieldEspecifico;

    /** ComboBox para seleção da categoria */
    private JComboBox<String> comboBoxCategoria;

    /** Lista de autores para livros */
    ArrayList<String> autores = new ArrayList<String>();

    /** Modelo de lista usado para exibir autores no JList */
    private DefaultListModel<String> modeloAutores = new DefaultListModel<>();

    /** Título original da mídia, usado para identificar a mídia no GerenciadorMidia */
    private String tituloOriginal;

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
					EditarArquivo frame = new EditarArquivo();	
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
     * Construtor padrão.
     */
	public EditarArquivo() {
	       this(null, null, null);
	}
	/**
     * Construtor principal.
     * <p>
     * Inicializa a tela de edição com os dados da mídia fornecida, gerenciador de mídias
     * e referência à tela que lista as mídias.
     * </p>
     * 
     * @param midia       objeto {@link Midia} a ser editado
     * @param gerenciador objeto {@link GerenciadorMidia} responsável por gerenciar mídias
     * @param telaLista   referência da {@link ListaArquivos} para atualizar a tabela após edição
     */
	public EditarArquivo(Midia midia, GerenciadorMidia gerenciador, ListaArquivos telaLista) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		//Iniciando as variaveis
		this.midia = midia;
		this.tituloOriginal = midia.getTitulo();

		setTitle("Novo Arquivo");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 616, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLocalArquivo = new JLabel("Local do Arquivo");
		lblLocalArquivo.setBounds(23, 98, 126, 12);
		contentPane.add(lblLocalArquivo);
		
		textFieldLocal = new JTextField();
		textFieldLocal.setEditable(false);
		textFieldLocal.setBounds(117, 120, 165, 18);
		contentPane.add(textFieldLocal);
		textFieldLocal.setColumns(10);
		
		JLabel lblTitulo = new JLabel("Título");
		lblTitulo.setBounds(23, 148, 111, 12);
		contentPane.add(lblTitulo);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setColumns(10);
		textFieldTitulo.setBounds(23, 170, 259, 18);
		contentPane.add(textFieldTitulo);
		
		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setBounds(23, 198, 69, 12);
		contentPane.add(lblCategoria);
		comboBoxCategoria = new JComboBox<>();
		comboBoxCategoria.setBounds(23, 215, 259, 18);
		contentPane.add(comboBoxCategoria);
		
		//COMPONENTES ESPECÍFICO
		JLabel lblEspecifico = new JLabel();
		lblEspecifico.setText("específica de input");
		lblEspecifico.setBounds(23, 265, 160, 12);
		contentPane.add(lblEspecifico);
		
		textFieldEspecifico = new JTextField();
		textFieldEspecifico.setBounds(23, 287, 160, 18);
		contentPane.add(textFieldEspecifico);
		textFieldEspecifico.setColumns(10);
		
		//Específico para o livro
		
		JList<String> listAutores = new JList<>();
		listAutores.setModel(modeloAutores); 
		listAutores.setBounds(1, 1, 16, 20);
		contentPane.add(listAutores);
		//Scroll para a lista, caso ela for muito grande
		JScrollPane scrollAutores = new JScrollPane(listAutores);
		scrollAutores.setBounds(23, 326, 259, 105);
		contentPane.add(scrollAutores);
		//Adiciona um autor a lista
		JButton btnAdicionar = new JButton("ADD");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String autor = textFieldEspecifico.getText();
			    if (!autor.isEmpty()) {
			        modeloAutores.addElement(autor); 
			        textFieldEspecifico.setText(""); 
			    }
			}
		});
		btnAdicionar.setBounds(198, 286, 84, 20);
		contentPane.add(btnAdicionar);
		//Remove o autor da lista
		JButton btnRemover = new JButton("Remover Autor Selecionado");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = listAutores.getSelectedIndex();
			    if (index != -1) {
			        modeloAutores.remove(index);
			    }
			}
		});
		btnRemover.setBounds(23, 441, 259, 20);
		contentPane.add(btnRemover);
		
		// Inicialmente invisíveis
		lblEspecifico.setVisible(false);
		textFieldEspecifico.setVisible(false);
		listAutores.setVisible(false);
		btnAdicionar.setVisible(false);
		btnRemover.setVisible(false);
		scrollAutores.setVisible(false);
		
		String tipo = midia.getTipo();

	    lblEspecifico.setVisible(true);
	    textFieldEspecifico.setVisible(true);

	    if (tipo.equals("Livro")) {
	    	Livro livro = (Livro) midia;
	    	for(String l : categoriasLivroEFilme()) {
	    		comboBoxCategoria.addItem(l);
	    	}
	        lblEspecifico.setText("Autores");
	        btnAdicionar.setVisible(true);
	        listAutores.setVisible(true);
	        btnRemover.setVisible(true);
	        scrollAutores.setVisible(true);

	        // Carregar autores existentes
	        for (String autor : livro.getAutores()) {
	            modeloAutores.addElement(autor);
	        }

	    } else if (tipo.equals("Filme")) {
	    	Filme filme = (Filme) midia;
	        lblEspecifico.setText("Idioma");
	        for(String l : categoriasLivroEFilme()) {
	    		comboBoxCategoria.addItem(l);
	    	}
	        textFieldEspecifico.setText(filme.getIdioma());

	    } else if (tipo.equals("Música")) {
	    	Musica musica = (Musica) midia;
	    	for(String l : categoriasMusica()) {
	    		comboBoxCategoria.addItem(l);
	    	}
	        lblEspecifico.setText("Artista");
	        textFieldEspecifico.setText(musica.getArtista());
	    }
	    
	    //Salva as alterações feitas dentro do arquivo .tpoo
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				try {
			       //Pegando as atualizacoes dos campos de input's
					
			       midia.setLocal(textFieldLocal.getText());
			       midia.setTitulo(textFieldTitulo.getText());
			       midia.setCategoria(comboBoxCategoria.getSelectedItem().toString());
			       if (midia instanceof Filme) {
			    	    ((Filme) midia).setIdioma(textFieldEspecifico.getText());
			    	}else if (midia instanceof Musica) {
			    	    ((Musica) midia).setArtista(textFieldEspecifico.getText());
			    	}else if (midia instanceof Livro) {
			    	    Livro livro = (Livro) midia;

			    	    ArrayList<String> novosAutores = new ArrayList<>();
			    	    for (int i = 0; i < modeloAutores.size(); i++) {
			    	        novosAutores.add(modeloAutores.get(i));
			    	    }
			    	    livro.setAutores(novosAutores);
			    	}
			      
			       gerenciador.editarMidia(midia,tituloOriginal);
			       telaLista.atualizarTabela();
			       JOptionPane.showMessageDialog(null, "Arquivo editado com sucesso!");
			       dispose();

			    } catch (Exception ex) {
			        ex.printStackTrace();
			        JOptionPane.showMessageDialog(null, "Erro ao salvar mídia: " + ex.getMessage());
			    }
			}
		});
		btnSalvar.setBounds(332, 426, 126, 35);
		contentPane.add(btnSalvar);
		
		//Retorna a tela de listar
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

                // Dispose, fecha a tela atual
                dispose();
			}
		});
		btnCancelar.setBounds(466, 426, 118, 35);
		contentPane.add(btnCancelar);
		
		JButton btnEscolherArquivo = new JButton("Escolher");
		btnEscolherArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Pegando o que está selecionado na caixinha do comboBox
				String selecionado = (String) midia.getTipo();
				//Fazendo um filtro para quais arquivos irão vir no windows explorer
				String[] arquivos = new String[2];
				FileNameExtensionFilter filter = null;
				
				if(selecionado.equals("Livro")) {
					arquivos[0] = "pdf";
					arquivos[1] = "epub";
					filter = new FileNameExtensionFilter("Arquivos", arquivos);
				}else if(selecionado.equals("Musica")) {
					arquivos[0] = "mp3";
					arquivos[1] = "mp3";
					filter = new FileNameExtensionFilter("Arquivos", arquivos);
				}else if(selecionado.equals("Filme")) {
					arquivos[0] = "mkv";
					arquivos[1] = "mp4";					
					filter = new FileNameExtensionFilter("Arquivos", arquivos);
				}else {
					//Irá buscar todos os arquvios caso não for selecionado
				}
				JFileChooser chooser = new JFileChooser(new File("C:\\Users\\LucasMachado\\eclipse-workspace\\Testes"));
				chooser.setFileFilter(filter);
				int retorno = chooser.showOpenDialog(null);
		    	if(retorno == JFileChooser.APPROVE_OPTION) {
		    		File arquivoSelecionado = chooser.getSelectedFile();
		    		textFieldLocal.setText(arquivoSelecionado.getAbsolutePath());
		    	}
			}
		});
		btnEscolherArquivo.setBounds(23, 119, 84, 20);
		contentPane.add(btnEscolherArquivo);
		
		
		carregarDados(); 
        
	}
	/**
     * Carrega os dados atuais da mídia nos campos da interface.
     * <p>
     * Atualiza os campos de texto e a seleção de categoria.
     * </p>
     */
	public void carregarDados() {
		textFieldLocal.setText(midia.getLocal());
		textFieldTitulo.setText(midia.getTitulo());
		comboBoxCategoria.setSelectedItem(midia.getCategoria());
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
}
