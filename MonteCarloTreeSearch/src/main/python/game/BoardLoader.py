import imp
from Board import Board

class BoardLoader:

    def load_board(variant):
        if variant == 1:
            return BoardLoader.board_reader("initialBoards/HNEFATAFL.txt")
        elif variant == 2:
            return BoardLoader.board_reader("initialBoards/TABLUT.txt")
        elif variant == 3:
            return BoardLoader.board_reader("initialBoards/ARD_RI.txt")
        elif variant == 4:
            return BoardLoader.board_reader("initialBoards/BRANDUBH.txt")
        elif variant == 5:
            return BoardLoader.board_reader("initialBoards/TAWLBWRDD.txt")
        elif variant == 6:
            return BoardLoader.board_reader("initialBoards/ALEA_EVANGELII.txt")
        else:
            raise Exception("Variante no válida")


    def board_reader(file):
        lines = []
        with open(file) as f:
            lines = f.readlines()

        integerDisplay = []
        for line in lines:
            row = line.split(",")
            integerDisplay.append(row)
        return Board.integer_display_to_board(integerDisplay)