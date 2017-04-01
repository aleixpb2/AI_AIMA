import IA.ProbIA5.RedesBoard;
import IA.ProbIA5.RedesGoalTest;
import IA.ProbIA5.RedesHeuristicFunction;
import IA.ProbIA5.RedesSuccesorFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{

        int seedcentros = 1234;
        int seedsensores = 4321;
        int numcentros = 4;
        int numsensors = 100;

        RedesBoard board = new RedesBoard(seedcentros,seedsensores, numcentros,numsensors );
        //System.out.println("Finished initializing");
        //System.out.println(board.getIncidentConnected());
        // Create the Problem object
        Problem p = new  Problem(board,
                                new RedesSuccesorFunction(),
                                new RedesGoalTest(),
                                new RedesHeuristicFunction());

        // Instantiate the search algorithm

        System.out.println("Do you want to execute Hill Climbing? y/n");
        Scanner sc =new Scanner(System.in);
        String str=sc.next();
        boolean HC = str.equals("y");
        final long startTime, duration;
        long sec;
        SearchAgent agent;
        RedesBoard finalB;
        if(HC) {// Hill Climbing, no parameters
            Search algHC = new HillClimbingSearch();

            // Instantiate the SearchAgent object
            System.out.println("Before SearchAgent"); // TODO: remove
            startTime = System.nanoTime();
            agent = new SearchAgent(p, algHC);
            System.out.println("After SearchAgent"); // TODO: remove
            finalB = (RedesBoard) algHC.getGoalState();
        }else {
            // Simmulated Annealing, 4 parameters: max iterations, iterations per temperature step
            // and temperature function parameters k and lambda
            System.out.println("Maximum iterations:");
            int maxIter = sc.nextInt();
            System.out.println("Iterations per temperature step:");
            int itStep = sc.nextInt();
            System.out.println("Temperature function parameter k");
            int k = sc.nextInt(); //5;
            System.out.println("Temperature function parameter lambda");
            double lambda = sc.nextDouble(); //0.1;
            Search algSA = new SimulatedAnnealingSearch(maxIter, itStep, k, lambda);

            // Instantiate the SearchAgent object
            startTime = System.nanoTime();
            agent = new SearchAgent(p, algSA);
            finalB  = (RedesBoard) algSA.getGoalState();
        }

        duration = System.nanoTime() - startTime;
        sec = duration/1000000000;
        System.out.println("Duration: " + sec +"s, which are " + sec/60.0 + " min");

        System.out.println (finalB.computeTotalDistanceCost());
        System.out.println ("Total info transmitted: " + finalB.computeTotalTransmitted());
        System.out.println ("Total info of sensors: "+finalB.getMaxInfo()+  " of maximum centers cap: "+numcentros*150);

	    // We print the results of the search
        System.out.println("Results: actions and instrumentation");
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        // TODO: remove DEBUG print for efficiency
        ArrayList<Integer> list = finalB.sensorsInTree();
        for(int i = 0; i < list.size(); ++i)
            if(list.get(i) == 0){
                System.out.println("ERROR, at least sensor "+ i +" is not connected!");
                break;
            }
        System.out.println("All sensors are connected");
    }

        private static void printInstrumentation(Properties properties) {
        Iterator keys = properties.keySet().iterator();
        while (keys.hasNext()) {
            String key = (String) keys.next();
            String property = properties.getProperty(key);
            System.out.println(key + " : " + property);
        }
    }
    
    private static void printActions(List actions) {
        for (int i = 0; i < actions.size(); i++) {
            String action = actions.get(i).toString();
            System.out.println(action);
        }
    }
}
