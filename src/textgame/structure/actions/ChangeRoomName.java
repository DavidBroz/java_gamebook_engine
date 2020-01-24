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
public class ChangeRoomName extends Action {
    private Room whatToChange;
    private String newName;

    public ChangeRoomName(Room whatToChange, String newName) {
        this.whatToChange = whatToChange;
        this.newName = newName;
    }

    @Override
    public void act() {
        whatToChange.setName(newName);
    }

    @Override
    public String toString() {
        return "ChangeRoomName{" + whatToChange + ", Name: " + newName + '}';
    }

    public Room getWhatToChange() {
        return whatToChange;
    }

    public void setWhatToChange(Room whatToChange) {
        this.whatToChange = whatToChange;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
    
}