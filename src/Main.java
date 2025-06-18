import java.util.Scanner;

public class Main {
    private static final String[] ARQUIVOS = {
            "Dracula-165307.txt",
            "MobyDick-217452.txt",
            "DonQuixote-388208.txt"
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Selecione o arquivo:");
        for (int i = 0; i < ARQUIVOS.length; i++) {
            System.out.printf("%d - %s\n", i+1, ARQUIVOS[i]);
        }

        int escolhaArquivo = 0;
        while (escolhaArquivo < 1 || escolhaArquivo > ARQUIVOS.length) {
            System.out.print("Digite o número do arquivo (1-" + ARQUIVOS.length + "): ");
            try {
                escolhaArquivo = sc.nextInt();
            } catch (Exception e) {
                sc.next();
            }
        }
        String caminhoArquivo = ARQUIVOS[escolhaArquivo-1];

        System.out.print("Digite a palavra a ser buscada: ");
        String palavraAlvo = sc.next();

        System.out.println("\n--- Executando buscas no arquivo " + caminhoArquivo + " ---");

        SerialReader serial = new SerialReader(caminhoArquivo, palavraAlvo);
        serial.executar();
        serial.mostrarResultado();

        ParallelReader paralelo = new ParallelReader(caminhoArquivo, palavraAlvo);
        paralelo.executar();
        paralelo.mostrarResultado();


        System.out.println("\n--- Resumo ---");
        System.out.println("Serial:   " + serial.getContagem() + " ocorrências");
        System.out.println("Paralelo: " + paralelo.getContagem() + " ocorrências");

        if (serial.getTempoExecucao() > 0 && paralelo.getTempoExecucao() > 0) {
            double speedupCPU = (double) serial.getTempoExecucao() / paralelo.getTempoExecucao();
            System.out.printf("\nSpeedup (CPU paralela): %.2fx mais rápido que serial\n", speedupCPU);
        }
    }
}