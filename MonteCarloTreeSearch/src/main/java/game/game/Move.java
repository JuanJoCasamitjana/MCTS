package game.game;

import game.board.Coordinate;
import lombok.Data;

@Data
public class Move {
    private Coordinate initialCoordinate;
    private Coordinate finalCoordinate;

    public Move(Coordinate initialCoordinate, Coordinate finalCoordinate){
        this.initialCoordinate = initialCoordinate;
        this.finalCoordinate = finalCoordinate;
    }

    public static Move of(Coordinate initialCoordinate, Coordinate finalCoordinate){
        return new Move(initialCoordinate, finalCoordinate);
    }

    public static Move of(Integer initialRow, Integer initialColumn, Integer finalRow, Integer finalColumn){
        return new Move(new Coordinate(initialRow, initialColumn), new Coordinate(finalRow, finalColumn));
    }


    public String toString(){
        return String.format("(%d, %d) -> (%d, %d)", initialCoordinate.getRowIndex(), initialCoordinate.getColumIndex(), 
                                        finalCoordinate.getRowIndex(), finalCoordinate.getColumIndex());
    }

}
