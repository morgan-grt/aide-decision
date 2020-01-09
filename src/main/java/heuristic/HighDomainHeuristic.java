package heuristic;

import java.util.Deque;
import representations.Variable;

public class HighDomainHeuristic implements Heuristic{

    /**
     *Retourne une variable selon une heuristique
     * retourne la variable avec le plus grand domaine
     * @param deque
     * @return highConstraint
     */
    @Override
    public Variable useHeuristic(Deque<Variable> deque) {
        Variable highDomain = deque.getFirst();
        for (Variable var : deque){
            if (var.getDomain().size() > highDomain.getDomain().size()){
                highDomain = var;
            }
        }
        return highDomain;
    }

}
