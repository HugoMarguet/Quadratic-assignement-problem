import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private final static String relativePath = "data/";

    public static void main (String[] args){
        try {

            Map<Integer, QAP> QAPs = new HashMap<>();
            int[] sizes = {12, 15, 17, 20, 25, 30, 35, 40, 50, 60, 80, 100};
            for(int size : sizes) {
                QAPs.put(size,new QAP(relativePath + "tai" + size + "a.txt"));
            }


            System.out.println("---------------------------------------");

            //TODO: tester l'algorithme avec des meilleures valeurs
            QAP simulatedAnnealing = new Algorithm(QAPs.get(12)).simulatedAnnealing(100, QAPs.get(12).sum(), 0.9);
            System.out.println("QAP found with simulated annealing:");
            //System.out.println(simulatedAnnealing.toString());
            System.out.println("QAP sum: " + simulatedAnnealing.sum());

            System.out.println("---------------------------------------");
            QAP tabou = new Algorithm(QAPs.get(12)).tabou(25, 100000);
            System.out.println("QAP found with method tabou: ");
            //System.out.println(simulatedAnnealing.toString());
            System.out.println("QAP sum: " + tabou.sum());
            System.out.println("---------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
