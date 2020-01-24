/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.StaticObject;

/**
 *
 * @author David Brož
 */
public class ChangeStaticObjectDescription extends Action {
    private StaticObject whatToChange;
    private String newDescription;

    public ChangeStaticObjectDescription(StaticObject whatToChange, String newDescription) {
        this.whatToChange = whatToChange;
        this.newDescription = newDescription;
    }

    @Override
    public void act() {
        whatToChange.setDesctiption(newDescription);
    }

    @Override
    public String toString() {
        return "ChangeStaticObjectDescription{" + whatToChange +'}';
    }

    public StaticObject getWhatToChange() {
        return whatToChange;
    }

    public void setWhatToChange(StaticObject whatToChange) {
        this.whatToChange = whatToChange;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
    
    
    
}
