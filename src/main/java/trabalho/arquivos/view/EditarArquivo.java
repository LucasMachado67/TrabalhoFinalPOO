package trabalho.arquivos.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
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

public class EditarArquivo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Midia midia;
	private JTextField textFieldLocal;
	private JTextField textFieldTitulo;
	private JTextField textFieldCategoria;
	private JTextField textFieldEspecifico;
	ArrayList<String> autores = new ArrayList<String>();
	private DefaultListModel<String> modeloAutores = new DefaultListModel<>();
	private String tituloOriginal;

	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public EditarArquivo() {
	       this(null, null, null);
	}
	 
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
		
		textFieldCategoria = new JTextField();
		textFieldCategoria.setBounds(23, 215, 259, 18);
		contentPane.add(textFieldCategoria);
		textFieldCategoria.setColumns(10);
		
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
		
		JScrollPane scrollAutores = new JScrollPane(listAutores);
		scrollAutores.setBounds(23, 326, 259, 105);
		contentPane.add(scrollAutores);
		
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
	        lblEspecifico.setText("Idioma");
	        Filme filme = (Filme) midia;
	        textFieldEspecifico.setText(filme.getIdioma());

	    } else if (tipo.equals("Musica")) {
	        lblEspecifico.setText("Artista");
	        Musica musica = (Musica) midia;
	        textFieldEspecifico.setText(musica.getArtista());
	    }
	    
	    
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				try {
			       //Pegando as atualizacoes dos campos de input's
					
			       midia.setLocal(textFieldLocal.getText());
			       midia.setTitulo(textFieldTitulo.getText());
			       midia.setCategoria(textFieldCategoria.getText());
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
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaInicial telaInicial = new TelaInicial();
				telaInicial.setVisible(true);

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
				JFileChooser chooser = new JFileChooser(new File("C:\\Users\\LucasMachado\\eclipse-workspace\\arquivos\\ArquivosTeste"));
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
	
	public void carregarDados() {
		textFieldLocal.setText(midia.getLocal());
		textFieldTitulo.setText(midia.getTitulo());
		textFieldCategoria.setText(midia.getCategoria());
	}
}
