package trabalho.arquivos.classes;

import java.io.File;

import javax.swing.JOptionPane;

public abstract class Midia {
	
	protected String local;
	protected long tamanho;
	protected String titulo;
	protected String categoria;
	protected int duracao;
	
	public Midia(String local, String titulo, String categoria) {
		this.setLocal(local);
		this.setTitulo(titulo);
		this.setCategoria(categoria);
		this.setTamanho(new File(local).length()); //Pega o tamanho do arquivo local
	}
	
	//Retorna "Filme", "Música" ou "Livro"
	public abstract String getTipo();

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public long getTamanho() {
		return tamanho;
	}

	public void setTamanho(long tamanho) {
		this.tamanho = tamanho;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {

		this.categoria = categoria;
	}

	public int getDuracao() {
		return duracao;
	}
	
	public int calcularDuracao (String local){
		return this.duracao;
	}
	
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Midia)) return false;
	    Midia m = (Midia) o;
	    return this.getTitulo().equalsIgnoreCase(m.getTitulo());
	}

	@Override
	public int hashCode() {
	    return getTitulo().toLowerCase().hashCode();
	}
}
