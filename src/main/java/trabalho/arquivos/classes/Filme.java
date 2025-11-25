package trabalho.arquivos.classes;

import java.io.File;
import trabalho.arquivos.Utils.ArquivoMidiaUtils;

/**
 * Classe que representa um filme, extensão da classe {@link Midia}.
 * <p>
 * Cada filme possui informações sobre o idioma, além dos atributos herdados de {@link Midia}
 * como título, categoria, local do arquivo, tamanho e duração.
 * </p>
 * <p>
 * A duração do filme é calculada automaticamente com base no arquivo de vídeo,
 * suportando arquivos nos formatos MP4 e MKV.
 * </p>
 */
public class Filme extends Midia{
	/** Idioma do filme */
	private String idioma;
	/**
     * Construtor da classe Filme.
     * 
     * @param local    Caminho do arquivo do filme
     * @param titulo   Título do filme
     * @param categoria Categoria do filme
     * @param idioma   Idioma do filme
     */
	public Filme(String local, String titulo, String categoria, String idioma) {
		super(local, titulo, categoria);
		setDuracao(calcularDuracao(local));
		setIdioma(idioma);
	}
	/**
     * Retorna o tipo da mídia.
     * 
     * @return "Filme" como tipo da mídia
     */
	@Override
	public String getTipo() {
		return "Filme";
	}
	/** Recupera o idioma do arquivo*/
	public String getIdioma() {
		return idioma;
	}
	/** Define o idioma do arquivo*/
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	/**
     * Calcula a duração do filme em minutos com base no arquivo de vídeo.
     * <p>
     * Suporta arquivos nos formatos MP4 e MKV. Caso ocorra algum erro na leitura do arquivo,
     * retorna 0.
     * </p>
     * 
     * @param local Caminho do arquivo do filme
     * @return Duração do filme em minutos
     */
	@Override
    public int calcularDuracao(String local) {
		
		//Validação isnicial para ver qual o tipo de extensao que o usuario seleciona
		File arquivo = new File(local);
		String nomeArquivo = arquivo.getName();
		String extensao = "";
		int i = nomeArquivo.lastIndexOf('.');
		extensao = nomeArquivo.substring(i + 1);
		
		if(extensao.equals("mp4")) {
			try {
				int minutos = ArquivoMidiaUtils.calcularDuracaoMp4(local);
				return this.duracao = minutos;
				
			} catch (Exception e) {
				System.out.println("Erro ao ler duração do vídeo: " + e.getMessage());
				return 0;
			}
		}else if(extensao.equals("mkv")){
			try {
				int minutos = ArquivoMidiaUtils.calcularDuracaoMkv(local);
				
				return this.duracao = minutos;
				
			} catch (Exception e) {
				System.out.println("Erro ao ler duração do vídeo: " + e.getMessage());
				return 0;
			}
		}
		return 0;
    }

}
