import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelMatrixTranspose {
    public static void main(String[] args) throws InterruptedException {
        int[][] matrix = generateMatrix(1000, 1000); // 1000x1000 boyutunda bir matris oluşturuyoruz


        System.out.println("Orjinal Matris:");
        printMatrix(matrix);

        int[][] transposedMatrix = transposeMatrix(matrix); // Paralel olarak matrisin transpozunu alıyoruz


        System.out.println("\nTranspoz Matris:");
        printMatrix(transposedMatrix);
    }


    public static int[][] generateMatrix(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (int) (Math.random() * 100); // 0-99 arası rastgele sayılarla dolduruyoruz
            }
        }
        return matrix;
    }


    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }


    public static int[][] transposeMatrix(int[][] matrix) throws InterruptedException {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] transposedMatrix = new int[cols][rows];

        // ThreadPool oluşturuyoruz
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());


        for (int i = 0; i < cols; i++) {
            final int col = i;
            executor.submit(() -> {
                for (int j = 0; j < rows; j++) {
                    transposedMatrix[col][j] = matrix[j][col];
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return transposedMatrix;
    }
}
