package trabalho.arquivos.controllers;

import java.util.List;

import javax.swing.JOptionPane;

import trabalho.arquivos.Utils.ArquivoMidiaUtils;
import trabalho.arquivos.classes.Filme;
import trabalho.arquivos.classes.Livro;
import trabalho.arquivos.classes.Midia;
import trabalho.arquivos.classes.Musica;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Controlador responsável pela gestão de mídias no Sistema.
 * <p>
 * Permite adicionar, editar, remover, mover, renomear e listar mídias, assim como 
 * carregar automaticamente mídias salvas do disco.
 * </p>
 * <p>
 * Os arquivos de mídia são salvos em formato .tpoo na pasta "C:\Users\LucasMachado\eclipse-workspace\Testes".
 * </p>
 * 
 */
public class GerenciadorMidia {
	/** Lista interna de mídias gerenciadas pelo sistema */
	private List<Midia> midias = new ArrayList<>();
	
	/**
     * Construtor da classe. Inicializa o gerenciador carregando mídias do disco.
     */
	public GerenciadorMidia() {
	    carregarMidiasDoDisco();
	}
	
	/**
     * Adiciona uma mídia ao sistema e a salva em disco.
     * 
     * @param midia Objeto {@link Midia} a ser adicionado
	 * @throws IOException 
     * @throws IllegalArgumentException Se os dados da mídia forem inválidos
     */
	public void adicionarMidia(Midia midia) throws IOException {
		
		boolean resultado = validadorCampos(midia);
		if(resultado == false) {
			throw new IllegalArgumentException("As credênciais da mídia estão incorretas ou faltando");
		}
		this.midias.add(midia);
		if(midia.getTipo().equals("Livro")) {
			ArquivoMidiaUtils.salvarMidiaEpubOrPdf(midia);
		}else if(midia.getTipo().equals("Música")){
			ArquivoMidiaUtils.salvarMidiaMusica(midia);
		}else if(midia.getTipo().equals("Filme")) {
			ArquivoMidiaUtils.salvarMidiaFilme(midia);
		}else {
			throw new IllegalAccessError();
		}
	}
	/**
     * Carrega uma mídia a partir de um arquivo .tpoo.
     * 
     * @param arquivo Arquivo .tpoo a ser lido
     * @return Objeto {@link Midia} carregado
     */
	public Midia carregarMidia(File arquivo) {
		if (arquivo == null || !arquivo.exists()) {
	        throw new IllegalArgumentException("Arquivo inválido");
	    }
		return ArquivoMidiaUtils.carregarMidia(arquivo);
		
	}
	/**
     * Edita uma mídia existente, atualizando arquivo e lista interna.
     * 
     * @param midia        Objeto {@link Midia} atualizado
     * @param tituloAntigo Título antigo da mídia
	 * @throws IOException 
     * @throws IllegalArgumentException Se os dados da mídia forem inválidos
     */
	public void editarMidia(Midia midia, String tituloAntigo) throws IOException {
		// validacao
        if (!validadorCampos(midia)) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        ArquivoMidiaUtils.editarMidiaArquivo(midia, tituloAntigo);

        // substitui na lista
        for (int i = 0; i < midias.size(); i++) {
            if (midias.get(i).getTitulo().equalsIgnoreCase(tituloAntigo)) {
                midias.set(i, midia);
                break;
            }
        }
	}
	/**
     * Retorna uma cópia da lista de todas as mídias cadastradas.
     * 
     * @return Lista de mídias
     */
	public List<Midia> listarMidias() {
		return new ArrayList<>(midias);
	}
	

	/**
     * Remove uma mídia do sistema e de disco.
     * 
     * @param midia Objeto {@link Midia} a ser removido
     */
	public void removerMidia(Midia midia) {
		
		ArquivoMidiaUtils.removerMidia(midia);
		this.midias.remove(midia);
		JOptionPane.showMessageDialog(null, "Arquivo " + midia.getTitulo()+" deletado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
     * Move o arquivo físico de uma mídia para um novo caminho.
     * 
     * @param midia      Objeto {@link Midia} a ser movido
     * @param novoCaminho Caminho destino
     */
	public void moverMidia(Midia midia, String novoCaminho) {
		
		try {
			File arquivoOrigem = new File(midia.getLocal());
			File arquivoDestino = new File(novoCaminho, arquivoOrigem.getName());
			
			Files.move(arquivoOrigem.toPath(), arquivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			//Atualizando o objeto mídia no sistema
			midia.setLocal(arquivoDestino.getAbsolutePath());
			
			ArquivoMidiaUtils.editarMidiaArquivo(midia, midia.getTitulo());
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
		}	
	}
	/**
     * Renomeia uma mídia, atualizando arquivo e objeto.
     * 
     * @param midia    Objeto {@link Midia} a ser renomeado
     * @param novoNome Novo título
     * @return true se a operação foi bem-sucedida, false caso contrário
     */
	public boolean renomearMidia(Midia midia, String novoNome) {
			
		 try {
		        // guarda o título antigo para o util para conseguir utilizar o método edit
		        String tituloAntigo = midia.getTitulo();
		        
		        File arquivoAntigo = new File(midia.getLocal());
		        if (!arquivoAntigo.exists()) {
		            System.out.println("Arquivo de mídia não encontrado: " + midia.getLocal());
		            return false;
		        }

		        // pega extensão e monta novo arquivo
		        String nomeAntigo = arquivoAntigo.getName();
		        String extensao = "";
		        int idx = nomeAntigo.lastIndexOf('.');
		        if (idx > 0) extensao = nomeAntigo.substring(idx);
		        String novoNomeFinal = novoNome + extensao;

		        File arquivoNovo = new File(arquivoAntigo.getParent(), novoNomeFinal);

		        
		        Files.move(arquivoAntigo.toPath(), arquivoNovo.toPath(), StandardCopyOption.REPLACE_EXISTING);

		        //Atualizando o objeto de mídia para o sistema
		        midia.setTitulo(novoNome);
		        midia.setLocal(arquivoNovo.getAbsolutePath());

		        // Usando o utils para recriar o tpoo com o local do arquivo atualizado e o titulo
		        ArquivoMidiaUtils.editarMidiaArquivo(midia, tituloAntigo);

		        return true;
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        return false;
		    }
	}
	/**
     * Lista mídias filtradas pelo tipo/formato.
     * 
     * @param formato Tipo de mídia ("Livro", "Música", "Filme")
     * @param midias Lista a ser ordenada
     * @return Lista filtrada
     */
	public List<Midia> listarPorFormato(String formato,List<Midia> midias){

		return midias = midias.stream()
		        .filter(m -> m.getTipo().equalsIgnoreCase(formato))
		        .toList();
	}
	/**
     * Lista mídias filtradas pela categoria.
     * 
     * @param categoria Categoria da mídia
     * @param midias Lista a ser ordenada
     * @return Lista filtrada
     */
	public List<Midia> listarPorCategoria(String categoria,List<Midia> midias){
		return midias = midias.stream()
                .filter(m -> m.getCategoria().equalsIgnoreCase(categoria))
                .toList();
	}
	/**
     * Ordena a lista de mídias pelo título em ordem alfabética.
     * 
     * @param midias Lista a ser ordenada
     */
	public List<Midia> ordenarPorTitulo(List<Midia> midias) {
		
		midias= new ArrayList<>(midias);
		midias.sort(Comparator.comparing(
                Midia::getTitulo, String.CASE_INSENSITIVE_ORDER));
        return midias;
	}
	/**
     * Ordena a lista de mídias pela duração em ordem decrescente.
     * 
     * @param midias Lista a ser ordenada
     */
	public List<Midia> ordenarPorDuracao(List<Midia> midias) {
		midias = new ArrayList<>(midias);
		midias.sort(Comparator.comparingInt(Midia::getDuracao));
		return midias;
	}
	/**
     * Valida os campos obrigatórios de uma mídia.
     * 
     * @param midia Objeto {@link Midia} a validar
     * @return true se válido, false caso contrário
     */
	private boolean validadorCampos(Midia midia) {
		
		if(midia.getLocal() == null || midia.getLocal().isBlank()) return false;
		if(midia.getTitulo() == null || midia.getTitulo().isBlank()) return false;
		if(midia.getCategoria() == null || midia.getCategoria().isBlank()) return false;
		
		String tipo = midia.getTipo().trim().toLowerCase();
		switch (tipo) {
	        case "livro":
	            Livro livro = (Livro) midia;
	            return livro.getAutores() != null && !livro.getAutores().isEmpty();
	
	        case "música":
	        case "musica": 
	            Musica musica = (Musica) midia;
	            return musica.getArtista() != null && !musica.getArtista().isBlank();
	
	        case "filme":
	            Filme filme = (Filme) midia;
	            return filme.getIdioma() != null && !filme.getIdioma().isBlank();
	
	        default:
	            return false;
	    }
	}
	/**
     * Carrega todas as mídias salvas do disco e atualiza a lista interna.
     */
	private void carregarMidiasDoDisco() {
		midias.clear();
		
		File pasta = new File("C:\\Users\\LucasMachado\\eclipse-workspace\\Testes");
		
		if (!pasta.exists()) return;
		
		File[] arquivos = pasta.listFiles((dir, name) -> name.endsWith(".tpoo"));
		if (arquivos == null) return;
		
		for (File arq : arquivos) {
			Midia m = carregarMidia(arq);
			if (m != null) {
				midias.add(m);
			}
		}
	}
}
