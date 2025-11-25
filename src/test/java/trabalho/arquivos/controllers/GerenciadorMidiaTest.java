package trabalho.arquivos.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import trabalho.arquivos.classes.Filme;
import trabalho.arquivos.classes.Livro;
import trabalho.arquivos.classes.Midia;
import trabalho.arquivos.classes.Musica;

public class GerenciadorMidiaTest  {
	
	String pasta = "C:\\Users\\LucasMachado\\eclipse-workspace\\Testes";
		
	private Midia midiaMp3;
	private Midia midiaPdf;
	private Midia midiaEpub;
	private Midia midiaMp4;
	private Midia midiaMkv;
	public GerenciadorMidia gerenciador;
	private List<Midia> midias = new ArrayList<>();
	
	@BeforeEach
	void setup() {
		gerenciador = new GerenciadorMidia();
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
		
		midias.add(midiaEpub);
		midias.add(midiaPdf);
		midias.add(midiaMp3);
		midias.add(midiaMp4);
		midias.add(midiaMkv);
	}
	
		
		@Test
		@DisplayName("Deve salvar midia (Musica) quando chamar método AdicionarMidia")
		public void testSalvarMidiaMusica() throws IOException {
			
			GerenciadorMidia gerenciador = new GerenciadorMidia();
			gerenciador.adicionarMidia(midiaMp3);
			
			assertTrue(gerenciador.listarMidias().contains(midiaMp3), "A mídia deve estar na lista do gerenciador");
			assertEquals(midiaMp3.getTipo(), "Música");
			assertEquals(midiaMp3.getTitulo(), "O sol");
			assertEquals(midiaMp3.getCategoria(), "POP");
			assertEquals(midiaMp3.getLocal(), "C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\O sol.mp3");
			Midia midia = midiaMp3;
			if (midia instanceof Musica musica) {
			    assertEquals("Vitor kley", musica.getArtista());
			}
		}
		
		@Test
		@DisplayName("Deve jogar exceção quando chamar método AdicionarMidia com mídia Inválida (Teste do validarCampos)")
		public void testSalvarMidiaMusicaJogaException() throws IOException {
			
			Midia midiaMoverTeste = new Musica("C:\\Users\\Lucas Machado\\Downloads\\5 - Diagrama de Objetos.pdf", "", "POP", "James");
			
			assertThrows(IllegalArgumentException.class, () -> gerenciador.adicionarMidia(midiaMoverTeste));
		}
		
		
		@Test
		@DisplayName("Deve lançar exceção se for um arquivo inválido ou local inválido")
		public void testCarregarMidia() {
			File arquivo = new File("Z:\\local inválido");
			assertThrows(IllegalArgumentException.class, () -> gerenciador.carregarMidia(arquivo));
			
			File arquivo1 = null;
			assertThrows(IllegalArgumentException.class, () -> gerenciador.carregarMidia(arquivo1));
		}
		
		@Test
		@DisplayName("Deve editar arquivo quando a mídia for passada")
		public void testEditarMidia() {
			
			String tituloOriginal = midiaEpub.getTitulo();
			
			midiaEpub.setTitulo("Novo titulo para teste para Editar");
			try {
				gerenciador.editarMidia(midiaEpub, tituloOriginal);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			assertEquals("Novo titulo para teste para Editar", midiaEpub.getTitulo());
		}
		
		@Test
		@DisplayName("Deve retornar lista de mídias")
		public void testListarMidias() {
			
			List<Midia> lista = gerenciador.listarMidias();
			
			for(Midia m : lista) {
				File arquivo = new File(m.getLocal());
				System.out.println(arquivo.getName());
				assertTrue(arquivo.exists());
			}
		}
		
		@Test
		@DisplayName("Deve remover midia")
		public void testRemoverMidia() throws IOException {
			
			Midia midiaMp3TesteRemover = new Musica(
					"C:\\Users\\LucasMachado\\eclipse-workspace\\Testes\\O sol Teste.mp3",
					"O sol teste",
					"Jazz",
					"Vitor kley"
					);
			File arquivo = new File("C:\\Users\\LucasMachado\\eclipse-workspace\\Testes",
					midiaMp3TesteRemover.getTitulo().replace(" ", "_") + ".tpoo");

		    arquivo.createNewFile();
		    assertTrue(arquivo.exists());

		    gerenciador.removerMidia(midiaMp3TesteRemover);

		    assertFalse(arquivo.exists());
		}
		
		@Test
		@DisplayName("Deve mover midia para outro caminho")
		public void testMoverMidia() throws IOException {
			
			Path arquivoOriginal = Files.createTempFile("teste-musica", ".mp3");
		    String caminhoOriginal = arquivoOriginal.toAbsolutePath().toString();

		    Midia midiaMoverTeste = new Musica(
		        caminhoOriginal,
		        "Musica de teste Mover Arquivo",
		        "POP",
		        "James"
		    );

		    gerenciador.adicionarMidia(midiaMoverTeste);

		    Path pastaDestino = Files.createTempDirectory("destino-mover-midia");

		    gerenciador.moverMidia(midiaMoverTeste, pastaDestino.toString());

		    File novoArquivo = new File(
		        pastaDestino.toString(),
		        arquivoOriginal.getFileName().toString()
		    );


		    assertFalse(Files.exists(arquivoOriginal));

		    assertTrue(novoArquivo.exists());

		    assertEquals(novoArquivo.getAbsolutePath(),midiaMoverTeste.getLocal());
		}
		
		@Test
		@DisplayName("Deve listar apenas mídias com o formato informado")
		public void testListarPorFormato() {

			Musica m1 = new Musica("c:/a.mp3", "Música 1", "Pop", "Art");
		    Livro l1 = new Livro("c:/b.pdf", "Livro 1", "Cat");
		    Filme f1 = new Filme("c:/c.mp4", "Filme 1", "Cat", "Ing");

		    List<Midia> lista = List.of(m1, l1, f1);

		    List<Midia> resultado = gerenciador.listarPorFormato("Música", lista);

		    assertEquals(1, resultado.size());
		    assertTrue(resultado.contains(m1));
		}
		
		@Test
		@DisplayName("Deve listar mídias pela categoria")
		public void testListarPorCategoria() {

		    Musica m1 = new Musica("c:/a.mp3", "Música 1", "Pop", "Art");

		    Musica m2 = new Musica("c:/b.mp3", "Música 2", "Rock", "Art");

		    Livro l1 = new Livro("c:/c.pdf", "Livro 1", "Pop");

		    List<Midia> lista = List.of(m1, m2, l1);

		    List<Midia> resultado = gerenciador.listarPorCategoria("Pop", lista);

		    assertEquals(2, resultado.size());
		    assertTrue(resultado.contains(m1));
		    assertTrue(resultado.contains(l1));
		}
		
		@Test
		@DisplayName("Deve ordenar mídias pelo título em ordem alfabética")
		public void testOrdenarPorTitulo() {

		    Musica m1 = new Musica("c:/a.mp3", "Zebra", "Pop", "Art");
		    Livro l1 = new Livro("c:/b.pdf", "Abacate", "Cat");
		    Filme f1 = new Filme("c:/c.mp4", "Manga", "Cat", "Ing");

		    List<Midia> lista = List.of(m1, l1, f1);

		    List<Midia> resultado = gerenciador.ordenarPorTitulo(lista);

		    assertEquals("Abacate", resultado.get(0).getTitulo());
		    assertEquals("Manga", resultado.get(1).getTitulo());
		    assertEquals("Zebra", resultado.get(2).getTitulo());
		}
		
		@Test
		@DisplayName("Deve ordenar mídias pela duracao")
		public void testOrdenarPorDuracao() {

		    Musica m1 = new Musica("c:/a.mp3", "Zebra", "Pop", "Art");
		    m1.setDuracao(10);
		    Livro l1 = new Livro("c:/b.pdf", "Abacate", "Cat");
		    l1.setDuracao(20000);
		    Filme f1 = new Filme("c:/c.mp4", "Manga", "Cat", "Ing");
		    f1.setDuracao(100);
		    
		    List<Midia> lista = List.of(m1, l1, f1);

		    List<Midia> resultado = gerenciador.ordenarPorDuracao(lista);

		    assertEquals(10, resultado.get(0).getDuracao());
		    assertEquals(100, resultado.get(1).getDuracao());
		    assertEquals(20000, resultado.get(2).getDuracao());
		}
		
		@Test
		@DisplayName("Deve renomear a mídia corretamente")
		void testRenomearMidia() throws Exception {

		    File arquivoOriginal = new File(pasta, "MusicaTeste.mp3");
		    arquivoOriginal.createNewFile();

		    // Criar mídia associada
		    Midia midia = new Musica(
		            arquivoOriginal.getAbsolutePath(),
		            "MusicaTeste",
		            "POP",
		            "Autor"
		    );

		    boolean resultado = gerenciador.renomearMidia(midia, "NovoNome");

		    assertTrue(resultado);

		    File arquivoRenomeado = new File(pasta, "NovoNome.mp3");
		    //Testando se o arquivo com o novo nome existe
		    assertTrue(arquivoRenomeado.exists());
		    //Testando se o arquivo orignal não existe
		    assertFalse(arquivoOriginal.exists());
		    
		    assertEquals("NovoNome", midia.getTitulo());
		    assertEquals(arquivoRenomeado.getAbsolutePath(), midia.getLocal());
		}

		
}
