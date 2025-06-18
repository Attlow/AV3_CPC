import java.io.*;
import java.util.*;

public class ParallelReader {
    static class ContadorThread extends Thread {
        private final List<String> linhas;
        private final String palavraAlvo;
        private int contagem;

        public ContadorThread(List<String> linhas, String palavraAlvo) {
            this.linhas = linhas;
            this.palavraAlvo = palavraAlvo.toLowerCase();
        }

        public int getContagem() {
            return contagem;
        }

        @Override
        public void run() {
            for (String linha : linhas) {
                String[] palavras = linha.split("[\\s\\p{Punct}]+");
                for (String palavra : palavras) {
                    if (palavra.toLowerCase().contains(palavraAlvo)) {
                        contagem++;
                    }
                }
            }
        }
    }

    private String caminhoArquivo;
    private String palavraAlvo;
    private long tempoExecucao;
    private int contagem;

    public ParallelReader(String caminhoArquivo, String palavraAlvo) {
        this.caminhoArquivo = caminhoArquivo;
        this.palavraAlvo = palavraAlvo.toLowerCase();
    }

    public void executar() {
        long inicio = System.currentTimeMillis();
        final int numThreads = Runtime.getRuntime().availableProcessors();
        List<String> todasLinhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                todasLinhas.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        int linhasPorThread = (int) Math.ceil((double) todasLinhas.size() / numThreads);
        List<ContadorThread> threads = new ArrayList<>();

        for (int i = 0; i < todasLinhas.size(); i += linhasPorThread) {
            int fim = Math.min(i + linhasPorThread, todasLinhas.size());
            List<String> bloco = todasLinhas.subList(i, fim);
            ContadorThread thread = new ContadorThread(bloco, palavraAlvo);
            threads.add(thread);
            thread.start();
        }

        contagem = 0;
        for (ContadorThread thread : threads) {
            try {
                thread.join();
                contagem += thread.getContagem();
            } catch (InterruptedException e) {
                System.err.println("Thread interrompida: " + e.getMessage());
            }
        }

        tempoExecucao = System.currentTimeMillis() - inicio;
    }

    public void mostrarResultado() {
        System.out.println("[Paralelo] A palavra \"" + palavraAlvo + "\" aparece " + contagem +
                " vezes em " + tempoExecucao + " milissegundos.");
    }

    public int getContagem() {
        return contagem;
    }

    public long getTempoExecucao() {
        return tempoExecucao;
    }
}