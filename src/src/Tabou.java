import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tabou extends Algorithms {

    public Tabou(QAP qap) throws Exception {
        super(qap);
    }

    /**
     * Temporel complexity : O(maxIterations * size²)
     * @param neighbourhoodSize
     * @param Tsize should be lower than the sum of integers from 0 to QAP' size - 1  to be useful
     * @param maxIterations use to avoid infinite loop
     * @return Tabou algorithm's solution
     */
    public QAP execute(int Tsize, int maxIterations, int neighbourhoodSize) throws Exception {
        List<Pair<Integer, Integer>> T = new ArrayList<>();
        QAP tabouSolution = qap.clone(), currentSolution = tabouSolution.clone(), solutionToTest = currentSolution.clone();
        Set<State> states = new HashSet<>();
        states.add(new State(qap.getLocationWithFacility(), T));

        int solutionToTestSum;
        Pair<Integer, Integer> transition = null;
        int bestLocalSolution;
        int compteur = 0;
        while (!(new Pair<>(0,0)).equals(transition) && compteur < maxIterations) {
            compteur++;
            bestLocalSolution = Integer.MAX_VALUE;
            transition = new Pair<>(0,0);
            for (int i = 0; i < size; i++) {
                for (int j : neighbourhood(neighbourhoodSize,i)) {
                    if (!T.contains(new Pair<>(i, j))) {
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
            if (tabouSolution.sum() > currentSolution.sum())
                tabouSolution = currentSolution.clone();

            if (tabouSolution.isOptimal() && !states.add(new State(currentSolution.getLocationWithFacility(), T)))
                return tabouSolution;
        }

        //System.out.println("Compteur : " + compteur + "/" + maxIterations + " -- T size : " + T.size() + "/" + Tsize);
        return tabouSolution;
    }

    public int findOptimalTSize(int maxIterations, int neighbourhoodSize) throws Exception {
        final int TsizeMax = size * (size - 1) / 2;
        int TsizeBest = 0, TsizeBestValue = Integer.MAX_VALUE, sum;

        for (int Tsize = 1; Tsize <= TsizeMax; Tsize++) {
            if ((sum = execute(Tsize, 1000, neighbourhoodSize).sum()) < TsizeBestValue) {
                TsizeBestValue = sum;
                TsizeBest = Tsize;

            }
        }
        return TsizeBest;
    }

    public int[] executeMultiple(int[] Tsizes, int neighbourhoodSize) throws Exception {

        int[] solutions = new int[Tsizes.length];
        for (int i = 0; i < Tsizes.length; i++)
            solutions[i] = execute(Tsizes[i], 1000, neighbourhoodSize).sum();
        return solutions;
    }

    /**
     *
     * @param demiSize neiberhood size : node will have size * 2 neighbours : size for each side
     * @param node
     * @return List of neigbours
     */
    private List<Integer> neighbourhood(int demiSize, int node) {

        int seq;
        final int QAPsize = qap.getSize();
        demiSize *= 2;
        if (demiSize > QAPsize)
            demiSize = QAPsize;
        List<Integer> neighbours = new ArrayList<>();
        for (int i = 0; i < demiSize; i++)
            if ((seq = modulo(node + sequence(i), QAPsize)) > node)
                neighbours.add(modulo(seq,QAPsize));
        return neighbours;
    }

    /**
     *
     * @param i
     * @return
     */
    private int sequence(int i) {
        final int QAPsize = qap.getSize();
        final boolean positif = i % 2 == 0;
        return (positif ? 1 : - 1) * (i/2 + 1);
    }

    /**
     *
     * @param i
     * @param m modulo
     * @return i % m if i is positif else -i % m
     */
    private int modulo(int i, int m) {
        return ((i % m) + m) % m;
    }
}
