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
        int i = 0;
        while(line.length() > i && line.charAt(i) == ' ') i++;
        return line.substring(i);
    }
    public int[][] getDistances() {
        return distances;
    }

    public int[][] getWeights() {
        return weights;
    }

    @Override
    public String toString() {
        StringBuilder distances = new StringBuilder("[\n");
        StringBuilder weights = new StringBuilder("[\n");
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
        distances.append("]");
        weights.append("]");
        return "size :" + this.size + "\n" +
                "distances :\n" + distances.toString() + "\n" +
                "weights :\n" + weights.toString();
    }
}