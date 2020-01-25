/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class ChangeRoomDescription implements Action{
    private Room whatToChange;
    private String newDesctiption;

    public ChangeRoomDescription(Room whatToChange, String newDesctiption) {
        this.whatToChange = whatToChange;
        this.newDesctiption = newDesctiption;
    }
    
    @Override
    public void act() {
        whatToChange.setDescription(newDesctiption);
    }

    @Override
    public String toString() {
        return "ChangeRoomDescription{"+ whatToChange + '}';
    }

    public Room getWhatToChange() {
        return whatToChange;
    }

    public void setWhatToChange(Room whatToChange) {
        this.whatToChange = whatToChange;
    }

    public String getNewDesctiption() {
        return newDesctiption;
    }

    public void setNewDesctiption(String newDesctiption) {
        this.newDesctiption = newDesctiption;
    }
    
}
