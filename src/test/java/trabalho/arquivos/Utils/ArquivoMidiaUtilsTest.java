package trabalho.arquivos.Utils;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import trabalho.arquivos.classes.Filme;
import trabalho.arquivos.classes.Livro;
import trabalho.arquivos.classes.Midia;
import trabalho.arquivos.classes.Musica;

public class ArquivoMidiaUtilsTest{
	
	private String pasta = "C:\\Users\\LucasMachado\\eclipse-workspace\\Testes";
	
	private Midia midiaMp3;
	private Midia midiaPdf;
	private Midia midiaEpub;
	private Midia midiaMp4;
	private Midia midiaMkv;
	
	@BeforeEach
	void setup() {
		
		midiaMp3 = new Musica(
				"C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\O sol.mp3",
				"O sol",
				"POP",
				"Vitor kley"
				);
		
		ArrayList<String> autores = new ArrayList<>();
	    autores.add("Autor 1");
	    autores.add("Autor 2");
		midiaPdf = new Livro(
				"C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\Trabalho POO.pdf",
				"Trabalho",
				"Terror"
				);
		((Livro) midiaPdf).setAutores(autores);
		
		midiaEpub = new Livro(
				"C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\Livro2.epub",
				"Cidades de Papel",
				"Romance"
				);
		((Livro) midiaEpub).setAutores(autores);
		
		midiaMp4 = new Filme(
				"C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\Anime 86.mp4",
				"Anime 86 ep 16",
				"Aventura",
				"Português"
				);
		
		midiaMkv = new Filme(
				"C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\Anime 86.mkv",
				"Anime 86 MKV",
				"Ação",
				"Inglês"
				);
	}
	
	@Test
	@DisplayName("Deve salvar midia (Musica) quando chamar método AdicionarMidia")
	public void testSalvarMidias() throws IOException {
		
		ArquivoMidiaUtils.salvarMidiaMusica(midiaMp3);
		File fileMp3 = new File(pasta + File.separator + midiaMp3.getTitulo().replaceAll("\\s+", "_") + ".tpoo");
		
		ArquivoMidiaUtils.salvarMidiaEpubOrPdf(midiaPdf);
		File filePdf = new File(pasta + File.separator + midiaPdf.getTitulo().replaceAll("\\s+", "_") + ".tpoo");
		
		ArquivoMidiaUtils.salvarMidiaFilme(midiaMp4);
		File fileMp4 = new File(pasta + File.separator + midiaMp4.getTitulo().replaceAll("\\s+", "_") + ".tpoo"); 
		
		ArquivoMidiaUtils.salvarMidiaEpubOrPdf(midiaEpub);
		File fileEpub = new File(pasta + File.separator + midiaEpub.getTitulo().replaceAll("\\s+", "_") + ".tpoo"); 
		
		ArquivoMidiaUtils.salvarMidiaFilme(midiaMkv);
		File fileMkv = new File(pasta + File.separator + midiaMkv.getTitulo().replaceAll("\\s+", "_") + ".tpoo");
		
		assertTrue(fileMp3.exists());
		assertTrue(filePdf.exists());
		assertTrue(fileMp4.exists());
		assertTrue(fileEpub.exists());
		assertTrue(fileMkv.exists());
		
	}
	
	@Test
	@DisplayName("Deve retornar o valor da duração em páginas do pdf inserido")
	public void testCalcularDuracaoPdf() {
		
		int resultado = ArquivoMidiaUtils.calcularDuracaoPdf(midiaPdf.getLocal());
		assertTrue(midiaMp3.getDuracao() > 0);
		assertEquals(2, resultado);
	}
	
	@Test
	@DisplayName("Deve retornar o valor da duração em capitulos do epub inserido")
	public void testCalcularDuracaoEpub() {
		
		int resultado = ArquivoMidiaUtils.calcularCapitulosEpub(midiaEpub.getLocal());
		assertTrue(midiaMp3.getDuracao() > 0);
		assertEquals(6, resultado);
	}
	
	@Test
	@DisplayName("Deve retornar o valor da duração em minutos do mp4 inserido")
	public void testCalcularDuracaoMp4() {
		
		int resultado = ArquivoMidiaUtils.calcularDuracaoMp4(midiaMp4.getLocal());
		assertTrue(midiaMp3.getDuracao() > 0);
		assertEquals(24, resultado);
	}
	
	@Test
	@DisplayName("Deve retornar o valor da duração em minutos do mkv inserido")
	public void testCalcularDuracaoMkv() {
		
		int resultado = ArquivoMidiaUtils.calcularDuracaoMkv(midiaMkv.getLocal());
		assertTrue(midiaMp3.getDuracao() > 0);
		assertEquals(24, resultado);
	}
	
	@Test
	@DisplayName("Deve retornar o valor da duração em segundos do mp3 inserido")
	public void testCalcularDuracaoMp3() {
		
		int resultado = ArquivoMidiaUtils.calcularDuracaoMp3(midiaMp3.getLocal());
		assertTrue(midiaMp3.getDuracao() > 0);
		assertEquals(204, resultado);
	}
	
	@Test
	@DisplayName("Deve substituir arquivo antigo e salvar novo arquivo ao editarMidiaArquivo")
	void testEditarMidiaArquivo() throws IOException {
	    //Cria uma mídia de teste
	    Musica musica = new Musica(
	        pasta + File.separator + "teste.mp3",
	        "Teste",
	        "POP",
	        "Autor"
	    );

	    ArquivoMidiaUtils.salvarMidiaMusica(musica);

	    File arquivoOriginal = new File(pasta + File.separator + "Teste.tpoo");
	    assertTrue(arquivoOriginal.exists());


	    musica.setTitulo("TesteEditado");
	    ArquivoMidiaUtils.editarMidiaArquivo(musica, "Teste");

	    assertFalse(arquivoOriginal.exists());

	    File arquivoNovo = new File(pasta + File.separator + "TesteEditado.tpoo");
	    assertTrue(arquivoNovo.exists());
	}
	
	@Test
	@DisplayName("Deve remover arquivo .tpoo existente")
	void testRemoverMidiaArquivo() throws IOException {
		
		ArquivoMidiaUtils.salvarMidiaMusica(midiaMp3);
		
		String tituloFormatado = midiaMp3.getTitulo().trim().replace(" ", "_");
		File arquivo = new File(ArquivoMidiaUtils.pasta, tituloFormatado + ".tpoo");
		
		assertTrue(arquivo.exists());
		
		ArquivoMidiaUtils.removerMidia(midiaMp3);
		
		assertFalse(arquivo.exists());
		
	}
	
	@Test
	@DisplayName("")
	void testCarregarMidia() {
		try {
			File arquivoTemp = File.createTempFile("livro_teste", ".tpoo");

		    try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemp))) {
		        writer.write("Tipo: Livro\n");
		        writer.write("Título: O Hobbit\n");
		        writer.write("Local: C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\hobbit.pdf\n");
		        writer.write("Tamanho: 2.5 bytes\n");
		        writer.write("Categoria: Fantasia\n");
		        writer.write("Duração: 310\n");
		        writer.write("Autor: J.R.R. Tolkien\n");
		        writer.write("Autor: Outro Autor\n");
		    }

		    Midia midia = ArquivoMidiaUtils.carregarMidia(arquivoTemp);

		    assertNotNull(midia);
		    assertTrue(midia instanceof Livro);

		    Livro livro = (Livro) midia;
		    
		    assertEquals("O Hobbit", livro.getTitulo());
		    assertEquals("C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\hobbit.pdf", livro.getLocal());
		    assertEquals("Fantasia", livro.getCategoria());
		    assertEquals(310, livro.getDuracao());
		    assertEquals(2, livro.getAutores().size());
		    assertEquals("J.R.R. Tolkien", livro.getAutores().get(0));
		    assertEquals("Outro Autor", livro.getAutores().get(1));

		    arquivoTemp.delete();
		}catch(IOException e) {
			e.printStackTrace();
		}
		 
	}

}
