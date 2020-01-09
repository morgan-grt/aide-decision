package heuristic;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;
import representations.Variable;

public class RandomHeuristic implements Heuristic{

    /**
     *Retourne une variable selon une heuristique
     * retourne une variable aléatoirement
     * @param deque
     * @return highConstraint
     */
    @Override
    public Variable useHeuristic(Deque<Variable> deque) {
        Variable randomVar = deque.getFirst();
        Deque<Variable> deque_tmp= new LinkedList<>(deque);
        int random = new Random().nextInt(deque.size());
        while(random != 0){
            randomVar = deque_tmp.pop();
            random--;
        }
        return randomVar;
    }

}
