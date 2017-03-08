package IA.ProbIA5;

/**
 * Created by bejar on 17/01/17.
 */

import aima.search.framework.HeuristicFunction;

public class RedesHeuristicFunction implements HeuristicFunction {

    public double getHeuristicValue(Object n){

        return ((RedesBoard) n).heuristic();
    }
}
