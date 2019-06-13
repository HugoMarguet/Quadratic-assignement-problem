import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QAP {

    private Map<Integer, Integer> locationWithFacility;
    private int[][] distances;
    private int[][] weights;
    private int size;

    private int optimalSolution;

    public QAP(String filePath, int optimalSolution) throws IOException {

        this.optimalSolution = optimalSolution;


        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        size = Integer.valueOf(removeAlinea(bufferedReader.readLine()));

        bufferedReader.readLine();
        distances = readTable(bufferedReader, size);
        bufferedReader.readLine();
        weights = readTable(bufferedReader, size);
        locationWithFacility = new HashMap<>();
        for (int i = 0; i < size; i++)
            locationWithFacility.put(i,i);
    }

    public QAP(Map<Integer, Integer> locationWithFacility, int size, int[][] distances, int[][] weights, int optimalSolution) {

        this.distances = distances;
        this.weights = weights;
        this.size = size;
        this.locationWithFacility = locationWithFacility;
        this.optimalSolution = optimalSolution;
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

    public int sum() {

        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : locationWithFacility.entrySet())
            for (int i = 0; i < size; i++)
                sum += weights[entry.getKey()][i] * distances[entry.getValue()][locationWithFacility.get(i)];

        return sum;
    }

    public void permute(int i, int j) {

        int temp = locationWithFacility.get(i);
        locationWithFacility.put(i, locationWithFacility.get(j));
        locationWithFacility.put(j,temp);
    }

    public boolean isOptimal() throws Exception {
        int sum = sum();
        if (sum < optimalSolution)
            throw new Exception("Solution can't be better than optimal one");
        return sum == optimalSolution;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, Integer> getLocationWithFacility() {
        return locationWithFacility;
    }

    public int getOptimalSolution() {
        return optimalSolution;
    }

    public void setOptimalSolution(int optimalSolution) {
        this.optimalSolution = optimalSolution;
    }

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

        return "QAP {" + "\n" +
                "size :" + this.size + "\n" +
                "locationWithFacility : " + locationWithFacility.toString() + "\n" +
                "distances :\n" + distances.toString() +
                "weights :\n" + weights.toString() +
                '}';
    }

    public QAP clone() {
        return new QAP(new HashMap<>(locationWithFacility), size, Arrays.copyOf(distances, size), Arrays.copyOf(weights, size), optimalSolution);
    }

    public double getAvgNeighboursFitnessDifference(){
        int numberOfNeighbours = 0;
        int neighboursFitnessDifferenceSum = 0;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                QAP neighbour = this.clone();
                neighbour.permute(i, j);
                neighboursFitnessDifferenceSum += Math.abs(this.sum() - neighbour.sum());
                numberOfNeighbours ++;
            }
        }

        return (double)neighboursFitnessDifferenceSum/numberOfNeighbours;
    }
}
