import java.util.HashMap;
import java.util.Map;


public class Main {

    private final static String relativePath = "data/";

    public static void main (String[] args){


        try {
            Map<Integer, Algorithm> QAPs = new HashMap<>();
            int[] sizes = {12, 15, 17, 20, 25, 30, 35, 40, 50, 60, 80, 100};
            QAP qap;
            for(int size : sizes) {
                QAPs.put(size,new Algorithm((new QAP(relativePath + "tai" + size + "a.txt"))));
            }


            qap = QAPs.get(12).getQap();
            System.out.println("-------------------------------------------------------------------------------------\n");
            System.out.println("******* QAP 12 intial value : " + qap.sum() +  " -- Tsize max : " + integerSum(qap.getSize() - 1));
            testTabou(new int[]{8}, QAPs.get(12), 12);
            System.out.println("Similated Annealing : " + QAPs.get(12).simulatedAnnealing(100,qap.sum(), 0.9).sum());



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int integerSum(int n) {
        return n * (n + 1) / 2;
    }

    private static void testTabou(int[] Tsizes, Algorithm QAPAlgorithm, int neighbourhoodSize) {

        final int size = QAPAlgorithm.getQap().getSize();
        for (int Tsize : Tsizes) {
            System.out.println("Tabou : " + QAPAlgorithm.tabou(Tsize, 1000, neighbourhoodSize).sum() + "\n-------");
        }
    }

    /**
     *
     * @param QAPAlgorithm
     * @param maxIterations
     * @return
     */
    private static int findOptimalTSize(Algorithm QAPAlgorithm, int maxIterations, int neighbourhoodSize) {
        final int size = QAPAlgorithm.getQap().getSize();
        final int TsizeMax = size * (size - 1) / 2;
        int TsizeBest = 0, TsizeBestValue = Integer.MAX_VALUE, sum;

        for (int Tsize = 1; Tsize <= TsizeMax; Tsize++) {
            if ((sum = QAPAlgorithm.tabou(Tsize, 1000, neighbourhoodSize).sum()) < TsizeBestValue) {
                TsizeBestValue = sum;
                TsizeBest = Tsize;

            }
        }
        return TsizeBest;
    }
}
