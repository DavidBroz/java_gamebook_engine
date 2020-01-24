/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Item;

/**
 *
 * @author David Brož
 */
public class ChangeItemName extends Action {
    private Item whatToChange;
    private String newName;

    public ChangeItemName(Item whatToChange, String newName) {
        this.whatToChange = whatToChange;
        this.newName = newName;
    }

    @Override
    public void act() {
        whatToChange.setName(newName);
    }

    @Override
    public String toString() {
        return "ChangeItemName{"+ whatToChange + ", Name: " + newName + '}';
    }

    public Item getWhatToChange() {
        return whatToChange;
    }

    public void setWhatToChange(Item whatToChange) {
        this.whatToChange = whatToChange;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
    
    
}
