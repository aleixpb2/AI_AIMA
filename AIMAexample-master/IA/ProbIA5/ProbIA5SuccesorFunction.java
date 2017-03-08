package IA.ProbIA5;

import aima.search.framework.SuccessorFunction;
import aima.search.framework.Successor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbIA5SuccesorFunction implements SuccessorFunction{

    public List getSuccessors(Object state){
        ArrayList retval = new ArrayList();
        IA.ProbIA5.ProbIA5Board board = (IA.ProbIA5.ProbIA5Board) state;

        // Some code here
        // (flip all the consecutive pairs of coins and generate new states
        // Add the states to retval as Succesor("flip i j, new_state)
        // new_state has to be a copy of state



        int [] init_disposition = board.boardCopy();
        int [] goal_disposition = board.getGoal();
        for (int i=0; i<board.getLength()-1; ++i){
            ProbIA5Board newboard = new ProbIA5Board(init_disposition,goal_disposition);
            newboard.flip_it(i);
            int [] newstate = newboard.boardCopy();
            retval.add (newstate);
        }
        return retval;
    }

}
