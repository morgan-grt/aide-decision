package planning;

import java.util.Set;
import representations.*;
import java.util.HashSet;

public class Action {
    private Set<RestrictedDomain> precondition;
    private Set<RestrictedDomain> effets;
    private int cost;

    public Action(Set<RestrictedDomain> precondition, Set<RestrictedDomain>  effets) {
       this.precondition = precondition;
       this.effets = effets;
       if(precondition == null){
           this.cost = 10*effets.size();
       }else{
       this.cost = effets.size();
       }
    }
    
    public Action(Set<RestrictedDomain> precondition, Set<RestrictedDomain>  effets,int cost) {
       this.precondition = precondition;
       this.effets = effets;
       this.cost = cost;
    }
    
    public int getCost(){
        return this.cost;
    }
    
    public State pre(){
        if(precondition == null){
            return new State(new HashSet<>());
        }
        return new State(precondition);
    }
    
    public Set<RestrictedDomain> getPre(){
        return this.precondition;
    }
    
    public Set<RestrictedDomain> getEff(){
        return this.effets;
    }
    
    public boolean is_applicable(State state){
        if(precondition == null){
            return true;
        }
        for(RestrictedDomain rd : precondition){
            if(state.satisfiesFull(pre())){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        String str = "";
        for(RestrictedDomain rd : effets){
            str += rd.toString() + " ";
        }
        return str;
    }
    
}
