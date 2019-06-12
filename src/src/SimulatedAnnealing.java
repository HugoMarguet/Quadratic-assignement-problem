import java.util.Random;

public class SimulatedAnnealing extends Algorithms {

    public SimulatedAnnealing(QAP qap) throws Exception {
        super(qap);
    }

    /**
     * Neighbourhood' size equals to QAP' size
     * Temporel complexity : O(nbIterations)
     * @return solution when simulated Annealing
     */
    public QAP execute(int nbIterations, double temperature, double temperatureVariation) throws Exception {

        Random random = new Random();
        QAP bestSolution = qap.clone(), currentSolution = qap.clone(), solutionToTest;
        int indexToPermute2, indexToPermute1;
        int solutionToTestSum, currentSolutionSum;
        double probabilityIfWorse;

        for (int i = 0; i < nbIterations; i++) {

            indexToPermute1 = random.nextInt(size);
            do
                indexToPermute2 = random.nextInt(size);
            while (indexToPermute2 == indexToPermute1);
            solutionToTest = currentSolution.clone();
            solutionToTest.permute(indexToPermute1, indexToPermute2);

            solutionToTestSum = solutionToTest.sum();
            currentSolutionSum = currentSolution.sum();
            probabilityIfWorse = (currentSolutionSum - solutionToTestSum) / temperature;
            if (solutionToTest.isOptimal())
                return solutionToTest;
            if (solutionToTestSum < currentSolutionSum || random.nextDouble() <= probabilityIfWorse) {
                if (solutionToTestSum < bestSolution.sum())
                    bestSolution = solutionToTest.clone();

                currentSolution = solutionToTest.clone();
                temperature *= temperatureVariation;
            }
            //System.out.println(probabilityIfWorse);
        }
        return bestSolution;
    }
}
