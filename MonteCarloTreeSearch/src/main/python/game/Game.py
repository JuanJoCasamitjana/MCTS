from random import shuffle
from Board import Board
from PlayerColor import PlayerColor
from Piece import Piece
from Move import Move


class Game:

    def __init__(self, board, turn, movesPlayed, moveLimit):
        self.board = board
        self.turn = turn
        self.movesPlayed = movesPlayed
        self.moveLimit = moveLimit
    
    def copy(self):
        return Game(self.board.copy(), self.turn, self.movesPlayed, self.moveLimit)


    def get_initial_state(variant, moveLimit):
        integerDisplay = [[0,0,0,1,1,1,1,1,0,0,0],
                        [0,0,0,0,0,1,0,0,0,0,0],
                        [0,0,0,0,0,0,0,0,0,0,0],
                        [1,0,0,0,0,2,0,0,0,0,1],
                        [1,0,0,0,2,2,2,0,0,0,1],
                        [1,1,0,2,2,3,2,2,0,1,1],
                        [1,0,0,0,2,2,2,0,0,0,1],
                        [1,0,0,0,0,2,0,0,0,0,1],
                        [0,0,0,0,0,0,0,0,0,0,0],
                        [0,0,0,0,0,1,0,0,0,0,0],
                        [0,0,0,1,1,1,1,1,0,0,0]]
        board = Board.integer_display_to_board(integerDisplay)
        game = Game(board, 1, 0, moveLimit)
        return game

    def get_moves(self):
        player = None
        if self.turn == 1:
            player = PlayerColor.BLACK
        else:
            player = PlayerColor.WHITE
        moves = list(self.board.get_player_available_moves()[player])
        shuffle(moves)
        return moves

    def black_wins(self):
        return (len(self.get_moves()) == 0 and self.turn == 2) or (not self.board.is_king_alive())

    def white_wins(self):
        return (len(self.get_moves()) == 0 and self.turn == 1) or self.board.boardDisplay[0][0] == Piece.WHITE_KING or self.board.boardDisplay[0][self.board.get_num_of_columns()-1] == Piece.WHITE_KING or self.board.boardDisplay[self.board.get_num_of_rows()-1][0] == Piece.WHITE_KING or self.board.boardDisplay[self.board.get_num_of_rows() -1][self.board.get_num_of_columns() -1] == Piece.WHITE_KING 

    def is_final_state(self):
        return self.white_wins() or self.black_wins() or (self.movesPlayed >=self.moveLimit and self.moveLimit>0)

    def apply_move(self, move):
        if move not in self.get_moves():
            raise Exception("Movimiento ilegal")
        
        nextBoard = self.board.copy()
        row = move.initialCoordinate.rowIndex
        column = move.initialCoordinate.columnIndex
        nextRow = move.finalCoordinate.rowIndex
        nextColumn = move.finalCoordinate.columnIndex
        pieceMoving = self.board.boardDisplay[row][column]

        nextBoard.boardDisplay[row][column] = Piece.EMPTY
        nextBoard.boardDisplay[nextRow][nextColumn] = pieceMoving

        nextBoardChecked = Board.check_pieces_taken(nextBoard)
        nextTurn = 1
        if self.turn == 1:
            nextTurn = 2
        nextMoveLimit = self.moveLimit

        return Game(nextBoardChecked, nextTurn, self.movesPlayed + 1, nextMoveLimit)

    def print_state(self):
        self.board.print_board()
        if self.is_final_state():
            if self.white_wins():
                print("¡Ganan las blancas!")
            elif self.black_wins():
                print("¡Ganan las negras!")
            else:
                print("¡Tablas!")
        else:
            turnString = "blancas"
            if self.turn == 1:
                turnString = "negras"
            print("Turno de las ",turnString)
        print("Jugados ", self.movesPlayed," movimientos de ", self.moveLimit)
        print("-------------------------------------------------------------") 


