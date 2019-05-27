import java.util.Random;

public class Algorithm {

    private QAP qap;

    public Algorithm(QAP qap) {
        this.qap = qap;
    }

    public QAP aleaWalk() {
        QAP qap = this.qap.clone();

        return qap;
    }

    public QAP simulatedAnnealing(int maxIterations, double temperature, double temperatureVariation ){
        Random rnd = new Random();
        int size = qap.getData().getSize();

        QAP bestSolution = qap.clone(), currentSolution = qap.clone();
        for(int i=0; i<maxIterations; i++){
            int indexToPermute1 = rnd.nextInt(size);
            int indexToPermute2;
            do{
                indexToPermute2 = rnd.nextInt(size);
            }while(indexToPermute2==indexToPermute1);

            QAP solutionToTest = currentSolution.clone();
            solutionToTest.permute(indexToPermute1, indexToPermute2);
            int solutionToTestSum = solutionToTest.getSum(), currentSolutionSum = currentSolution.getSum();
            double probabilityIfWorse = (currentSolutionSum - solutionToTestSum )/temperature;
            System.out.println(probabilityIfWorse);
            if(solutionToTestSum < currentSolutionSum || rnd.nextDouble() <= probabilityIfWorse){
                if(solutionToTestSum < bestSolution.getSum()){
                    bestSolution = solutionToTest.clone();
                }
                currentSolution = solutionToTest.clone();

                temperature *= temperatureVariation;
            }
        }

        return bestSolution;
    }

    public QAP tabou() {
        return qap.clone();
    }
}
