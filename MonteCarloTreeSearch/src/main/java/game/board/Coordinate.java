package game.board;

import lombok.Data;

@Data
public class Coordinate {
	private Integer rowIndex;
	private Integer columIndex;



	public Coordinate(Integer rowIndex, Integer columIndex){
		this.rowIndex = rowIndex;
		this.columIndex = columIndex;
	}

	public String toString(){
		return String.format("(%d,%d)", rowIndex,columIndex);
	}
}
