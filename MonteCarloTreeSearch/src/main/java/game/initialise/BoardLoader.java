package game.initialise;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.board.Board;
import game.errors.BoardIndexOutOfBoundsException;
import game.utils.Constants;

public class BoardLoader {

	/**
	 * 
	 * @param i the index of the initial board from 1 to 6
	 * @return a new board in its initial state, throws error if the index is out of bounds
	 */
	public static Board loadBoard(Integer i) {
		switch(i) {
		case 1:
			return boardReader(Constants.HNEFATAFL);
		case 2:
			return boardReader(Constants.TABLUT);
		case 3:
			return boardReader(Constants.ARD_RI);
		case 4:
			return boardReader(Constants.BRANDUBH);
		case 5:
			return boardReader(Constants.TAWLBWRDD);
		case 6:
			return boardReader(Constants.ALEA_EVANGELII);
		default:
			throw new BoardIndexOutOfBoundsException();
		}
	}

	/**
	 * 
	 * @param boardDirectory the file dirctory that contains the initial board
	 * @return a new Board if the board directory and file contents are correct, null if it throws an error
	 */
	private static Board boardReader(String boardDirectory) {
		Board board = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(boardDirectory));
			String line;
			List<String[]> rows = new ArrayList<>();
			while((line = reader.readLine()) != null) {
				rows.add(line.split(","));
			}
			int k = rows.size();
			Integer[][] intDisplay = new Integer[k][k];
			for(int i = 0; i<k; ++i) {
				String[] row = rows.get(i);
				for(int j=0;j<k;++j) {
					Integer value = Integer.valueOf(row[j]);
					intDisplay[i][j] = value;
				}
			}
			board = Board.of(intDisplay);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return board;
	}
}
