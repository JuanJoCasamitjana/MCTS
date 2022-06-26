package game.IA;

import java.util.List;

import game.game.Game;
import game.game.Move;
import lombok.Data;

@Data
public class Node {

    private Game state;
    private List<Move> moves;
    protected Integer n, q, i;
    private Node father;
    private List<Node> children;

    public Node(Game game, List<Move> moves, Integer n, Integer q, Integer i, Node father, List<Node> children){
        this.state = game;
        this.moves = moves;
        this.n = n;
        this.q = q;
        this.i = i;
        this.father = father;
        this.children = children;

    }

}
