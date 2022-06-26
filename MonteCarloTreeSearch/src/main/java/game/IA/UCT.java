package game.IA;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import game.game.Game;
import game.game.Move;

public class UCT {

    public static Move buscaSolucion(Game s0, long tiempoMax) throws Exception{
        Node v0 = crearNodo(s0, null);
        long tiempoGastado = 0L;
        while(tiempoGastado < tiempoMax*1000){
            System.out.println("-------------Iteración nueva----------");
            long tiempoInicial = System.currentTimeMillis();

            Node v1 = treePolicy(v0);
            Integer delta = defaultPolicy(v1);
            backup(v1, delta);
            tiempoGastado += System.currentTimeMillis() - tiempoInicial;
        }
        return v0.getMoves().get(bestChild(v0, 0));
    }

    private static Node crearNodo(Game s0, Node father) {
        return new Node(s0, s0.getMoves(), 0, 0, 0, father, new ArrayList<Node>());
    }

    private static Node treePolicy(Node v) throws Exception{
        System.out.println("Treepolicy receives " + v.toString());
        while(!v.getState().isFinalState()){
            if(v.getI() < v.getMoves().size()){
                System.out.println(v.getI() + " es menor que " + v.getMoves().size());
                return expand(v);
            }
                
            else{
                System.out.println(v.getI() + " no es menor que " + v.getMoves().size());
                v = v.getChildren().get(bestChild(v, 1/(Math.sqrt(2))));
            }    
                
        }
        return v;
    }

    private static Node expand(Node v) throws Exception{
        System.out.println("Expand receives " + v.toString());
        Game s = v.getState().applyMovement(v.getMoves().get(v.getI()));
        v.setI(v.getI() + 1);
        Node child = crearNodo(s, v);
        List<Node> vChildren = v.getChildren();
        vChildren.add(child);
        v.setChildren(vChildren);
        return child;
    }

    private static int bestChild(Node v, double c) {
        System.out.println("BestChild receives " + v.toString() + " " + c);
        Integer iRes = 0;
        Double maxValue = Double.MIN_VALUE;

        for(int i = 0; i<v.getChildren().size(); i++){
            Integer numerador1 = v.getChildren().get(i).getQ();
            Integer denominador = v.getChildren().get(i).getN();
            Double numerador2 = 2 * Math.log(v.getN());

            Double value = (numerador1/denominador) + c*Math.sqrt(numerador2/denominador);
            if(value > maxValue){
                maxValue = value;
                iRes = i;
            }
        }
        return iRes;
    }

    public static Integer defaultPolicy(Node v) throws Exception{
        System.out.println("DefaultPolicy receives " + v.toString());
        Game s = v.getState();
        List<Move> movs = v.getMoves();
        Integer player = v.getFather().getState().getTurn();

        while(!s.isFinalState()){
            Move a = movs.get(new Random().nextInt(movs.size()));
            s = s.applyMovement(a);
            movs = s.getMoves();
            //s.printState();
        }

        if((s.blackWins() && player==1) || (s.whiteWins() && player==2))
            return 1;
        else
            return -1;

    }

    public static void backup(Node v, Integer delta){
        System.out.println("Backup receives: " + v + " " + delta);
        while(v!=null){
            v.setN(v.getN()+1);
            v.setQ(v.getQ()+delta);
            delta = -delta;
            v = v.getFather();
        }
    }


    public static void main(String[] args) throws Exception{

        Game game = Game.getInitialState(1, -1);

        Move move = buscaSolucion(game, 2);
        System.out.println(game.getMoves().contains(move));
        

    }
}
