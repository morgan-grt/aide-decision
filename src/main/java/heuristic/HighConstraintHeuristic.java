package heuristic;

import java.util.Deque;
import java.util.Set;
import representations.Constraint;
import representations.Variable;

public class HighConstraintHeuristic implements Heuristic{
    private Set<Constraint> constraints;

    public HighConstraintHeuristic(Set<Constraint> constraints) {
        this.constraints = constraints;
    }
    
    /**
     *Retourne une variable selon une heuristique
     * retourne la variable la plus contrainte
     * @param deque
     * @return highConstraint
     */
    @Override
    public Variable useHeuristic(Deque<Variable> deque) {
        Variable highConstraint = deque.getFirst();
        for (Variable var : deque){
            if (calculFrequency(var) > calculFrequency(highConstraint)){
                highConstraint = var;
            }
        } 
        
        
        return highConstraint;
    }
    
    /**
     *Calcul la fréquence d'une variable dans un ensemble
     * @param variable
     * @return frequency
     */
    public int calculFrequency(Variable variable){
        int frequency = 0;
        for (Constraint constraint : constraints){
            for (Variable var : constraint.getScope()){
                if (var.getName().equals(variable.getName())){
                    frequency++;
                }
            }
        }
        return frequency;
    }
}
