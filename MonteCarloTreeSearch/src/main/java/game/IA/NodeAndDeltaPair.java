package game.IA;

import lombok.Data;

@Data
public class NodeAndDeltaPair {

    private Node node;
    private Integer delta;
    

    public NodeAndDeltaPair(Node v, Integer delta) {
        this.node = v;
        this.delta = delta;
    }

}
