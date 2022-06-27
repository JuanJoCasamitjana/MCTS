package game.uctAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.game.Game;
import game.game.Move;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

	private Game gameState;
	private Integer q,n;
	private Integer i;
	private List<Node> children;
	private Move moveApplied;
	private Node father;
	public Node(Game gameState, Integer q, Integer n, List<Node> children,Move move , Node father) {
		super();
		this.gameState = gameState;
		this.q = q;
		this.n = n;
		this.children = children;
		this.moveApplied = move;
		this.father = father;
	}
	
	public Node nextNode(Move move) {
		Game gameState = null;
		try {
			gameState = this.gameState.applyMovement(move);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Node(gameState, 0,0,new ArrayList<>(), move,this);
	}
	public Node expande() throws Exception {
		List<Move> moves = this.gameState.getMoves();
		for(Move move:moves) {
			
			Node child = this.createOffspring(move);
			this.children.add(child);
		}
		return null;
	}

	private Node createOffspring(Move move) throws Exception {
		Game gameState = this.gameState.applyMovement(move);
		return new Node(gameState, 0,0, new ArrayList<>(),move,this);
	}
	public void randomPlay() throws Exception {
		Random rdn = new Random();
		Game state = this.gameState; 
		this.n += 1;
		Integer player = this.gameState.getTurn() == 1? 2:1;
		while(!state.isFinalState()) {
			List<Move> moves = state.getMoves();
			Integer upperLimit = moves.size()>0?moves.size():1;
			Integer rdnIndex = rdn.nextInt(upperLimit);
			state = state.applyMovement(moves.get(rdnIndex));
		}
		if((state.blackWins() && player==1) || (state.whiteWins() && player==2))
            this.q +=1 ;
	}
	public Integer bestChild(double c) {
		Integer iN = 0;
		Double maxValue = Double.MIN_VALUE;
		List<Node> children = this.getChildren();
		for(int i=0;i<children.size();++i) {
			Integer visitsToChild = children.get(i).n;
			Integer winsOfChild = children.get(i).q;
			Integer numberOfCicles = this.n;
			Double delta = Math.sqrt(Math.log(numberOfCicles)/visitsToChild);
			Double evaluation = winsOfChild/visitsToChild*1.0 + c*delta;
			if(evaluation > maxValue){
                maxValue = evaluation;
                iN = i;
            }
		}
		return iN;
	}
}
