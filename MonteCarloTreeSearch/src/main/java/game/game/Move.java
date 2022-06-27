package game.game;

import game.board.Coordinate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Move implements Comparable<Move>{
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Move other = (Move) obj;
        if (finalCoordinate == null) {
            if (other.finalCoordinate != null)
                return false;
        } else if (!finalCoordinate.equals(other.finalCoordinate))
            return false;
        if (initialCoordinate == null) {
            if (other.initialCoordinate != null)
                return false;
        } else if (!initialCoordinate.equals(other.initialCoordinate))
            return false;
        return true;
    }

    public int compareTo(Move other) {
        if(this.initialCoordinate.getRowIndex() > other.getInitialCoordinate().getRowIndex()){
            return 1;
        }else if(this.initialCoordinate.getRowIndex() < other.getInitialCoordinate().getRowIndex()){
            return -1;
        }else{
            if(this.initialCoordinate.getColumIndex() > other.getInitialCoordinate().getColumIndex()){
                return 1;
            }else if(this.initialCoordinate.getColumIndex() < other.getInitialCoordinate().getColumIndex()){
                return -1;
            }else{
                if(this.finalCoordinate.getRowIndex() > other.getFinalCoordinate().getRowIndex()){
                    return 1;
                }else if(this.finalCoordinate.getRowIndex() < other.getFinalCoordinate().getRowIndex()){
                    return -1;
                }else{
                    if(this.finalCoordinate.getColumIndex() > other.getFinalCoordinate().getColumIndex()){
                        return 1;
                    }else if(this.finalCoordinate.getColumIndex() < other.getFinalCoordinate().getColumIndex()){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            }
        }
    }



    
}
