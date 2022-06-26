package game.IA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.game.Game;
import game.game.Move;

public class UCT {

    public static Move searchForSolution(Game game, Integer timeMax) throws Exception{

        long timeSpentInMillis = 0L;

        Node v0 = createNode(game, null);

        while(timeSpentInMillis < timeMax){
            long begginingTime = System.currentTimeMillis();

            Node v1 = treePolicy(v0);
            Integer delta = defaultPolicy(v1);
            backup(v1, delta);

            timeSpentInMillis+=System.currentTimeMillis()-begginingTime;
        }

        return v0.getMoves().get(bestChild(v0,0));

    }

    private static Node createNode(Game game, Node father) {
        return new Node(game, game.getMoves(), 0, 0, 0, father, new ArrayList<Node>());
    }

    private static Node treePolicy(Node v) throws Exception {

        while (!v.getState().isFinalState()){

            //v.getState().printState();

            if(v.i < v.getMoves().size()){
                System.out.println("Expanding...");
                return expand(v);
            }
                
            else{
                System.out.println("Getting best child...");
                v = v.getChildren().get(bestChild(v, 1/Math.sqrt(2)));
            }
                
        }
        throw new Exception("Abnormal behaviour");
    }

    private static Node expand(Node v) throws Exception {
        Game s = v.getState().applyMovement(v.getMoves().get(v.i));
        v.i = v.i + 1;
        Node child = createNode(s, v);
        v.getChildren().add(child);
        return child;
    }

    private static int bestChild(Node v, double c) {
        Integer iRes = 0;
        Double maxValue = Double.MIN_VALUE;

        for(int i = 0; i<v.getChildren().size(); i++){
            Double value = (v.getChildren().get(i).q/v.getChildren().get(i).n)+c*Math.sqrt(2*Math.log(v.n)/v.getChildren().get(i).n);
            if(value > maxValue){
                maxValue = value;
                iRes = i;
            }
                
        }

        return iRes;
    }

    private static Integer defaultPolicy(Node v) throws Exception {
        Game s = v.getState();
        List<Move> movs = v.getMoves();
        Integer player = v.getFather().getState().getTurn(); 

        while(!s.isFinalState()){
            Move a = movs.get(new Random().nextInt(movs.size()));
            s = s.applyMovement(a);
            movs = s.getMoves();
        }

        if((s.whiteWins() && player == 2) || (s.blackWins() && player == 1))
            return 1;
        else
            return -1;
    }

    private static void backup(Node v, Integer delta) {

        while(v != null){
            v.n = v.n +1;
            v.q = v.q + delta;
            delta = -delta;
            v = v.getFather();
        }

    }
   
    

    


    


    public static void main(String[] args) {

      
    }
    
}
