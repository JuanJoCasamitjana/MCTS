package game.IA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import game.game.Game;
import game.game.Move;

public class UCT2 {



    public static Move buscaSolucion(Game s0, long tiempoMax) throws Exception{
        Node n0 = crearNodo(s0, null);
        Double mainNodeId = n0.getNodeId();
        System.out.println("Principio: " + n0);
        long tiempoGastado = 0L;
        while(tiempoGastado < tiempoMax*1000){
            long begginingTime = System.currentTimeMillis();
            
            Node n = seleccion(n0);
            System.out.println("Presimulación: " + n);
            Integer delta = defaultPolicy(n);   //Simulación
                
            retropropaga(n, delta);

            tiempoGastado+=System.currentTimeMillis()-begginingTime;
        }
        //return v0.getMoves().get(bestChild(v0, 0));
        return n0.getMoves().get(mejorSucesorUCT(n0, 1/Math.sqrt(2)));
    }

    private static Node seleccion(Node n) throws Exception {
        while(!n.getState().isFinalState()){
            if(n.getI() < n.getMoves().size()){
                return expande(n);
            }else{
                n = n.getChildren().get(mejorSucesorUCT(n, 1/Math.sqrt(2)));
            }
        }
        return n;
    }

    private static Node expande(Node n) throws Exception {
        Move move = n.getMoves().get(n.getI());
        n.setI(n.getI() + 1);
        Game newState = n.getState().applyMovement(move);
        Node child = crearNodo(newState, n);
        n.getChildren().add(child);
        return child;
    }

    private static Integer mejorSucesorUCT(Node n, double c) {
        Integer iRes = 0;
        Double maxValue = Double.MIN_VALUE;

        for(int i = 0; i<n.getChildren().size(); i++){
            Integer numerador1 = n.getChildren().get(i).getQ();
            Integer denominador = n.getChildren().get(i).getN();
            Double numerador2 = 2 * Math.log(n.getN());
            Double value = (numerador1/denominador) + c*Math.sqrt(numerador2/denominador);
            if(value > maxValue){
                maxValue = value;
                iRes = i;
            }
        }
        return iRes;
    }

    public static Integer defaultPolicy(Node v) throws Exception{
        Game s = Game.copy(v.getState());
        List<Move> movs = s.getMoves();
        Integer player = v.getFather().getState().getTurn();

        while(!s.isFinalState()){
            Move a = movs.get(0);
            s = s.applyMovement(a);
            movs = s.getMoves();
        }

        if((s.blackWins() && player==1) || (s.whiteWins() && player==2))
            return 1;
        else
            return -1;

    }

    private static void retropropaga(Node n, Integer delta){

        while(n != null){
            n.setN(n.getN() + 1);
            n.setQ(n.getQ() + delta);
            delta = -delta;
            n = n.getFather();
        }
        
    }

    private static Node crearNodo(Game s0, Node father) {
        return new Node(s0, s0.getMoves(), 0, 0, 0, father, new ArrayList<Node>());
    }

    public static void main(String[] args) throws Exception{

        Game game = Game.getInitialState(1, -1);

        Move move = buscaSolucion(game, 1);
        

    }

    
    
}
