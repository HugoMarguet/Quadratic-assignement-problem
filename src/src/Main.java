import java.util.HashMap;
import java.util.Map;


public class Main {

    private final static String relativePath = "data/";

    public static void main (String[] args){


        try {
            Map<Integer, Algorithm> QAPs = new HashMap<>();
            Map<Integer, Integer> optimalSolutions = new HashMap<Integer, Integer>(){{
                put(12, 224416); put(15, 388214); put(17, 491812); put(20, 703482); put(25, 1167256); put(30, 1818146);
                put(35, 2422002); put(40, 3139370); put(50, 4938796); put(60, 7205962); put(80, 13499184); put(100, 21044752);
            }};
            QAP qap;
            for(Map.Entry<Integer, Integer> sizeSol : optimalSolutions.entrySet())
                QAPs.put(sizeSol.getKey(),new Algorithm((new QAP(relativePath + "tai" + sizeSol.getKey() + "a.txt", sizeSol.getValue()))));


            qap = QAPs.get(12).getQap();
            System.out.println("-------------------------------------------------------------------------------------\n");
            System.out.println(" QAP 12 : " + qap.getOptimalSolution());
            System.out.println("******* intial value : " + qap.sum() +  " -- Tsize max : " + integerSum(qap.getSize() - 1));
            testTabou(new int[]{8}, QAPs.get(12), 12);
            System.out.println("Similated Annealing : " + QAPs.get(12).simulatedAnnealing(100,qap.sum(), 0.9).sum());

            qap = QAPs.get(15).getQap();
            System.out.println("-------------------------------------------------------------------------------------\n");
            System.out.println(" QAP 15 : " + qap.getOptimalSolution());
            System.out.println("******* intial value : " + qap.sum() +  " -- Tsize max : " + integerSum(qap.getSize() - 1));
            testTabou(new int[]{9}, QAPs.get(15), 8);
            System.out.println("Similated Annealing : " + QAPs.get(15).simulatedAnnealing(100,qap.sum(), 0.9).sum());

            qap = QAPs.get(17).getQap();
            System.out.println("-------------------------------------------------------------------------------------\n");
            System.out.println(" QAP 17 : " + qap.getOptimalSolution());
            System.out.println("******* intial value : " + qap.sum() +  " -- Tsize max : " + integerSum(qap.getSize() - 1));
            testTabou(new int[]{39}, QAPs.get(17), 9);
            System.out.println("Similated Annealing : " + QAPs.get(17).simulatedAnnealing(100,qap.sum(), 0.9).sum());




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int integerSum(int n) {
        return n * (n + 1) / 2;
    }

    private static void testTabou(int[] Tsizes, Algorithm QAPAlgorithm, int neighbourhoodSize) throws Exception {

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
    private static int findOptimalTSize(Algorithm QAPAlgorithm, int maxIterations, int neighbourhoodSize) throws Exception {
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
