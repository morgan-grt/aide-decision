package representations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Disjunction extends Rule{
    private Set<RestrictedDomain> premisse;

    public Disjunction(Set<RestrictedDomain> premisse, Set<RestrictedDomain> conclusion) {
        super(premisse, conclusion);
    }
    
    /**
     *Retourne l'ensemble des variables comprises dans un ensemble
     * @return variables
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<>();
        for (RestrictedDomain rD : this.premisse){
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
        boolean premisseIsSatisfied = true;
        for (RestrictedDomain rD : premisse){
            for (Map.Entry<Variable, String> entry : map.entrySet()){
                if ((entry.getKey().getName().equals(rD.getVariable().getName()))) {
                    premisseIsSatisfied = premisseIsSatisfied && (rD.getDomain().contains(entry.getValue()));
                }
            }
        }
        return premisseIsSatisfied;
    }

}
