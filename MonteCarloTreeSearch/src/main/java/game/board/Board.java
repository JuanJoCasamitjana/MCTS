package game.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import game.game.Move;
import lombok.Getter;

@Getter
public class Board {
	private Piece[][] boardDisplay;
	
	public Board(Piece[][] boardDisplay) {
		super();
		this.boardDisplay = boardDisplay;
	}
	

	public static Board of(Piece[][] boardDisplay) {
		return new Board(boardDisplay);
	}

	public static Board of(Integer[][] integerDisplay){
		Piece[][] boardDisplay = new Piece[integerDisplay.length][integerDisplay[0].length];
		for(int i = 0; i<integerDisplay.length; i++){
			Integer[] integerRow = integerDisplay[i];
			for(int j = 0; j<integerRow.length; j++){
				switch (integerRow[j]) {
					case 0:
						boardDisplay[i][j] = Piece.EMPTY;
						break;
					case 1:
						boardDisplay[i][j] = Piece.BLACK_PAWN;
						break;
					case 2:
						boardDisplay[i][j] = Piece.WHITE_PAWN;
						break;
					case 3:
						boardDisplay[i][j] = Piece.WHITE_KING;
						break;
				
					default:
						break;
				}
			}
		}
		return Board.of(boardDisplay);

	}


	@Override
	public Board clone() {
		return new Board(this.boardDisplay);
	}

	public Integer getNumOfRows(){
		return boardDisplay.length;
	}

	public Integer getNumOfColumns(){
		return boardDisplay[0].length;
	}





	 /**
	* 
	* @param variant The variant of the game is going to be played
	* @return the initial state of the board with said variant
	*/
	public static Board getInitialStateOfVariant(Integer variant){
		Integer[][] integerDisplay =  
		{{0,0,1,1,1,0,0}, 
		{0,0,0,1,0,0,0}, 
		{1,0,2,2,2,0,1}, 
		{1,1,2,3,2,1,1}, 
		{1,0,2,2,2,0,1},
		{0,0,0,1,0,0,0},
		{0,0,1,1,1,0,0}};

		return Board.of(integerDisplay);
	}




	/**
	* 
	* @return the coordinates in control of each player
	*/
	public Map<PlayerColor, List<Coordinate>> getPlayerAvailablePieces(){
		Map<PlayerColor, List<Coordinate>> res = new HashMap<PlayerColor,List<Coordinate>>();

		for(int i = 0; i<boardDisplay.length; i++){
			Piece[] row = boardDisplay[i];
			for(int j = 0; j<row.length; j++){
				Piece piece = row[j];
				if(piece.equals(Piece.BLACK_PAWN)){
					if(res.containsKey(PlayerColor.BLACK)){
						List<Coordinate> coordinateList = res.get(PlayerColor.BLACK);
						coordinateList.add(new Coordinate(i, j));
						res.put(PlayerColor.BLACK, coordinateList);
					}else{
						List<Coordinate> coordinateList = new ArrayList<Coordinate>();
						coordinateList.add(new Coordinate(i, j));
						res.put(PlayerColor.BLACK, coordinateList);
					}
				}else if(piece.equals(Piece.WHITE_KING) || piece.equals(Piece.WHITE_PAWN)){
					if(res.containsKey(PlayerColor.WHITE)){
						List<Coordinate> coordinateList = res.get(PlayerColor.WHITE);
						coordinateList.add(new Coordinate(i, j));
						res.put(PlayerColor.WHITE, coordinateList);
					}else{
						List<Coordinate> coordinateList = new ArrayList<Coordinate>();
						coordinateList.add(new Coordinate(i, j));
						res.put(PlayerColor.WHITE, coordinateList);
					}
				}

			}
		}

		return res;
	}


	/**
	* 
	* @return the moves available for each player in a position
	*/
	public Map<PlayerColor, List<Move>> getPlayerAvailableMoves(){

		Map<PlayerColor, List<Move>> res = new HashMap<PlayerColor,List<Move>>();
		Integer numOfRows = this.getNumOfRows();
		Integer numOfColumns = this.getNumOfColumns();

		for(int i = 0; i<numOfRows; i++){
			for(int j = 0; j<numOfColumns; j++){
				Piece piece = boardDisplay[i][j];
				List<Move> moves = Board.getAvailableMovesOfPiece(new Coordinate(i, j), boardDisplay);
				if(piece.equals(Piece.BLACK_PAWN)){
					if(res.containsKey(PlayerColor.BLACK)){
						List<Move> moveList = res.get(PlayerColor.BLACK);
						moveList.addAll(moves);
						res.put(PlayerColor.BLACK, moveList);
					}else{
						res.put(PlayerColor.BLACK, moves);
					}
				}else if(piece.equals(Piece.WHITE_KING) || piece.equals(Piece.WHITE_PAWN)){
					if(res.containsKey(PlayerColor.WHITE)){
						List<Move> moveList = res.get(PlayerColor.WHITE);
						moveList.addAll(moves);
						res.put(PlayerColor.WHITE, moveList);
					}else{
						res.put(PlayerColor.WHITE, moves);
					}
				}
			}
		}
		return res;
	}



	/**
	* @param coordinate The coordinate of the piece that we want to check
	* @param boardDisplay The distribution of pieces in the board
	* @return the moves available for the piece contained in said coordinate
	*/
	private static List<Move> getAvailableMovesOfPiece(Coordinate coordinate, Piece[][] boardDisplay) {

		List<Move> res = new ArrayList<Move>();
		Integer numOfRows = boardDisplay.length;
		Integer numOfColumns = boardDisplay[0].length;
		Piece piece = boardDisplay[coordinate.getRowIndex()][coordinate.getColumIndex()];

		List<Coordinate> keySquares = new ArrayList<Coordinate>();
		keySquares.add(new Coordinate(0, 0));
		keySquares.add(new Coordinate(numOfRows-1, 0));
		keySquares.add(new Coordinate(0, numOfColumns-1));
		keySquares.add(new Coordinate(numOfRows-1, numOfColumns-1));
		keySquares.add(new Coordinate((numOfRows-1)/2, (numOfColumns-1)/2));


		if(piece.equals(Piece.EMPTY))
			return res;

		//Up checking
		for(int j = coordinate.getColumIndex()-1; j>=0; j--){
			if(!boardDisplay[coordinate.getRowIndex()][j].equals(Piece.EMPTY)){
				break;
			}
				
			if(keySquares.contains(new Coordinate(coordinate.getRowIndex(), j)) && !piece.equals(Piece.WHITE_KING)){
				continue;
			}
			res.add(Move.of(coordinate, new Coordinate(coordinate.getRowIndex(), j)));
		}
		//Down checking
		for(int j = coordinate.getColumIndex()+1; j<numOfColumns; j++){
			if(!boardDisplay[coordinate.getRowIndex()][j].equals(Piece.EMPTY))
				break;
			if(keySquares.contains(new Coordinate(coordinate.getRowIndex(), j)) && !piece.equals(Piece.WHITE_KING))
				continue;
			res.add(Move.of(coordinate, new Coordinate(coordinate.getRowIndex(), j)));
		}
		//Left checking
		for(int i = coordinate.getRowIndex()-1; i>=0; i--){
			if(!boardDisplay[i][coordinate.getColumIndex()].equals(Piece.EMPTY))
				break;
			if(keySquares.contains(new Coordinate(i, coordinate.getColumIndex())) && !piece.equals(Piece.WHITE_KING))
				continue;
			res.add(Move.of(coordinate, new Coordinate(i, coordinate.getColumIndex())));
		}
		//Right checking
		for(int i = coordinate.getRowIndex()+1; i<numOfRows; i++){
			if(!boardDisplay[i][coordinate.getColumIndex()].equals(Piece.EMPTY))
				break;
			if(keySquares.contains(new Coordinate(i, coordinate.getColumIndex())) && !piece.equals(Piece.WHITE_KING))
				continue;
			res.add(Move.of(coordinate, new Coordinate(i, coordinate.getColumIndex())));
		}
		return res;
	}


	/**
	 * 
	 * @param move The move to be executed
	 * @return a copy of the current board but the boardDisplay now reflects the changes
	 */
	public Board executeMove(Move move) {
		Board nextBoard = this.clone();
		Integer row = move.getInitialCoordinate().getRowIndex();
		Integer colum = move.getInitialCoordinate().getColumIndex();
		
		Integer nextRow = move.getFinalCoordinate().getRowIndex();
		Integer nextColum = move.getFinalCoordinate().getColumIndex();
		
		Piece p = nextBoard.boardDisplay[row][colum];
		nextBoard.boardDisplay[row][colum] = Piece.EMPTY;
		nextBoard.boardDisplay[nextRow][nextColum] = p;
		//Changes to the availableMoves map needs to be implemented
		return nextBoard;
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


	/**
	 * 
	 * @return True if the king is on the board, False otherwise
	 */
	public boolean isKingAlive(){

		boolean res = false;

		for(Piece[] row : boardDisplay){
			for(Piece piece : row){
				if(piece.equals(Piece.WHITE_KING)){
					res = true;
					break;
				}
					
			}
		}
		return res;
	}




}
