import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {

    private final static String relativePath = "data/";

    public static void main (String[] args){


        try {
            Map<Integer, Pair<Tabou, SimulatedAnnealing>> QAPs = new HashMap<>();
            Map<Integer, Integer> optimalSolutions = new HashMap<Integer, Integer>(){{
                put(12, 224416); put(15, 388214); put(17, 491812); put(20, 703482); put(25, 1167256); put(30, 1818146);
                put(35, 2422002); put(40, 3139370); put(50, 4938796); put(60, 7205962); put(80, 13499184); put(100, 21044752);
            }};
            QAP qap;
            for(Map.Entry<Integer, Integer> sizeSol : optimalSolutions.entrySet()) {
                qap = new QAP(relativePath + "tai" + sizeSol.getKey() + "a.txt", sizeSol.getValue());
                QAPs.put(sizeSol.getKey(), new Pair<>(new Tabou(qap), new SimulatedAnnealing(qap)));
            }

            List<Double> temperatures = new ArrayList<>();
            for(double temperature = 0; temperature <= 25000; temperature+=100){
                temperatures.add(temperature);
            }
            List<Double> temperatureVariations = new ArrayList<>();
            for(double temperatureVariation = 0.9; temperatureVariation <= 0.991; temperatureVariation+=0.01){
                temperatureVariations.add(temperatureVariation);
            }

            //int[] sizes = {12, 15, 17, 20, 25, 30, 35, 40, 50, 60, 80, 100};
            int[] sizes = {15};
            for(int size : sizes) {
                qap = QAPs.get(size).getKey().getQap();
                System.out.println("-------------------------------------------------------------------------------------\n");
                System.out.println(" QAP " + size + " : " + optimalSolutions.get(size));
                System.out.println("******* Tabou : Tsize max = " + integerSum(size - 1));
                System.out.println(QAPs.get(size).getKey().executeMultiple(new int[]{8}, size)[0]);

                double[] simulatedAnnealingResults = QAPs.get(size).getValue().executeMultiple(temperatures, temperatureVariations);
                System.out.println("******* Simulated Annealing : the best solution has been found with initial temperature "
                        + simulatedAnnealingResults[1] + " and temperature variation " + simulatedAnnealingResults[2]);
                System.out.println((int) simulatedAnnealingResults[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int integerSum(int n) {
        return n * (n + 1) / 2;
    }

}
