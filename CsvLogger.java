package tools;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvLogger {

    private static final String FILE_NAME = "tempos.csv";
    private static final String HEADER = "tamanhoBuffer,min,max,numExecucao,numRemocao,numCompactacao,garbagePercent,bloco,tempo";

    public static void salvarDados(int tamanhoBuffer, int min, int max, int numExecucao, int numRemocao, int numCompactacao, int garbagePercent, int bloco, long tempo) {
        File file = new File(FILE_NAME);
        boolean fileExists = file.exists();

        try (FileWriter writer = new FileWriter(file, true)) {
            // Escreve o cabeçalho apenas se o arquivo ainda não existir
            if (!fileExists) {
                writer.write(HEADER + "\n");
            }

            // Adiciona a nova linha de dados
            writer.write(String.format("%d,%d,%d,%d,%d,%d,%d,%d,%d\n", tamanhoBuffer, min, max, numExecucao, numRemocao, numCompactacao,garbagePercent, bloco, tempo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
