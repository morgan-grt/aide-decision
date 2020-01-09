package extraction;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import representations.Rule;
import representations.Variable;

public class InitExtraction {
    public void init(String[] args) throws IOException {
        int args1 = (Integer.parseInt(args[1]));
        float args2 = (Float.parseFloat(args[2]));
        String args3 = args[3];
        System.out.println("args 1: "+args1+" | args 2: "+args2+" | args 3: "+args3);
        
        
        //si args3 == "0" on utilise les exemples vus en cours
        //A B C D E
        if (args3.equals("0")){
            
            //////CREATION DES VARIABLES\\\\\\
            Set<Variable> variables = new HashSet<>();
            Variable A = new Variable("A","0","1");variables.add(A);
            Variable B = new Variable("B","0","1");variables.add(B);
            Variable C = new Variable("C","0","1");variables.add(C);
            Variable D = new Variable("D","0","1");variables.add(D);
            Variable E = new Variable("E","0","1");variables.add(E);

            //////CREATION DE LA LISTE DES VARIABLES\\\\\\
            Set<Variable> allVariable = new HashSet();
            allVariable.add(A);
            allVariable.add(B);
            allVariable.add(C);
            allVariable.add(D);
            allVariable.add(E);

            //////CREATION DES TRANSACTIONS\\\\\\
            Set<Map<Variable, String>> transactions = new HashSet<>();
            List<Map<Variable, String>> transactionsList = new LinkedList<>();
            Map<Variable, String> transaction1 = new HashMap<>();
            transaction1.put(A, "1");
            transaction1.put(B, "1");
            transaction1.put(C, "1");
            transaction1.put(D, "1");
            transaction1.put(E, "1");

            Map<Variable, String> transaction2 = new HashMap<>();
            transaction2.put(A, "1");
            transaction2.put(C, "1");
            transaction2.put(B, "0");
            transaction2.put(D, "0");
            transaction2.put(E, "0");

            Map<Variable, String> transaction3 = new HashMap<>();
            transaction3.put(A, "1");
            transaction3.put(B, "1");
            transaction3.put(C, "1");
            transaction3.put(D, "1");
            transaction3.put(E, "0");

            Map<Variable, String> transaction4 = new HashMap<>();        
            transaction4.put(B, "1");
            transaction4.put(C, "1");
            transaction4.put(A, "0");
            transaction4.put(D, "0");
            transaction4.put(E, "0");

            Map<Variable, String> transaction5 = new HashMap<>();        
            transaction5.put(A, "1");
            transaction5.put(B, "1");
            transaction5.put(C, "1");
            transaction5.put(D, "0");
            transaction5.put(E, "0");

            Map<Variable, String> transaction6 = new HashMap<>();
            transaction6.put(E, "1");
            transaction6.put(A, "0");
            transaction6.put(B, "0");
            transaction6.put(C, "0");
            transaction6.put(D, "0");

            transactions.add(transaction1);
            transactions.add(transaction2);
            transactions.add(transaction3);
            transactions.add(transaction4);
            transactions.add(transaction5);
            transactions.add(transaction6);

            transactionsList.add(transaction1);
            transactionsList.add(transaction2);
            transactionsList.add(transaction3);
            transactionsList.add(transaction4);
            transactionsList.add(transaction5);
            transactionsList.add(transaction6);

            //////DETERMINATION DES ITEMSETFREQUENTS ET DES REGLES\\\\\\
            BooleanDatabase booleanDB;
            FrequentItemsetMiner frequentItemSet;
            booleanDB = new BooleanDatabase(allVariable, transactions, transactionsList);
            frequentItemSet = new FrequentItemsetMiner(booleanDB, allVariable);
            Map<Set<Variable>, Float>  frequentItemMap = frequentItemSet.frequentItemsets(args1);
            AssociationRuleMiner asso = new AssociationRuleMiner(frequentItemMap, frequentItemSet);
            System.out.println(asso.calculRules(args1, args2));
        }
        else{
            //utilisation d'une base de données
            
            //////CREATION DES VARIABLES\\\\\\
            Variable angine = new Variable("angine", "0", "1");
            Variable fievre = new Variable("fievre", "0", "1");
            Variable toux = new Variable("toux", "1", "0");
            Variable grippe = new Variable("grippe", "1", "0");
            Variable vaccination = new Variable("vaccination", "1", "0");
            Variable fatigue = new Variable("fatigue", "1", "0");
            Variable virus = new Variable("virus", "1", "0");
            Variable prise_sirop = new Variable("prise_sirop", "1", "0");
            Variable allergie_sucre = new Variable("allergie_sucre", "0", "1");
            Variable boutons = new Variable("boutons", "1", "0");
            Variable oedeme = new Variable("oedeme", "1", "0");
            Variable hypothermie = new Variable("hypothermie", "1", "0");
            
            //////CREATION DE LA LISTE DES VARIABLES\\\\\\
            Set<Variable> allVariables = new HashSet<>();
            allVariables.add(angine);
            allVariables.add(fievre);
            allVariables.add(toux);
            allVariables.add(grippe);
            allVariables.add(vaccination);
            allVariables.add(fatigue);
            allVariables.add(virus);
            allVariables.add(prise_sirop);
            allVariables.add(allergie_sucre);
            allVariables.add(boutons);
            allVariables.add(oedeme);
            allVariables.add(hypothermie);

            //////DETERMINATION DES ITEMSETFREQUENTS ET DES REGLES\\\\\\
            BooleanDatabase booleanDB2;
            FrequentItemsetMiner frequentItemSet2;
            DBReader reader = new DBReader(allVariables);
            Database db = reader.importDB("src/main/java/extraction/database/"+args3+".csv");
            booleanDB2 = new BooleanDatabase(db.getVariables(), db.getInstances(), db.getInstancesList());
            frequentItemSet2 = new FrequentItemsetMiner(booleanDB2, allVariables);
            Map<Set<Variable>, Float>  frequentItemMap2 = frequentItemSet2.frequentItemsets(args1);
            AssociationRuleMiner asso2 = new AssociationRuleMiner(frequentItemMap2, frequentItemSet2);
            Set<Rule> asso2Rules = asso2.calculRules(args1, args2);
            System.out.println(asso2Rules.size()+"   "+asso2Rules);
        }
    }
}
