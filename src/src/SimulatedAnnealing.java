import java.util.List;
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

        boolean showProbabilityIfWorse = (temperature > 19.5 && temperature < 20.5 && temperatureVariation > 0.87);

        Random random = new Random(0);
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
//            if(showProbabilityIfWorse){
//                System.out.println(solutionToTestSum-currentSolutionSum);
//            }
            probabilityIfWorse = Math.exp( (currentSolutionSum - solutionToTestSum) / temperature);
            if (solutionToTest.isOptimal())
                return solutionToTest;
//            if(solutionToTestSum > currentSolutionSum && showProbabilityIfWorse){
//                System.out.println(probabilityIfWorse/* + " " + */);
//            }
            if (solutionToTestSum <= currentSolutionSum || random.nextDouble() <= probabilityIfWorse) {
                if (solutionToTestSum < bestSolution.sum())
                    bestSolution = solutionToTest.clone();

                currentSolution = solutionToTest.clone();
                temperature *= temperatureVariation;
            }
            //System.out.println(probabilityIfWorse);
        }
        return bestSolution;
    }

    public double[] executeMultiple(List<Double> initialTemperatures, List<Double> temperatureVariations) throws Exception {
        int bestFitness = Integer.MAX_VALUE;
        double bestInitialTemperature = -1d, bestTemperatureVariation = -1d;
        System.out.println("initialTemperature;temperatureVariation;fitness");

        for(double initialTemperature: initialTemperatures){
            for(double temperatureVariation: temperatureVariations){
                int fitness = execute(1000,initialTemperature, temperatureVariation).sum();

                //TODO A retirer (code pour générer les graphs)
                String variationString = Double.toString(temperatureVariation);
                if(variationString.length()>4)
                    variationString = variationString.substring(0, 4);
                String initialString = Double.toString(initialTemperature);
                initialString = initialString.substring(0, initialString.length()-2);
                System.out.println(initialString + ";" + variationString + ";" + fitness);

                if(fitness < bestFitness){
                    bestFitness = fitness;
                    bestInitialTemperature = initialTemperature;
                    bestTemperatureVariation = temperatureVariation;
                }
            }
        }

        double[] ret = {bestFitness, bestInitialTemperature, bestTemperatureVariation};
        return ret;
    }
}
