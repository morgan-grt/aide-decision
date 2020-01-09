package representations;
import java.util.Map;
import java.util.Set;

public interface Constraint {
    public Set<Variable> getScope();
    public boolean isSatisfiedBy(Map<Variable, String> map);
    public Set<RestrictedDomain> getPremisse();
    public boolean filter(Map<Variable, String> ass_part, Map<Variable, Set<String>> var_non_ass_domain);
}
