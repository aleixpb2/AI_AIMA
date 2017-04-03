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

        int factor = 4000;
        PairCosts a = board.computeTotalDistanceCost();
        double total_transmitted =  board.computeTotalTransmitted();

        return ((a.getFirst()) - factor*total_transmitted);

        // computeTotalDistanceCost().first(); --> minimizar

        // computeTotalTransmitted(); -->maximizar


    }
}
