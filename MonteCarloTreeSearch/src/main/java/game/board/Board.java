package game.board;

import java.util.List;
import java.util.Map;

import lombok.Getter;

@Getter
public class Board {
	private Piece[][] boardDisplay;
	private Map<PlayerColor, Coordinate> playerAvailablePieces;
	private Map<Coordinate, List<Coordinate>> availableMoves;
	
	public Board(Piece[][] boardDisplay, Map<PlayerColor, Coordinate> playerAvailablePieces,
			Map<Coordinate, List<Coordinate>> availableMoves) {
		super();
		this.boardDisplay = boardDisplay;
		this.playerAvailablePieces = playerAvailablePieces;
		this.availableMoves = availableMoves;
	}
	

	public static Board of(Piece[][] boardDisplay, Map<PlayerColor, Coordinate> playerAvailablePieces,
			Map<Coordinate, List<Coordinate>> availableMoves) {
		return new Board(boardDisplay,playerAvailablePieces,availableMoves);
	}
	/**
	 * 
	 * @param originPosition The position of the piece to be moved
	 * @param nextPostion The position that said piece is to be moved
	 * @return a copy of the current board but the boardDisplay now reflects the changes
	 */
	public Board move(Coordinate originPosition, Coordinate nextPostion) {
		Board nextBoard = this.clone();
		Integer row = originPosition.getRowIndex();
		Integer colum = originPosition.getColumIndex();
		
		Integer nextRow = nextPostion.getRowIndex();
		Integer nextColum = nextPostion.getColumIndex();
		
		Piece p = nextBoard.boardDisplay[row][colum];
		nextBoard.boardDisplay[row][colum] = Piece.EMPTY;
		nextBoard.boardDisplay[nextRow][nextColum] = p;
		//Changes to the availableMoves map needs to be implemented
		return nextBoard;
	}
	
	@Override
	public Board clone() {
		return new Board(this.boardDisplay,this.playerAvailablePieces,this.availableMoves);
	}
	/**
	 * 
	 * @param c The position of the piece we want to know the color of
	 * @return the color of the player if the position is occupied, null otherwise
	 */
	public PlayerColor getPlayerColorOfPiece(Coordinate c) {
		Piece p = boardDisplay[c.getRowIndex()][c.getColumIndex()];
		switch(p) {
		case BLACK_PAWN:
			return PlayerColor.BLACK;
		case WHITE_PAWN:
			return PlayerColor.WHITE;
		case WHITE_KING:
			return PlayerColor.WHITE;
		default:
			return null;
		}
	}
}
