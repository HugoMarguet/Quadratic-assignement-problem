import javafx.util.Pair;

import java.util.HashMap;
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


            qap = QAPs.get(12).getKey().getQap();
            System.out.println("-------------------------------------------------------------------------------------\n");
            System.out.println(" QAP 12 : " + optimalSolutions.get(12));
            System.out.println("******* Tabou : Tsize max = " + integerSum(12 - 1));
            System.out.println(QAPs.get(12).getKey().executeMultiple(new int[]{8}, 12)[0]);
            System.out.println("******* Similated Annealing : ");
            QAPs.get(12).getValue().execute(100, qap.sum(),0.9);






        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int integerSum(int n) {
        return n * (n + 1) / 2;
    }

}
