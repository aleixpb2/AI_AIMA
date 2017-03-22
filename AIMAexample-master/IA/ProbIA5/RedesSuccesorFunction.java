package IA.ProbIA5;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RedesSuccesorFunction implements SuccessorFunction{
    @Override
    public List getSuccessors(Object state) {
        RedesBoard rb = (RedesBoard) state;
        ArrayList l = new ArrayList();
        RedesHeuristicFunction redesHF  = new RedesHeuristicFunction();
        HashMap<Integer,Pairintbool> connexions = rb.getConnexions();
        for (int i = 0; i < rb.nSensors(); ++i){ // for all sensors
            Pairintbool p1 = new Pairintbool(i, true);
            Pairintbool p2 = connexions.get(i);
            for (int j = 0; j < rb.nSensors(); ++j){ // for all sensors
                RedesBoard newBoard = rb.copy();
                if(newBoard.changeArc(p1, p2, new Pairintbool(j, true))){
                    double v = redesHF.getHeuristicValue(newBoard);
                    String S = RedesBoard.SWAP + " " + i + " " + j + " sensor. Cost(" + v + ") ---> " + newBoard.toString();
                    l.add(new Successor(S, newBoard));
                }
            }
            for (int j = 0; j < rb.nCentros(); ++j){ // for all centros
                RedesBoard newBoard = rb.copy();
                if(newBoard.changeArc(p1, p2, new Pairintbool(j, false))) {
                    double v = redesHF.getHeuristicValue(newBoard);
                    String S = RedesBoard.SWAP + " " + i + " " + j + " centro. Cost(" + v + ") ---> " + newBoard.toString();
                    l.add(new Successor(S, newBoard));
                }
            }
        }
        return l;
    }
}