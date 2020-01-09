package representations;

import java.util.HashSet;
import java.util.Set;

public class RestrictedDomain {
    private Variable variable;
    private Set<String> domain;

    public RestrictedDomain(Variable variable, Set<String> domain) {
        this.variable = variable;
        this.domain = domain;
    }
    
    public RestrictedDomain(Variable var, String... vals) {
        this.variable = var;
        this.domain = new HashSet<>();
        for(String val : vals)
            this.domain.add(val);
    }

    public Variable getVariable() {
        return variable;
    }

    public void setVariable(Variable variable) {
        this.variable = variable;
    }

    public Set<String> getDomain() {
        return domain;
    }

    public void setDomain(Set<String> domain) {
        this.domain = domain;
    }
    
    public Set<String> getDomainCP(){
        return new HashSet<String>(domain);
    }
    
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        RestrictedDomain rD = (RestrictedDomain) object;
        if (this.variable.equals(rD.variable)
                && this.getDomain().containsAll(rD.getDomain())){
            return true;
        }
        return false;
    }
        
    @Override
    public String toString(){
        return variable.getName();// + "= " + domain.toString();
    }
}
