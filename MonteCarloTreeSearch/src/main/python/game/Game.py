from Board import Board


class Game:

    def __init__(self, board, turn, movesPlayed, moveLimit):
        self.board = board
        self.turn = turn
        self.movesPlayed = movesPlayed
        self.moveLimit = moveLimit
    
    def copy(self):
        return Game(self.board.copy(), self.turn, self.movesPlayed, self.moveLimit)


    def get_initial_state(variant):
        integerDisplay = [[0,3,0,1,0],[0,2,2,0,1],[1,0,2,1,2],[2,0,0,0,0],[0,0,1,0,0]]
        board = Board.integer_display_to_board(integerDisplay)
        game = Game(board, 1, 1, 30)

    def get_moves(self):
        pass



    
