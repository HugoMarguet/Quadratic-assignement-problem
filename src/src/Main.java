import java.io.IOException;

public class Main {

    public static void main (String[] args){
        try {
            QAP qap = new QAP("data/tai12a.txt");
            System.out.println(qap.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
