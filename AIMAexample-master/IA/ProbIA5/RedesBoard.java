package IA.ProbIA5;

import IA.Red.Centro;
import IA.Red.CentrosDatos;
import IA.Red.Sensor;
import IA.Red.Sensores;
import com.sun.org.apache.xpath.internal.operations.Bool;
import sun.misc.Sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by bejar on 17/01/17.
 */
public class RedesBoard {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     *

    /* State data structure
        vector with the parity of the coins (we can assume 0 = heads, 1 = tails
     */

    private Sensor[] sensors;
    private Centro [] centros;
    private HashMap<Integer,Pairintbool> connexions;
    private ArrayList<ArrayList<PairID_Dist> > dist_matrix;
    private static int [] solution;
    private int N;

    /* Constructor */
    public RedesBoard(int seed, int ncent, int nsens) {
        CentrosDatos cd = new CentrosDatos(ncent,seed);
        Sensores sensores = new Sensores(nsens,seed);
        for (int i= 0 ; i<cd.size(); ++i){
            sensors[i]=sensores.get(i);
            centros[i]=cd.get(i);
        }

        dist_matrix = new ArrayList<ArrayList<PairID_Dist>>(sensors.length);
        generarDadesAuxiliars();
        generarConexionesInicial();
    }

    /* vvvvv TO COMPLETE vvvvv */

    private void generarConexionesInicial (){

    }
    private void generarDadesAuxiliars(){
        for (int i=0;i<sensors.length; ++i){

            dist_matrix.set(i,new ArrayList<PairID_Dist>());
            ArrayList<PairID_Dist> vecactual = dist_matrix.get(i);
            for (int j=0; j<sensors.length; ++j){
                double dist = getDist(sensors[i].getCoordX(),sensors[j].getCoordX(),sensors[i].getCoordY(),sensors[j].getCoordY());

                vecactual.set(j, new PairID_Dist(j,dist,true));

            }
            for (int k =0 ;k<centros.length; k++){
                double dist = getDist(sensors[i].getCoordX(),centros[k].getCoordX(),sensors[i].getCoordY(),centros[k].getCoordY());
                vecactual.set(k+sensors.length, new PairID_Dist(k,dist,false));

            }

            Collections.sort(dist_matrix.get(i), new Comparator<PairID_Dist>() {
                @Override
                public int compare (PairID_Dist o1 ,PairID_Dist o2){
                    if (o2.getDist()>o1.getDist()) return -1;
                    else if (o2.getDist()==o1.getDist()) return 0;
                    else return 1;
                }
            });
        }

    }
    private double getDist (int x1,int x2, int y1,int y2){
        double dist = Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));

        return dist;
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
