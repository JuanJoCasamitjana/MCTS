package game.gameManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

import game.board.PlayerColor;
import game.game.Game;
import game.initialise.BoardLoader;
import game.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameManager {

	private List<String> sucesos;
	private Game gameState;
	private int maxMoves = -1;
	public GameManager(List<String> sucesos, Game gameState, int maxMoves) {
		super();
		this.sucesos = sucesos;
		this.gameState = gameState;
		this.maxMoves = maxMoves;
	}
	public GameManager(List<String> sucesos, Game gameState) {
		super();
		this.sucesos = sucesos;
		this.gameState = gameState;
	}
	
	public static GameManager initialise() {
		Predicate<Integer> maxMoves = e-> e<=0&&(e!=-1);
		Predicate<Integer> boardIndexes = e->(e<1)||(e>6);
		Integer max = getInputAsInteger(Constants.MESSAGE_INPUT_MAX_MOVES, Constants.ERROR_INPUT_MESSAGE,maxMoves);
		Integer board = getInputAsInteger(Constants.MESSAGE_INPUT_BOARD_INDEXES, Constants.ERROR_INPUT_MESSAGE,boardIndexes);
		
		GameManager manager = new GameManager(new ArrayList<String>(), Game.getInitialState(board));
		return manager;
	}
	public static Integer getInputAsInteger(String message, String errorMessage,Predicate<Integer> predicate) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Integer input = null;
		while(true) {
			try {
				System.out.println(message);
				String inputStr = reader.readLine();
				input = Integer.parseInt(inputStr);
				if(predicate.test(input)) {
					throw new Exception();
				}
				break;
			} catch(Exception e) {
				System.out.println(errorMessage);
			}
		}
		return input;
	}
	public void play() {
		
	}
}
