package game.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import game.game.Move;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

	public Coordinate getThrone(){
		return new Coordinate((getNumOfRows()-1)/2, (getNumOfColumns()-1)/2);
	}

	public boolean isSurroundingsOfThrone(Coordinate c){
		return c.equals(new Coordinate(((getNumOfRows()-1)/2)-1, (getNumOfColumns()-1)/2)) ||
				c.equals(new Coordinate(((getNumOfRows()-1)/2)+1, (getNumOfColumns()-1)/2)) ||
				c.equals(new Coordinate((getNumOfRows()-1)/2, ((getNumOfColumns()-1)/2)-1)) ||
				c.equals(new Coordinate((getNumOfRows()-1)/2, ((getNumOfColumns()-1)/2)+1));
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
	public Map<PlayerColor, Set<Move>> getPlayerAvailableMoves(){

		Map<PlayerColor, Set<Move>> res = new HashMap<PlayerColor,Set<Move>>();
		Integer numOfRows = this.getNumOfRows();
		Integer numOfColumns = this.getNumOfColumns();

		for(int i = 0; i<numOfRows; i++){
			for(int j = 0; j<numOfColumns; j++){
				Piece piece = boardDisplay[i][j];
				Set<Move> moves = Board.getAvailableMovesOfPiece(new Coordinate(i, j), boardDisplay);
				if(piece.equals(Piece.BLACK_PAWN)){
					if(res.containsKey(PlayerColor.BLACK)){
						Set<Move> moveList = res.get(PlayerColor.BLACK);
						moveList.addAll(moves);
						res.put(PlayerColor.BLACK, moveList);
					}else{
						res.put(PlayerColor.BLACK, moves);
					}
				}else if(piece.equals(Piece.WHITE_KING) || piece.equals(Piece.WHITE_PAWN)){
					if(res.containsKey(PlayerColor.WHITE)){
						Set<Move> moveList = res.get(PlayerColor.WHITE);
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
	* @param currentCoordinate The coordinate of the piece that we want to check
	* @param boardDisplay The distribution of pieces in the board
	* @return the moves available for the piece contained in said coordinate
	*/
	private static Set<Move> getAvailableMovesOfPiece(Coordinate currentCoordinate, Piece[][] boardDisplay) {

		Set<Move> res = new HashSet<Move>();
		Integer numOfRows = boardDisplay.length;
		Integer numOfColumns = boardDisplay[0].length;
		Piece piece = boardDisplay[currentCoordinate.getRowIndex()][currentCoordinate.getColumIndex()];

		List<Coordinate> keySquares = new ArrayList<Coordinate>();
		keySquares.add(new Coordinate(0, 0));
		keySquares.add(new Coordinate(numOfRows-1, 0));
		keySquares.add(new Coordinate(0, numOfColumns-1));
		keySquares.add(new Coordinate(numOfRows-1, numOfColumns-1));
		keySquares.add(new Coordinate((numOfRows-1)/2, (numOfColumns-1)/2));


		if(piece.equals(Piece.EMPTY))
			return res;

		res.addAll(checkLeftMoves(piece, currentCoordinate, numOfColumns, numOfRows, boardDisplay, keySquares));
		res.addAll(checkRightMoves(piece, currentCoordinate, numOfColumns, numOfRows, boardDisplay, keySquares));
		res.addAll(checkUpMoves(piece, currentCoordinate, numOfColumns, numOfRows, boardDisplay, keySquares));
		res.addAll(checkDownMoves(piece, currentCoordinate, numOfColumns, numOfRows, boardDisplay, keySquares));
		
		return res;
	}


	private static Collection<? extends Move> checkDownMoves(Piece piece, Coordinate currentCoordinate, Integer numOfColumns, Integer numOfRows, Piece[][] boardDisplay, List<Coordinate> keySquares) {
		List<Move> res = new ArrayList<Move>();
		for(int i = currentCoordinate.getRowIndex()+1; i<numOfRows; i++){
			Move move = Move.of(currentCoordinate.getRowIndex(), currentCoordinate.getColumIndex(), i, currentCoordinate.getColumIndex());
			if(!boardDisplay[i][currentCoordinate.getColumIndex()].equals(Piece.EMPTY))
				break;
			if(keySquares.contains(new Coordinate(i, currentCoordinate.getColumIndex())) && !piece.equals(Piece.WHITE_KING))
				continue;
				if(currentCoordinate.getColumIndex() == 0 || currentCoordinate.getColumIndex()==numOfColumns-1){
					res.add(move);
				}else{
					if(isBlack(piece) && (!isWhite(boardDisplay[i][currentCoordinate.getColumIndex()+1]) || !isWhite(boardDisplay[i][currentCoordinate.getColumIndex()-1])))
						res.add(move);
					if(isWhite(piece) && (!isBlack(boardDisplay[i][currentCoordinate.getColumIndex()+1]) || !isBlack(boardDisplay[i][currentCoordinate.getColumIndex()-1])))
						res.add(move);
				}
		}
		return res;
	}


	private static Collection<? extends Move> checkUpMoves(Piece piece, Coordinate currentCoordinate, Integer numOfColumns, Integer numOfRows, Piece[][] boardDisplay, List<Coordinate> keySquares) {
		List<Move> res = new ArrayList<Move>();
		for(int i = currentCoordinate.getRowIndex()-1; i>=0; i--){
			Move move = Move.of(currentCoordinate.getRowIndex(), currentCoordinate.getColumIndex(), i, currentCoordinate.getColumIndex());
			if(!boardDisplay[i][currentCoordinate.getColumIndex()].equals(Piece.EMPTY))
				break;
			if(keySquares.contains(new Coordinate(i, currentCoordinate.getColumIndex())) && !piece.equals(Piece.WHITE_KING))
				continue;
			if(currentCoordinate.getColumIndex() == 0 || currentCoordinate.getColumIndex()==numOfColumns-1){
				res.add(move);
			}else{
				if(isBlack(piece) && (!isWhite(boardDisplay[i][currentCoordinate.getColumIndex()+1]) || !isWhite(boardDisplay[i][currentCoordinate.getColumIndex()-1])))
					res.add(move);
				if(isWhite(piece) && (!isBlack(boardDisplay[i][currentCoordinate.getColumIndex()+1]) || !isBlack(boardDisplay[i][currentCoordinate.getColumIndex()-1])))
					res.add(move);
			}
		}
		return res;
	}


	private static Collection<? extends Move> checkRightMoves(Piece piece, Coordinate currentCoordinate, Integer numOfColumns, Integer numOfRows, Piece[][] boardDisplay, List<Coordinate> keySquares) {
		List<Move> res = new ArrayList<Move>();
		for(int j = currentCoordinate.getColumIndex()+1; j<numOfColumns; j++){
			Move move = Move.of(currentCoordinate.getRowIndex(), currentCoordinate.getColumIndex(), currentCoordinate.getRowIndex(), j);
			if(!boardDisplay[currentCoordinate.getRowIndex()][j].equals(Piece.EMPTY))
				break;
			if(keySquares.contains(new Coordinate(currentCoordinate.getRowIndex(), j)) && !piece.equals(Piece.WHITE_KING))
				continue;
			if(currentCoordinate.getRowIndex()==0 || currentCoordinate.getRowIndex()==numOfRows-1){
				res.add(Move.of(currentCoordinate, new Coordinate(currentCoordinate.getRowIndex(), j)));
			}else{
				if(isBlack(piece) && (!isWhite(boardDisplay[currentCoordinate.getRowIndex()+1][j]) || !isWhite(boardDisplay[currentCoordinate.getRowIndex()-1][j])))
					res.add(move);
				if(isWhite(piece) && (!isBlack(boardDisplay[currentCoordinate.getRowIndex()+1][j]) || !isBlack(boardDisplay[currentCoordinate.getRowIndex()-1][j])))
					res.add(move);
				}
		}
		return res;
	}


	private static Collection<Move> checkLeftMoves(Piece piece, Coordinate currentCoordinate, Integer numOfColumns, Integer numOfRows,Piece[][] boardDisplay, List<Coordinate> keySquares) {

		List<Move> res = new ArrayList<Move>();
		for(int j = currentCoordinate.getColumIndex()-1; j>=0; j--){
			Move move = Move.of(currentCoordinate.getRowIndex(), currentCoordinate.getColumIndex(), currentCoordinate.getRowIndex(), j);
			if(!boardDisplay[currentCoordinate.getRowIndex()][j].equals(Piece.EMPTY)){
				break;
			}
			if(keySquares.contains(new Coordinate(currentCoordinate.getRowIndex(), j)) && !piece.equals(Piece.WHITE_KING)){
				continue;
			}
			if(currentCoordinate.getRowIndex()==0 || currentCoordinate.getRowIndex()==numOfRows-1){
				res.add(Move.of(currentCoordinate, new Coordinate(currentCoordinate.getRowIndex(), j)));
			}else{
				if(isBlack(piece) && (!isWhite(boardDisplay[currentCoordinate.getRowIndex()+1][j]) || !isWhite(boardDisplay[currentCoordinate.getRowIndex()-1][j])))
					res.add(move);
				if(isWhite(piece) && (!isBlack(boardDisplay[currentCoordinate.getRowIndex()+1][j]) || !isBlack(boardDisplay[currentCoordinate.getRowIndex()-1][j])))
					res.add(move);
			}
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

	public Piece getPieceAtCoordinate(Coordinate c){
		return boardDisplay[c.getRowIndex()][c.getColumIndex()];
	}

	public static boolean isWhite(Piece p){
		return p.equals(Piece.WHITE_KING) || p.equals(Piece.WHITE_PAWN);
	}

	public static boolean isBlack(Piece p){
		return p.equals(Piece.BLACK_PAWN);
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

	public void imprimeTablero(){
		String res = "";

		for(int i = 0; i<this.getNumOfRows(); i++){
			for(int j = 0; j<this.getNumOfColumns(); j++){
				Piece piece = boardDisplay[i][j];
				if(piece.equals(Piece.EMPTY))
					res+="·  ";
				else if(piece.equals(Piece.BLACK_PAWN))
					res+="B  ";
				else if(piece.equals(Piece.WHITE_PAWN))
					res+="w  ";
				else
					res+="k  ";
			}
			res += "\n\n";
		}

		System.out.println(res);
		System.out.println("---------------------------------------------------------------------------");
	}


	public static Board checkPiecesTaken(Board nextBoard) {

		Board nextBoardChecked = nextBoard.clone();


		for(int i = 0; i<nextBoard.getNumOfRows(); i++){
            for(int j = 1; j<nextBoard.getNumOfColumns()-1; j++){
                Piece leftPiece = nextBoard.getBoardDisplay()[i][j-1];
                Piece middlePiece = nextBoard.getBoardDisplay()[i][j];
                Piece rightPiece = nextBoard.getBoardDisplay()[i][j+1];

				if(middlePiece.equals(Piece.BLACK_PAWN) && (leftPiece.equals(Piece.WHITE_KING) || leftPiece.equals(Piece.WHITE_PAWN)) && (rightPiece.equals(Piece.WHITE_KING) || rightPiece.equals(Piece.WHITE_PAWN)))
					nextBoardChecked.getBoardDisplay()[i][j] = Piece.EMPTY;

				if(middlePiece.equals(Piece.WHITE_PAWN) && leftPiece.equals(Piece.BLACK_PAWN) && rightPiece.equals(Piece.BLACK_PAWN))
					nextBoardChecked.getBoardDisplay()[i][j] = Piece.EMPTY;

                if(middlePiece.equals(Piece.WHITE_KING)){
					Coordinate coordinate = new Coordinate(i, j);
					if(coordinate.equals(nextBoard.getThrone()) && nextBoard.getPieceAtCoordinate(new Coordinate(i-1, j)).equals(Piece.BLACK_PAWN) && nextBoard.getPieceAtCoordinate(new Coordinate(i+1, j)).equals(Piece.BLACK_PAWN) && nextBoard.getPieceAtCoordinate(new Coordinate(i, j-1)).equals(Piece.BLACK_PAWN) && nextBoard.getPieceAtCoordinate(new Coordinate(i, j+1)).equals(Piece.BLACK_PAWN))
						nextBoardChecked.getBoardDisplay()[i][j] = Piece.EMPTY;
					else if(nextBoard.isSurroundingsOfThrone(coordinate)){
						List<Piece> piecesAround = new ArrayList<Piece>();
						piecesAround.add(nextBoard.getPieceAtCoordinate(new Coordinate(i-1, j)));
						piecesAround.add(nextBoard.getPieceAtCoordinate(new Coordinate(i+1, j)));
						piecesAround.add(nextBoard.getPieceAtCoordinate(new Coordinate(i, j-1)));
						piecesAround.add(nextBoard.getPieceAtCoordinate(new Coordinate(i, j+1)));
						if(piecesAround.stream().filter(new Predicate<Piece>() {
							public boolean test(Piece t) {
								return t.equals(Piece.BLACK_PAWN);
							}
						}).collect(Collectors.counting()) >= 3){
							nextBoardChecked.getBoardDisplay()[i][j] = Piece.EMPTY;
						}
					}
					else if(leftPiece.equals(Piece.BLACK_PAWN) && rightPiece.equals(Piece.BLACK_PAWN))
						nextBoardChecked.getBoardDisplay()[i][j] = Piece.EMPTY;
				}
            }
        }
        for(int j = 0; j<nextBoard.getNumOfColumns(); j++){
            for(int i = 1; i<nextBoard.getNumOfRows()-1; i++){
                Piece topPiece = nextBoard.getBoardDisplay()[i-1][j];
                Piece middlePiece = nextBoard.getBoardDisplay()[i][j];
                Piece bottomPiece = nextBoard.getBoardDisplay()[i+1][j];

                if(middlePiece.equals(Piece.BLACK_PAWN) && (topPiece.equals(Piece.WHITE_KING) || topPiece.equals(Piece.WHITE_PAWN)) && (bottomPiece.equals(Piece.WHITE_KING) || bottomPiece.equals(Piece.WHITE_PAWN)))
					nextBoardChecked.getBoardDisplay()[i][j] = Piece.EMPTY;
				if(middlePiece.equals(Piece.WHITE_PAWN) && topPiece.equals(Piece.BLACK_PAWN) && bottomPiece.equals(Piece.BLACK_PAWN))
					nextBoardChecked.getBoardDisplay()[i][j] = Piece.EMPTY;
				if(middlePiece.equals(Piece.WHITE_KING)){
					Coordinate coordinate = new Coordinate(i, j);
					if(!coordinate.equals(nextBoard.getThrone()) && nextBoard.isSurroundingsOfThrone(coordinate) && topPiece.equals(Piece.BLACK_PAWN) && bottomPiece.equals(Piece.BLACK_PAWN))
						nextBoardChecked.getBoardDisplay()[i][j] = Piece.EMPTY;
				}
            }
        }

		return nextBoardChecked;
	}
}
