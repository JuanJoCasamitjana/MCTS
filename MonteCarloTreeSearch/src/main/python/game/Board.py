
from cmath import pi
from Piece import Piece
from Coordinate import Coordinate
from PlayerColor import PlayerColor
from Move import Move
from copy import copy


class Board:

    def __init__(self, boardDisplay):
        self.boardDisplay = boardDisplay

    '''
    Devuelve un tablero a patir de una lista de listas donde 0 es un espacio vacío, 
    1 es un peón negro, 2 es un peón blanco y 3 es el rey
    '''
    def integer_display_to_board(integerDisplay):
        boardDisplay = []
        for i in range(0, len(integerDisplay)):
            row = []
            for j in range(0, len(integerDisplay[i])):
                piece = integerDisplay[i][j]
                row.append(Piece._value2member_map_.get(piece))
            boardDisplay.append(row)
        return Board(boardDisplay)

    def copy(self):
        newBoardDisplay = []
        for i in range(0, len(self.boardDisplay)):
            row = []
            for j in range(0, len(self.boardDisplay[i])):
                piece = self.boardDisplay[i][j]
                if piece == Piece.EMPTY:
                    row.append(Piece.EMPTY)
                elif piece == Piece.BLACK_PAWN:
                    row.append(Piece.BLACK_PAWN)
                elif piece == Piece.WHITE_PAWN:
                    row.append(Piece.WHITE_PAWN)
                elif piece == Piece.WHITE_KING:
                    row.append(Piece.WHITE_KING)
                else:
                    row.append(piece)
            newBoardDisplay.append(row)

        newBoard = Board(newBoardDisplay)
        
        return Board(newBoardDisplay)


    '''
    Devuelve el número de filas del tablero
    '''
    def get_num_of_rows(self):
        return len(self.boardDisplay)

    '''
    Devuelve el número de columnas del tablero
    '''
    def get_num_of_columns(self):
        return len(self.boardDisplay[0])

    '''Devuelve las coordenadas del trono del tablero'''
    def get_throne(self):
        return Coordinate((self.get_num_of_rows()-1)/2, (self.get_num_of_rows()-1)/2)

    '''Devuelve True si la Coordenada c es una de las casillas que rodean por los dos
    lados al trono'''
    def is_surroundings_of_throne(self, c):

        isMiddleTop = c.rowIndex == ((self.get_num_of_rows()-1)/2)-1 and c.columnIndex == (self.get_num_of_columns()-1)/2
        isMiddleBottom = c.rowIndex == ((self.get_num_of_rows()-1)/2)+1 and c.columnIndex == (self.get_num_of_columns()-1)/2
        isMiddleLeft = c.rowIndex == ((self.get_num_of_rows()-1)/2) and c.columnIndex == ((self.get_num_of_columns()-1)/2)-1
        isMiddleRight = c.rowIndex == ((self.get_num_of_rows()-1)/2) and c.columnIndex == ((self.get_num_of_columns()-1)/2)+1

        return isMiddleTop or isMiddleBottom or isMiddleRight or isMiddleLeft
    '''
    Devuelve si una pieza es negra
    '''
    def is_black(piece):
        return piece == Piece.BLACK_PAWN

    '''
    Devuelve si una pieza es blanca
    '''
    def is_white(piece):
        return piece == Piece.WHITE_PAWN or piece == Piece.WHITE_KING
    
    '''
    Devuelve los movimientos disponibles de una pieza en un tablero comprobando hacia la izquierda
    '''
    def check_left_moves(piece, currentCoordinate, columns, rows, boardDisplay, keySquares):
        res = []
        for j in range(int(currentCoordinate.columnIndex)-1, -1, -1):
            move = Move(currentCoordinate.rowIndex, currentCoordinate.columnIndex, currentCoordinate.rowIndex, j)
            if boardDisplay[int(currentCoordinate.rowIndex)][j] != Piece.EMPTY:
                break
            if (Coordinate(currentCoordinate.rowIndex, j) in keySquares) and (piece != Piece.WHITE_KING):
                continue
            if currentCoordinate.rowIndex == 0 or currentCoordinate.rowIndex == rows-1:
                res.append(move)
            else:
                pieceDown = boardDisplay[currentCoordinate.rowIndex+1][j]
                pieceUp = boardDisplay[currentCoordinate.rowIndex-1][j]
                if Board.is_black(piece) and (not Board.is_white(pieceUp) or not Board.is_white(pieceDown)):
                    res.append(move)
                if Board.is_white(piece) and (not Board.is_black(pieceUp) or not Board.is_black(pieceDown)):
                    res.append(move)
        return res
    
    '''
    Devuelve los movimientos disponibles de una pieza en un tablero comprobando hacia la derecha
    '''
    def check_right_moves(piece, currentCoordinate, columns, rows, boardDisplay, keySquares):
        res = []
        for j in range(int(currentCoordinate.columnIndex)+1, columns):
            move = Move(currentCoordinate.rowIndex, currentCoordinate.columnIndex, currentCoordinate.rowIndex, j)
            if boardDisplay[int(currentCoordinate.rowIndex)][j] != Piece.EMPTY:
                break
            if (Coordinate(currentCoordinate.rowIndex, j) in keySquares) and (piece != Piece.WHITE_KING):
                continue
            if currentCoordinate.rowIndex == 0 or currentCoordinate.rowIndex == rows-1:
                res.append(move)
            else:
                pieceDown = boardDisplay[currentCoordinate.rowIndex+1][j]
                pieceUp = boardDisplay[currentCoordinate.rowIndex-1][j]
                if Board.is_black(piece) and (not Board.is_white(pieceUp) or not Board.is_white(pieceDown)):
                    res.append(move)
                if Board.is_white(piece) and (not Board.is_black(pieceUp) or not Board.is_black(pieceDown)):
                    res.append(move)
        return res

    '''
    Devuelve los movimientos disponibles de una pieza en un tablero comprobando hacia arriba
    '''
    def check_up_moves(piece, currentCoordinate, columns, rows, boardDisplay, keySquares):
        res = []
        for i in range(int(currentCoordinate.rowIndex)-1, -1, -1):
            move = Move(currentCoordinate.rowIndex, currentCoordinate.columnIndex, i, currentCoordinate.columnIndex)
            if boardDisplay[i][int(currentCoordinate.columnIndex)] != Piece.EMPTY:
                break
            if (Coordinate(i, currentCoordinate.columnIndex) in keySquares) and (piece != Piece.WHITE_KING):
                continue
            if currentCoordinate.columnIndex == 0 or currentCoordinate.columnIndex == columns-1:
                res.append(move)
            else:
                pieceRight = boardDisplay[i][currentCoordinate.columnIndex+1]
                pieceLeft = boardDisplay[i][currentCoordinate.columnIndex-1]
                if Board.is_black(piece) and (not Board.is_white(pieceLeft) or not Board.is_white(pieceRight)):
                    res.append(move)
                if Board.is_white(piece) and (not Board.is_black(pieceLeft) or not Board.is_black(pieceRight)):
                    res.append(move)
        return res

    '''
    Devuelve los movimientos disponibles de una pieza en un tablero comprobando hacia abajo
    '''
    def check_down_moves(piece, currentCoordinate, columns, rows, boardDisplay, keySquares):
        res = []
        for i in range(int(currentCoordinate.rowIndex)+1, rows):
            move = Move(currentCoordinate.rowIndex, currentCoordinate.columnIndex, i, currentCoordinate.columnIndex)
            if boardDisplay[i][int(currentCoordinate.columnIndex)] != Piece.EMPTY:
                break
            if (Coordinate(i, currentCoordinate.columnIndex) in keySquares) and (piece != Piece.WHITE_KING):
                continue
            if currentCoordinate.columnIndex == 0 or currentCoordinate.columnIndex == columns-1:
                res.append(move)
            else:
                pieceRight = boardDisplay[i][currentCoordinate.columnIndex+1]
                pieceLeft = boardDisplay[i][currentCoordinate.columnIndex-1]
                if Board.is_black(piece) and (not Board.is_white(pieceLeft) or not Board.is_white(pieceRight)):
                    res.append(move)
                if Board.is_white(piece) and (not Board.is_black(pieceLeft) or not Board.is_black(pieceRight)):
                    res.append(move)
        return res

    
    '''
    Devuelve un diccionario que contiene los movimientos disponibles de ambos jugadores en la posición del tablero
    '''
    def get_player_available_moves(self):
        res = dict()
        res[PlayerColor.BLACK] = []
        res[PlayerColor.WHITE] = []
        rows = self.get_num_of_rows()
        columns = self.get_num_of_columns()

        for i in range(0,rows):
            for j in range(0, columns):
                piece = self.boardDisplay[i][j]
                moves = Board.get_available_moves_of_piece(Coordinate(i,j), self.boardDisplay)

                if piece == Piece.BLACK_PAWN:
                    if PlayerColor.BLACK in res:
                        moveList = list(res[PlayerColor.BLACK])
                        moveList.extend(moves)
                        res[PlayerColor.BLACK] = moveList
                    else:
                        res[PlayerColor.BLACK] = moves
                elif piece == Piece.WHITE_PAWN or piece == Piece.WHITE_KING:
                    if PlayerColor.WHITE in res:
                        moveList = list(res[PlayerColor.WHITE])
                        moveList.extend(moves)
                        res[PlayerColor.WHITE] = moveList
                    else:
                        res[PlayerColor.WHITE] = moves
        return res

    '''
    Devuelve los movimientos disponibles de una pieza concreta en un tablero
    '''
    def get_available_moves_of_piece(currentCoordinate, boardDisplay):
        res = []
        rows = len(boardDisplay)
        columns = len(boardDisplay[0])
        piece = boardDisplay[currentCoordinate.rowIndex][currentCoordinate.columnIndex]

        keySquares = [Coordinate(0,0), Coordinate(rows-1, 0), Coordinate(0, columns-1), Coordinate(rows-1, columns-1)]

        if piece == Piece.EMPTY:
            return res

        res.extend(Board.check_left_moves(piece, currentCoordinate, columns, rows, boardDisplay, keySquares))
        res.extend(Board.check_right_moves(piece, currentCoordinate, columns, rows, boardDisplay, keySquares))
        res.extend(Board.check_up_moves(piece, currentCoordinate, columns, rows, boardDisplay, keySquares))
        res.extend(Board.check_down_moves(piece, currentCoordinate, columns, rows, boardDisplay, keySquares))

        return res

    '''
    Devuelve la pieza en una coordenada del tablero
    '''
    def get_piece_at_coordinate(self, c):
        return self.boardDisplay[c.rowIndex][c.columnIndex]

    '''
    Devuelve True si y solo si el rey está vivo en el tablero
    '''
    def is_king_alive(self):
        res = False
        for row in self.boardDisplay:
            for piece in row:
                if piece == Piece.WHITE_KING:
                    return True
        return res

    '''
    Imprime el estado actual del tablero
    '''
    def print_board(self):
        res = ""
        res += "       "
        for i in range(0, len(self.boardDisplay)):
            res+=str(i)+"  "
        res+="\n\n\n\n"
        for i in range(0, self.get_num_of_rows()):
            res += str(i)+"      "
            for j in range(0, self.get_num_of_columns()):
                piece = self.boardDisplay[i][j]
                if piece == Piece.EMPTY:
                    res+="·  "
                elif piece == Piece.BLACK_PAWN:
                    res+="B  "
                elif piece == Piece.WHITE_PAWN:
                    res+="w  "
                else:
                    res+="k  "
            res += "\n\n"
        print(res)

    '''
    Devuelve una copia del tablero pasado por parámetro tras las comprobaciones de captura, eliminando las piezas
    eliminadas en dicha posición
    '''
    def check_pieces_taken(nextBoard):
        nextBoardChecked = nextBoard.copy()

        #Checkeo horizontal
        for i in range(0, nextBoard.get_num_of_rows()):
            for j in range(1, nextBoard.get_num_of_columns()-1):
                leftPiece = nextBoard.boardDisplay[i][j-1]
                middlePiece = nextBoard.boardDisplay[i][j]
                rightPiece = nextBoard.boardDisplay[i][j+1]

                if Board.is_black(middlePiece) and Board.is_white(leftPiece) and Board.is_white(rightPiece):
                    nextBoardChecked.boardDisplay[i][j] = Piece.EMPTY
                if middlePiece == Piece.WHITE_PAWN and Board.is_black(leftPiece) and Board.is_black(rightPiece):
                    nextBoardChecked.boardDisplay[i][j] = Piece.EMPTY
                
                if middlePiece == Piece.WHITE_KING:
                    coordinate = Coordinate(i,j)
                    if coordinate == nextBoard.get_throne() and nextBoard.get_piece_at_coordinate(Coordinate(i-1, j)) == Piece.BLACK_PAWN and nextBoard.get_piece_at_coordinate(Coordinate(i+1, j)) == Piece.BLACK_PAWN and nextBoard.get_piece_at_coordinate(Coordinate(i, j-1)) == Piece.BLACK_PAWN and nextBoard.get_piece_at_coordinate(Coordinate(i, j+1)) == Piece.BLACK_PAWN:
                        nextBoardChecked.boardDisplay[i][j] = Piece.EMPTY
                    elif nextBoard.is_surroundings_of_throne(coordinate):
                        piecesAround = []
                        piecesAround.append(nextBoard.get_piece_at_coordinate(Coordinate(i-1, j)))
                        piecesAround.append(nextBoard.get_piece_at_coordinate(Coordinate(i+1, j)))
                        piecesAround.append(nextBoard.get_piece_at_coordinate(Coordinate(i, j-1)))
                        piecesAround.append(nextBoard.get_piece_at_coordinate(Coordinate(i, j+1)))
                        blackPawnsCounter = 0
                        for pieceAround in piecesAround:
                            if pieceAround == Piece.BLACK_PAWN:
                                blackPawnsCounter += 1
                        if blackPawnsCounter >= 3:
                            nextBoardChecked.boardDisplay[i][j] = Piece.EMPTY
                    elif Board.is_black(rightPiece) and Board.is_black(leftPiece):
                        nextBoardChecked.boardDisplay[i][j] = Piece.EMPTY

        #Checkeo vertical

        for i in range(1, nextBoard.get_num_of_rows()-1):
            for j in range(0, nextBoard.get_num_of_columns()):
                topPiece = nextBoard.boardDisplay[i-1][j]
                middlePiece = nextBoard.boardDisplay[i][j]
                bottomPiece = nextBoard.boardDisplay[i+1][j]

                if Board.is_black(middlePiece) and Board.is_white(topPiece) and Board.is_white(bottomPiece):
                    nextBoardChecked.boardDisplay[i][j] = Piece.EMPTY
                if(middlePiece == Piece.WHITE_PAWN and Board.is_black(topPiece) and Board.is_black(bottomPiece)):
                    nextBoardChecked.boardDisplay[i][j] = Piece.EMPTY
                if(middlePiece == Piece.WHITE_KING and Coordinate(i,j) != nextBoard.get_throne() and not nextBoard.is_surroundings_of_throne(Coordinate(i,j)) and Board.is_black(topPiece) and Board.is_black(bottomPiece)):
                    nextBoardChecked.boardDisplay[i][j] = Piece.EMPTY
        return nextBoardChecked