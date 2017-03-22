package IA.ProbIA5;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RedesSuccesorFunctionSA implements SuccessorFunction{
    @Override
    public List getSuccessors(Object state) {
        RedesBoard rb = (RedesBoard) state;
        ArrayList l = new ArrayList();
        RedesHeuristicFunction redesHF  = new RedesHeuristicFunction();
        Random myRandom=new Random();
        int i,j;
        // TODO: com l'anterior pero generar nomes un succesor amb operador aleatori i params. aleatoris
        //i=myRandom.nextInt(rb.getNCities());

        //do{
            //j=myRandom.nextInt(rb.getNCities());
        //} while (i==j);

        /*
        ProbTSPBoard newBoard = new ProbTSPBoard(board.getNCities(), board.getPath(), board.getDists());

        newBoard.swapCities(i, j);

        double   v = TSPHF.getHeuristicValue(newBoard);
        String S = ProbTSPBoard.INTERCAMBIO + " " + i + " " + j + " Coste(" + v + ") ---> " + newBoard.toString();

        retVal.add(new Successor(S, newBoard));*/
        return l;
    }
}
