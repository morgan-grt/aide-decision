package ppc;
import heuristic.*;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import representations.*;

public class Backtracking {
    private final Set<Variable> variables;
    private final Set<Constraint> constraints;
    private Heuristic heuristic;

    public Backtracking(Set<Variable> variables, Set<Constraint> constraints) {
        this.variables = variables;
        this.constraints = constraints;
        this.heuristic = new RandomHeuristic();
    }
    
    /**
     *Génération de solutions qui respectent des contraintes
     * @param ass_part
     * @param var_non_ass
     * @param toutes_solutions
     */
    public void solutions(Map<Variable, String> ass_part, Deque<Variable> var_non_ass,
            Set<Map<Variable, String>> toutes_solutions){
        //si toutes les variables sont assignés c'est une solution
        if (var_non_ass.isEmpty()){
            toutes_solutions.add(new HashMap<>(ass_part));
        }
        else{
            //utilisation d'une heuristic
            Variable variable= heuristic.useHeuristic(var_non_ass);
            var_non_ass.remove(variable);
            //génération des candidats
            for (String string : variable.getDomain()){
                ass_part.put(variable, string);
                boolean satisfaite = true;
                Map<Variable, Set<String>> var_non_ass_domain = new HashMap<>();
                for (Variable var : var_non_ass){
                    var_non_ass_domain.put(new Variable(var.getName(), var.copyDomains()), var.copyDomains());
                }
                //vérification du candidat sur les contraintes
                for (Constraint constraint : constraints){
                    constraint.filter(ass_part, var_non_ass_domain);
                    if (verifiable(ass_part, constraint) && !constraint.isSatisfiedBy(ass_part)){
                        satisfaite = false;
                    }
                    
                }
                //si le candidat satisfait les contraintes on appelle récursivement
                //pour générer de nouvelles solutions
                if(satisfaite)
                    solutions(ass_part, var_non_ass, toutes_solutions);
                ass_part.remove(variable, string);
            }
            
            var_non_ass.add(variable);
        }
    }
    
    /**
     *Retourne toutes les solutions selon des contraintes
     * @return toutes_solutions
     */
    public Set<Map<Variable, String>> allSolutions(){
        Map<Variable, String> ass_part = new HashMap<Variable, String>();
        Deque<Variable> var_non_ass = new LinkedList<Variable>();
        for(Variable var: variables){
            var_non_ass.add(var);
        }
        Set<Map<Variable, String>> toutes_solutions = new HashSet<Map<Variable, String>>();
        solutions(ass_part, var_non_ass, toutes_solutions);
        return toutes_solutions;
    }

    /**
     *Détermine si une association partielle de variables est vérifiable selon
     * les contraintes
     * @param ass_part
     * @param constraint
     * @return boolean
     */
    public boolean verifiable(Map<Variable, String> ass_part, Constraint constraint){
        for (Variable variable : constraint.getScope()){
            boolean bool = false;
            for (Map.Entry<Variable, String> entry : ass_part.entrySet()){
                if (variable.getName().equals(entry.getKey().getName()))
                    bool = bool || true;
                
            }
            if (!bool)
                return false;
        }
        return true;
    }
    
    
}
