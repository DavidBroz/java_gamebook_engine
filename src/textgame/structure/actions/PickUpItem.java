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
public class PickUpItem implements Action, java.io.Serializable {

    private Item whatToPickUp;

    public PickUpItem(Item whatToPickUp) {
        this.whatToPickUp = whatToPickUp;
    }

    @Override
    public void act() {
        Game.getInstance().getPlayer().PickUpItem(whatToPickUp);
    }

    @Override
    public String toString() {
        return "PickUpItem{" + whatToPickUp + '}';
    }

    public Item getWhatToPickUp() {
        return whatToPickUp;
    }

    public void setWhatToPickUp(Item whatToPickUp) {
        this.whatToPickUp = whatToPickUp;
    }

    private boolean isValid = true;

    @Override
    public boolean isValid() {
        return isValid;
    }

    @Override
    public void setValidity(boolean b) {
        isValid = b;
    }

}
