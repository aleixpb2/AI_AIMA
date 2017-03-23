package IA.ProbIA5;

import IA.Red.Centro;
import IA.Red.CentrosDatos;
import IA.Red.Sensores;

import java.util.*;

public class RedesBoard {
    /* Class independent from AIMA classes
       - It has to implement the state of the problem and its operators
     */

    public static String SWAP = "Swap";
    private SensorM[] sensors;
    private static Centro [] centros;
    private HashMap<Integer,Pairintbool>  connexions; // First: idSensor, Second: sensor or center to which is conencted (id + bool)
    private HashMap<Pairintbool, ArrayList<Integer>>  incidentConnected; // Key: First -> id Second -> isSensor/center Value: list of sensor ids connected to the key

    private ArrayList<ArrayList<IdDistSensor> > dist_matrix = null;
    //TODO GENERATE GETTERS AND SETTERS FOR OTHER FUNCTIONS : WHICH DO WE NEED?
    /* Constructor */

    public RedesBoard(int seed, int ncent, int nsens) {
        CentrosDatos cd = new CentrosDatos(ncent,seed);
        Sensores sensores = new Sensores(nsens,seed);
        centros = new Centro[cd.size()];
        sensors = new SensorM[sensores.size()];
        for (int i= 0 ; i<cd.size(); ++i){
            centros[i]=cd.get(i);
        }
        for (int i=0; i<sensores.size(); ++i){
            sensors[i]= new SensorM(sensores.get(i), i);
        }

        connexions = new HashMap<Integer, Pairintbool>();
        incidentConnected = new HashMap<Pairintbool, ArrayList<Integer>>();

        if (dist_matrix==null) {
            dist_matrix = new ArrayList<ArrayList<IdDistSensor>>();
            generarDistMatrix();
        }
        generarConexionesRandom();
        //generarConexionesInicial();
    }

    private void generarConexionesRandom (){

        Random myRandom=new Random();
        int j;
        for (int i=0; i<sensors.length; ++i) {
            Pairintbool currentsensor = new Pairintbool(i,true);
            boolean sensor = true;
            do {
                j = myRandom.nextInt(sensors.length + centros.length);
                if (j >= sensors.length) {
                    j -= sensors.length;
                    sensor = false;
                    //System.out.println (j);
                }
            } while (!createArc(currentsensor, new Pairintbool(j, sensor)));

        }
    }
    private RedesBoard (HashMap<Integer,Pairintbool>  connex,  HashMap<Pairintbool, ArrayList<Integer>>incidentConnec,SensorM[] sensorlist,Centro[] centroslist,ArrayList<ArrayList<IdDistSensor> > dist_matr){
        connexions = connex;
        incidentConnected = incidentConnec;
        sensors = sensorlist;
        centros = centroslist;
        dist_matrix = dist_matr;
    }

    //GETTERS AND SETTERS
    public HashMap<Integer, Pairintbool> getConnexions() {
        return connexions;
    }

    public HashMap<Pairintbool, ArrayList<Integer>> getIncidentConnected() {
        return incidentConnected;
    }
    public int nSensors (){
        return sensors.length;
    }
    public int nCentros (){
        return centros.length;
    }

    public SensorM[] getSensors() {
        return sensors;
    }
    public Centro[] getCentros(){
        return centros;
    }


    public ArrayList<ArrayList<IdDistSensor>> getDist_matrix() {
        return dist_matrix;
    }


    public RedesBoard copy (){
        RedesBoard newBoard = new RedesBoard((HashMap<Integer,Pairintbool>)this.getConnexions().clone(),(HashMap<Pairintbool, ArrayList<Integer>> )this.getIncidentConnected().clone(),this.getSensors().clone(),this.getCentros(),this.getDist_matrix());
        return newBoard;
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
    private void generarDistMatrix(){
        for (int i=0;i<sensors.length; ++i){


            dist_matrix.add(i,new ArrayList<IdDistSensor>());
            ArrayList<IdDistSensor> vecactual = dist_matrix.get(i);
            for (int j=0; j<sensors.length; ++j){
                double dist = getDist(sensors[i].getCoordX(),sensors[j].getCoordX(),sensors[i].getCoordY(),sensors[j].getCoordY());

                vecactual.add(j, new IdDistSensor(j,dist,true));

            }
            for (int k =0 ;k<centros.length; k++){
                double dist = getDist(sensors[i].getCoordX(),centros[k].getCoordX(),sensors[i].getCoordY(),centros[k].getCoordY());
                vecactual.add(k+sensors.length, new IdDistSensor(k,dist,false));

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

    public double computeTotalDistanceCost (){
        double sum = 0;
        for (Pairintbool i : incidentConnected.keySet()){
            System.out.println("Inside computeTotalDistanceCost with "+ i.toString());
            if (i.isSensor()){
                int x1 = sensors[i.getID()].getCoordX();
                int y1 = sensors[i.getID()].getCoordY();
                ArrayList<Integer>sensorlist = incidentConnected.get(i);
                for (int j=0; j<sensorlist.size(); ++j){
                    int x2 = sensors[sensorlist.get(j)].getCoordX();
                    int y2 = sensors[sensorlist.get(j)].getCoordY();
                    sum += Math.sqrt(getDist(x1,x2,y1,y2)) * sensors[sensorlist.get(j)].getCurrentCap();
                }
            }
            else {
                int x1 = centros[i.getID()].getCoordX();
                int y1 = centros[i.getID()].getCoordY();
                ArrayList<Integer>sensorlist = incidentConnected.get(i);
                for (int j=0; j<sensorlist.size(); ++j){
                    int x2 = sensors[sensorlist.get(j)].getCoordX();
                    int y2 = sensors[sensorlist.get(j)].getCoordY();
                    sum += Math.sqrt(getDist(x1,x2,y1,y2)) * sensors[sensorlist.get(j)].getCurrentCap();
                }
            }
        }
        return sum;
    }

    /**
     * suponiendo que currentCap de cada sensor es la cantidad de informacion acumulada hasta entonces
     * @return transm : la suma de la cantidad de informacion acumulada que llegan a todos los centros
     */
    public double computeTotalTransmitted (){
        double transm = 0;
        for (Pairintbool i: incidentConnected.keySet()){
            if (!i.isSensor()){ //si es centro
                ArrayList<Integer>sensorlist = incidentConnected.get(i);
                for (int j=0; j<sensorlist.size(); ++j){
                    transm+= sensors[sensorlist.get(j)].getCurrentCap();
                }


            }

        }
        return transm;
    }

    // Operators

    /**
     * In this function we check if the number of sensors connected is less than the maximum, and if we're not creating cycles
     * @param id2 sensor we want to connect
     * @param p sensor or center which we want to connect to
     * @return if the connection is possible
     */
    private boolean isPossibleAdd(int id2, Pairintbool p){

        if(p.isSensor()){
            if(sensors[id2].getLast().getID()== sensors[p.getID()].getLast().getID()) return false;
            if (incidentConnected.keySet().contains(p)) {
                ArrayList<Integer> l = incidentConnected.get(p);
                return l.size() < 3;
            }
            else return true;
        }else{
            ArrayList<Integer> l = incidentConnected.get(p);
            return  l.size() < 25;
        }
    }
    public boolean checkCapacity (int id2, Pairintbool p){// id2: sensor we want to connect p: sensor or center to which we want to connect
        if (p.isSensor()){
            return checkCapacityRecursive(p,sensors[id2].getCurrentCap());
        }
        else {
            double cap = 150;
            double currentCap = 0;
            if (incidentConnected.keySet().contains(p)){
                ArrayList<Integer> l = incidentConnected.get(p);
                for(int i = 0; i < l.size(); ++i){
                    currentCap += sensors[i].getCurrentCap();
                }
                return (currentCap + sensors[id2].getCurrentCap()) < cap;
            }
            else return true;
        }

    }





    public boolean createArc(Pairintbool p1, Pairintbool p2)  {
//        System.out.println ("Trying to create arc between "+p1.getID()+" "+p1.isSensor()+" and "+p2.getID()+" "+p2.isSensor());
        if (!connexions.containsKey(p1.getID()) &&
                isPossibleAdd(p2.getID(), p1 )){
            connexions.put(p1.getID(), p2);
            SensorM sensorm = sensors[p1.getID()];
            //En caso de poder añadir mas informacion, la añadimos. Sino, no actualizamos el volumen de informacion
            if (checkCapacity(p1.getID(),p2)) capacityRecursive(p2, sensorm.getCurrentCap());

            if (incidentConnected.containsKey(p2)) {
                System.out.println("lel");
                ArrayList<Integer> l = incidentConnected.get(p2);
                l.add(p1.getID());
                incidentConnected.put(p2,l);
            } else {
                System.out.println("altre");
                ArrayList<Integer> l = new ArrayList<Integer>();
                l.add(p1.getID());
                incidentConnected.put(p2,l);
            }
            lastRecurse(p1,sensors[p2.getID()].getLast());
            //System.out.println ("Successfully created arc between "+p1.getID()+" "+p1.isSensor()+" and "+p2.getID()+" "+p2.isSensor());
            return true;
        } else return false; // p1 is already connected or the connection is impossible
    }

    public boolean  removeArc(Pairintbool p1, Pairintbool p2)  {
        if(connexions.containsKey(p1.getID()) && connexions.get(p1.getID()).equals(p2)){
            connexions.remove(p1.getID());
            SensorM sensorm = sensors[p1.getID()];
            //Siempre podremos sacar informacion asi que actualizamos el volumen de informacion
            capacityRecursive(p2, -sensorm.getCurrentCap());

            ArrayList<Integer> l = incidentConnected.get(p2);
            for(int i = 0; i < l.size(); ++i){
                if(l.get(i).equals(p1.getID())){
                    l.remove(i);
                    break;
                }
            }
            lastRecurse(p1,p1);

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
        if(connexions.containsKey(p.getID()) && p.isSensor())  { // is not a leaf
            sensors[p.getID()].setCurrentCap(deltaCapacity + sensors[p.getID()].getCurrentCap());
            //System.out.println(p.getID());

            capacityRecursive(connexions.get(p.getID()), deltaCapacity);
            }

    }
    public void lastRecurse (Pairintbool p, Pairintbool last){
        System.out.println("Assigning "+p.getID()+" to last of tree "+ last.getID());
        sensors[p.getID()].setLast(last);
        if (incidentConnected.keySet().contains(p)){
            ArrayList<Integer> fills =incidentConnected.get(p);
            for (int i=0; i<fills.size(); ++i) System.out.println (fills.get(i));
            for (int i=0; i<fills.size(); ++i){
                lastRecurse(new Pairintbool(fills.get(i),true),last);
            }
        }
    }

    public boolean checkCapacityRecursive(Pairintbool p, double capToAdd){

        if(sensors[p.getID()].getCurrentCap() + capToAdd > sensors[p.getID()].getCapacidad()*3) return false;
        if(connexions.containsKey(p.getID()) && p.isSensor()) { // is not a leaf
            System.out.println ("checking "+p.isSensor()+" "+p.getID()+"which has a capacity of "+sensors[p.getID()].getCapacidad()*3 +" adding capacity of "+capToAdd);
            return checkCapacityRecursive(connexions.get(p.getID()), capToAdd);
        }else{
            return true;
        }
    }

    public String toString() {
        String retVal = "";

        for (Pairintbool i: incidentConnected.keySet()){
            ArrayList<Integer> current = incidentConnected.get(i);
            if (i.isSensor()) retVal+= ("Sensor ");
            else retVal+=("Center ");
            retVal+=(String.valueOf(i.getID())+"---> Sensors:");
            for (int j=0; j<current.size(); ++j){
                retVal+= String.valueOf(current.get(j));

            }
            retVal+="\n";
        }

        return retVal;
    }
}
