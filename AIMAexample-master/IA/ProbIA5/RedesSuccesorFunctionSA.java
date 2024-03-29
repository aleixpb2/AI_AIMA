package IA.ProbIA5;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RedesSuccesorFunctionSA implements SuccessorFunction{
    @Override
    public List getSuccessors(Object state) {
        RedesBoard rb = (RedesBoard) state;
        ArrayList l = new ArrayList();

        HashMap<Integer,Pairintbool> connexions = rb.getConnexions();

        Random myRandom=new Random();
        int i,j;
        i=myRandom.nextInt(rb.nSensors());
        Pairintbool p1 = new Pairintbool(i, true);
        Pairintbool p2 = connexions.get(i);
        RedesBoard newBoard = rb.copy();
        boolean sensor;
        do{
            sensor = true;
            j=myRandom.nextInt(rb.nSensors() + rb.nCentros());
            if(j >= rb.nSensors()){
                j -= rb.nSensors();
                sensor = false;
            }
        } while (!newBoard.changeArc(p1, p2, new Pairintbool(j, sensor)));


        String S;
        if(sensor) S = RedesBoard.SWAP + " " + i + " " + j + " sensor.";
        else S = RedesBoard.SWAP + " " + i + " " + j + " centro.";
        l.add(new Successor(S, newBoard));
        return l;
    }
}
