/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extraction;

import java.util.List;
import java.util.Map;
import java.util.Set;
import representations.Variable;

public class Database {
    private Set<Variable> variables;
    private Set<Map<Variable, String>> instances;
    private List<Map<Variable, String>> instancesList;

    public Database(Set<Variable> variables, Set<Map<Variable, String>> instances
            , List<Map<Variable, String>> instancesList) {
        this.variables = variables;
        this.instances = instances;
        this.instancesList = instancesList;
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public Set<Map<Variable, String>> getInstances() {
        return instances;
    }

    public List<Map<Variable, String>> getInstancesList() {
        return instancesList;
    }
    
    
}
