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

    '''
    Devulve los movimientos disponibles en un estado de juego
    '''
    def get_moves(self):
        player = None
        if self.turn == 1:
            player = PlayerColor.BLACK
        else:
            player = PlayerColor.WHITE
        moves = list(self.board.get_player_available_moves()[player])
        shuffle(moves)
        return moves

    '''
    Devuelve True si el negro gana en una posición
    '''
    def black_wins(self):
        return (len(self.get_moves()) == 0 and self.turn == 2) or (not self.board.is_king_alive())

    '''
    Devuelve True si el blanco gana en una posición
    '''
    def white_wins(self):
        return (len(self.get_moves()) == 0 and self.turn == 1) or self.board.boardDisplay[0][0] == Piece.WHITE_KING or self.board.boardDisplay[0][self.board.get_num_of_columns()-1] == Piece.WHITE_KING or self.board.boardDisplay[self.board.get_num_of_rows()-1][0] == Piece.WHITE_KING or self.board.boardDisplay[self.board.get_num_of_rows() -1][self.board.get_num_of_columns() -1] == Piece.WHITE_KING 

    '''
    Devuelve si el estado de juego es tal que ganen las blancas, ganen las negras o haya tablas
    '''
    def is_final_state(self):
        return self.white_wins() or self.black_wins() or (self.movesPlayed >=self.moveLimit and self.moveLimit>0)

    '''
    Devuelve una copia del estado actual tras aplicar el movimiento que se pasa por parámetro
    '''
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

    '''
    Imprime por pantalla el estado de la partida
    '''
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


