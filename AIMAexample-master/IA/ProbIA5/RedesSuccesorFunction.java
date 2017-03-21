package IA.ProbIA5;

import aima.search.framework.SuccessorFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17.
 */
public class RedesSuccesorFunction implements SuccessorFunction{
    @Override
    public List getSuccessors(Object state) {
        RedesBoard rb = (RedesBoard) state;
        List l = new ArrayList();
        for (Integer i : rb.getConnexions().keySet()){
            for (Integer j: rb.getConnexions().keySet()){
                //change arc i,j ?? proposta
            }
        }

        return null;
    }

    //   public List getSuccessors(Object state){
//        ArrayList retval = new ArrayList();
//        RedesBoard board = (RedesBoard) state;

        // Some code here
        // (flip all the consecutive pairs of coins and generate new states
        // Add the states to retval as Succesor("flip i j, new_state)
        // new_state has to be a copy of state
//
//
//
//        int [] init_disposition = board.boardCopy();
//        int [] goal_disposition = board.getGoal();
//        for (int i=0; i<board.getLength()-1; ++i){
//            RedesBoard newboard = new RedesBoard(init_disposition,goal_disposition);
//            //newboard.flip_it(i);
//            int [] newstate = newboard.boardCopy();
//            retval.add (newstate);
//        }
//        return retval;
//    }

}
