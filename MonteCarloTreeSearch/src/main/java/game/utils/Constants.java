package game.utils;

public class Constants {
	public static final String HNEFATAFL = "initialBoards/HNEFATAFL.txt";
	public static final String TABLUT = "initialBoards/TABLUT.txt";
	public static final String ARD_RI = "initialBoards/ARD_RI.txt";
	public static final String BRANDUBH = "initialBoards/BRANDUBH.txt";
	public static final String TAWLBWRDD = "initialBoards/TAWLBWRDD.txt";
	public static final String ALEA_EVANGELII = "initialBoards/ALEA_EVANGELII.txt";
	
	public static final String ERROR_BOARD_OUT_OF_BOUNDS = "You must index the initial board with an integer between 1 and 6";
	
	public static final String MESSAGE_INPUT_MAX_MOVES = "Select the maximum number of moves,\nmust be greater than 0 or -1 if there would be no limit";
	public static final String ERROR_INPUT_MESSAGE = "Input unrecognized or incorrect";
	public static final String MESSAGE_INPUT_BOARD_INDEXES = "Use an integer between 1 and 6:\n        1. Hnefatafl\r\n"
			+ "        2. Tablut\r\n"
			+ "        3. Ard Ri\r\n"
			+ "        4. Brandubh\r\n"
			+ "        5. Tawlbwrdd\r\n"
			+ "        6. Alea Evangelii";
	public static final String MESSAGE_INPUT_SELECT_PLAYER = "Please select what do you want to play as:\n" 
			+"1. White\n"
			+"2. Black";
	public static final String MESSAGE_INPUT_SELECT_MOVE = "Please select a move from the pool";
	public static final String SYS_ERROR = "System error";
}
