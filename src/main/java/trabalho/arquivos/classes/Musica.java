package trabalho.arquivos.classes;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.mpatric.mp3agic.Mp3File;

public class Musica extends Midia{
	
	private String artista;
	
	public Musica(String local, String titulo, String categoria, String artista) {
		super(local, titulo, categoria);
		this.setDuracao(calcularDuracao(local));
		setArtista(artista);
	}
	@Override
	public String getTipo() {
		return "Música";
	}

	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}
	
	@Override
	public int calcularDuracao(String local){
		 try {
	            Mp3File mp3file = new Mp3File(local);
	            long duracaoEmSegundos = mp3file.getLengthInSeconds();
	            return this.duracao = (int) duracaoEmSegundos;
	        } catch(IOException e){
	        	e.printStackTrace();
	        }catch (Exception e) {
	        	System.out.println("Erro: ao calcular duração de música" + e.getMessage());
	            e.printStackTrace();
	        }
		 return duracao;
	}
}
