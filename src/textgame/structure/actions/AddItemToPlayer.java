/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;
import textgame.structure.Item;

/**
 *
 * @author David Brož
 */
public class AddItemToPlayer implements Action, java.io.Serializable {

    private Item whatToAdd;
    private boolean isValid = true;

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid=b;
    }

    @Override
    public void act() {
        Game.getInstance().getPlayer().addItemToInvenotory(whatToAdd, true);
    }

    public AddItemToPlayer(Item whatToAdd) {
        this.whatToAdd = whatToAdd;
    }

    @Override
    public String toString() {
        return "AddItemToPlayer{" + whatToAdd.toString() + '}';
    }

    public Item getWhatToAdd() {
        return whatToAdd;
    }

    public void setWhatToAdd(Item whatToAdd) {
        this.whatToAdd = whatToAdd;
    }
}
