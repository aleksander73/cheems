package aleksander73.cheems.adt;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StateMachine {
    private Map<String, State> states = new HashMap<>();
    private State currentState;

    private final boolean[][] transitions;
    private final Runnable[][] onTransition;

    public StateMachine(Set<String> stateNames, String currentState) {
        int n = 0;
        for(String name : stateNames) {
            State state = new State(n++, name);
            states.put(name, state);
        }

        this.currentState = this.getState(currentState);

        transitions = new boolean[n][n];
        onTransition = new Runnable[n][n];
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                onTransition[i][j] = new Runnable() {
                    @Override
                    public void run() {}
                };
            }
        }
    }

    public void update() {
        currentState.action.run();
    }

    public void reset() {}

    public String currentState() {
        return currentState.name;
    }

    public void changeState(String target) throws IllegalStateException {
        State targetState = this.getState(target);
        boolean transitionExists = transitions[currentState.id][targetState.id];
        if(transitionExists) {
            currentState.onExit.run();
            onTransition[currentState.id][targetState.id].run();
            currentState = targetState;
            currentState.onEnter.run();
        } else {
            throw new IllegalStateException("error: Transition from " + this.currentState() + " to " + target + " doesn't exist!");
        }
    }

    private State getState(String name) {
        return states.get(name);
    }

    public void setOnEnter(String stateName, Runnable action) {
        this.getState(stateName).onEnter = action;
    }

    public void setAction(String stateName, Runnable action) {
        this.getState(stateName).action = action;
    }

    public void setOnExit(String stateName, Runnable action) {
        this.getState(stateName).onExit = action;
    }

    public void enableTransition(String source, String target) {
        transitions[this.getState(source).id][this.getState(target).id] = true;
    }

    public void setOnTransitionHandler(String source, String target, Runnable action) {
        onTransition[this.getState(source).id][this.getState(target).id] = action;
    }

    // --------------------------------------------------

    private class State {
        private final int id;
        private final String name;

        private Runnable onEnter = new Runnable() {
            @Override
            public void run() {}
        };

        private Runnable action = new Runnable() {
            @Override
            public void run() {}
        };
        
        private Runnable onExit = new Runnable() {
            @Override
            public void run() {}
        };

        public State(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
