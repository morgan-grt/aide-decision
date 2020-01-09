package representations;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Variable {
    private String name;
    private Set<String> domain;

    public Variable(String name, Set<String> domain) {
        this.name = name;
        this.domain = domain;
    }
    
    
    public Variable(String name, String... vals) {
        this.name = name;
        this.domain = new HashSet<>();
        for(String val : vals)
            this.domain.add(val);
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getDomain() {
        return domain;
    }

    public void setDomain(Set<String> domain) {
        this.domain = domain;
    }
    
    @Override
    public String toString(){
        return name;
    }
    
    public Set<String> copyDomains(){
        return new HashSet<>(domain);
    }
    
    @Override
    public boolean equals(Object object){
        if (this == object){
            return true;
        }
        Variable var = (Variable) object;
        if (this.getName().equals(var.getName()) 
                && this.getDomain().containsAll(var.getDomain())){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.name);
        hash = 13 * hash + Objects.hashCode(this.domain);
        return hash;
    }
    
    
    
}
