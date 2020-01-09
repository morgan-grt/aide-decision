package representations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Rule implements Constraint{
    private Set<RestrictedDomain> premisse;
    private Set<RestrictedDomain> conclusion;
    
    public Rule(Set<RestrictedDomain> premisse, Set<RestrictedDomain> conclusion){
        this.premisse = premisse;
        this.conclusion = conclusion;
    }

    @Override
    public Set<RestrictedDomain> getPremisse() {
        return premisse;
    }
    
    /**
     *Retourne l'ensemble des variables comprises dans un ensemble
     * @return variables
     */
    @Override
    public Set<Variable> getScope() {
        Set<Variable> variables = new HashSet<>();
        for (RestrictedDomain rD : premisse){
            variables.add(rD.getVariable());
        }
        for (RestrictedDomain rD : conclusion){
            variables.add(rD.getVariable());
        }
        return variables;
    }
    
    /**
     *Détermine si un ensemble de variable/valeur satisfait une règle
     * @param map
     * @return boolean
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable, String> map){
        //parcours des variables/valeurs d'un ensemble
        for (Map.Entry<Variable, String> entry : map.entrySet()){
            //étape de vérification sur la premisse (et)
            for (RestrictedDomain rD : premisse){
                if ((entry.getKey().getName().equals(rD.getVariable().getName()))) {
                    if (!rD.getDomain().contains(entry.getValue())){
                        return true;
                    }
                }
            }
            //étape de vérification sur la conclusion (ou)
            for (RestrictedDomain rD : conclusion){
                if ((entry.getKey().getName().equals(rD.getVariable().getName()))) {
                    if (rD.getDomain().contains(entry.getValue())){
                        return true;
                    }
                }
            }
            
        }
        return false;
    }
    
    /**
     *Etape de backtrack pour réduire le domaine d'une variable
     * retire une valeur d'un domaine de variable si elle ne satisfait pas
     * les règles
     * @param ass_part
     * @param var_non_ass_domain
     * @return boolean
     */
    @Override
    public boolean filter(Map<Variable, String> ass_part, Map<Variable, Set<String>> var_non_ass_domain){
        boolean isFiltered = false;
        for (Map.Entry<Variable, Set<String>> entry : var_non_ass_domain.entrySet()){
            Variable var = entry.getKey();
            Variable tmpVar = new Variable(var.getName(), var.copyDomains());
            for (String string : tmpVar.getDomain()){
                Map<Variable, String> map = new HashMap<>();
                map.put(new Variable(var.getName()), string);
                for (Map.Entry<Variable, String> entryAss : ass_part.entrySet()){
                    map.put(entryAss.getKey(), string);
                }
                if (!isSatisfiedBy((map))){
                    var.getDomain().remove(string);
                    isFiltered = true;
                }
            }
        }
        return isFiltered;
    }
    @Override
    public String toString(){
        String string = "[ ";
        Iterator iterate_premisse = premisse.iterator();
        while (iterate_premisse.hasNext()){
            string += iterate_premisse.next();
            if (iterate_premisse.hasNext()){
                string += " et ";
            }
        }
        string += (" => ");
        Iterator iterate_conclusion = conclusion.iterator();
        while (iterate_conclusion.hasNext()){
            string += iterate_conclusion.next();
            if (iterate_conclusion.hasNext()){
                string += " ou ";
            }
        }
        string += " ]";
        return string;
    }
}
