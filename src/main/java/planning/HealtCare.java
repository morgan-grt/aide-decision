package planning;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import representations.*;

public class HealtCare {
    private final Set<Variable> allMALADIE;
    private final Set<Variable> allSYMPTOMES;
    
    /**
     *
     * @param allMALADIE Contient toutes les maladies.
     * @param allSYMPTOMES Contient tous les symptomes.
     */
    public HealtCare(Set<Variable> allMALADIE, Set<Variable> allSYMPTOMES){
        this.allMALADIE = allMALADIE;
        this.allSYMPTOMES = allSYMPTOMES;
    }
    
    /**
     *
     * @return Un état comprenant des Maladies et Symptomes tiré au hasard.
     */
    public State planification(){
        Set<RestrictedDomain> randSET= new HashSet<>();
        int size = allMALADIE.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for(Variable mal : allMALADIE){
            if(i==item){
                randSET.add(new RestrictedDomain(mal,"oui"));
                //                  Ajout d'une maladie, elle est ajouté directement à "oui" pour ne pas riquer d'avoir que des non.
                //                  Et donc un cas trop simple à résoudre.
            }
            i++;
        }
        
        for(Variable sym : this.allSYMPTOMES){
            int rand = new Random().nextInt(sym.getDomain().size()-1);
            i = 0;
            for(String string : sym.getDomain()){
                if(i == rand){
                    randSET.add(new RestrictedDomain(sym,"high"));
                    //              Le Symptomes est tiré au sort, mais sa valeur est automatiquement initialisée
                    //              à high, car nous n'avous pas les règles pour des symptomes différents (mais cela est possible et rapide à mettre en place);
                    //              Pour générée des valeurs aléatoire il suffit de modifier "high" par string, mais dans ce cas il faut ajouter les actions traitant tout les cas.
                }
                i++;
            }
        }
        State st = new State(randSET);
        //  On retourne l'état avec les Symptomes et maladies tiré au sort.
        return st;
    }
}
