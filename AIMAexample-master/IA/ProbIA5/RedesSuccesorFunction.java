package IA.ProbIA5;

import aima.search.framework.Successor;
import aima.search.framework.SuccessorFunction;
import aima.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RedesSuccesorFunction implements SuccessorFunction{
    @Override
    public List getSuccessors(Object state) {
        final RedesBoard rb = (RedesBoard) state;
        ArrayList l = new ArrayList();
        RedesHeuristicFunction redesHF  = new RedesHeuristicFunction();
        HashMap<Integer,Pairintbool> connexions = rb.getConnexions();
        HashMap<Pairintbool,ArrayList<Integer>> incidents = rb.getIncidentConnected();


        for (int i = 0; i < rb.nSensors(); ++i){ // for all sensors
            Pairintbool p1 = new Pairintbool(i, true);
            Pairintbool p2 = connexions.get(i);
            for (int j = 0; j < rb.nSensors(); ++j) { // for all sensors
                RedesBoard newBoard = rb.copy();
                //System.out.println ("NEW ITERATION!!!!! Initial board is-------------");
                //System.out.println(newBoard.getIncidentConnected());


                Pairintbool p3 = new Pairintbool(j, true);
                SensorM[] currentsensorsArray  = newBoard.getSensors();

                if (!p1.equals(p3)){
                    if (newBoard.changeArc(p1, p2, p3)) {

                        //System.out.println ("           It has changed to this:");
                        //System.out.println("            "+newBoard.getIncidentConnected());
                        double v = redesHF.getHeuristicValue(newBoard);
                        String S = RedesBoard.SWAP + " " + i + " " + j + " sensor. Cost(" + v + ") ---> \n" + newBoard.toString();
                        l.add(new Successor(S, newBoard));
                    } else {
                        /*System.out.println("ELSE:");
                        System.out.println("Tried to create : " + newBoard.createArc(p1, p3));
                        System.out.println("Tried to disconnect p1 and p2 : " + newBoard.removeArc(p1, p2));

                        if (!newBoard.createArc(p1, p3) && !newBoard.removeArc(p1, p2)) {
                            System.out.println("           IMPOSSIBLE TO DISCONNECT!!! BOARD STATE IS");
                            System.out.println(newBoard.toString());

                        }*/
                        Random myRandom = new Random();
                        int rand1;

                        ArrayList<Integer> incidP3 = incidents.get(p3);
                        if (incidP3!=null && incidP3.size()>=3){
                            rand1=myRandom.nextInt(incidP3.size());
                            Pairintbool selectedRemove = new Pairintbool(incidP3.get(rand1),true);
                            newBoard.removeArc(selectedRemove,p3);
                            newBoard.changeArc(p1,p2,p3);
                            newBoard.createArc(selectedRemove,p2);
                        }
                        /*
                        System.out.println("incidents in p3 are " + incidP3);
                        System.out.println(p1);
                        System.out.println(p2);
                        System.out.println(p3);
                        System.out.println();*/



                    /*
                    System.out.println (p3);


                    newBoard.removeArc(p1,p2);
                    ArrayList<Integer> incidP3 = incidents.get(p3);
                    System.out.println (incidP3);
                    Random myRandom=new Random();
                    int rand;
                    rand=myRandom.nextInt(incidP3.size());

                    newBoard.removeArc(new Pairintbool(incidP3.get(rand),true),p3);
                    newBoard.createArc(p1,p3);
                */

                    }
                }
            }
            for (int j = 0; j < rb.nCentros(); ++j){ // for all centros
                RedesBoard newBoard = rb.copy();
                if(newBoard.changeArc(p1, p2, new Pairintbool(j, false))) {
                    double v = redesHF.getHeuristicValue(newBoard);
                    String S = RedesBoard.SWAP + " " + i + " " + j + " centro. Cost(" + v + ") ---> \n"+ newBoard.toString();
                    l.add(new Successor(S, newBoard));
                }
            }
        }
        return l;
    }
}