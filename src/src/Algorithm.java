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

    public QAP simulatedAnnealing(int maxIterations, double temperature, double temperatureVariation ){

        Random random = new Random();
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

    public QAP tabou(int Tsize, int maxIterations) {
        List<Pair> T = new ArrayList<>();
        QAP tabooSolution = qap.clone(), currentSolution = tabooSolution.clone(), solutionToTest = currentSolution.clone();

        int solutionToTestSum;
        Pair<Integer, Integer> transition = new Pair<>(0,0);
        Integer bestLocalSolution;
        int compteur = 0;
        while (T.size() <= Tsize && (Tsize != T.size() || compteur < maxIterations)) {
            compteur ++;
            bestLocalSolution = Integer.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = i + 1; j < size; j++) {
                    if (i != j && !T.contains(new Pair(i,j))) {
                        solutionToTest.permute(i,j);
                        solutionToTestSum = solutionToTest.sum();
                        if (bestLocalSolution > solutionToTestSum) {
                            bestLocalSolution = solutionToTestSum;
                            transition = new Pair<>(i,j);
                        }
                        solutionToTest = currentSolution.clone();
                    }
                }
            }
            if (currentSolution.sum() < bestLocalSolution)
                T.add(new Pair(transition.getKey(), transition.getValue()));
            currentSolution.permute(transition.getKey(), transition.getValue());
            if (tabooSolution.sum() > currentSolution.sum())
                tabooSolution = currentSolution.clone();
        }

        return tabooSolution;
    }
}
