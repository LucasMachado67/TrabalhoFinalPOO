package trabalho.arquivos.classes;


import java.io.File;

import trabalho.arquivos.Utils.ArquivoMidiaUtils;

/**
 * Classe que representa uma música, extensão da classe {@link Midia}.
 * <p>
 * Cada música possui informações sobre o artista, além dos atributos herdados de {@link Midia}
 * como título, categoria, local do arquivo, tamanho e duração.
 * </p>
 * <p>
 * A duração da música é calculada automaticamente com base no arquivo MP3.
 * </p>
 * 
 */
public class Musica extends Midia{
	/** Nome do artista da música */
	private String artista;
	/**
     * Construtor da classe Musica.
     * 
     * @param local    Caminho do arquivo da música
     * @param titulo   Título da música
     * @param categoria Categoria da música
     * @param artista  Nome do artista
     */
	public Musica(String local, String titulo, String categoria, String artista) {
		super(local, titulo, categoria);
		File arq = new File(local);
		if (arq.exists()) {
		    this.duracao = calcularDuracao(local);
		} else {
		    this.duracao = 0;
		}
		setArtista(artista);
	}
	/**
     * Retorna o tipo da mídia.
     * 
     * @return "Música" como tipo da mídia
     */
	@Override
	public String getTipo() {
		return "Música";
	}
	/** @return Nome do artista da música */
	public String getArtista() {
		return artista;
	}
	/**
     * Define o nome do artista da música.
     * 
     * @param artista Nome do artista
     */
	public void setArtista(String artista) {
		this.artista = artista;
	}
	/**
     * Calcula a duração da música em segundos a partir do arquivo MP3.
     * <p>
     * Este método sobrescreve {@link Midia#calcularDuracao(String)} para fornecer
     * cálculo específico de duração para arquivos de música.
     * </p>
     * 
     * @param local Caminho do arquivo da música
     * @return Duração da música em segundos
     */
	@Override
	public int calcularDuracao(String local){
	     int segundos = ArquivoMidiaUtils.calcularDuracaoMp3(local);
	     return this.duracao = segundos;

	}
}
