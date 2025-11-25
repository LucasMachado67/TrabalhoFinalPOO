package trabalho.arquivos.classes;

import java.io.File;
import java.util.ArrayList;

import trabalho.arquivos.Utils.ArquivoMidiaUtils;

/**
 * Classe que representa um livro, extensão da classe {@link Midia}.
 * <p>
 * Cada livro possui uma lista de autores, além dos atributos herdados de {@link Midia}
 * como título, categoria, local do arquivo, tamanho e duração.
 * </p>
 * <p>
 * A duração do livro é calculada com base no arquivo:
 * - Para PDF: número de páginas
 * - Para EPUB: número de capítulos
 * </p>
 */
public class Livro extends Midia{
	/** Lista de autores do livro */
	private ArrayList<String> autores;
	/**
     * Construtor da classe Livro.
     * 
     * @param local    Caminho do arquivo do livro
     * @param titulo   Título do livro
     * @param categoria Categoria do livro
     */
	public Livro(String local, String titulo, String categoria) {
		super(local, titulo, categoria);
		this.setDuracao(this.calcularDuracao(local));
		this.autores = new ArrayList<String>();
	}
	/**
     * Retorna o tipo da mídia.
     * 
     * @return "Livro" como tipo da mídia
     */
	@Override
	public String getTipo() {
		return "Livro";
	}

	/** Recupera a lista de autores*/
	public ArrayList<String> getAutores() {
		return autores;
	}

	/** Define uma lista de autores*/
	public void setAutores(ArrayList<String> autores) {
		this.autores = autores;
	}
	/**
     * Adiciona um autor à lista de autores do livro.
     * 
     * @param autor Nome do autor a ser adicionado
     */
	public void setAutor(String autor) {
		if (autor != null && !autor.isBlank()) {
            autores.add(autor);
        }else {
        	throw new IllegalArgumentException("autor inválido");
        }
	}
	/**
     * Calcula a duração do livro com base no arquivo.
     * <p>
     * Para arquivos PDF, retorna o número de páginas.
     * Para arquivos EPUB, retorna o número de capítulos.
     * </p>
     * 
     * @param local Caminho do arquivo do livro
     * @return Duração do livro (páginas ou capítulos)
     */
	@Override
	public int calcularDuracao(String local) {
		
		File arquivo = new File(local);
		String nomeArquivo = arquivo.getName();
		//Extraindo a extensão para jogar no if
		String extension = "";
		int i = nomeArquivo.lastIndexOf('.');
		extension = nomeArquivo.substring(i + 1);
		
		if(extension.equals("pdf")) {
			int paginas = ArquivoMidiaUtils.calcularDuracaoPdf(local);
			
			return this.duracao = paginas;
		}else if(extension.equals("epub")) {
			
			int capitulos = ArquivoMidiaUtils.calcularCapitulosEpub(local);

			return this.duracao = capitulos;
			
		}else {
			return duracao;
		}
	}
}
