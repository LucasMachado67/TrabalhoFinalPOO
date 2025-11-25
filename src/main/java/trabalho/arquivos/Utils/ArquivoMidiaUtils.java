package trabalho.arquivos.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import trabalho.arquivos.classes.Filme;
import trabalho.arquivos.classes.Livro;
import trabalho.arquivos.classes.Midia;
import trabalho.arquivos.classes.Musica;
/**
 * Classe utilitária para manipulação de arquivos de mídias .tpoo.
 * <p>
 * Fornece métodos para salvar, editar e calcular a duração de mídias como músicas, filmes e livros
 * (PDF/EPUB). Também lida com leitura de arquivos binários e compactados.
 * </p>
 * 
 * <p>
 * Os arquivos salvos são armazenados na pasta definida pela variável {@link #pasta}.
 * </p>
 */
public class ArquivoMidiaUtils {
	/** Caminho da pasta onde os arquivos .tpoo serão salvos */
	public static String pasta = "C:\\Users\\LucasMachado\\eclipse-workspace\\Testes";
	
	/**
     * Salva os dados de uma música em um arquivo .tpoo.
     * 
     * @param midia Objeto {@link Musica} a ser salvo
	 * @throws IOException caso não conseguir criar o arquivo .tpoo
     */
	public static void salvarMidiaMusica(Midia midia) throws IOException {
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
            
		} 
	}
	/**
     * Salva os dados de um livro (PDF ou EPUB) em um arquivo .tpoo.
     * 
     * @param midia Objeto {@link Livro} a ser salvo
	 * @throws IOException caso não conseguir criar o arquivo .tpoo
     */
	public static void salvarMidiaEpubOrPdf(Midia midia) throws IOException {
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
		} 
	}
	/**
     * Salva os dados de um filme em um arquivo .tpoo.
     * 
     * @param midia Objeto {@link Filme} a ser salvo
	 * @throws IOException caso não conseguir criar o arquivo .tpoo
     */
	public static void salvarMidiaFilme(Midia midia) throws IOException {
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
            
		} 
	}
	/**
     * Edita um arquivo .tpoo existente, apagando o arquivo antigo e salvando a nova mídia.
     * 
     * @param midia       Objeto {@link Midia} atualizado
     * @param tituloAntigo Título antigo da mídia a ser substituída
	 * @throws IOException caso não conseguir editar o arquivo .tpoo
     */
	public static void editarMidiaArquivo(Midia midia, String tituloAntigo) throws IOException {

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
	/**
     * Carrega uma mídia a partir de um arquivo .tpoo.
     * 
     * @param arquivo Arquivo .tpoo a ser lido
     * @return Objeto {@link Midia} carregado
     */
	public static Midia carregarMidia(File arquivo) {
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
		                midia.setTamanhoMb(Double.parseDouble(tamanho));
	
	
	
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
	/**
     * Remove uma mídia do sistema e de disco.
     * 
     * @param midia Objeto {@link Midia} a ser removido
     */
	public static void removerMidia(Midia midia) {
		
		String tituloFormatado = midia.getTitulo().trim().replace(" ", "_");
		File arquivoToDelete = new File(pasta, tituloFormatado + ".tpoo");
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
	}
	/**
     * Calcula a duração de um PDF em número de páginas.
     * 
     * @param local Caminho do arquivo PDF
     * @return Número de páginas do PDF
     */
	public static int calcularDuracaoPdf(String local) {
		try {
			// Abre o arquivo binário
	        RandomAccessFile raf = new RandomAccessFile(local, "r");

	        // Lê todo o conteúdo em bytes
	        byte[] buffer = new byte[(int) raf.length()];
	        raf.readFully(buffer);
	        raf.close();

	        // Converte bytes para String mantendo 1:1 (ISO-8859-1 preserva os bytes)
	        String content = new String(buffer, StandardCharsets.ISO_8859_1);

	        // Expressão para capturar valores como "/Count 22"
	        Pattern pattern = Pattern.compile("/Count\\s+(\\d+)");
	        Matcher matcher = pattern.matcher(content);

	        int maxCount = -1;

	        // Procura TODAS as ocorrências de "/Count"
	        while (matcher.find()) {
	            int count = Integer.parseInt(matcher.group(1));
	            if (count > maxCount) {
	                maxCount = count;
	            }
	        }

	        if (maxCount == -1) {
	            throw new Exception("Nenhuma tag /Count encontrada no PDF.");
	        }

	        return maxCount;
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
		
	}
	 /**
     * Calcula o número de capítulos de um arquivo EPUB.
     * 
     * @param local Caminho do arquivo EPUB
     * @return Número de capítulos
     */
	public static int calcularCapitulosEpub(String local) {
		try {
			ZipFile zipFile = new ZipFile(local);

	        // 1. Encontrar o arquivo content.opf
	        ZipEntry container = zipFile.getEntry("META-INF/container.xml");
	        if (container == null) {
	        	zipFile.close();
	            throw new Exception("container.xml não encontrado");
	        }

	        // Ler container.xml
	        InputStream is = zipFile.getInputStream(container);
	        String containerXml = new String(is.readAllBytes(), "UTF-8");

	        // Encontrar caminho do content.opf
	        Pattern rootPattern = Pattern.compile("<rootfile[^>]*full-path=\"([^\"]+)\"");
	        Matcher matcher = rootPattern.matcher(containerXml);

	        if (!matcher.find()) {
	        	zipFile.close();
	            throw new Exception("full-path do content.opf não encontrado");
	        }

	        String contentOpfPath = matcher.group(1);

	        // Ler content.opf
	        ZipEntry opfEntry = zipFile.getEntry(contentOpfPath);
	        if (opfEntry == null) {
	        	zipFile.close();
	            throw new Exception("content.opf não encontrado");
	        }

	        is = zipFile.getInputStream(opfEntry);
	        String opfContent = new String(is.readAllBytes(), "UTF-8");

	        // Contar itemref (cada um é um capítulo)
	        Pattern itemrefPattern = Pattern.compile("<itemref[^>]*idref=");
	        Matcher m2 = itemrefPattern.matcher(opfContent);

	        int count = 0;
	        while (m2.find()) {
	            count++;
	        }

	        zipFile.close();
	        return count;
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	 /**
     * Calcula a duração de um arquivo MP3 em segundos.
     * 
     * @param local Caminho do arquivo MP3
     * @return Duração em segundos
     */
	public static int calcularDuracaoMp3(String local) {
		try {
			
			FileInputStream fis = new FileInputStream(local);
			
	        // Verifica se a tag ID3v2 e pula se existir
	        byte[] id3 = new byte[10];
	        fis.read(id3);

	        if (id3[0] == 'I' && id3[1] == 'D' && id3[2] == '3') {
	            int size = (id3[6] & 0x7F) << 21 |
	                       (id3[7] & 0x7F) << 14 |
	                       (id3[8] & 0x7F) << 7  |
	                       (id3[9] & 0x7F);

	            fis.skip(size); // pula tag ID3v2 completa
	        } else {
	            fis.close();
	            fis = new FileInputStream(local);
	        }

	        byte[] header = new byte[4];
	        int durationMs = 0;

	        while (fis.read(header) == 4) {

	            // Verifica sync (0xFFE)
	            if ((header[0] & 0xFF) == 0xFF && (header[1] & 0xE0) == 0xE0) {

	                int bitrateIndex = (header[2] & 0xF0) >> 4;
	                int samplingIndex = (header[2] & 0x0C) >> 2;
	                int padding = (header[2] & 0x02) >> 1;

	                int[] bitrates = {
	                    0, 32, 40, 48, 56, 64, 80, 96,
	                    112, 128, 160, 192, 224, 256, 320, 0
	                };

	                int[] samplingRates = {44100, 48000, 32000, 0};

	                int bitrate = bitrates[bitrateIndex] * 1000;
	                int samplingRate = samplingRates[samplingIndex];

	                if (bitrate == 0 || samplingRate == 0) break;

	                int frameLength = (144 * bitrate) / samplingRate + padding;

	                fis.skip(frameLength - 4);

	                durationMs += 26;
	            } else {
	                fis.skip(1);
	            }
	        }

	        fis.close();

	        return durationMs / 1000;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
     * Lê um valor UInt32 de um arquivo binário.
     * 
     * @param raf RandomAccessFile
     * @return Valor lido como long
     * @throws Exception Se houver erro de leitura
     */
	private static long readUInt32(RandomAccessFile raf) throws Exception {
        return ((raf.readByte() & 0xFFL) << 24) |
               ((raf.readByte() & 0xFFL) << 16) |
               ((raf.readByte() & 0xFFL) << 8) |
               (raf.readByte() & 0xFFL);
    }
	/**
     * Calcula a duração de um arquivo MP4 em minutos.
     * 
     * @param local Caminho do arquivo MP4
     * @return Duração em minutos
     */
	public static int calcularDuracaoMp4(String local) {
		try {
			RandomAccessFile raf = new RandomAccessFile(local, "r");

	        while (raf.getFilePointer() < raf.length()) {

	            long boxStart = raf.getFilePointer();
	            int size = raf.readInt();
	            String type = "" +
	                (char) raf.readByte() +
	                (char) raf.readByte() +
	                (char) raf.readByte() +
	                (char) raf.readByte();

	            if (size < 8) break;

	            if (type.equals("moov")) {
	                long moovEnd = boxStart + size;

	                while (raf.getFilePointer() < moovEnd) {

	                    long innerStart = raf.getFilePointer();
	                    int innerSize = raf.readInt();
	                    String innerType = "" +
	                        (char) raf.readByte() +
	                        (char) raf.readByte() +
	                        (char) raf.readByte() +
	                        (char) raf.readByte();

	                    if (innerType.equals("mvhd")) {

	                        int version = raf.readByte() & 0xFF;
	                        raf.skipBytes(3);

	                        // versão 0 → timestamps 32 bits / versão 1 → 64 bits
	                        raf.skipBytes(version == 1 ? 16 : 8);

	                        long timeScale = readUInt32(raf);
	                        long duration = readUInt32(raf);

	                        raf.close();

	                        double seconds = (double) duration / timeScale;
	                        return  (int) Math.round(seconds / 60.0);

	                    }

	                    raf.seek(innerStart + innerSize);
	                }
	            }

	            raf.seek(boxStart + size);
	        }

	        raf.close();
	        return 0;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
     * Calcula a duração de um arquivo MKV em minutos.
     * 
     * @param local Caminho do arquivo MKV
     * @return Duração em minutos
     */
	public static int calcularDuracaoMkv(String local) {
		try {
			byte[] data = Files.readAllBytes(Path.of(local));
	        String s = new String(data, StandardCharsets.ISO_8859_1);

	        int idx = s.indexOf("DURATION");
	        if (idx < 0) return 0;

	        // pula o nome da tag + possíveis bytes estranhos
	        int start = idx + "DURATION".length();

	        // extrai algo como "00:03:03.129"
	        StringBuilder sb = new StringBuilder();
	        for (int i = start; i < s.length(); i++) {
	            char c = s.charAt(i);
	            if ((c >= '0' && c <= '9') || c == ':' || c == '.') {
	                sb.append(c);
	            } else if (sb.length() > 0) break;
	        }

	        String time = sb.toString();
	        if (!time.contains(":")) return 0;

	        String[] p = time.split(":");
	        double h = Double.parseDouble(p[0]);
	        double m = Double.parseDouble(p[1]);
	        double s2 = Double.parseDouble(p[2]);

	        double totalMinutes = h * 60 + m + s2 / 60.0;
	        return (int) Math.round(totalMinutes);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
