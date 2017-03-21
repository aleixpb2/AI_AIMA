package IA.ProbIA5;

import IA.Red.Centro;
import IA.Red.CentrosDatos;
import IA.Red.Sensor;
import IA.Red.Sensores;
import aima.util.Pair;

import java.util.*;

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

    private SensorM[] sensors;
    private Centro [] centros;

    public HashMap<Integer, Pairintbool> getConnexions() {
        return connexions;
    }

    private HashMap<Integer,Pairintbool> connexions; // First: idSensor, Second: sensor or center to which is conencted (id + bool)
    private HashMap<Pairintbool, ArrayList<Integer>> numConnected; // Key: First -> id Second -> isSensor/center Value: list of sensor ids connected to the key
    private ArrayList<ArrayList<IdDistSensor> > dist_matrix;
    //TODO GENERATE GETTERS AND SETTERS FOR OTHER FUNCTIONS : WHICH DO WE NEED?
    /* Constructor */
    public RedesBoard(int seed, int ncent, int nsens) {
        CentrosDatos cd = new CentrosDatos(ncent,seed);
        Sensores sensores = new Sensores(nsens,seed);
        for (int i= 0 ; i<cd.size(); ++i){
            sensors[i]= new SensorM(sensores.get(i), i);
            centros[i]=cd.get(i);
        }

        connexions = new HashMap<Integer, Pairintbool>();
        numConnected = new HashMap<Pairintbool, ArrayList<Integer>>();

        dist_matrix = new ArrayList<ArrayList<IdDistSensor>>(sensors.length);
        generarDadesAuxiliars();
        generarConexionesInicial();
    }

    /* vvvvv TO COMPLETE vvvvv */

    private void generarConexionesInicial (){
        for(int i = 0; i < sensors.length; ++i) {
            Pairintbool closer = new Pairintbool(dist_matrix.get(i).get(0).getID(),dist_matrix.get(i).get(0).isSensor()); // get the closest
            try {
                createArc(new Pairintbool(i, true), closer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void generarDadesAuxiliars(){
        for (int i=0;i<sensors.length; ++i){

            dist_matrix.set(i,new ArrayList<IdDistSensor>());
            ArrayList<IdDistSensor> vecactual = dist_matrix.get(i);
            for (int j=0; j<sensors.length; ++j){
                double dist = getDist(sensors[i].getCoordX(),sensors[j].getCoordX(),sensors[i].getCoordY(),sensors[j].getCoordY());

                vecactual.set(j, new IdDistSensor(j,dist,true));

            }
            for (int k =0 ;k<centros.length; k++){
                double dist = getDist(sensors[i].getCoordX(),centros[k].getCoordX(),sensors[i].getCoordY(),centros[k].getCoordY());
                vecactual.set(k+sensors.length, new IdDistSensor(k,dist,false));

            }

            Collections.sort(dist_matrix.get(i), new Comparator<IdDistSensor>() {
                @Override
                public int compare (IdDistSensor o1 , IdDistSensor o2){
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

    private double computeTotalDistanceCost (){
        double dist = 0;
        for (Pairintbool i : numConnected.keySet()){
            if (i.isSensor()){
                int x1 = sensors[i.getID()].getCoordX();
                int y1 = sensors[i.getID()].getCoordY();
                ArrayList<Integer>sensorlist = numConnected.get(i);
                for (int j=0; j<sensorlist.size(); ++j){
                    int x2 = sensors[sensorlist.get(j)].getCoordX();
                    int y2 = sensors[sensorlist.get(j)].getCoordY();
                    dist += getDist(x1,x2,y1,y2);
                }
            }
            else {
                int x1 = centros[i.getID()].getCoordX();
                int y1 = centros[i.getID()].getCoordY();
                ArrayList<Integer>sensorlist = numConnected.get(i);
                for (int j=0; j<sensorlist.size(); ++j){
                    int x2 = sensors[sensorlist.get(j)].getCoordX();
                    int y2 = sensors[sensorlist.get(j)].getCoordY();
                    dist += getDist(x1,x2,y1,y2);
                }
            }

        }
        return dist;
    }

    /**
     * suponiendo que currentCap de cada sensor es la cantidad de informacion acumulada hasta entonces
     * @return transm : la suma de la cantidad de informacion acumulada que llegan a todos los centros
     */
    private double computeTotalTransmitted (){
        double transm = 0;
        for (Pairintbool i: numConnected.keySet()){
            if (!i.isSensor()){ //si es centro
                ArrayList<Integer>sensorlist = numConnected.get(i);
                for (int j=0; j<sensorlist.size(); ++j){
                    transm+= sensors[sensorlist.get(j)].getCurrentCap();
                }


            }

        }
        return transm;
    }
//    /* Heuristic function */
    // TODO
    public double heuristic(){

    //1. todos los sensores conectados (pero no te perque transmitir)
    //maximizar total informacion que llega a los centros de datos
    //minimizar coste total transmisio (distancia)

        //de moment nomes he posat minimizar cost distancia
        return computeTotalDistanceCost();
    }

    // Operators

    public boolean isPossibleAdd(int id2, Pairintbool p){ // id2: sensor we want to connect p: sensor or center to which we want to connect
        if(p.isSensor()){
            if(sensors[id2].getLast().equals(sensors[p.getID()].getLast())) return false;
            ArrayList<Integer> l = numConnected.get(p);

            return checkCapacityRecursive(p, sensors[id2].getCurrentCap()) && l.size() < 3;
        }else{
            double cap = 150;
            ArrayList<Integer> l = numConnected.get(p);
            double currentCap = 0;
            for(int i = 0; i < l.size(); ++i){
                currentCap += sensors[i].getCurrentCap();
            }

            //TODO: CANVIAR AIXO, si hi ha mes de 150 o si hi ha mes de la informacio possible a transmetre si que es pot crear la conexio pero NO ENVIA INFORMACIO!
            return currentCap + sensors[id2].getCurrentCap() < cap && l.size() < 25;
        }
    }

    public boolean createArc(Pairintbool p1, Pairintbool p2)  {
        if (!connexions.containsKey(p1.getID()) &&
                isPossibleAdd(p2.getID(), p1 )){
            connexions.put(p1.getID(), p2);
            SensorM sensorm = sensors[p1.getID()];
            capacityRecursive(p2, sensorm.getCurrentCap());

            if (numConnected.containsKey(p2)) {
                ArrayList<Integer> l = numConnected.get(p2);
                l.add(p1.getID());
            } else {
                ArrayList<Integer> l = new ArrayList<Integer>();
                l.add(p1.getID());
            }
            sensors[p1.getID()].setLast(sensors[p2.getID()].getLast());
            return true;
        } else return false; // p1 is already connected or the connection is impossible
    }

    public boolean  removeArc(Pairintbool p1, Pairintbool p2)  {
        if(connexions.containsKey(p1.getID()) && connexions.get(p1.getID()).equals(p2)){
            connexions.remove(p1.getID());
            SensorM sensorm = sensors[p1.getID()];
            capacityRecursive(p2, -sensorm.getCurrentCap());

            ArrayList<Integer> l = numConnected.get(p2);
            for(int i = 0; i < l.size(); ++i){
                if(l.get(i).equals(p1.getID())){
                    l.remove(i);
                    break;
                }
            }
            sensors[p1.getID()].setLast(new Pairintbool(p1.getID(), true));
            return true;
        }else {
            // p1 has no connections or is not connected to p2
            return false;
        }
    }

    public boolean changeArc(Pairintbool p1, Pairintbool p2, Pairintbool p3)  {
            if (removeArc(p1, p2)) return createArc(p1, p3);
            else return false;

    }

    public void capacityRecursive(Pairintbool p, double deltaCapacity){
        if(connexions.containsKey(p.getID())) { // is not a leaf
            sensors[p.getID()].setCurrentCap(deltaCapacity + sensors[p.getID()].getCurrentCap());
            capacityRecursive(connexions.get(p.getID()), deltaCapacity);
        }
    }

    public boolean checkCapacityRecursive(Pairintbool p, double capToAdd){
        //TODO: AQUI EL LIMIT NO ES GETCAPACIDAD*3 ?
        if(sensors[p.getID()].getCurrentCap() + capToAdd > sensors[p.getID()].getCapacidad()*2) return false;
        if(connexions.containsKey(p.getID())) { // is not a leaf
            return checkCapacityRecursive(connexions.get(p.getID()), capToAdd);
        }else{
            return true;
        }
    }
}
