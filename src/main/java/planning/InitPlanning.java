package planning;

import java.util.HashSet;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Set;
import java.util.Map;
import representations.RestrictedDomain;
import representations.Variable;

public class InitPlanning {
    
    public void init(String[] args) {
 
        //////////////// MALADIE ////////////////////
        Variable ANGINA = new Variable("ANGINA", "oui", "non");
        Variable FLU = new Variable("FLU", "oui", "non");
        Variable POX = new Variable("POX", "oui", "non");
        Variable PLAGUE = new Variable("PLAGUE", "oui", "non");
    
        Set<Variable> allMALADIE = new HashSet<>();
        allMALADIE.add(ANGINA);
        allMALADIE.add(FLU);
        allMALADIE.add(POX);
        allMALADIE.add(PLAGUE);
        
        //////////////// SYMPTOMES //////////////////
        Variable FEVER = new Variable("FEVER", "high", "medium","low","none");
        Variable COUGH = new Variable("COUGH", "high", "medium","low","none");
        Variable BUTTONS = new Variable("BUTTONS", "high", "medium","low","none");
        
        Set<Variable> allSYMPTOMES = new HashSet<>();
        allSYMPTOMES.add(FEVER);
        allSYMPTOMES.add(COUGH);
        allSYMPTOMES.add(BUTTONS);
        
        //////////////// SYRUP BUTTONS ///////////////
        Set<RestrictedDomain> premisse = new HashSet<>();
        premisse.add(new RestrictedDomain(BUTTONS,"high"));
        Set<RestrictedDomain> effects = new HashSet<>();
        effects.add(new RestrictedDomain(BUTTONS,"none"));
        Action SYRUP_BUTTONS_HIGH = new Action(null,effects,1);
        //////////////////////////////////////////////

        //////////////// SYRUP COUGH ///////////////
        Set<RestrictedDomain> premiCOUGH = new HashSet<>();
        premiCOUGH.add(new RestrictedDomain(COUGH,"high"));
        Set<RestrictedDomain> effCOUGH = new HashSet<>();
        effCOUGH.add(new RestrictedDomain(COUGH,"none"));
        Action SYRUP_COUGH_HIGH = new Action(null,effCOUGH,1);
        //////////////////////////////////////////////
        
        //////////////// SYRUP FEVER ///////////////
        Set<RestrictedDomain> premiFEVER = new HashSet<>();
        premiFEVER.add(new RestrictedDomain(FEVER,"high"));
        Set<RestrictedDomain> effFEVER = new HashSet<>();
        effFEVER.add(new RestrictedDomain(FEVER,"none"));
        Action SYRUP_FEVER_HIGH = new Action(null,effFEVER,1);
        //////////////////////////////////////////////

        ////////////// HEAL ANGINA //////////////////
        Set<RestrictedDomain> preANG = new HashSet<>();
        preANG.add(new RestrictedDomain(BUTTONS,"none"));
        preANG.add(new RestrictedDomain(FEVER,"none"));
        preANG.add(new RestrictedDomain(COUGH,"none"));
        Set<RestrictedDomain> effANG = new HashSet<>();
        effANG.add(new RestrictedDomain(ANGINA,"non"));
        Action HEAL_ANGINA = new Action(preANG,effANG);
        ///////////////////////////////////////////////
        
        ////////////// HEAL FLU //////////////////
        Set<RestrictedDomain> preFLU = new HashSet<>();
        preFLU.add(new RestrictedDomain(BUTTONS,"none"));
        preFLU.add(new RestrictedDomain(FEVER,"none"));
        preFLU.add(new RestrictedDomain(COUGH,"none"));
        Set<RestrictedDomain> effFLU = new HashSet<>();
        effFLU.add(new RestrictedDomain(FLU,"non"));
        Action HEAL_FLU = new Action(preFLU,effFLU);
        ///////////////////////////////////////////////

        ////////////// HEAL POX //////////////////
        Set<RestrictedDomain> prePOX = new HashSet<>();
        prePOX.add(new RestrictedDomain(BUTTONS,"none"));
        prePOX.add(new RestrictedDomain(FEVER,"none"));
        prePOX.add(new RestrictedDomain(COUGH,"none"));
        Set<RestrictedDomain> effPOX = new HashSet<>();
        effPOX.add(new RestrictedDomain(POX,"non"));
        Action HEAL_POX = new Action(prePOX,effPOX);
        ///////////////////////////////////////////////

        ////////////// HEAL PLAGUE //////////////////
        Set<RestrictedDomain> prePLAGUE = new HashSet<>();
        prePLAGUE.add(new RestrictedDomain(BUTTONS,"none"));
        prePLAGUE.add(new RestrictedDomain(FEVER,"none"));
        prePLAGUE.add(new RestrictedDomain(COUGH,"none"));
        Set<RestrictedDomain> effPLAGUE = new HashSet<>();
        effPLAGUE.add(new RestrictedDomain(PLAGUE,"non"));
        Action HEAL_PLAGUE = new Action(prePLAGUE,effPLAGUE);
        ///////////////////////////////////////////////
        
        
        ////////////// MEDECINE EXP /////////////// 
        Action MEDECINE_A = new Action(null,effPOX,1);
        Action MEDECINE_B = new Action(null,effPLAGUE,1);
        Action MEDECINE_C = new Action(null,effFLU,1);
        Action MEDECINE_D = new Action(null,effANG,1);
        ///////////////////////////////////////////////
        
        /////////////// SOIGNER /////////////////////
        Set<RestrictedDomain> allOFF = new HashSet<>();
        allOFF.add(new RestrictedDomain(FEVER,"none"));
        allOFF.add(new RestrictedDomain(BUTTONS,"none"));
        allOFF.add(new RestrictedDomain(COUGH,"none"));
        allOFF.add(new RestrictedDomain(POX,"non"));
        allOFF.add(new RestrictedDomain(PLAGUE,"non"));
        allOFF.add(new RestrictedDomain(FLU,"non"));
        allOFF.add(new RestrictedDomain(ANGINA,"non"));
        Action ButState = new Action(null,allOFF);
        ///////////////////////////////////////////////
        
        Set<RestrictedDomain> allOFF1 = new HashSet<>();
        Set<RestrictedDomain> allOFF2 = new HashSet<>();
        allOFF1.add(new RestrictedDomain(FEVER,"none"));
        allOFF1.add(new RestrictedDomain(BUTTONS,"none"));
        allOFF1.add(new RestrictedDomain(COUGH,"none"));
        allOFF2.add(new RestrictedDomain(POX,"non"));
        allOFF2.add(new RestrictedDomain(PLAGUE,"non"));
        allOFF2.add(new RestrictedDomain(FLU,"non"));
        allOFF2.add(new RestrictedDomain(ANGINA,"non"));
        Action ButState1 = new Action(null,allOFF1);
        Action ButState2 = new Action(null,allOFF2);
        
        Set<Action> allActions = new HashSet<>();
        allActions.add(ButState1);
        allActions.add(ButState2);
        allActions.add(SYRUP_BUTTONS_HIGH);
        allActions.add(MEDECINE_A);
        allActions.add(MEDECINE_B);
        allActions.add(MEDECINE_C);
        allActions.add(MEDECINE_D);
        allActions.add(HEAL_PLAGUE);
        allActions.add(HEAL_POX);
        allActions.add(HEAL_FLU);
        allActions.add(HEAL_ANGINA);
        allActions.add(SYRUP_FEVER_HIGH);
        allActions.add(SYRUP_COUGH_HIGH);


        
        
        HealtCare hc = new HealtCare(allMALADIE,allSYMPTOMES);
        
        State malade = hc.planification();
        System.out.println(malade.getVar());
        Deque<Action> plan = new LinkedList<>();
        Deque<State> closed = new LinkedList<>();

        PlanningProblem pb = new PlanningProblem(malade,malade.apply(ButState),allActions);
        
        Deque actions = pb.DFS(malade,plan,closed);
        System.out.println("#######################");
        System.out.println("Result from DFS");
        while(!actions.isEmpty()){
            Object act = actions.pollFirst();
            System.out.println(act);
        }

        
        
        System.out.println("#######################");
        Map<State,Action> actions2 = pb.BFS(malade);
        System.out.println("Result from BFS");
        for(Map.Entry val : actions2.entrySet()){
            System.out.println(val.getValue());
        }

        System.out.println("########################");
        Map<State,Action> actions3 = pb.dijkstra(malade);
        System.out.println("Result from dijkstra");
        for(Map.Entry val : actions3.entrySet()){
            System.out.println(val.getValue());
        }
        
        System.out.println("########################");
        Map<State,Action> actions4 = pb.AStar(malade);
        System.out.println("Result from Astar");
        for(Map.Entry val : actions4.entrySet()){
            System.out.println(val.getValue());
        }
    }
}
