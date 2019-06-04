import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Algorithm {

    private final QAP qap;
    private final int size;

    public Algorithm(QAP qap) {
        this.qap = qap;
        this.size = qap.getSize();
    }

    public QAP aleaWalk() {
        QAP qap = this.qap.clone();

        return qap;
    }

    /**
     * @return solution when simulated Annealing
     */
    public QAP simulatedAnnealing(int nbIterations, double temperature, double temperatureVariation) {

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

    /**
     * @param Tsize should be lower than the sum of integers from 0 to QAP' size - 1  to be useful
     * @param maxIterations to avoid infinite loop
     * @return Tabou algorithm's solution
     */
    public QAP tabou(int Tsize, int maxIterations) {
        List<Pair<Integer, Integer>> T = new ArrayList<>();
        QAP tabooSolution = qap.clone(), currentSolution = tabooSolution.clone(), solutionToTest = currentSolution.clone();

        int solutionToTestSum;
        Pair<Integer, Integer> transition = null;
        Integer bestLocalSolution;
        int compteur = 0;
        while (!(new Pair<>(0,0)).equals(transition) && compteur < maxIterations) {
            compteur++;
            bestLocalSolution = Integer.MAX_VALUE;
            transition = new Pair<>(0,0);
            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    if (i != j && !T.contains(new Pair<>(i, j))) {
                        solutionToTest.permute(i, j);
                        solutionToTestSum = solutionToTest.sum();
                        if (bestLocalSolution > solutionToTestSum) {
                            bestLocalSolution = solutionToTestSum;
                            transition = new Pair<>(i, j);
                        }
                        solutionToTest = currentSolution.clone();
                    }
                }
            }
            if (currentSolution.sum() < bestLocalSolution) {
                if (T.size() == Tsize)
                    T.remove(0);
                T.add(new Pair<>(transition.getKey(), transition.getValue()));
            }
            currentSolution.permute(transition.getKey(), transition.getValue());
            if (tabooSolution.sum() > currentSolution.sum())
                tabooSolution = currentSolution.clone();
        }

        System.out.println("Compteur : " + compteur + "/" + maxIterations + " -- T size: " + T.size() + "/" + size * (size - 1) / 2);
        return tabooSolution;
    }

}
