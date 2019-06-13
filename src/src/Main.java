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

            long timeBegin, timeEnd;
            int Tsize;
            int[] sizes = {12,15,17};
            for(int size : sizes) {
                qap = QAPs.get(size).getKey().getQap();
                System.out.println("-------------------------------------------------------------------------------------\n");
                System.out.println("QAP " + size + " -- Optimal : " + optimalSolutions.get(size) + "\nIterations = 100");
                System.out.println("******* Tabou : Tsize max = " + integerSum(size - 1));
                timeBegin = System.currentTimeMillis();
                System.out.print("the best solution has been found with neighbourhood size = " + size + " Tabou size = " + (Tsize = QAPs.get(size).getKey().findOptimalTSize(100,  size)));
                System.out.print(" : " + QAPs.get(size).getKey().execute(Tsize,200, size).sum());
                timeEnd = System.currentTimeMillis();
                System.out.println(" in " +  (double) (timeEnd-timeBegin)/1000d + "s : ");


                timeBegin = System.currentTimeMillis();
                double[] simulatedAnnealingResults = QAPs.get(size).getValue().executeMultiple(temperatures, temperatureVariations);
                System.out.println("******* Simulated Annealing : ");
                System.out.print("the best solution has been found with initial temperature "
                        + simulatedAnnealingResults[1] + " and temperature variation " + simulatedAnnealingResults[2] + " : ");
                System.out.print((int) simulatedAnnealingResults[0]);
                timeEnd = System.currentTimeMillis();
                System.out.println(" in " +  (double) (timeEnd-timeBegin)/1000d + "s : ");
                System.out.println("-------------------------------------------------------------------------------------\n");
            }

            System.out.println("Bonne solutions pour des tailles intermédiares (25-40) avec la méthode Tabou");
            sizes = new int[]{25,30,35,40};
            int[] Tsizes = new int[]{11,14,22,26};
            for(int i = 0; i < sizes.length; i++) {
                System.out.println("QAP " + sizes[i] + " -- Optimal : " + optimalSolutions.get(sizes[i]) + "\nIterations = 1000");
                System.out.println("******* Tabou : Tsize max = " + integerSum(sizes[i] - 1));
                System.out.println("Solution avec Tsize = " + Tsizes[i] + " : " + QAPs.get(sizes[i]).getKey().execute(Tsizes[i],1000, sizes[i]).sum() + "\n");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int integerSum(int n) {
        return n * (n + 1) / 2;
    }

}
