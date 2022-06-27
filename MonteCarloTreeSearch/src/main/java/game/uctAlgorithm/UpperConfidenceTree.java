package game.uctAlgorithm;

import java.util.ArrayList;
import java.util.List;

import game.game.Game;
import game.game.Move;

public class UpperConfidenceTree {

	public static Move busca_solucion(Game state, Integer time) throws Exception {
		Node node = new Node(state, 0,0,new ArrayList<>(),null, null);
		Long spentTime = 0l;
		long begginingTime = System.currentTimeMillis();
		while(spentTime<time) {
			node.expande();
			List<Node> children = node.getChildren();
			for(Node child:children) {
				child.randomPlay();
			}
			spentTime = System.currentTimeMillis() - begginingTime;
		}
		return node.getChildren().get(node.bestChild(1/Math.sqrt(2))).getMoveApplied();
	}
	
}
