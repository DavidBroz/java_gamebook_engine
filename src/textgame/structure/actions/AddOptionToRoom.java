/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Option;
import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class AddOptionToRoom implements Action, java.io.Serializable{
    private Option whatToAdd;
    private Room whereToAdd;

    @Override
    public void act() {
        whereToAdd.addOption(whatToAdd);
    }

    public AddOptionToRoom(Option whatToAdd, Room whereToAdd) {
        this.whatToAdd = whatToAdd;
        this.whereToAdd = whereToAdd;
    }

    @Override
    public String toString() {
        return "AddOptionToRoom{" +whatToAdd.toString() + ", Option: " + whereToAdd.toString() + '}';
    }

    public Option getWhatToAdd() {
        return whatToAdd;
    }

    public void setWhatToAdd(Option whatToAdd) {
        this.whatToAdd = whatToAdd;
    }

    public Room getWhereToAdd() {
        return whereToAdd;
    }

    public void setWhereToAdd(Room whereToAdd) {
        this.whereToAdd = whereToAdd;
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
