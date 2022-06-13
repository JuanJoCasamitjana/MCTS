package game.game;

import java.util.Collections;
import java.util.List;

import game.board.Board;
import game.board.Piece;
import game.board.PlayerColor;
import lombok.Data;



@Data
public class Game {

    //Attributes
    private Board board;
    private Integer turn;

    //Constructors

    public Game(Board board, Integer turn){
        this.board = board;
        this.turn = turn;
    }

    public static Game of(Board board, Integer turn){
        return new Game(board, turn);
    }


    //Methods

    /**
	* 
	* @param variant The variant of the game is going to be played
	* @return the initial state of the game with said variant

    Variants:
        1. Hnefatafl
        2. Tablut
        3. Ard Ri
        4. Brandubh
        5. Tawlbwrdd
        6. Alea Evangelii

	*/
    public static Game getInitialState(Integer variant){

        return Game.of(Board.getInitialStateOfVariant(variant), 1);

    }

    public List<Move> getMoves(){
        List<Move> moves = board.getPlayerAvailableMoves().get(turn == 1 ? PlayerColor.BLACK : PlayerColor.WHITE);
        Collections.shuffle(moves);
        return moves;
    }

    public boolean blackWins(){
        return (this.getMoves().size() == 0 && turn == 2) || !board.isKingAlive();
    }

    public boolean whiteWins(){

        boolean isKingAtCorners = board.getBoardDisplay()[0][0].equals(Piece.WHITE_KING) ||
                            board.getBoardDisplay()[0][board.getNumOfColumns()].equals(Piece.WHITE_KING) ||
                            board.getBoardDisplay()[board.getNumOfRows()][0].equals(Piece.WHITE_KING) ||
                            board.getBoardDisplay()[board.getNumOfRows()][board.getNumOfColumns()].equals(Piece.WHITE_KING);

        return (this.getMoves().size() == 0 && turn == 1) || isKingAtCorners;
    }



public static void main(String[] args) {


    Game game = Game.getInitialState(1);

    List<Move> moves = game.getMoves();
    for(Move move : moves){
        System.out.println(move);
    }
}
    
}
