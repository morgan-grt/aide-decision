package extraction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import representations.Variable;

public class BooleanDatabase {
    private Set<Variable> variables;
    private Set<Map<Variable, String>> transactions;
    private List<Map<Variable, String>> transactionsList;

    public BooleanDatabase(Set<Variable> variables, Set<Map<Variable, String>> transactions
            , List<Map<Variable, String>> transactionsList) {
        this.variables = variables;
        this.transactions = transactions; //contient la bdd sans doublon (plus performant)
        this.transactionsList = transactionsList; // contient la bdd complète (pour les fréquences)
        transformation();
        if (this.transactionsList != null){
            transformationList();
        }
    }
    
    /**
     *transforme une base de données stockée en Set<Map> en base de données booléenne
     *supprime les variables qui ont pour valeur "0"
     */
    public void transformation(){
        for (Map<Variable, String> map : transactions){
            for (Map.Entry<Variable, String> entry : new HashMap<Variable, String>(map).entrySet()){
                if (!entry.getValue().equals("0") && !entry.getValue().equals("1")){
                    String name = entry.getKey() + "_" + entry.getValue();
                    Variable tmp = new Variable(name, "0", "1");
                    map.put(tmp, "1");
                    map.remove(entry.getKey(), entry.getValue());
                    boolean bool = true;
                    for (Variable var : new HashSet<Variable>(variables)){
                        if (var.getName().equals(tmp.getName())){
                            bool = false;
                        }
                    }
                    if (bool){
                        variables.add(tmp);
                        variables.remove(entry.getKey());
                    }
                }
                if (entry.getValue().equals("0")){
                    map.remove(entry.getKey(), entry.getValue());
                }
            }
        }
    }
    
    /**
     *identique à transformation mais pour une base de données
     * stockée en List<Map>
     */
    public void transformationList(){
        for (Map<Variable, String> map : transactionsList){
            for (Map.Entry<Variable, String> entry : new HashMap<Variable, String>(map).entrySet()){
                if (!entry.getValue().equals("0") && !entry.getValue().equals("1")){
                    String name = entry.getKey() + "_" + entry.getValue();
                    Variable tmp = new Variable(name, "0", "1");
                    map.put(tmp, "1");
                    map.remove(entry.getKey(), entry.getValue());
                    boolean bool = true;
                    for (Variable var : new HashSet<Variable>(variables)){
                        if (var.getName().equals(tmp.getName())){
                            bool = false;
                        }
                    }
                    if (bool){
                        variables.add(tmp);
                        variables.remove(entry.getKey());
                    }
                    
                }
                if (entry.getValue().equals("0")){
                    map.remove(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public Set<Variable> getVariables() {
        return variables;
    }

    public Set<Map<Variable, String>> getTransactions() {
        return transactions;
    }

    public List<Map<Variable, String>> getTransactionsList() {
        return transactionsList;
    }
    
    
    
}
