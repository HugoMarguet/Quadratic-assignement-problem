import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Data {

    private int[][] distances;
    private int[][] weights;

    private int size;

    public Data(String filePath) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        size = Integer.valueOf(removeAlinea(bufferedReader.readLine()));

        bufferedReader.readLine();
        distances = readTable(bufferedReader, size);
        bufferedReader.readLine();
        weights = readTable(bufferedReader, size);
    }

    public Data(int[][] distances, int[][] weights, int size) {

        this.distances = distances;
        this.weights = weights;
        this.size = size;
    }

    private int[][] readTable(BufferedReader bufferedReader, int size) throws IOException {
        int[][] table = new int[size][size];
        for (int i = 0; i < size; i++) {
            String line = removeAlinea(bufferedReader.readLine());
            table[i] = Arrays
                    .stream(line.split(" "))
                    .filter(s -> s.length() != 0)
                    .mapToInt(Integer::valueOf)
                    .toArray();
        }
        return table;
    }

    private String removeAlinea(String line) {
        int nbEspace = 0;
        while(line.length() > nbEspace && line.charAt(nbEspace) == ' ')
            nbEspace++;
        return line.substring(nbEspace);
    }
    public int[][] getDistances() {
        return distances;
    }

    public int[][] getWeights() {
        return weights;
    }

    public int getSize() { return size; }

    @Override
    public String toString() {

        final StringBuilder distances = new StringBuilder();
        final StringBuilder weights = new StringBuilder();

        for(int i = 0; i < size; i++) {
            distances.append("[ ");
            weights.append("[ ");
            for (int j = 0; j < size; j++){
                distances.append(this.distances[i][j]).append(" ");
                weights.append(this.weights[i][j]).append(" ");
            }
            distances.append("]\n");
            weights.append("]\n");
        }

        return "Data {" + "\n" +
                "size :" + this.size + "\n" +
                "distances :\n" + distances.toString() +
                "weights :\n" + weights.toString() +
                "}\n";
    }

    public Data clone() {

        return new Data(Arrays.copyOf(distances, size), Arrays.copyOf(weights, size), size);
    }
}