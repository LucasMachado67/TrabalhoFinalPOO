package trabalho.arquivos.classes;

import java.io.File;

/**
 * Classe abstrata que representa uma mídia genérica, como filmes, músicas ou livros.
 * <p>
 * Cada mídia possui um local (caminho do arquivo), tamanho em MB, título, categoria e duração.
 * Subclasses devem implementar o método {@link #getTipo()} para definir o tipo específico da mídia e 
 * o método {@link #calcularDuracao()} para pegar a duração dos arquivos
 * </p>
 * <p>
 * O tamanho do arquivo é calculado automaticamente com base no arquivo local informado.
 * </p>

 */
public abstract class Midia {
	
	 /** Caminho do arquivo da mídia */
	protected String local;
	 /** Tamanho do arquivo em megabytes (MB) */
	protected double tamanho;
	/** Título da mídia */
	protected String titulo;
	 /** Categoria da mídia (ex: ação, drama, ficção) */
	protected String categoria;
	 /** Duração da mídia  */
	protected int duracao;
	
	/**
     * Construtor da classe Midia.
     * 
     * @param local   Caminho do arquivo da mídia
     * @param titulo  Título da mídia
     * @param categoria Categoria da mídia
     */
	public Midia(String local, String titulo, String categoria) {
		this.setLocal(local);
		this.setTitulo(titulo);
		this.setCategoria(categoria);
		File arq = new File(local);
		this.setTamanho(arq.length()); //Pega o tamanho do arquivo local
	}
	/**
     * Retorna o tipo da mídia.
     * <p>
     * Este método deve ser implementado pelas subclasses para retornar, por exemplo, 
     * "Filme", "Música" ou "Livro".
     * </p>
     * 
     * @return Tipo da mídia como String
     */
	public abstract String getTipo();
	/** Recupera o local do arquivo*/
	public String getLocal() {
		return local;
	}
	/** Define o local do arquivo*/
	public void setLocal(String local) {
		this.local = local;
	}
	/** Recupera o tamanho do arquivo*/
	public double getTamanho() {
		return tamanho;
	}
	/** Define o tamanho do arquivo em bytes*/
	public void setTamanho(double tamanho) {
		this.tamanho = tamanho;
	}
	/**
	 * Define o tamanho da mídia em bytes e converte para MB arredondado.
	 * 
	 * @param tamanho Tamanho do arquivo em bytes
	 */
	public void setTamanhoMb(double tamanho) {
		double tamanhoMb = tamanho / (1000.0 * 1000.0);
		tamanhoMb = Math.round(tamanhoMb * 100.0) / 100.0;
		this.tamanho = tamanhoMb;
	}
	/** Recupera o tiutlo do arquivo*/
	public String getTitulo() {
		return titulo;
	}
	/** Define o local do arquivo*/
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/** Recupera a categoria do arquivo*/
	public String getCategoria() {
		return categoria;
	}
	/** Define a categoria do arquivo*/
	public void setCategoria(String categoria) {

		this.categoria = categoria;
	}
	/** Recupera a duracao do arquivo*/
	public int getDuracao() {
		return duracao;
	}
	/**
     * Calcula a duração da mídia com base no arquivo.
     * <p>
     * Método pode ser sobrescrito por subclasses para cálculo específico.
     * </p>
     * 
     * @param local Caminho do arquivo
     * @return Duração da mídia em segundos, páginas, capitulos ou minutos
     */
	public int calcularDuracao (String local){
		return this.duracao;
	}
	/** Recupera a duracao do arquivo*/
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	
	/**
     * Compara duas mídias pelo título (ignorando maiúsculas/minúsculas).
     * 
     * @param o Objeto a ser comparado
     * @return true se os títulos forem iguais, false caso contrário
     */
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Midia)) return false;
	    Midia m = (Midia) o;
	    return this.getTitulo().equalsIgnoreCase(m.getTitulo());
	}
	 /**
     * Gera o hashCode com base no título da mídia (em minúsculas).
     * 
     * @return Código hash do título
     */
	@Override
	public int hashCode() {
	    return getTitulo().toLowerCase().hashCode();
	}
}
