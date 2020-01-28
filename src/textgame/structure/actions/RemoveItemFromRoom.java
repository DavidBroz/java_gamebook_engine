/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Item;
import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class RemoveItemFromRoom implements Action, java.io.Serializable {
    private Item whatToRemove;
    private Room whereToRemove;

    public RemoveItemFromRoom(Item whatToRemove, Room whereToRemove) {
        this.whatToRemove = whatToRemove;
        this.whereToRemove = whereToRemove;
    }

    @Override
    public void act() {
        whereToRemove.removeItem(whatToRemove, true);
    }

    @Override
    public String toString() {
        return "RemoveItemFromRoom{" + whereToRemove + ", Item: " + whatToRemove + '}';
    }

    public Item getWhatToRemove() {
        return whatToRemove;
    }

    public void setWhatToRemove(Item whatToRemove) {
        this.whatToRemove = whatToRemove;
    }

    public Room getWhereToRemove() {
        return whereToRemove;
    }

    public void setWhereToRemove(Room whereToRemove) {
        this.whereToRemove = whereToRemove;
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
