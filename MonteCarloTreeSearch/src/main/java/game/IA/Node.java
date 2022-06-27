package game.IA;

import java.util.List;

import game.board.Board;
import game.board.Piece;
import game.game.Game;
import game.game.Move;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
        for(int a = 0; a<board.getNumOfRows();a++){
            for(int b = 0; b<board.getNumOfColumns();b++){
                Integer number;
                if(board.getBoardDisplay()[a][b].equals(Piece.EMPTY))
                    number = 0;
                if(board.getBoardDisplay()[a][b].equals(Piece.BLACK_PAWN))
                    number = 1;
                if(board.getBoardDisplay()[a][b].equals(Piece.WHITE_PAWN))
                    number = 2;
                else
                    number = 3;
                
                res += (a+1)*(b+1) *number; 
            }
        }
        return res*this.getMoves().size();
    }

    public static Double getGameId(Game g){
        Double res = 0.0;
        Board board = g.getBoard();
        for(int a = 0; a<board.getNumOfRows();a++){
            for(int b = 0; b<board.getNumOfColumns();b++){
                Integer number;
                if(board.getBoardDisplay()[a][b].equals(Piece.EMPTY))
                    number = 0;
                if(board.getBoardDisplay()[a][b].equals(Piece.BLACK_PAWN))
                    number = 1;
                if(board.getBoardDisplay()[a][b].equals(Piece.WHITE_PAWN))
                    number = 2;
                else
                    number = 3;
                
                res += (a+1)*(b+1) *number; 
            }
        }
        return res*g.getMoves().size();
    }

}
