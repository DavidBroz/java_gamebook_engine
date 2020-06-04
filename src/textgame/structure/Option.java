/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.actions.Action;
import java.util.ArrayList;
import textgame.structure.gameEvents.GameEvent;

/**
 *
 * @author David Brož
 */
public class Option implements java.io.Serializable{
    private int id;
    private String optionLabel;
    private ArrayList<Action> actionList;
    private ArrayList referencedBy;

    public Option(String message) {
        referencedBy=new ArrayList<Object>();
        id=Game.getInstance().getOptionMaxID();
        Game.getInstance().incOptionMaxID();
        this.optionLabel = message;
        this.actionList = new ArrayList();
    }
    
    public void runActions(){
        Game.getInstance().throwGameEvent(new GameEvent(new Object[]{this},GameEvent.GameEventType.OPTION_SELECTED));
        System.out.println("-OPTION-:THREW EVENT ACTED");
        for(Action a: actionList){
            a.act();
        }
    }
    
    public void setLabel(String newLabel){
        optionLabel = newLabel;
    }
    
    public void addAction(Action actionToAdd){
        actionList.add(actionToAdd);
    }
    public void removeAction(Action actionToRemove){
        actionList.remove(actionToRemove);
    }

    public int getId() {
        return id;
    }

    public String getOptionLabel() {
        return optionLabel;
    }

    public ArrayList<Action> getActionList() {
        checkActionValidity();
        return actionList;
    }

    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }
    
    @Override
    public String toString() {
        return ""+id+"_"+optionLabel;
    }

    public void deleteAction(Action action) {
        actionList.remove(action);
    }

    void removeReferencedBy(Object o) {
        if(referencedBy.contains(o))referencedBy.remove(o);
        else System.out.println("-OPTION-:Object " + o + " was attemed to be removed from IsReferencedBy of" + toString() + " but was not found!");
    }
    
    public void deleteAllReferencesToThis() {
        for (Object o : referencedBy) {
            if (o instanceof Room) {
                Room r = (Room) o;                
                r.removeOption(this,false);
            } else if (o instanceof Player) {
                Player pl = (Player) o;                
                pl.removeOption(this,false);
            }if(o instanceof Action){
                Action a =(Action) o;
                a.setValidity(false);
            }
        }
    }

    void addReferencedBy(Object o) {
        referencedBy.add(o);
    }

    private void checkActionValidity() {
        for (Action action : actionList) {
            if(!action.isValid())actionList.remove(action);
        }
    }
    
    
    
    }    
