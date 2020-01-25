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
public class ChangeItemDescription implements Action {
    private Item whatToChange;
    private String newDescription;

    public ChangeItemDescription(Item whatToChange, String newDescription) {
        this.whatToChange = whatToChange;
        this.newDescription = newDescription;
    }

    @Override
    public void act() {
        whatToChange.setDescription(newDescription);
    }

    @Override
    public String toString() {
        return "ChangeItemDescription{"+ whatToChange+'}';
    }

    public Item getWhatToChange() {
        return whatToChange;
    }

    public void setWhatToChange(Item whatToChange) {
        this.whatToChange = whatToChange;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
    
}
