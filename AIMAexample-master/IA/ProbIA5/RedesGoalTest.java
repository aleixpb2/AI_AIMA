package IA.ProbIA5;

import aima.search.framework.GoalTest;

/**
 * Created by bejar on 17/01/17.
 */
public class RedesGoalTest implements GoalTest {

    public boolean isGoalState(Object state){
        return true;
        //return((RedesBoard) state).is_goal();
    }
}
