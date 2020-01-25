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
public class ChangeStaticObjectName implements Action {
    
    private StaticObject whatToChange;
    private String newName;

    public ChangeStaticObjectName(StaticObject whatToChange, String newName) {
        this.whatToChange = whatToChange;
        this.newName = newName;
    }

    @Override
    public void act() {
        whatToChange.setName(newName);
    }

    @Override
    public String toString() {
        return "ChangeStaticObjectName{" + whatToChange + ", Name: " + newName + '}';
    }

    public StaticObject getWhatToChange() {
        return whatToChange;
    }

    public void setWhatToChange(StaticObject whatToChange) {
        this.whatToChange = whatToChange;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
    
    
    
    
}
