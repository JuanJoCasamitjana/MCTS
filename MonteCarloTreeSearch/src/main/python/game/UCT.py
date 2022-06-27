from cmath import sqrt
import math
import random
import time
from Game import Game
from UCTNode import UCTNode


class UCT:

    def look_for_solution(s0, t):
        v0 = UCT.create_node(s0, None)
        timePassed = 0
        while(timePassed < t):
            start = time.time()

            v1 = UCT.tree_policy(v0)
            delta = UCT.default_policy(v1)
            UCT.backup(v1, delta)

            end = time.time()
            timePassed += (end - start)
        return v0.moves[UCT.best_child(v0,0)]
            
    
    def create_node(s, father):
        return UCTNode(s, 0, 0, 0, [], father)

    def tree_policy(v):
        while(not v.state.is_final_state()):
            if v.i < len(v.state.get_moves()):
                return UCT.expand(v)
            else:
                v = v.children[UCT.best_child(v, 1/sqrt(2))]
        return v

    def expand(v):
        s = v.state.apply_move(v.moves[v.i])
        v.i = v.i +1 
        child = UCT.create_node(s,v)
        v.children.append(child)
        return child

    def best_child(v,c):
        iRes = 0
        valMax = -99999999.0
        for i in range(0, len(v.children)):
            value = ((v.children[i].q/v.children[i].n) + c*sqrt(2*math.log(v.n)/v.children[i].n)).real
            if value > valMax:
                valMax = value
                iRes = i
        return iRes


    def default_policy(v):
        s = v.state
        movs = v.moves
        player = v.father.state.turn

        while(not s.is_final_state()):
            a = movs[random.randint(0, len(movs)-1)]
            s = s.apply_move(a)
            movs = s.get_moves()

        if (s.white_wins() and player == 2) or (s.black_wins() and player == 1):
            return 1
        else:
            return -1


    def backup(v, delta):
        while v != None:
            v.n = v.n + 1
            v.q = v.q + delta
            delta = -delta
            v = v.father


game = Game.get_initial_state(1, 100)
game.print_state()  

while(not game.is_final_state()):
    game = game.apply_move(UCT.look_for_solution(game, 5))
    game.print_state()
    if game.is_final_state():
        break
    game = game.apply_move(game.get_moves()[0])
    game.print_state()