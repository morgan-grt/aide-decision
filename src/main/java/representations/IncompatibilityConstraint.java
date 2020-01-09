package representations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IncompatibilityConstraint extends Rule{
    private Set<RestrictedDomain> conclusion;

    public IncompatibilityConstraint(Set<RestrictedDomain> premisse, Set<RestrictedDomain> conclusion) {
        super(premisse, conclusion);
    }
    
    /**
     *Retourne l'ensemble des variables comprises dans un ensemble
     * @return variables
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<>();
        for (RestrictedDomain rD : this.conclusion){
            variables.add(rD.getVariable());
        }
        return variables;
    }
    
    /**
     *Détermine si un ensemble de variable/valeur satisfait une conclusion
     * @param map
     * @return boolean
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, String> map) {
        boolean conclusionIsSatisfied = false;
        for (RestrictedDomain rD : conclusion){
            for (Map.Entry<Variable, String> entry : map.entrySet()){
                if ((entry.getKey().getName().equals(rD.getVariable().getName()))) {
                    conclusionIsSatisfied = conclusionIsSatisfied || (rD.getDomain().contains(entry.getValue()));
                }
            }
        }
        return conclusionIsSatisfied;
    }
}
