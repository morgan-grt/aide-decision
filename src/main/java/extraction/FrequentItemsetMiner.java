package extraction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import representations.RestrictedDomain;
import representations.Variable;

public class FrequentItemsetMiner {
    private BooleanDatabase booleanDB;
    protected Set<Variable> variables;

    public FrequentItemsetMiner(BooleanDatabase booleanDB, Set<Variable> variables) {
        this.booleanDB = booleanDB;
        this.variables = variables;
    }
    
    /**
     *Extraction des motifs fréquents en fonction de la fréquence minimale
     * on créer à partir des variables de rang 1 les motif de rang +1
     * pour tester si les motifs sont fréquent ou non, si oui
     * ils seront utilisés pour créer les motifs de rang n+1
     * @param minFr
     * @return map qui contient les motifs fréquents et leur fréquence
     */
    public Map<Set<Variable>, Float> frequentItemsets(int minFr){
        Map<Set<Variable>, Float> map = new HashMap<>();
        Set<Set<Variable>> openSet = new HashSet();
        Set<Set<Variable>> closedSet = new HashSet();
        
        //initialisation des list de motifs de rang 1
        for (Variable variable : variables){
            Set<Variable> tmp = new HashSet();
            tmp.add(variable);
            openSet.add(tmp);
            closedSet.add(tmp);
            float frequence = getFrequency(tmp);
            if (frequence >= minFr ){
                map.put(tmp, frequence);
            }
        }
        
        //tant qu'il y a des motifs à parcourir; on créer à partir de ces
        //motifs le rang n+1
        while (!closedSet.isEmpty()){
            openSet = new HashSet<Set<Variable>>(closedSet);
            closedSet.clear();
            for (Set<Variable> vars : openSet){
                for (Variable var : variables){
                    if (!vars.contains(var)){
                        Set<Variable> tmp = new HashSet(vars);
                        tmp.add(var);
                        float frequence = getFrequency(tmp);
                        if (frequence >= minFr ){
                            map.put(tmp, frequence);
                            closedSet.add(tmp);
                        }
                    }
                }
            }
        }
        return map;
    }
    
    /**
     *Calcul de la fréquence d'un motif donné dans une base de données
     * @param variables
     * @return frequence la frequence du motif donné
     */
    public float getFrequence(Set<Variable> variables){
        float frequence = 0;
        for (Map<Variable, String> transaction : booleanDB.getTransactionsList()){
            boolean bool = false;
            boolean bool2 = true;
            for (Variable var : variables){
                bool = false;
                for (Map.Entry<Variable, String> entry : transaction.entrySet()){
                    if (entry.getKey().equals(var)){
                        bool = true;
                        break;
                    }
                }
                bool2 = bool2 && bool;
            }
            if (bool2){
                frequence++;
            }
        }
        return (frequence);
    }
    
    /**
     *Identique à getFrequence() mais plus performant en terme
     * de temps d'éxecution
     * @param variables
     * @return frequence la frequence du motif donné
     */
    public float getFrequency(Set<Variable> variables){
        float frequence = 0;
        for (Map<Variable, String> transaction : booleanDB.getTransactionsList()){
            boolean bool = true;
            for (Variable var : variables){
                if (!transaction.containsKey(var)){
                    bool = false;
                    break;
                }
            }
            if (bool){
                frequence++;
            }
        }
        return frequence;
    }
    
    /**
     *Vérifie si un motif est présent dans un ensemble de motifs
     * @param open_closed_set
     * @param set_verify
     * @return boolean
     */
    public boolean setSetContainsSet(Set<Set<Variable>> open_closed_set, Set<Variable> set_verify){
        if (open_closed_set.isEmpty()){
            return false;
        }
        for (Set<Variable> vars : open_closed_set){
            if (setContainsSet(vars, set_verify)){
                return true;
            }
        }
        return false;
    }
    
    /**
     *Vérifie si un restrictedDomain est présent dans un ensemble de restrictedDomain
     * @param premisse
     * @param rD
     * @return boolean
     */
    public boolean setContainsRestrictedDomain(Set<RestrictedDomain> premisse, RestrictedDomain rD){
        if (premisse.isEmpty()){
            return false;
        }
        for (RestrictedDomain var : premisse){
            if (var.equals(rD)){
                return true;
            }
        }
        return false;
    }    
    
    /**
     * Vérifie si un motif contient l'ensemble d'un motif
     * @param set_var
     * @param set_verify
     * @return boolean
     */
    public boolean setContainsSet(Set<Variable> set_var, Set<Variable> set_verify){
        for (Variable var : set_verify){
            if (!set_var.contains(var)){
                return false;
            }
        }
        return true;
    }
    
}
