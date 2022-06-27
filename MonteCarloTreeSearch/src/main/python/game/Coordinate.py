class Coordinate:

    def __init__(self, rowIndex, columnIndex):
        self.rowIndex = int(rowIndex)
        self.columnIndex = int(columnIndex)

    def __str__(self):
        return "(" + str(self.rowIndex) + "," + str(self.columnIndex) + ")"

    def __eq__(self, other):
        return self.rowIndex == other.rowIndex and self.columnIndex == other.columnIndex

