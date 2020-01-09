package heuristic;

import java.util.Deque;
import representations.Variable;

public interface Heuristic {
    public Variable useHeuristic(Deque<Variable> deque);
}
