package heuristic;

import java.util.Deque;
import java.util.Set;
import representations.Constraint;
import representations.Variable;

public class LowConstraintHeuristic implements Heuristic{    
    private Set<Constraint> constraints;

    public LowConstraintHeuristic(Set<Constraint> constraints) {
        this.constraints = constraints;
    }
    
    /**
     *Retourne une variable selon une heuristique
     * retourne la variable la moins contrainte
     * @param deque
     * @return highConstraint
     */
    @Override
    public Variable useHeuristic(Deque<Variable> deque) {
        Variable lowConstraint = deque.getFirst();
        for (Variable var : deque){
            if (calculFrequency(var) < calculFrequency(lowConstraint)){
                lowConstraint = var;
            }
        } 
        
        
        return lowConstraint;
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
