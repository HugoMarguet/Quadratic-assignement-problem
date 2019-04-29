import java.io.IOException;

public class QAP {

    public static void main (String[] args){
        try {
            Data importData = new Data("data/tai12a.txt");
            System.out.println(importData.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
