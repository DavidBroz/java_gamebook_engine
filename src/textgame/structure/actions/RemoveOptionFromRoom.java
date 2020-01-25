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
public class RemoveOptionFromRoom implements Action {
    private Option whatToRemove;
    private Room whereToRemove;

    public RemoveOptionFromRoom(Option whatToRemove, Room whereToRemove) {
        this.whatToRemove = whatToRemove;
        this.whereToRemove = whereToRemove;
    }

    @Override
    public void act() {
        whereToRemove.removeOption(whatToRemove);
    }

    @Override
    public String toString() {
        return "RemoveOptionFromRoom{" + whereToRemove + ", Option: " + whatToRemove + '}';
    }

    public Option getWhatToRemove() {
        return whatToRemove;
    }

    public void setWhatToRemove(Option whatToRemove) {
        this.whatToRemove = whatToRemove;
    }

    public Room getWhereToRemove() {
        return whereToRemove;
    }

    public void setWhereToRemove(Room whereToRemove) {
        this.whereToRemove = whereToRemove;
    }
    
}
