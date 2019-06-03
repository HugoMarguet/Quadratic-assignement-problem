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

        Random random = new Random();
        int size = qap.getSize();
        QAP bestSolution = qap.clone(), currentSolution = qap.clone(), solutionToTest;
        int indexToPermute2, indexToPermute1;
        int solutionToTestSum, currentSolutionSum;
        double probabilityIfWorse;

        for(int i=0; i<maxIterations; i++){

            indexToPermute1 = random.nextInt(size);
            do
                indexToPermute2 = random.nextInt(size);
            while (indexToPermute2 == indexToPermute1);
            solutionToTest = currentSolution.clone();
            solutionToTest.permute(indexToPermute1, indexToPermute2);

            solutionToTestSum = solutionToTest.sum();
            currentSolutionSum = currentSolution.sum();
            probabilityIfWorse = (currentSolutionSum - solutionToTestSum)/temperature;
            if(solutionToTestSum < currentSolutionSum || random.nextDouble() <= probabilityIfWorse) {
                if(solutionToTestSum < bestSolution.sum())
                    bestSolution = solutionToTest.clone();

                currentSolution = solutionToTest.clone();
                temperature *= temperatureVariation;
            }
            //System.out.println(probabilityIfWorse);
        }

        return bestSolution;
    }

    public QAP tabou() {
        return qap.clone();
    }
}
