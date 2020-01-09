package planning;
import java.util.HashSet;
import java.util.Set;
import representations.*;

public class State {
    private Set<RestrictedDomain> var;

    public State(Set<RestrictedDomain> var) {
        this.var = var;
    }
    
    public State(RestrictedDomain... vars){
        this.var = new HashSet<>();
        for(RestrictedDomain vari : vars)
            this.var.add(vari);
    }
    
    public Set<RestrictedDomain> getVar() {
        return var;
    }
    
    public void setVar(RestrictedDomain rd){
        for(RestrictedDomain rdvar : this.var){
            if(rdvar.getVariable().getName().equals(rd.getVariable().getName())){
                rdvar.setDomain(rd.getDomain());
            }
        }
    }
    
    
    public boolean satisfies(State partial_state){
        if(partial_state.getVar().isEmpty()){
            return true;
        }
        for(RestrictedDomain part_var : partial_state.getVar()){
            if(!containsCust(part_var)){
                return false;
            }
        }
        return true;
    }

    public State apply(Action action){
        State stateCP = this.getCP();
        if(action.is_applicable(stateCP)){
                if(satisfies(action.pre())){
                    for(RestrictedDomain ef : action.getEff()){
                        stateCP.setVar(ef);
                    }
                }
            
        }
        return stateCP;
    }
    
    public State getCP(){
        Set<RestrictedDomain> setCP = new HashSet<>();
        for(RestrictedDomain rd : var){
            setCP.add(new RestrictedDomain(new Variable(rd.getVariable().getName(),rd.getDomainCP()),rd.getDomainCP()));
        }
        return new State(setCP);
    }
    
    public boolean containsCust(RestrictedDomain partial){
        for (RestrictedDomain rd : var){
            if (rd.getVariable().getName().equals(partial.getVariable().getName())){
                return true;
            }
        }
        return false;
    }

    public boolean satisfiesFull(State state){
        for(RestrictedDomain part_var : state.getVar()){
            if(!containsFull(part_var)){
                return false;
            }
        }
        return true;
    }

    public boolean containsFull(RestrictedDomain partial){
        for (RestrictedDomain rd : var){
            if (rd.getVariable().getName().equals(partial.getVariable().getName()) && rd.getDomain().iterator().next().equals(partial.getDomain().iterator().next())){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean equals(Object o){
        return satisfiesFull((State)o);
    }
    
    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString(){
        String str = "";
        for(RestrictedDomain rd : var){
            str+= rd.toString() + " ; ";
        }
        return str;
    }

}
