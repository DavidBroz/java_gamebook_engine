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
public class AddItemToPlayer extends Action {
    private Item whatToAdd;
    @Override
    public void act() {
        Game.getInstance().getPlayer().AddItemToPlayer(whatToAdd);
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
