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

    public static void main(String[] args) {
        String caminhoArquivo = "MobyDick-217452.txt";
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite a palavra: ");
        String palavraAlvo = sc.next();

        final int numThreads = Runtime.getRuntime().availableProcessors(); // número ideal de threads
        List<String> todasLinhas = new ArrayList<>();

        // Lê todo o arquivo na memória
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                todasLinhas.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }

        // Divide as linhas em blocos para cada thread
        int linhasPorThread = (int) Math.ceil((double) todasLinhas.size() / numThreads);
        List<ContadorThread> threads = new ArrayList<>();

        for (int i = 0; i < todasLinhas.size(); i += linhasPorThread) {
            int fim = Math.min(i + linhasPorThread, todasLinhas.size());
            List<String> bloco = todasLinhas.subList(i, fim);
            ContadorThread thread = new ContadorThread(bloco, palavraAlvo);
            threads.add(thread);
            thread.start(); // inicia a thread
        }

        // Espera todas as threads terminarem
        int total = 0;
        for (ContadorThread thread : threads) {
            try {
                thread.join();
                total += thread.getContagem();
            } catch (InterruptedException e) {
                System.err.println("Thread interrompida: " + e.getMessage());
            }
        }

        System.out.println("A palavra \"" + palavraAlvo + "\" aparece " + total + " vezes.");
    }
}
