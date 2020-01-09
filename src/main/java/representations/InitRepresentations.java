package representations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import ppc.Backtracking;

public class InitRepresentations {

    /**
     * @param args the command line arguments
     */
    public void init(String[] args) {
        
        //////CREATION DES VARIABLES\\\\\\
        Variable ANGINE = new Variable("ANGINE", "NON", "OUI");
        Variable FIEVRE = new Variable("FIEVRE", "NON", "MOYENNE", "HAUTE");
        Variable TOUX = new Variable("TOUX", "OUI", "NON");
        Variable GRIPPE = new Variable("GRIPPE", "OUI", "NON");
        Variable VACCINATION = new Variable("VACCINATION", "OUI", "NON");
        Variable FATIGUE = new Variable("FATIGUE", "OUI", "NON");
        Variable VIRUS = new Variable("VIRUS", "OUI", "NON");
        Variable SIROP = new Variable("SIROP", "OUI", "NON");
        Variable ALLERGIE_SUCRE = new Variable("ALLERGIE_SUCRE", "NON", "MOYENNE", "HAUTE");
        Variable BOUTONS = new Variable("BOUTONS", "OUI", "NON");
        Variable OEDEME = new Variable("OEDEME", "OUI", "NON");
        Variable HYPOTHERMIE = new Variable("HYPOTHERMIE", "OUI", "NON");
        
        //////CREATION DE LA LISTE DES VARIABLES\\\\\\
        Set<Variable> allVariables = new HashSet<>();
        allVariables.add(ANGINE);
        allVariables.add(FIEVRE);
        allVariables.add(TOUX);
        allVariables.add(GRIPPE);
        allVariables.add(VACCINATION);
        allVariables.add(FATIGUE);
        allVariables.add(VIRUS);
        allVariables.add(SIROP);
        allVariables.add(ALLERGIE_SUCRE);
        allVariables.add(BOUTONS);
        allVariables.add(OEDEME);
        allVariables.add(HYPOTHERMIE);
        
        //////CREATION DE LA LISTE DES VARIABLES SELON L'EXEMPLE FIL ROUGE\\\\\\
        Set<Variable> fourConstraintsVariables = new HashSet<>();
        fourConstraintsVariables.add(ANGINE);
        fourConstraintsVariables.add(FIEVRE);
        fourConstraintsVariables.add(TOUX);
        fourConstraintsVariables.add(GRIPPE);
        fourConstraintsVariables.add(VACCINATION);
        fourConstraintsVariables.add(FATIGUE);
        
        //////CREATION DES REGLES\\\\\\
        Set<RestrictedDomain> premisse_fievre = new HashSet<>();
        premisse_fievre.add(new RestrictedDomain(ANGINE, "OUI"));       
        Set<RestrictedDomain> conclusion_fievre = new HashSet<>();
        conclusion_fievre.add(new RestrictedDomain(FIEVRE, "HAUTE"));
        conclusion_fievre.add(new RestrictedDomain(FIEVRE, "MOYENNE"));        
        Rule rule_fievre = new Rule(premisse_fievre, conclusion_fievre);
        
        
        Set<RestrictedDomain> premisse_toux = new HashSet<>();
        premisse_toux.add(new RestrictedDomain(ANGINE, "OUI"));       
        Set<RestrictedDomain> conclusion_toux = new HashSet<>();
        conclusion_toux.add(new RestrictedDomain(TOUX, "OUI"));
        Rule rule_toux = new Rule(premisse_toux, conclusion_toux);
        
        
        Set<RestrictedDomain> premisse_grippe_fievre = new HashSet<>();
        premisse_grippe_fievre.add(new RestrictedDomain(GRIPPE, "OUI"));
        premisse_grippe_fievre.add(new RestrictedDomain(VACCINATION, "NON"));  
        Set<RestrictedDomain> conclusion_grippe_fievre = new HashSet<>();
        conclusion_grippe_fievre.add(new RestrictedDomain(FIEVRE, "HAUTE"));
        Rule rule_grippe_fievre = new Rule(premisse_grippe_fievre, conclusion_grippe_fievre);
        
        
        Set<RestrictedDomain> premisse_grippe_fatigue = new HashSet<>();
        premisse_grippe_fatigue.add(new RestrictedDomain(GRIPPE, "OUI"));
        premisse_grippe_fatigue.add(new RestrictedDomain(VACCINATION, "NON"));  
        Set<RestrictedDomain> conclusion_grippe_fatigue = new HashSet<>();
        conclusion_grippe_fatigue.add(new RestrictedDomain(FATIGUE, "OUI"));
        Rule rule_grippe_fatigue = new Rule(premisse_grippe_fatigue, conclusion_grippe_fatigue);
        
        
        //////BACKTRACK\\\\\\
        Set<Constraint> constraints;
        Backtracking search;
        Map<Variable, String> solution;
        Map<Variable, String> assign = new HashMap<>();
        Set<Map<Variable, String>> allSolutions;
        

        constraints = new HashSet<>();
        constraints.add(rule_fievre);
        constraints.add(rule_toux);
        constraints.add(rule_grippe_fievre);
        constraints.add(rule_grippe_fatigue);
        search = new Backtracking(fourConstraintsVariables, constraints);

        allSolutions = search.allSolutions();
        System.out.println("\n\nNombre de solutions = " + allSolutions.size());
        System.out.println("rule :   " + rule_fievre + "\n");
        for (Map<Variable, String> sol : allSolutions)
                System.out.println(sol);
    }
    
}
