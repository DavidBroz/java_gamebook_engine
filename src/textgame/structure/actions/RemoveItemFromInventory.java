/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;
import textgame.structure.Item;
import textgame.structure.actions.Action;

/**
 *
 * @author david
 */
public class RemoveItemFromInventory implements Action {
    private Item itemToRemove;
    private boolean isValid;
    
    public RemoveItemFromInventory(Item itemToRemove) {
        this.itemToRemove=itemToRemove;
        if(itemToRemove!=null)isValid=true;
    }

    @Override
    public void act() {
        Game.getInstance().getPlayer().removeItem(itemToRemove, true);
    }

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid=b;
    }

    public Item getItemToRemove() {
        return itemToRemove;
    }

    public void setItemToRemove(Item itemToRemove) {
        this.itemToRemove = itemToRemove;
    }

    @Override
    public String toString() {
        return "RemoveItemFromInventory{"+ itemToRemove + '}';
    }
    
}
