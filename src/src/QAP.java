import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QAP {

    public static void main (String[] args){
        try {
            Data importData = new Data("data/tai12a.txt");
            System.out.println(importData.toString());
            Map<Integer, Integer> map = new HashMap<>();
            map.put(0,0);
            map.put(1,1);
            map.put(2,2);
            map.put(3,3);
            map.put(4,7);
            map.put(5,6);
            map.put(6,5);
            map.put(7,4);
            map.put(8,8);
            map.put(9,9);
            map.put(10,11);
            map.put(11,10);
            int sum = sum(map, importData.getWeights(), importData.getDistances());
            System.out.println(sum);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int sum(Map<Integer, Integer> arrangement, int[][] weights, int[][] distances){
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : arrangement.entrySet()) {
            for (int i = 0; i < weights.length; i++) {
                sum += weights[entry.getKey()][i] * distances[entry.getValue()][arrangement.get(i)];
            }
        }
        return sum;
    }

    private static void permute(Map<Integer, Integer> arrangement, int i, int j) {
        int temp = arrangement.get(i);
        arrangement.put(i,arrangement.get(j));
        arrangement.put(j,temp);
    }

}
