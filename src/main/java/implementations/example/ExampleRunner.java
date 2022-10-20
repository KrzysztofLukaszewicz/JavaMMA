package implementations.example;

import implementations.crossover.OnePointCrossover;
import implementations.crossover.TwoPointCrossover;
import implementations.crossover.UniformCrossover;
import implementations.evaluation.KnapsackEvaluation;
import implementations.hillclimb.DavisBit;
import implementations.hillclimb.NextAscent;
import implementations.hillclimb.RandomMutation;
import implementations.hillclimb.SteepestAscent;
import implementations.multimeme.SimpleMemeplexInheritenceFunction;
import implementations.multimeme.SimpleMemeplexMutationOperator;
import implementations.mutation.RandomBitFlip;
import implementations.replacement.TransGenerationalElitism;
import implementations.selection.TournamentSelection;
import framework.factories.*;
import framework.general.SolutionOperator;
import framework.genetic.CrossoverOperator;
import framework.genetic.ParentSelectionFunction;
import framework.genetic.ReplacementStrategy;
import framework.multimeme.MultiMeme;
import implementations.rnr.LargeNeighbourhoodSearch;

import java.util.ArrayList;

public class ExampleRunner {


    static int popSize = 20;
    static int generationCount = 500;
    static int geneCount = 200;

    public static void main(String args[]){

        // Setting up knapsack evaluation function
        ArrayList<Double> weights = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();
        for (int i = 1; i <= 200; i ++){
            weights.add((double) Math.random()*100);
        }

        for (int i = 0; i <= 400; i +=2){
            prices.add((double) i);
        }


        KnapsackEvaluation knapsackEvaluation = new KnapsackEvaluation(weights, prices, 100);

        // Setting up bounds for the 0-1 knapsack
        ArrayList<Integer> bounds = new ArrayList<>(){{
            add(0);
            add(1);
        }};


        // Setting up tournament selection
        ParentSelectionFunctionFactory psFuncFac = new TournamentSelection();
        ParentSelectionFunction psFunc = psFuncFac.apply(3);

        // Setting up TGE with a = 0.5
        ReplacementStrategyFactory rsFac = new TransGenerationalElitism();
        ReplacementStrategy rs = rsFac.apply(0.5);

        // Setting up different crossover operators
        ArrayList<CrossoverOperator> crossoverOperators = new ArrayList<>();
        CrossoverOperatorFactory coFac = new OnePointCrossover();
        CrossoverOperatorFactory coFac1 = new TwoPointCrossover();
        CrossoverOperatorFactory coFac2 = new UniformCrossover();
        for (double i = 0; i <= 1.0; i += 0.2){
            crossoverOperators.add(coFac.apply(i));
            crossoverOperators.add(coFac1.apply(i));
            crossoverOperators.add(coFac2.apply(i));
        }

        // Setting up mutation operator
        ArrayList<SolutionOperator> mutationOperators = new ArrayList<>();
        SolutionOperatorFactory muFac = new RandomBitFlip();

        for (double i = 0; i <= 1; i += 0.2){
            mutationOperators.add(muFac.apply(i));
        }

        // Setting up Hillclimb operators
        ArrayList<SolutionOperator> hillclimbOperators = new ArrayList<>();
        SolutionOperatorFactory hcFac = new DavisBit();
        SolutionOperatorFactory hcFac1 = new NextAscent();
        SolutionOperatorFactory hcFac2 = new SteepestAscent();
        SolutionOperatorFactory hcFac3 = new RandomMutation();
        for (double i = 1; i <= 10; i ++){
            hillclimbOperators.add(hcFac.apply(i));
            hillclimbOperators.add(hcFac1.apply(i));
            hillclimbOperators.add(hcFac2.apply(i));
            hillclimbOperators.add(hcFac3.apply(i));

        }

        // Adding ruin and recreate operators
        ArrayList<SolutionOperator> rnrOperators = new ArrayList<>();
        SolutionOperatorFactory lns = new LargeNeighbourhoodSearch();
        for (double i = 1; i <= 10; i ++){
            rnrOperators.add(lns.apply(i));
        }



        // Memeplex functions
        MemeplexInheritenceFunctionFactory mifFac = new SimpleMemeplexInheritenceFunction();
        MemeplexMutationOperatorFactory mmoFac = new SimpleMemeplexMutationOperator();


        MultiMeme mma = new MultiMeme(
                knapsackEvaluation,
                popSize,
                generationCount,
                geneCount,
                false,
                bounds,
                psFunc,
                rs,
                crossoverOperators,
                mutationOperators,
                hillclimbOperators,
                rnrOperators,
                mifFac.apply(null),
                mmoFac.apply(0.2)
        );
        mma.run();

        ArrayList<Double> toPrintBest = mma.getBestPerGeneration();
        ArrayList<Double> toPrintWorst = mma.getWorstPerGeneration();

        for (int i = 0; i < toPrintBest.size(); i++){
            System.out.println("Best: "+toPrintBest.get(i) + " worst: "+toPrintWorst.get(i));
        }
    }
}
