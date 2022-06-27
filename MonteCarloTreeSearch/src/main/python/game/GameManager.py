from Game import Game
from BoardLoader import BoardLoader
from Board import Board
from UCT import UCT
from Move import Move

class GameManager:

    '''
    Inicia la secuencia de interfaz de usuario, llamándo a los submétodos correspondientes según las elecciones del usuario
    y permitiéndole acceder a todas las funcionalidades del programa
    '''
    def interfaz_usuario():

        print("¡Bienvenido a Hnefatafl!")
        variante = GameManager.get_variant()
        opcion = GameManager.get_option()   #0 = P vs IA.  1 = P vs P  2 = IA vs IA
        if opcion==0:
            GameManager.pvsIA(variante)
        elif opcion==1:
            GameManager.pvsp(variante)
        elif opcion==2:
            GameManager.IAvsIA(variante)

    def IAvsIA(variante):
        moveLimit = GameManager.get_max_moves()
        game = Game(BoardLoader.load_board(variante), 1, 0, moveLimit)
        print("IA negra:")
        tiempo1 = GameManager.get_max_time()
        print("IA Blanca")
        tiempo2 = GameManager.get_max_time()

        while(not game.is_final_state()):
            game.print_state()
            game = game.apply_move(UCT.look_for_solution(game, tiempo1))
            if game.is_final_state():
                break
            game.print_state()
            game = game.apply_move(UCT.look_for_solution(game, tiempo2))
        
        game.print_state()
        



    def pvsp(variante):
        moveLimit = GameManager.get_max_moves()
        game = Game(BoardLoader.load_board(variante), 1, 0, moveLimit)
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

    def pvsIA(variante):
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
                moveStr = input("Introduzca su movimiento o '?' para una pista: ")
                if moveStr == "?":
                    print("La IA recomienda " + str(UCT.look_for_solution(game,tiempo)))
                    continue
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

    def get_option():
        opcion = -1
        while opcion!=0 and opcion!=1 and opcion!=2:
            print("¿Quienes van a jugar?")
            print("        0. Jugador vs IA\r\n"
                + "        1. Jugador vs Jugador\r\n"
                + "        2. IA vs IA\r\n")
            try:
                opcion = int(input())
            except:
                opcion = -1
        return opcion




GameManager.interfaz_usuario()


            
        