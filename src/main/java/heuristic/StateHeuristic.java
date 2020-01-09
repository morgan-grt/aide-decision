package heuristic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import planning.State;
import representations.RestrictedDomain;

public class StateHeuristic {
    private Map<String,Integer> val;
    
    
    
    public StateHeuristic(){
        this.val = new HashMap<>();
        this.val.put("oui",4);
        this.val.put("non", 0);
        this.val.put("high",4);
        this.val.put("medium",2);
        this.val.put("low",1);
        this.val.put("none",0);
    }
    
    public int getHeuristic(State state){
        int heur = 0;
        Set<RestrictedDomain> rdlist = state.getVar();
        for(RestrictedDomain rd : rdlist){
            heur += val.get(rd.getDomain().iterator().next());
        }
        return heur;
    }
    
    
}
