package IA.ProbIA5;

import aima.search.framework.GoalTest;

public class RedesGoalTest implements GoalTest {

    public boolean isGoalState(Object state){
        return false; // it is a local search, we don't know if we have reached a final state
    }
}
