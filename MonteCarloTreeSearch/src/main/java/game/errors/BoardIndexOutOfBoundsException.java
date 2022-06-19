package game.errors;

import game.utils.Constants;

public class BoardIndexOutOfBoundsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BoardIndexOutOfBoundsException() {
		super(Constants.ERROR_BOARD_OUT_OF_BOUNDS);
	}

}
