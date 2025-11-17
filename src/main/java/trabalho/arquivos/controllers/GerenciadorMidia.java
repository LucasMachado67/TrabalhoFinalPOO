package trabalho.arquivos.controllers;

import java.util.List;

import javax.swing.JOptionPane;

import trabalho.arquivos.Utils.ArquivoMidiaUtils;
import trabalho.arquivos.classes.Filme;
import trabalho.arquivos.classes.Livro;
import trabalho.arquivos.classes.Midia;
import trabalho.arquivos.classes.Musica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class GerenciadorMidia {
	
	public GerenciadorMidia() {
	    carregarMidiasDoDisco();
	}
	
	private List<Midia> midias = new ArrayList<>();
	
	public void adicionarMidia(Midia midia) {
		
		boolean resultado = validadorCampos(midia);
		if(resultado == false) {
			System.out.println("DEBUG:");
			System.out.println("Local: " + midia.getLocal());
			System.out.println("Titulo: " + midia.getTitulo());
			System.out.println("Categoria: " + midia.getCategoria());
			System.out.println("Tipo: " + midia.getTipo());
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
	
	public Midia carregarMidia(File arquivo) {
		
		Midia midia = null;
		
		try(BufferedReader reader = new BufferedReader(new FileReader(arquivo))){
			
			String linha;
			String tipo = "";
			String titulo = "";
			String local = "";
			String categoria = "";
			String duracao = "";
			String tamanho = "";
			
	        while ((linha = reader.readLine()) != null) {

	        	if (linha.startsWith("Tipo:")) {
	                tipo = linha.substring(5).trim();

	                switch (tipo) {
	                    case "Livro":
	                        midia = new Livro("", "", "");
	                        break;
	                    case "Música":
	                        midia = new Musica("", "", "", "");
	                        break;
	                    case "Filme":
	                        midia = new Filme("", "", "", "");
	                        break;
	                    default:
	                        throw new RuntimeException("Tipo desconhecido: " + tipo);
	                }
	            }else if (linha.startsWith("Título:")) {
	                titulo = linha.substring(7).trim();
	                midia.setTitulo(titulo);

	            } else if (linha.startsWith("Local:")) {
	                local = linha.substring(6).trim();
	                midia.setLocal(local);

	            } else if (linha.startsWith("Tamanho:")) {
	                tamanho = linha.substring(8).trim().replace(" bytes", "");
	                midia.setTamanho(Long.parseLong(tamanho));

	            } else if (linha.startsWith("Categoria:")) {
	            	categoria = linha.substring(10).trim();
	            	midia.setCategoria(categoria);

	            } else if (linha.startsWith("Duração:")) {
	                duracao = linha.substring(8).trim();
	                midia.setDuracao(Integer.parseInt(duracao));
	            }
	            else if (linha.startsWith("Idioma:") && midia instanceof Filme) {
	                ((Filme) midia).setIdioma(linha.substring(7).trim());
	            }

	            else if (linha.startsWith("Artista:") && midia instanceof Musica) {
	                ((Musica) midia).setArtista(linha.substring(8).trim());
	            }

	            else if (linha.startsWith("Autor:") && midia instanceof Livro) {
	                String autor = linha.substring(6).trim();
	                ((Livro) midia).setAutor(autor); // adiciona na lista interna
	            }
	        
	        }
		}catch (Exception e) {
	        e.printStackTrace();
	    }
		return midia;
	}
	
	public void editarMidia(Midia midia, String tituloAntigo) {
		// validacao
        if (!validadorCampos(midia)) {
            throw new IllegalArgumentException("Dados inválidos");
        }

        // remove arquivo antigo se o título mudou
        if (!midia.getTitulo().equals(tituloAntigo)) {
            File pasta = new File("C:\\Users\\LucasMachado\\eclipse-workspace\\arquivos\\ArquivosTeste");
            File oldFile = new File(pasta, tituloAntigo.replace(" ", "_") + ".tpoo");

            if (oldFile.exists()) oldFile.delete();
        }

        // atualiza arquivo
        if (midia instanceof Livro) {
            ArquivoMidiaUtils.salvarMidiaEpubOrPdf(midia);
        } else if (midia instanceof Musica) {
            ArquivoMidiaUtils.salvarMidiaMusica(midia);
        } else if (midia instanceof Filme) {
            ArquivoMidiaUtils.salvarMidiaFilme(midia);
        }

        // substitui na lista
        for (int i = 0; i < midias.size(); i++) {
            if (midias.get(i).getTitulo().equalsIgnoreCase(tituloAntigo)) {
                midias.set(i, midia);
                break;
            }
        }
	}
	
	public List<Midia> listarMidias() {
		return new ArrayList<>(midias);
	}
	
	public List<Midia> filtrarPorCategoria(String categoria){
		return midias.stream()
				.filter(m -> m.getCategoria().equalsIgnoreCase(categoria))
				.toList();
	}
	
	public void removerMidia(Midia midia) {
		File dir = new File("C:\\Users\\LucasMachado\\eclipse-workspace\\arquivos\\ArquivosTeste");
		String tituloFormatado = midia.getTitulo().trim().replace(" ", "_");
		File arquivoToDelete = new File(dir, tituloFormatado + ".tpoo");
		if (arquivoToDelete.exists()) {
	        boolean deletado = arquivoToDelete.delete();

	        if (deletado) {
	            System.out.println("Arquivo .tpoo removido: " + arquivoToDelete.getAbsolutePath());
	        } else {
	            System.out.println("Falha ao remover arquivo: " + arquivoToDelete.getAbsolutePath());
	        }
	    } else {
	        System.out.println("Arquivo .tpoo não encontrado: " + arquivoToDelete.getAbsolutePath());
	    }
		this.midias.remove(midia);
		JOptionPane.showMessageDialog(null, "Arquivo " + midia.getTitulo()+" deletado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	public void moverMidia(Midia midia, String novoCaminho) {
			
		File arquivoAntigo = new File(midia.getLocal());
		File novoArquivo = new File(novoCaminho, arquivoAntigo.getName());
		
		try {
			Files.move(arquivoAntigo.toPath(), novoArquivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

			midia.setLocal(novoArquivo.getAbsolutePath());
			System.out.println("Mídia movida com sucesso para: " + novoArquivo.getAbsolutePath());
		} catch (IOException e) {
	        System.out.println("Erro ao mover a mídia: " + e.getMessage());
	        e.printStackTrace();
	    } 
	}
	
	public void renomearMidia(Midia midia, String novoNome) {
		
	}
	
	public List<Midia> listarPorFormato(String formato){
		
		List<Midia> lista = new ArrayList<>();
		for (Midia midia : midias) {
			if(formato.equals(midia.getTipo())) {
				lista.add(midia);
			}
		}
		
		return lista;
	}
	
	public void ordenarPorTitulo(List<Midia> midias) {
		midias.sort((m1, m2) -> m1.getTitulo().compareToIgnoreCase(m2.getTitulo()));
	}
	
	public void ordenarPorDuracao(List<Midia> midias) {
		midias.sort((m1,m2) -> Double.compare(m2.getDuracao(), m1.getDuracao()));
	}
	
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
	
	private void carregarMidiasDoDisco() {
		midias.clear();
		
		File pasta = new File("C:\\Users\\LucasMachado\\eclipse-workspace\\arquivos\\ArquivosTeste");
		
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
