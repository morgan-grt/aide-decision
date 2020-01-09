package planning;

import heuristic.*;
import java.util.Deque;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.LinkedList;

public class PlanningProblem {

    private State initial_state;
    private State final_State;
    private Set<Action> actions;

    public PlanningProblem(State initial_state, State final_State, Set<Action> actions) {
        this.initial_state = initial_state;
        this.final_State = final_State;
        this.actions = actions;
    }

    /**
     *
     * @param state L'état initial du problème.
     * @param plan Contient la liste d'action permettant d'atteindre le but voulu.
     * @param closed Les états déjà parcouru.
     * @return Soit fait un appel recursif sur elle même avec l'état suivant, soit retourne le plan contenant les actions à réaliser pour atteindre le but.
     */
    public Deque<Action> DFS(State state, Deque plan, Deque closed) {
        if (final_State.satisfiesFull(state)) { // Si state ("état actuel appelé par l'appel recursif), est équivalent au but;
            return plan;                 
        } else {
            for (Action act : actions) {        // On parcours la liste de toutes les actions possible.
                if (act.is_applicable(state)) { // Si elle est applicable dans l'état actuel alors:
                    State next = state.apply(act);
                    if (!contains(closed, next)) {// Si le nouvel état n'est pas dans closed alors:
                        plan.push(act);         // Ajout de l'action;
                        closed.push(next);      // Ajout dans closed;
                        Deque subplan = DFS(next, plan, closed);    // Appel recursif avec le nouvel état généré.
                        if (subplan != null) {
                            return subplan;
                        } else {
                            plan.pop(); // Si supblan vaut nul, cela veut dire que les appels récursifs n'ont rien donné, on retire donc l'actoin que l'on vient d'appliquer.
                        }

                    }
                }
            }
            return null;
        }
    }

    /**
     *
     * @param state Etat initial dont on veut trouver une liste d'actions pour atteindre un But.
     * @return  Retourne Map contenant les différents états et l'action correspondant pour y arriver.
     */
    public Map<State, Action> BFS(State state) {
        Map<State, State> father = new HashMap<>();
        Map<State, Action> plan = new HashMap<>();
        Deque<State> open = new LinkedList<>(); 
        open.push(state.getCP());
        Deque<State> closed = new LinkedList<>();

        while (!open.isEmpty()) { 
            state = open.pollFirst();   //    On prend l'état ce trouvant la Open, et on le retire de celle ci.
            closed.push(state);         //    On ajoute cette état à la liste des états parcourus.
            for (Action act : actions) {//    Parcours toutes les actions possibles;
                if (act.is_applicable(state)) { // Si applicable alors:
                    State next = state.apply(act);  // On l'applique dans un nouvel état.
                    if (!contains(closed, next) && !contains(open, next)) { //Vérifie si elle n'est pas dans Open ni dans Closed.
                        father.put(next, state);    
                        plan.put(next, act);
                        if (final_State.satisfiesFull(next)) {
                            return get_bfs_plan(father, plan, next); // L'état actuel est équivalent au But, alors appèle la fonction qui retourne le plan.
                        } else {
                            open.push(next);// Si n'est pas équivalent au But alors on continu.
                        }
                    }
                }
            }
        }
        return null; 
    }

    /**
     *
     * @param father Permet de relié deux état Key:Etat initial, Value:Etat avec action appliqué
     * @param action Contient Un état, et l'action pour y arrivé
     * @param goal Etat But.
     * @return Retourne Map contenant les différents états et l'action correspondant pour y arriver.
     */
    public Map<State, Action> get_bfs_plan(Map<State, State> father, Map<State, Action> action, State goal) {
        Map<State, Action> result = new HashMap<>();
        State goalTMP = goal.getCP();
        Boolean bool = true;
        while (bool) {
            for (Map.Entry val : father.entrySet()) {
                if (goalTMP.satisfiesFull((State) val.getKey())) {
                    for (Map.Entry val2 : action.entrySet()) {
                        if (val2.getKey() == val.getKey()) { // Retrouve l'état contenu dans father et dans action.
                            result.put((State) val.getValue(), (Action) val2.getValue());  // On ajout l'état et l'action pour y parvenir.
                            goalTMP = (State) val.getValue(); // On réactualise pour rechercher la prochaine action.
                        }
                    }
                }
            }
            for (Map.Entry act : result.entrySet()) {
                if (initial_state.equals(act.getKey())) { // On arrive à l'état final.
                    bool = false;
                }
            }
        }
        return result;
    }

    /**
     *
     * @param state Etat initial
     * @return Retourne Map contenant les différents états et l'action correspondant pour y arriver.
     */
    public Map<State, Action> dijkstra(State state) {
        State stateCP = state.getCP();
        Map<State, State> father = new HashMap<>();
        father.put(stateCP, null);
        Map<State, Action> plan = new HashMap<>();
        Deque<State> goals = new LinkedList<>();
        Deque<State> open = new LinkedList<>();
        open.push(stateCP);
        Map<State, Integer> distance = new HashMap<>();
        distance.put(stateCP, 0);
        sortByCost(); // On trie la Liste des Actions par couts.
        while (!open.isEmpty() && !distance.isEmpty()) {
            state = getMinState(distance, open); // On récupère l'état ayant la plus petite distance.
            if (final_State.satisfiesFull(state)) { // On vérifie si ce n'est pas la solution:
                goals.push(state);  // Si oui on l'ajoute à la liste des résultats satisfaisant.
            }
            for (Action act : actions) { // Parcours toutes les Actions possibles.
                if (act.is_applicable(state)) {
                    State next = state.apply(act);
                    if (!distance.containsKey(next)) { // Si le nouvel état n'est pas encore dans la liste,
                        distance.put(next, 999999);    // Alors on l'ajoute avec la valeur la plus haute possible, on la modifie ensuite.
                    }
                    if (distance.get(next) > distance.get(state) + act.getCost()) { // Si dans la liste nous avons déja l'état actuel et que nous avons trouvé une distance plus courte.
                        distance.put(next, distance.get(state) + act.getCost());
                        plan.put(next, act);
                        father.put(next, state);                // Alors on l'ajoute.
                        open.push(next);
                    }
                }
            }
            distance.remove(state); // On retire cette état car nous avons tout parcouru pour celui-ci.
        }
        return get_dijkstra(father, plan, goals, state); // Retourne le chemin le plus court pour atteindre le but.
    }

    /**
     *
     * @param state Etat initial
     * @return Retourne Map contenant les différents états et l'action correspondant pour y arriver. 
     */
    public Map<State, Action> AStar(State state) {
        State stateCP = state.getCP();
        Map<State, State> father = new HashMap<>();
        father.put(stateCP, null);
        Map<State, Action> plan = new HashMap<>();
        Deque<State> open = new LinkedList<>();
        open.push(stateCP);
        Map<State, Integer> distance = new HashMap<>();
        distance.put(stateCP, 0);
        sortByCost(); // On trie la Liste des Actions par couts.
        Map<State, Integer> value = new HashMap<>();
        StateHeuristic heuristic = new StateHeuristic();

        value.put(stateCP, heuristic.getHeuristic(stateCP));
        while (!open.isEmpty()) {
            state = getMinState(value, open);  // On récupère l'état ayant la plus petite valeur(distance+cout).
            if (final_State.satisfiesFull(state)) { // On vérifie si ce n'est pas la solution:
                return get_bfs_plan(father, plan, state); // On utilise la même méthoque que pour BFS pour retourner le plan.
            } else {
                open.pop(); // Si ce n'est pas le cas on le retire d'open.
            }
            for (Action act : actions) { // Parcours toutes les actions possibles.
                if (act.is_applicable(state)) { // Vérifie si elle est applicable.
                    State next = state.apply(act);
                    if (!distance.containsKey(next)) { // Si elle n'est pas dans notre liste alors:
                        distance.put(next, 999999); // On l'ajoute avec la valeur maximale possible.
                    }
                    if (distance.get(next) > distance.get(state) + act.getCost()) {
                        distance.put(next, distance.get(state) + act.getCost());
                        value.put(next, distance.get(next) + heuristic.getHeuristic(next));
                        plan.put(next, act);
                        father.put(next, state);
                        open.push(next);
                    }
                }
            }
            distance.remove(state);// On retire cette état car nous avons tout parcouru pour celui-ci.
        }
        return null;
    }

    public Map<State, Action> get_dijkstra(Map<State, State> father, Map<State, Action> action, Deque<State> goals, State goal) {
        Map<State, Action> result = new HashMap<>();
        State goalTMP = goal.getCP();
        int cost = 9999999;
        while (!goals.isEmpty()) {
            Map<State, Action> searchR = new HashMap<>(); // On créer un resultat pour chaque solution ce trouvant dans goals.
            int costR = 0;
            goalTMP = goals.pop();
            while (goalTMP != null) {
                if (action.get(goalTMP) != null) {
                    searchR.put(goalTMP, action.get(goalTMP));  // On ajoute l'état et l'action pour y arrivé.
                    costR += action.get(goalTMP).getCost(); // Ajoute le couts pour chaque action réalisé.
                }
                goalTMP = father.get(goalTMP);
            }
            if (costR < cost) { // On vérifie si ce resultat est plus petit que le précédent,
                result = searchR; // Si c'est le cas alors c'est la solution.
                cost = costR;
            }
        }
        return result;
    }

    public State getMinState(Map<State, Integer> distance, Deque<State> open) {
        int cost = 9999999;
        State result = null;
        for (Map.Entry val : distance.entrySet()) {
            if ((int) val.getValue() < cost && contains(open, (State) val.getKey())) {
                result = (State) val.getKey();
                cost = (int) val.getValue();
            }
        }
        return result;
    }

    public void sortByCost() {
        Set<Action> sorted = new LinkedHashSet<>();
        Set<Action> initial_actions = new HashSet<>(this.actions);
        while (!initial_actions.isEmpty()) {
            int cost = 9999999;
            Action actTmp = null;
            for (Action act : initial_actions) {
                if (act.getCost() < cost) {
                    cost = act.getCost();
                    actTmp = act;
                }
            }
            sorted.add(actTmp);
            initial_actions.remove(actTmp);
        }
        this.actions = sorted;
    }

    public boolean contains(Deque<State> closed, State state) {
        Deque<State> tmp = new LinkedList<>();
        boolean bool = false;
        while (!closed.isEmpty()) {
            State actTmp = closed.pollFirst();
            tmp.push(actTmp);
            if (actTmp.satisfiesFull(state)) {
                bool = true;
            }
        }
        while (!tmp.isEmpty()) {
            closed.push(tmp.pollFirst());
        }

        return bool;
    }

}
