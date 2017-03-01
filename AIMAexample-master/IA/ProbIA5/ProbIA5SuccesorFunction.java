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

        for (int i=0; i<board.getLength()-1; ++i){
            IA.ProbIA5.ProbIA5Board board_current = (IA.ProbIA5.ProbIA5Board) board.boardCopy();
            board.flip_it(i);
            int newState[] = board.boardCopy();
            retval.add (newState);
            board=board_current;
        }

        return retval;

    }

}
