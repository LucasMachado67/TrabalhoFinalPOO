package trabalho.arquivos.classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.WriteOutContentHandler;
import org.xml.sax.SAXException;


public class Livro extends Midia{
	
	private ArrayList<String> autores;
	
	public Livro(String local, String titulo, String categoria) {
		super(local, titulo, categoria);
		this.setDuracao(this.calcularDuracao(local));
		this.autores = new ArrayList<String>();
	}
	
	@Override
	public String getTipo() {
		return "Livro";
	}


	public ArrayList<String> getAutores() {
		return autores;
	}


	public void setAutores(ArrayList<String> autores) {
		this.autores = autores;
	}
	public void setAutor(String autor) {
		if (autor != null && !autor.isBlank()) {
            autores.add(autor);
        }
	}
	
	@Override
	public int calcularDuracao(String local) {
		
		File arquivo = new File(local);
		String nomeArquivo = arquivo.getName();
		
		String extension = "";
		int i = nomeArquivo.lastIndexOf('.');
		extension = nomeArquivo.substring(i + 1);
		if(extension.equals("pdf")) {
			
			try {
				PDDocument documento = PDDocument.load(arquivo);
				duracao = documento.getNumberOfPages();
				documento.close();
				return duracao;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return duracao;
		}else if(extension.equals("epub")) {
			
			//Epub não tem uma quantidade de pagina, pois e baseada em html entao pode variar de dispositivo
			//para isso, estou utilizando o padrao ADOBE para contar as páginas, sendo que uma página é calculada por 1500 caracteres.
			FileInputStream input;
			try {
				
				input = new FileInputStream(local);
				WriteOutContentHandler writeOut = new WriteOutContentHandler(-1);
				BodyContentHandler handler = new BodyContentHandler(writeOut);
				Metadata metadata = new Metadata();
				ParseContext context = new ParseContext();
				
				AutoDetectParser parser = new AutoDetectParser();
				parser.parse(input, handler, metadata, context);
				
				//calculando o numero de paginas
				String text = handler.toString();
				int charCount = text.length();
				return duracao = (int) Math.ceil(charCount / 1024.0);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			}
			return duracao;
		}else {
			return duracao;
		}
	}
}
