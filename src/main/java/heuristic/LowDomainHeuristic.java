package heuristic;

import java.util.Deque;
import representations.Variable;

public class LowDomainHeuristic implements Heuristic{

    /**
     *Retourne une variable selon une heuristique
     * retourne la variable avec le plus petit domaine
     * @param deque
     * @return highConstraint
     */
    @Override
    public Variable useHeuristic(Deque<Variable> deque) {
        Variable lowDomain = deque.getFirst();
        for (Variable var : deque){
            if (var.getDomain().size() < lowDomain.getDomain().size()){
                lowDomain = var;
            }
        }
        return lowDomain;
    }

}
