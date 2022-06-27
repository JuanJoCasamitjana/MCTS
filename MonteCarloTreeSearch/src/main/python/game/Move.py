from Coordinate import Coordinate

class Move:
    def __init__(self, initialCoordinate, finalCoordinate):
        self.initialCoordinate = initialCoordinate
        self.finalCoordinate = finalCoordinate

    def __init__(self, row1, col1, row2, col2):
        self.initialCoordinate = Coordinate(row1, col1)
        self.finalCoordinate = Coordinate(row2, col2)

    def __eq__(self, other):
        return self.initialCoordinate.__eq__(other.initialCoordinate) and self.finalCoordinate.__eq__(other.finalCoordinate) 

    def __str__(self):
        return str(self.initialCoordinate) + "->" + str(self.finalCoordinate)