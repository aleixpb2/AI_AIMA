package IA.ProbIA5;

/**
 * Created by bejar on 17/01/17.
 */
public class ProbIA5Board {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     *

    /* State data structure
        vector with the parity of the coins (we can assume 0 = heads, 1 = tails
     */

    private int [] board;    
    private static int [] solution;
    private int N;

    /* Constructor */
    public ProbIA5Board(int []init, int[] goal) {

        board = new int[init.length];
        solution = new int[init.length];

        for (int i = 0; i< init.length; i++) {
            board[i] = init[i];
            solution[i] = goal[i];
        }
        N = board.length;

    }

    /* vvvvv TO COMPLETE vvvvv */
    public void flip_it(int i){
        // flip the coins i and i + 1
        board[i] = 1-board[i];
        board[(i+1)%N] = 1-board[(i+1)%N];
    }

    /* Heuristic function */
    public double heuristic(){
        // compute the number of coins out of place respect to solution
        double diff = 0;
        for(int i = 0; i < N; ++i){
             if(board[i] != solution[i])
                 ++diff;
         }
        return diff;
    }

     /* Goal test */
     public boolean is_goal(){
         // compute if board = solution
         for(int i = 0; i < N; ++i){
             if(board[i] != solution[i])
                 return false;
         }
         return true;
     }
    public int [] getGoal (){
         return solution;
    }
     /* auxiliary functions */

     // Some functions will be needed for creating a copy of the state
     public int[] boardCopy(){
         return board.clone();
     }
     
     public int getLength(){
         return N;
     }

    /* ^^^^^ TO COMPLETE ^^^^^ */

}
