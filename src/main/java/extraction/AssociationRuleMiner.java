/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extraction;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import representations.RestrictedDomain;
import representations.Rule;
import representations.Variable;

public class AssociationRuleMiner {
    protected Map<Set<Variable>, Float> frequentItemsets;
    protected FrequentItemsetMiner frequentItemSet;

    public AssociationRuleMiner(Map<Set<Variable>, Float> frequentItemsets, FrequentItemsetMiner frequentItemSet) {
        this.frequentItemsets = frequentItemsets;
        this.frequentItemSet = frequentItemSet;
    }
    

    public Map<Set<Variable>, Float> getFrequentItemsets() {
        return frequentItemsets;
    }
    
    /**
     *Création des règles à partir des itemsetfréquents
     * calculé auparavant
     * <p>
     * on parcourt chaque itemsetfréquent, pour chacune de ses variables on construit
     * les instances possibles
     * à partir des instances on compare selon la fréquence minimale et la confiance minimale
     * pour garder que les instances valides et créer une nouvelle règle
     * @param minFr
     * @param minConf
     * @return rulesSet l'ensemble des règles extraitent
     */
    public Set<Rule> calculRules(int minFr, double minConf){
        Set<Rule> rulesSet = new HashSet();
        //parcours de chaque itemsetfréquent
        for (Map.Entry<Set<Variable>, Float> entry : frequentItemsets.entrySet()){
            Set<Set<Variable>> openSet = new HashSet();     //contient les instances à parcourir
            Set<Set<Variable>> closedSet = new HashSet();   //contient les instances parcourues
            //initialisation de openSet avec chaque variable
            for (Variable variable : entry.getKey()){
                Set<Variable> tmp = new HashSet();
                tmp.add(variable);
                if (!frequentItemSet.setSetContainsSet(openSet, tmp)){
                    openSet.add(tmp);
                }
                closedSet.add(tmp);
            }
            //pour chaque instances dans closedSet on créer les instances
            //du rang n+1
            for (Set<Variable> vars : closedSet){
                for (Variable var : entry.getKey()){
                    if (!vars.contains(var)){
                        Set<Variable> tmp = new HashSet(vars);
                        tmp.add(var);
                        if (!frequentItemSet.setSetContainsSet(closedSet, tmp) 
                                && !frequentItemSet.setSetContainsSet(openSet, tmp)){
                            openSet.add(tmp);
                        }
                    }
                }
            }
            //création des règles à partir des instances à parcourir
            //critères de validité : la fréquence min et la confiance min
            for (Set<Variable> vars : openSet){
                float frequence = frequentItemSet.getFrequency(vars);
                if (frequence >= minFr 
                        && entry.getValue() / frequence >= minConf){
                    Set<RestrictedDomain> premisse = new HashSet();
                    Set<RestrictedDomain> conclusion = new HashSet();
                    for (Variable var : vars){
                            premisse.add(new RestrictedDomain(var, var.getDomain()));
                    }
                    for (Variable var : entry.getKey()){
                        RestrictedDomain rD = new RestrictedDomain(var, var.getDomain());
                        if (!frequentItemSet.setContainsRestrictedDomain(premisse, rD)){
                            conclusion.add(rD);
                        }
                    }

                    if (!conclusion.isEmpty()){
                        Rule ruleTMP = new Rule(premisse, conclusion);
                        rulesSet.add(ruleTMP);
                    }
                }
            }  
        }
        return rulesSet;
    }
}
