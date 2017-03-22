import IA.ProbIA5.RedesBoard;
import IA.ProbIA5.RedesGoalTest;
import IA.ProbIA5.RedesHeuristicFunction;
import IA.ProbIA5.RedesSuccesorFunction;
import aima.search.framework.GraphSearch;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.AStarSearch;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        /**
         *  For a problem to be solvable:
         *    count(0,prob) % 2 == count(0,sol) %2
         */
        int seed = 30;
        int numcentros = 4;
        int numsensors = 100;

        RedesBoard board = new RedesBoard(seed,numcentros,numsensors );

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

        SearchAgent agent;
        if(HC) {// Hill Climbing, no parameters
            Search algHC = new HillClimbingSearch();

            // Instantiate the SearchAgent object
            agent = new SearchAgent(p, algHC);
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
            agent = new SearchAgent(p, algSA);
        }

	// We print the results of the search
        System.out.println();
        printActions(agent.getActions());
        printInstrumentation(agent.getInstrumentation());

        // You can access also to the goal state using the
	// method getGoalState of class Search

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
            String action = (String) actions.get(i);
            System.out.println(action);
        }
    }
    
}
