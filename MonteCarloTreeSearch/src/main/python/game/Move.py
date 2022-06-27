from Coordinate import Coordinate

class Move:
    def __init__(self, initialCoordinate, finalCoordinate):
        self.initialCoordinate = initialCoordinate
        self.finalCoordinate = finalCoordinate

    def __init__(self, row1, col1, row2, col2):
        self.initialCoordinate = Coordinate(row1, col1)
        self.finalCoordinate = Coordinate(row2, col2)

    def __str__(self):
        return str(self.initialCoordinate) + "->" + str(self.finalCoordinate)