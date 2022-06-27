class UCTNode:

    def __init__(self, state, n, q, i, children, father):
        self.state = state
        self.moves = state.get_moves()
        self.n = n
        self.q = q
        self.i = i
        self.children = children
        self.father = father

    def __str__(self):
        #Node[isFinalState=%s, id=%s, n=%d, q=%d, i=%d, father=%s, children=%s, moves=%d]
        return "Node[isFinalState="+str(self.state.is_final_state())+", n="+str(self.n)+", q="+str(self.q)+ ", i="+str(self.i)+ ", children="+ str(len(self.children))+", moves="+str(len(self.moves))+"]"
