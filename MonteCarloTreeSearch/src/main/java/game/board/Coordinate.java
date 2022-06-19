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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (columIndex == null) {
			if (other.columIndex != null)
				return false;
		} else if (!columIndex.equals(other.columIndex))
			return false;
		if (rowIndex == null) {
			if (other.rowIndex != null)
				return false;
		} else if (!rowIndex.equals(other.rowIndex))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columIndex == null) ? 0 : columIndex.hashCode());
		result = prime * result + ((rowIndex == null) ? 0 : rowIndex.hashCode());
		return result;
	}
	
	
}
