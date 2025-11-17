package trabalho.arquivos.classes;
import java.io.File;

import javax.swing.JOptionPane;

import org.mp4parser.IsoFile;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

public class Filme extends Midia{
	
	private String idioma;
	
	public Filme(String local, String titulo, String categoria, String idioma) {
		super(local, titulo, categoria);
		setDuracao(calcularDuracao(local));
		setIdioma(idioma);
	}

	@Override
	public String getTipo() {
		return "Filme";
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	
	@Override
    public int calcularDuracao(String local) {
		
		//Validação isnicial para ver qual o tipo de extensao que o usuario seleciona
		File arquivo = new File(local);
		String nomeArquivo = arquivo.getName();
		
		String extensao = "";
		int i = nomeArquivo.lastIndexOf('.');
		extensao = nomeArquivo.substring(i + 1);
		if(extensao.equals("mp4")) {
			try {
				// Lê o MP4
				IsoFile isoFile = new IsoFile(local);
				
				long duration = isoFile.getMovieBox().getMovieHeaderBox().getDuration();
				long timescale = isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
				
				if (timescale == 0) {	        	
					isoFile.close(); 
					return 0;
				}
				
				double seconds = (double) duration / timescale;
				int minutos = (int) Math.ceil(seconds / 60.0);
				
				isoFile.close(); 
				return minutos;
				
			} catch (Exception e) {
				System.out.println("Erro ao ler duração do vídeo: " + e.getMessage());
				return 0;
			}
		}else if(extensao.equals("mkv")){
			try {
				FFprobe ffprobe = new FFprobe("C:\\devTools\\ffmpeg-8.0-essentials_build\\bin\\ffprobe.exe");
				FFmpegProbeResult probeResult = ffprobe.probe(local);
				
				FFmpegFormat format = probeResult.getFormat();
				double duration = format.duration;
				int minutos = (int) Math.ceil(duration / 60.0);
				return minutos;
				
			} catch (Exception e) {
				System.out.println("Erro ao ler duração do vídeo: " + e.getMessage());
				return 0;
			}
		}
		return 0;
    }

}
