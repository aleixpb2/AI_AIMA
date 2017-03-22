package IA.ProbIA5;

import aima.search.framework.HeuristicFunction;

public class RedesHeuristicFunction implements HeuristicFunction {
    // TODO
    //1. todos los sensores conectados (pero no te perque transmitir)
    //maximizar total informacion que llega a los centros de datos
    //minimizar coste total transmisio (distancia)

    //de moment nomes he posat minimizar cost distancia
    public double getHeuristicValue(Object state){
        RedesBoard board=(RedesBoard)state;


        //RANGE total transmitted € (0, 5*numSensors)
        //RANGE total distance € (1, 25*diagonal * numCentros)
        int factor = 1;
        return board.computeTotalDistanceCost() - factor * board.computeTotalTransmitted();
        //return computeTotalDistanceCost(); // minimizar
        //return computeTotalTransmitted(); //maximizar


    }
}
