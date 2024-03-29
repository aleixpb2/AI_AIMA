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
    //private double totalCost;
    private ArrayList<ArrayList<IdDistSensor> > dist_matrix = null;
    //TODO GENERATE GETTERS AND SETTERS FOR OTHER FUNCTIONS : WHICH DO WE NEED?
    /* Constructor */

    public RedesBoard(int seedcentros, int seedsensores, int ncent, int nsens) {
        //totalCost=0;
        CentrosDatos cd = new CentrosDatos(ncent,seedcentros);
        Sensores sensores = new Sensores(nsens,seedsensores);
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

        //chose which one of these functions is the best for initial state
        //generarConexionesRandomToCenters();
        //generarConexionesInicial();
        generarConexionesRandom(); // this is the best option

    }
    private void generarConexionesRandomToCenters (){
        Random myRandom=new Random();
        int j;
        int maxIter = Math.max(sensors.length,centros.length) ;
        for (int i=0; i<sensors.length; ++i) {
            Pairintbool currentsensor = new Pairintbool(i,true);

            int aux = 0;
            do {
                j = myRandom.nextInt(centros.length);
                aux ++;
            } while (aux<maxIter && !createArc(currentsensor, new Pairintbool(j, false)) );


            if (aux>=maxIter) {
                do {
                    j = myRandom.nextInt(sensors.length);
                }while (!createArc(currentsensor, new Pairintbool(j, true)));
            }
        }
    }

    private void generarConexionesRandom (){
        Random myRandom=new Random();
        int j;
        int maxIter = sensors.length;
        for (int i=0; i<sensors.length; ++i) {
            Pairintbool currentsensor = new Pairintbool(i,true);
            boolean sensor;
            int aux = 0;
            do {
                sensor = true;
                j = myRandom.nextInt(sensors.length + centros.length);
                if (j >= sensors.length) {
                    j -= sensors.length;
                    sensor = false;
                }
                aux ++;
            } while (aux<maxIter && !createArc(currentsensor, new Pairintbool(j, sensor)) );
            if (aux==maxIter){
                // if we've iterated too much, we try to connect it only with centers
                do {
                    sensor = false;
                    j = myRandom.nextInt(centros.length);

                } while (!createArc(currentsensor, new Pairintbool(j, sensor)) );
            }
        }
    }

    private RedesBoard (HashMap<Integer,Pairintbool>  connex,  HashMap<Pairintbool, ArrayList<Integer>>incidentConnec,SensorM[] sensorlist,Centro[] centroslist,ArrayList<ArrayList<IdDistSensor> > dist_matr){
        //totalCost = cost;
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
        //create deep copy
        HashMap<Integer,Pairintbool> newConnexions = new HashMap<Integer, Pairintbool>();
        for (Integer i : this.getConnexions().keySet()){
            newConnexions.put(i,new Pairintbool(this.getConnexions().get(i).getID(),this.getConnexions().get(i).isSensor()));
        }
        HashMap<Pairintbool,ArrayList<Integer>> newIncident = new HashMap<Pairintbool, ArrayList<Integer>>();
        for (Pairintbool i : this.getIncidentConnected().keySet()){
            newIncident.put(i,new ArrayList<Integer>(this.getIncidentConnected().get(i)));
        }
        SensorM[] newsensors = new SensorM[this.sensors.length];
        for (int i=0; i<sensors.length;++i) newsensors[i] = sensors[i].myClone();

        RedesBoard newBoard = new RedesBoard(newConnexions,newIncident,newsensors,this.getCentros(),this.getDist_matrix());
        return newBoard;
    }

    private void generarConexionesInicial (){
        for(int i = 0; i < sensors.length; ++i) {
            int nextcloser = 0;
            Pairintbool currentsensor = new Pairintbool(i,true);
            int closerid;
            boolean closersensor;
            do {
                closerid = dist_matrix.get(i).get(nextcloser).getID();
                closersensor = dist_matrix.get(i).get(nextcloser).isSensor();
                nextcloser++;

            } while (!createArc(currentsensor, new Pairintbool(closerid, closersensor)));
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
        return Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
    }

    public PairCosts computeTotalDistanceCost (){
        double cost_sum = 0;
        double distance_sum = 0;

        for (Pairintbool i : incidentConnected.keySet()){
            int x1, y1, x2, y2;
            if (i.isSensor()){
                x1 = sensors[i.getID()].getCoordX();
                y1 = sensors[i.getID()].getCoordY();
            }else {
                x1 = centros[i.getID()].getCoordX();
                y1 = centros[i.getID()].getCoordY();
            }
            ArrayList<Integer>sensorlist = incidentConnected.get(i);
            for (int j=0; j<sensorlist.size(); ++j){
                x2 = sensors[sensorlist.get(j)].getCoordX();
                y2 = sensors[sensorlist.get(j)].getCoordY();
                double dist = getDist(x1,x2,y1,y2);
                cost_sum += dist*dist*sensors[sensorlist.get(j)].getCurrentCap();
                distance_sum += dist;
            }
        }
        //cost_sum = totalCost;
        return new PairCosts(cost_sum,distance_sum);
    }

    /**
     * suponiendo que currentCap de cada sensor es la cantidad de informacion acumulada hasta entonces
     * @return transm : la suma de la cantidad de informacion acumulada que llegan a todos los centros
     */
    public double computeTotalTransmitted(){
        double transm = 0;
        for (int i=0; i< centros.length;++i){
            Pairintbool current = new Pairintbool(i,false);
            if (incidentConnected.containsKey(current)){
                double sumCent = 0;
                ArrayList<Integer>sensorlist = incidentConnected.get(current);
                for (int j=0; j<sensorlist.size(); ++j){

                    sumCent+= Math.min(sensors[sensorlist.get(j)].getCurrentCap(), sensors[sensorlist.get(j)].getCapacidad()*3);
                }
                transm += Math.min(sumCent, 150);
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
            if(sensors[id2].getLast().equals(sensors[p.getID()].getLast())) {
                return false;
            }

            if (incidentConnected.containsKey(p)) {
                ArrayList<Integer> l = incidentConnected.get(p);
                return l.size() < 3;
            }
            else return true;
        }else{
            if (incidentConnected.containsKey(p)) {
                ArrayList<Integer> l = incidentConnected.get(p);
                return l.size() < 25;
            }
            else return true;
        }
    }
/*    public boolean checkCapacity (int id2, Pairintbool p){// id2: sensor we want to connect p: sensor or center to which we want to connect
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
       */

    public boolean createArc(Pairintbool p1, Pairintbool p2)  {
        /*1. Comprovar si connexions te la clau de lobjecte que vols connectar
            -En cas que no --> COnnectem si isPossibleAdd
            -En cas que si --> Mirem si la connexió que té porta a null
                - En cas que si -> COnnectem si isPossibleAdd
                - En cas que no -> No connectem
        */
        if ((!connexions.containsKey(p1.getID())) && isPossibleAdd(p1.getID(), p2 )){
            connexions.put(p1.getID(), p2);
            SensorM sensorm = sensors[p1.getID()];
            //En caso de poder añadir mas informacion, la añadimos. Sino, no actualizamos el volumen de informacion
            //if (checkCapacity(p1.getID(),p2))
            capacityRecursive(p2, Math.min(sensorm.getCurrentCap(), sensorm.getCapacidad()*3));

            if (incidentConnected.containsKey(p2)) {
                ArrayList<Integer> l = incidentConnected.get(p2);
                l.add(p1.getID());
                incidentConnected.put(p2,l);
            } else {
                ArrayList<Integer> l = new ArrayList<Integer>();
                l.add(p1.getID());
                incidentConnected.put(p2,l);
            }
            double dist;
            if (p2.isSensor()) {
                //if it's a sensor, we have to check the last element
                lastRecurse(p1, sensors[p2.getID()].getLast());
                dist =getDist(sensorm.getCoordX(),sensors[p2.getID()].getCoordX(), sensorm.getCoordY(), sensors[p2.getID()].getCoordY());
            }
            else{
                //if it's a center the last element is itself - always.
                lastRecurse(p1,p2);
                dist =getDist(sensorm.getCoordX(),centros[p2.getID()].getCoordX(), sensorm.getCoordY(), centros[p2.getID()].getCoordY());
            }
            return true;
        } else {
            return false; // p1 is already connected or the connection is impossible
        }
    }

    public boolean  removeArc(Pairintbool p1, Pairintbool p2)  {
        if(connexions.containsKey(p1.getID()) && connexions.get(p1.getID()).equals(p2)){
            connexions.remove(p1.getID());
            SensorM sensorm = sensors[p1.getID()];
            //Siempre podremos sacar informacion asi que actualizamos el volumen de informacion


            capacityRecursive(p2, -Math.min(sensorm.getCurrentCap(), sensorm.getCapacidad()*3));
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
        if (removeArc(p1, p2)) {

            if (createArc(p1, p3)) return true;
            else {

                createArc(p1,p2);

                return false;
            }
        }
        else {

            return false;
        }
    }

    public void capacityRecursive(Pairintbool p, double deltaCapacity){
        if(p.isSensor())  {
            double p_capacity_before = sensors[p.getID()].getCurrentCap();
            double p_capacidad = sensors[p.getID()].getCapacidad();
            //We always update the current sensor with the capacity that arrives.
            sensors[p.getID()].setCurrentCap(deltaCapacity + p_capacity_before);

            double sent_before = Math.min (p_capacity_before,3*p_capacidad);
            double sent_now = Math.min(sensors[p.getID()].getCurrentCap(),3*p_capacidad);

            double difference = sent_now - sent_before;
            if (difference!=0) {
                if (connexions.containsKey(p.getID())){

                    capacityRecursive(connexions.get(p.getID()), difference);
                }
            }

        }
    }

    public void lastRecurse (Pairintbool p, Pairintbool last){
        sensors[p.getID()].setLast(last);
        if (incidentConnected.containsKey(p)){
            ArrayList<Integer> fills =incidentConnected.get(p);
            for (int i=0; i<fills.size(); ++i){
                lastRecurse(new Pairintbool(fills.get(i),true),last);
            }
        }
    }



    public String toString() {
        String retVal = "";

        for (Pairintbool i: incidentConnected.keySet()){
            ArrayList<Integer> current = incidentConnected.get(i);
            if (i.isSensor()) retVal+= ("Sensor ");
            else retVal+=("Center ");
            retVal+=(String.valueOf(i.getID())+" with a capacity of "+sensors[i.getID()].getCapacidad() +" ---> Sensors: ");
            for (int j=0; j<current.size(); ++j){
                retVal+= String.valueOf(current.get(j))+" with a capacity of "+sensors[j].getCapacidad()+",";

            }
            retVal+="\n";
        }
        return retVal;
    }

    public int getMaxInfo(){
        int sum = 0;
        for(int i = 0; i < nSensors(); ++i)
            sum += sensors[i].getCapacidad();
        return sum;
    }

    public ArrayList<Integer> centersNotUsed(){ // list with IDs of unused centers
        ArrayList<Integer> ret = new ArrayList<>();
        for(int i = 0; i < nCentros(); ++i){
            Pairintbool p = new Pairintbool(i, false); // centro i
            if(!incidentConnected.containsKey(p) || incidentConnected.get(p) == null || incidentConnected.get(p).size() == 0) ret.add(i); // not appears in map or appers with no incidents

        }
        return ret;
    }
}
