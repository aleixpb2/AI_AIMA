package IA.ProbIA5;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

public class RedesSuccesorFunction implements SuccessorFunction{
    @Override
    public List getSuccessors(Object state) {
        RedesBoard rb = (RedesBoard) state;
        ArrayList l = new ArrayList();
        RedesHeuristicFunction redesHF  = new RedesHeuristicFunction();
        for (Integer i : rb.getConnexions().keySet()){
            for (Integer j: rb.getConnexions().keySet()){
                //change arc i,j ?? proposta

                //ProbTSPBoard newBoard = new ProbTSPBoard(board.getNCities(), board.getPath(), board.getDists());
                //newBoard.swapCities(i, j);

                //double    v = redesHF.getHeuristicValue(newBoard);
                //String S = RedesBoard.SWAP + " " + i + " " + j + " Cost(" + v + ") ---> " + newBoard.toString();

                //retVal.add(new Successor(S, newBoard));
            }
        }

        return l;
    }
}
