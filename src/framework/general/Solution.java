package framework.general;

import java.util.ArrayList;

/**
* Class representing a solution to an optimisation problem
* @param <E> Can be any number
*/
public class Solution<E extends Number> extends ArrayList<E> implements Comparable<Solution>, Cloneable{

    private static int id_counter;
    private final EvaluationFunction evaluationFunction;
    public double solutionValue;
    public int id;

    public Solution(EvaluationFunction evaluationFunction){
        super();
        this.evaluationFunction = evaluationFunction;
        id_counter = id_counter+1;
        id = id_counter;
    }

    public double getSolutionValue(){
        solutionValue = evaluationFunction.applyAsDouble(this);
        return solutionValue;
    }

    public EvaluationFunction getEvaluationFunction(){
        return evaluationFunction;
    }

    @Override
    public int compareTo(Solution o) {
        if (solutionValue == o.getSolutionValue()) {
            return 0;
        } else if (solutionValue < o.getSolutionValue()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public Solution subList(int from, int to){
        Solution ret = new Solution(this.evaluationFunction);
        for (int i = from; i < to; i ++){
            ret.add(this.get(i));
        }
        return ret;
    }

    @Override
    public String toString(){
        String ret = "";
        for (E i : this){
            ret = ret + i;
        }
        return ret;
    }


    @Override
    public boolean equals(Object obj){
        if (obj instanceof Solution sol){
            return this.id == sol.id;
        }
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public Solution clone(){
        Solution ret = new Solution(this.getEvaluationFunction());
        for(E e : this){
            ret.add(e);
        }
        return ret;
    }
}
