/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.gameEvents.GameEvent;
import java.util.ArrayList;
import textgame.structure.actions.Action;
import textgame.structure.gameEvents.GameEvent.GameEventType;
import textgame.structure.actions.ThrowGameEvent;
import textgame.structure.gameEvents.GameEventListenerActed;

/**
 *
 * @author David Brož
 */
public class GameEventListener implements java.io.Serializable {

    public GameEventType expectedEventType;
    private int id;
    private String name;
    private boolean enabled = true;
    private ArrayList<Action> actions;
    private ArrayList referencedBy;
    private Object expectedValue;

    
    public GameEventListener(String name,GameEventType expectedvEventType, String expectedValue, Action ...actionsToDo) {
        referencedBy=new ArrayList();
        this.name = name;
        id=Game.getInstance().getGameEventListenerMaxID();
        Game.getInstance().incGameEventListenerMaxID();
        this.actions= new ArrayList();
        for(Action a : actionsToDo){
            this.actions.add(a);
        }
        this.expectedEventType=expectedvEventType;
        this.expectedValue=expectedValue;
    }
    void listen(GameEvent gameEvent) {
        if(!enabled)return;
        
        System.out.println("EVENT-LISTENER: Listening to: Type("+ gameEvent.getEventType().toString()+") Value("+gameEvent.getValue()+")");
        System.out.println("EVENT-LISTENER: Expecting: Type("+ expectedEventType.toString()+") Value("+expectedValue+")");
        
        if(gameEvent.getEventType()==expectedEventType && gameEvent.getValue().equals(expectedValue)){
            System.out.println("EVENT-LISTENER: Game event match found.");
            act();
        }
    }

    public void enable() {
        enabled=true;
    }
     public void disable() {
        enabled=false;
    }
    
     @Override
    public String toString(){
        return id + "_" + name; 
    }

    public GameEventType getExpectedEventType() {
        return expectedEventType;
    }

    public void setExpectedvEventType(GameEventType expectedvEventType) {
        this.expectedEventType = expectedvEventType;
    }

    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ArrayList<Action> getActions() {
        checkActionValidity();
        return actions;
    }

    public Object getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(Object expectedValue) {
        this.expectedValue = expectedValue;
    }

    public void addAction(Action actionToAdd) {
        actions.add(actionToAdd);
    }
    
     public void deleteAction(Action actionToDelete) {
        actions.remove(actionToDelete);
    }

    private void act() {
        Game.getInstance().throwGameEvent(new GameEventListenerActed(this));
        for(Action a : actions){
                a.act();
            }
    }
    
     public void deleteAllReferencesToThis() {
        for (Object o : referencedBy) {
            if(o instanceof Action){
                Action a =(Action) o;
                a.setValidity(false);
            }
        }
    }

    void removeIsReferencedBy(Object o) {
        if(referencedBy.contains(o))referencedBy.remove(o);
        else System.out.println("-GAME-EVENT-LISTENER-: Referenced by was atempted to be removed but there was not isntance of: "+o.toString() );
    }

    void addIsReferencedBy(Object o) {
        referencedBy.add(o);
    }
    
    
    private void checkActionValidity() {
        for (Action action : actions) {
            if(!action.isValid())actions.remove(action);
        }
    }
}
