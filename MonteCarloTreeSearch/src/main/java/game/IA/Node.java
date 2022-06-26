package game.IA;

import java.util.List;

import game.board.Board;
import game.board.Piece;
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

    

    public String toString(){
        //this.getState().printState();
        return String.format("Node[isFinalState=%s, id=%s, n=%d, q=%d, i=%d, father=%s, children=%s, moves=%d]", state.isFinalState(),this.getNodeId() , n, q, i, this.getFather()==null ? "null" : this.getFather().getNodeId(), children.size(), getMoves().size());
    }

    public Double getNodeId(){
        Double res = 0.0;
        Board board = this.getState().getBoard();
        for(int i = 0; i<board.getNumOfRows();i++){
            for(int j = 0; j<board.getNumOfColumns();j++){
                Integer number;
                if(board.getBoardDisplay()[i][j].equals(Piece.EMPTY))
                    number = 0;
                if(board.getBoardDisplay()[i][j].equals(Piece.BLACK_PAWN))
                    number = 1;
                if(board.getBoardDisplay()[i][j].equals(Piece.WHITE_PAWN))
                    number = 2;
                else
                    number = 3;
                
                res += i*j*number; 
            }
        }
        return res;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        return true;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

}
