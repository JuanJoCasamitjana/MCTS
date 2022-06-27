from Game import Game
from BoardLoader import BoardLoader
from Board import Board
from UCT import UCT
from Move import Move

class GameManager:

    def interfaz_usuario():

        print("¡Bienvenido a Hnefatafl!")
        variante = GameManager.get_variant()
        tiempo = GameManager.get_max_time()
        moveLimit = GameManager.get_max_moves()
        turn = GameManager.get_color()
        game = Game(BoardLoader.load_board(variante), 1, 0, moveLimit)

        if turn == 2:
            game = game.apply_move(UCT.look_for_solution(game, tiempo))
        game.print_state()

        while(not game.is_final_state()):
            moveStrOk = False
            while not moveStrOk:
                moveStr = input("Introduzca su movimiento: ")
                try:
                    move = Move(int(moveStr[0]),int(moveStr[1]),int(moveStr[2]),int(moveStr[3]))
                    if move in game.get_moves():
                        moveStrOk = True
                        game = game.apply_move(move)
                    else:
                        print("Movimiento ilegal")
                except:
                    print("No fue capaz procesar este movimiento")
            game.print_state()
            if game.is_final_state():
                break
            moveSelected = UCT.look_for_solution(game, tiempo)
            game = game.apply_move(moveSelected)
            print("La IA juega ", str(moveSelected), "\n")
            game.print_state()

        
        


    def get_variant():
        variante = 0
        while variante<1 or variante > 6:
            print("¿Qué variante desea jugar?")
            print("        1. Hnefatafl\r\n"
                + "        2. Tablut\r\n"
                + "        3. Ard Ri\r\n"
                + "        4. Brandubh\r\n"
                + "        5. Tawlbwrdd\r\n"
                + "        6. Alea Evangelii")

            try:
                variante = int(input())
            except:
                variante = 0
        return variante

    def get_max_time():
        tiempo = 0
        while tiempo<1:
            print("¿Cuánto tiempo desea darle a la IA para que piense cada jugada??")
            try:
                tiempo = int(input())
            except:
                tiempo = 0
        return tiempo

    def get_max_moves():
        moveLimit = 0
        movesOk = False
        while(not movesOk):
            print("¿Cual quiere que sea el máximo de jugadas? Escoja 0 o menor para no tener límite")
            try:
                moveLimit = int(input())
                movesOk = True
            except:
                movesOk = False
        return moveLimit

    def get_color():
        turn = 0
        while turn != 1 and turn != 2:
            print("¿Qué color desea ser?")
            print("        1. Negras\r\n"
                + "        2. Blancas\r\n")
            try:
                turn = int(input())
            except:
                pass
        return turn



GameManager.interfaz_usuario()


            
        