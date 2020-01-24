/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.actions.Action;
import java.util.ArrayList;
import textgame.structure.gameEvents.OptionSelected;

/**
 *
 * @author David Brož
 */
public class Option implements java.io.Serializable{
    private int id;
    private String optionLabel;
    private ArrayList<Action> actionList;

    public Option(String message) {
        id=Game.getInstance().getOptionMaxID();
        Game.getInstance().incOptionMaxID();
        this.optionLabel = message;
        this.actionList = new ArrayList();
    }
    
    public void runOption(){
        Game.getInstance().throwGameEvent(new OptionSelected(this));
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
    
    
    
    }    
