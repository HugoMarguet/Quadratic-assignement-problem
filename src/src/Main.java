import java.io.IOException;

public class Main {

    public static void main (String[] args){
        try {
            QAP qap = new QAP("data/tai12a.txt");
            //System.out.println("Initial QAP:");
            //System.out.println(qap.toString());
            System.out.println("QAP sum: " + qap.sum());

            System.out.println("---------------------------------------");

            //TODO: tester l'algorithme avec des meilleures valeurs
            QAP simulatedAnnealing = new Algorithm(qap).simulatedAnnealing(100, qap.sum(), 0.9);
            System.out.println("QAP found with simulated annealing:");
            //System.out.println(simulatedAnnealing.toString());
            System.out.println("QAP sum: " + simulatedAnnealing.sum());

            System.out.println("---------------------------------------");
            QAP tabou = new Algorithm(qap).tabou(200, 100000);
            System.out.println("QAP found with method tabou: ");
            //System.out.println(simulatedAnnealing.toString());
            System.out.println("QAP sum: " + tabou.sum());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
