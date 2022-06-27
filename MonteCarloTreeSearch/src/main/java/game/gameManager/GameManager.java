package game.gameManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import game.IA.UCT;
import game.game.Game;
import game.game.Move;
import game.uctAlgorithm.UpperConfidenceTree;
import game.utils.Constants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameManager {

	private List<String> sucesos;
	private Game gameState;
	private int player ;
	public GameManager(List<String> sucesos, Game gameState, int player) {
		super();
		this.sucesos = sucesos;
		this.gameState = gameState;
		this.player = player;
	}
	public GameManager(List<String> sucesos, Game gameState) {
		super();
		this.sucesos = sucesos;
		this.gameState = gameState;
	}
	
	public static GameManager initialise() {
		Predicate<Integer> maxMoves = e-> e<=0&&(e!=-1);
		Predicate<Integer> boardIndexes = e->(e<1)||(e>6);
		Predicate<Integer> playerSelection = e->e!=1&&e!=2;
		Integer max = getInputAsInteger(Constants.MESSAGE_INPUT_MAX_MOVES, Constants.ERROR_INPUT_MESSAGE,maxMoves);
		Integer board = getInputAsInteger(Constants.MESSAGE_INPUT_BOARD_INDEXES, Constants.ERROR_INPUT_MESSAGE,boardIndexes);
		Integer player = getInputAsInteger(Constants.MESSAGE_INPUT_SELECT_PLAYER, Constants.ERROR_INPUT_MESSAGE, playerSelection);
		GameManager manager = new GameManager(new ArrayList<String>(), Game.getInitialState(board,max), player);
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
	public static Move getInputAsMove(String message, String errorMessage, List<Move> availableMoves) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		Move playerMove = null;
		System.out.println(printMoves(availableMoves));
		while(true) {
			try {
				System.out.println(message);
				String inputStr = reader.readLine();
				Integer index = Integer.parseInt(inputStr);
				playerMove = availableMoves.get(index);
				break;
			} catch(Exception e) {
				System.out.println(errorMessage);
			}
		}
		return playerMove;
	}
	public static String printMoves(List<Move> moves) {
		String str = "";
		for(int i=0;i<moves.size();++i) {
			str = i + ". " + moves.get(i).toString()+"\n";
		}
		System.out.println(str);
		return str;
	}
	public void play() throws Exception {
		boolean continuePlaying = true;
		while(continuePlaying) {
			this.gameState.printState();
			switch(this.gameState.getTurn()) {
			case 1:
				if(player==1) {
					this.playerHandler();
					continuePlaying = !this.gameState.isFinalState();
				} else {
					this.AIHandler();
					continuePlaying = !this.gameState.isFinalState();
				}
				break;
			case 2:
				if(player==2) {
					this.playerHandler();
					continuePlaying = !this.gameState.isFinalState();
				} else {
					this.AIHandler();
					continuePlaying = !this.gameState.isFinalState();
				}
				break;
			}
		}
	}
	public void playerHandler() {
		System.out.println("Your turn!!!");
		List<Move> moves = this.gameState.getMoves();
		Move playerMove = getInputAsMove(Constants.MESSAGE_INPUT_SELECT_MOVE, Constants.ERROR_INPUT_MESSAGE,moves);
		try {
			this.gameState.applyMovement(playerMove);
		} catch (Exception e) {
			System.out.println(Constants.SYS_ERROR);
		}
	}
	public void AIHandler() {
		try {
			Move move = UpperConfidenceTree.busca_solucion(gameState, 10000);
			System.out.println("The AI has selected the following move: "+ move);
			this.gameState.applyMovement(move);
		} catch (Exception e) {
			System.out.println(Constants.SYS_ERROR);
		}
	}
}
