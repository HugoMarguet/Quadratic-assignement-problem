import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QAP {

    private Map<Integer, Integer> map;
    private Data data;

    public QAP(String path) throws IOException {

        data = new Data(path);
        map = new HashMap<>();
        for (int i = 0; i < data.getSize(); i++)
            map.put(i,i);
    }

    public QAP(Data data, Map<Integer, Integer> map) {
        this.data = data;
        this.map = map;
    }

    public int sum(Map<Integer, Integer> arrangement) {

        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : arrangement.entrySet())
            for (int i = 0; i < data.getSize(); i++)
                sum += data.getWeights()[entry.getKey()][i] * data.getDistances()[entry.getValue()][arrangement.get(i)];

        return sum;
    }

    public int getSum(){
        return sum(map);
    }

    public void permute(int i, int j) {

        int temp = map.get(i);
        map.put(i,map.get(j));
        map.put(j,temp);
    }

    public Data getData(){
        return data;
    }

    public String toString() {
        return "QAP {" + "\n" +
                "map : " + map.toString() + "\n" +
                "data : " + data.toString() +
                '}';
    }

    public QAP clone() {
        return new QAP(data.clone(), new HashMap<>(map));
    }
}
