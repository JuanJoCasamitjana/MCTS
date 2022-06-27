package game.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import game.IA.UCT;
import game.IA.UCT2;
import game.board.Board;
import game.board.Piece;
import game.board.PlayerColor;
import game.gameManager.GameManager;
import game.initialise.BoardLoader;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class Game {

    //Attributes
    private Board board;
    private Integer turn;
    private Integer movesPlayed;
    private Integer movesLimit;

    //Constructors

    public Game(Board board, Integer turn, Integer movesPlayed, Integer movesLimit){
        this.board = board;
        this.turn = turn;
        this.movesPlayed = movesPlayed;
        this.movesLimit = movesLimit;
    }

    public static Game of(Board board, Integer turn, Integer movesPlayed, Integer movesLimit){
        return new Game(board, turn, movesPlayed, movesLimit);
    }

	public static Game copy(Game game) {
		return new Game(game.board, game.turn, game.movesPlayed, game.movesLimit);
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
    public static Game getInitialState(Integer variant, Integer movesLimit){

        return Game.of(BoardLoader.loadBoard(1), 1, 0, movesLimit);

    }

    public List<Move> getMoves(){
        List<Move> moves = new ArrayList<Move>(board.getPlayerAvailableMoves().get(turn == 1 ? PlayerColor.BLACK : PlayerColor.WHITE));
        Collections.shuffle(moves);
        return moves;
    }

    public boolean blackWins(){
        return (this.getMoves().size() == 0 && turn == 2) || !board.isKingAlive();
    }

    public boolean whiteWins(){

        boolean isKingAtCorners = board.getBoardDisplay()[0][0].equals(Piece.WHITE_KING) ||
                            board.getBoardDisplay()[0][board.getNumOfColumns()-1].equals(Piece.WHITE_KING) ||
                            board.getBoardDisplay()[board.getNumOfRows()-1][0].equals(Piece.WHITE_KING) ||
                            board.getBoardDisplay()[board.getNumOfRows()-1][board.getNumOfColumns()-1].equals(Piece.WHITE_KING);

        return (this.getMoves().size() == 0 && turn == 1) || isKingAtCorners;
    }

    public boolean isFinalState(){
        return blackWins() || whiteWins() || (this.getMovesPlayed() >= this.getMovesLimit() && this.getMovesLimit() > 0);
    }


    public Game applyMovement(Move move) throws Exception{



        if(!this.getMoves().contains(move)){
            throw new Exception("Movimiento ilegal.");
        }

        Board nextBoard = this.getBoard().clone(); 

        Integer row = move.getInitialCoordinate().getRowIndex();
		Integer colum = move.getInitialCoordinate().getColumIndex();
		Integer nextRow = move.getFinalCoordinate().getRowIndex();
		Integer nextColum = move.getFinalCoordinate().getColumIndex();

        Piece pieceMoving = this.getBoard().getBoardDisplay()[row][colum];
        
        nextBoard.getBoardDisplay()[row][colum] = Piece.EMPTY;

        nextBoard.getBoardDisplay()[nextRow][nextColum] = pieceMoving;

        Board nextBoardChecked = Board.checkPiecesTaken(nextBoard);
        
        
        return Game.of(nextBoardChecked, this.getTurn() == 1 ? 2 : 1, this.getMovesPlayed()+1, this.getMovesLimit());
    }

    public void printState(){
        this.getBoard().imprimeTablero();
        System.out.println("Movimientos jugados: " + this.getMovesPlayed());
        if(isFinalState()){
            if(whiteWins())
                System.out.println("¡Ganan las blancas!");
            else if(blackWins())
                System.out.println("¡Ganan las negras!");
            else
                System.out.println("Tablas.");
        }else{
            System.out.println("Turno de las " + ((turn==1) ? "negras" : "blancas"));
        }
        System.out.println("---------------------------------------------------------------------------");
    }



public static void main(String[] args) throws Exception {
    GameManager manager = GameManager.initialise();
    manager.play();
            
    }


}
    

