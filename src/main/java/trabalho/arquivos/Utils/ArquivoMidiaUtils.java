package trabalho.arquivos.Utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import trabalho.arquivos.classes.Filme;
import trabalho.arquivos.classes.Livro;
import trabalho.arquivos.classes.Midia;
import trabalho.arquivos.classes.Musica;

public class ArquivoMidiaUtils {
	
	public static void salvarMidiaMusica(Midia midia) {
		String pasta = "C:\\Users\\LucasMachado\\eclipse-workspace\\arquivos\\ArquivosTeste";
		new File(pasta).mkdirs(); // Cria a pasta se não existir
		
		String nomeArquivo = pasta + File.separator + midia.getTitulo().replaceAll("\\s+", "_") + ".tpoo";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))){
			writer.write("Tipo: " + midia.getTipo() + "\n");
            writer.write("Título: " + midia.getTitulo() + "\n");
            writer.write("Local: " + midia.getLocal() + "\n");
            writer.write("Tamanho: " + midia.getTamanho() + " bytes\n");
            writer.write("Categoria: " + midia.getCategoria() + "\n");
            writer.write("Duração: " + midia.getDuracao() + "\n");
            Musica musica = (Musica) midia;
            writer.write("Artista: " + musica.getArtista() + "\n");
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void salvarMidiaEpubOrPdf(Midia midia) {
		String pasta = "C:\\Users\\LucasMachado\\eclipse-workspace\\arquivos\\ArquivosTeste";
		new File(pasta).mkdirs(); // Cria a pasta se não existir

		String nomeArquivo = pasta + File.separator + midia.getTitulo().replaceAll("\\s+", "_") + ".tpoo";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))){
			writer.write("Tipo: " + midia.getTipo() + "\n");
            writer.write("Título: " + midia.getTitulo() + "\n");
            writer.write("Local: " + midia.getLocal() + "\n");
            writer.write("Tamanho: " + midia.getTamanho() + " bytes\n");
            writer.write("Categoria: " + midia.getCategoria() + "\n");
            writer.write("Duração: " + midia.getDuracao() + "\n");
            Livro livro = (Livro) midia;
            for(String nome: livro.getAutores()) {
            	writer.write("Autor: " + nome + "\n");
            }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void salvarMidiaFilme(Midia midia) {
		String pasta = "C:\\Users\\LucasMachado\\eclipse-workspace\\arquivos\\ArquivosTeste";
		new File(pasta).mkdirs(); // Cria a pasta se não existir

		String nomeArquivo = pasta + File.separator + midia.getTitulo().replaceAll("\\s+", "_") + ".tpoo";
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))){
			writer.write("Tipo: " + midia.getTipo() + "\n");
            writer.write("Título: " + midia.getTitulo() + "\n");
            writer.write("Local: " + midia.getLocal() + "\n");
            writer.write("Tamanho: " + midia.getTamanho() + " bytes\n");
            writer.write("Categoria: " + midia.getCategoria() + "\n");
            writer.write("Duração: " + midia.getDuracao() + "\n");
            Filme filme = (Filme) midia;
            writer.write("Idioma: " + filme.getIdioma() + "\n");
            
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void editarMidiaArquivo(Midia midia, String tituloAntigo) {
		String pasta = "C:\\Users\\LucasMachado\\eclipse-workspace\\arquivos\\ArquivosTeste";

		System.out.println(tituloAntigo);
		String nomeAntigo = tituloAntigo.replaceAll("\\s+", "_") + ".tpoo";
	    File arquivoAntigo = new File(pasta + File.separator + nomeAntigo);
	    
	    if (arquivoAntigo.exists()) {
	        arquivoAntigo.delete();
	    }

	    if (midia instanceof Livro) {
	        salvarMidiaEpubOrPdf(midia);
	    } else if (midia instanceof Musica) {
	        salvarMidiaMusica(midia);
	    } else if (midia instanceof Filme) {
	        salvarMidiaFilme(midia);
	    }
	}
}
