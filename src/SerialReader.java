import java.io.*;
import java.util.*;

public class SerialReader {
    private String caminhoArquivo;
    private String palavraAlvo;
    private long tempoExecucao;
    private int contagem;

    public SerialReader(String caminhoArquivo, String palavraAlvo) {
        this.caminhoArquivo = caminhoArquivo;
        this.palavraAlvo = palavraAlvo.toLowerCase();
    }

    public void executar() {
        long inicio = System.currentTimeMillis();
        contagem = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] palavras = linha.split("[\\s\\p{Punct}]+");
                for (String palavra : palavras) {
                    if (palavra.toLowerCase().contains(palavraAlvo)) {
                        contagem++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        tempoExecucao = System.currentTimeMillis() - inicio;
    }

    public void mostrarResultado() {
        System.out.println("[Serial] A palavra \"" + palavraAlvo + "\" aparece " + contagem +
                " vezes em " + tempoExecucao + " milissegundos.");
    }

    public int getContagem() {
        return contagem;
    }

    public long getTempoExecucao() {
        return tempoExecucao;
    }
}