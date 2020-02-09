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
public class AddItemToRoom implements Action, java.io.Serializable {

    private Room whereToAdd;
    private Item whatToAdd;

    @Override
    public void act() {
        whereToAdd.addItemToRoom(whatToAdd,true);
    }

    public AddItemToRoom(Room whereToAdd, Item whatToAdd) {
        this.whereToAdd = whereToAdd;
        this.whatToAdd = whatToAdd;
    }

    @Override
    public String toString() {
        return "AddItemToRoom{" + whereToAdd.toString() + ", Item:" + whatToAdd.toString() + '}';
    }

    public Room getWhereToAdd() {
        return whereToAdd;
    }

    public void setWhereToAdd(Room whereToAdd) {
        this.whereToAdd = whereToAdd;
    }

    public Item getWhatToAdd() {
        return whatToAdd;
    }

    public void setWhatToAdd(Item whatToAdd) {
        this.whatToAdd = whatToAdd;
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
