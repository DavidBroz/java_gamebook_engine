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
public class RemovePathFromRoom implements Action{
    private Room toWhere;
    private Room fromWhere;

    public RemovePathFromRoom(Room fromWhere, Room toWhere) {
        this.toWhere = toWhere;
        this.fromWhere = fromWhere;
    }

    @Override
    public void act() {
        fromWhere.removePathToRoom(toWhere);
    }

    @Override
    public String toString() {
        return "RemovePathFromRoom{" + fromWhere + ", to " + toWhere + '}';
    }

    public Room getToWhere() {
        return toWhere;
    }

    public void setToWhere(Room toWhere) {
        this.toWhere = toWhere;
    }

    public Room getFromWhere() {
        return fromWhere;
    }

    public void setFromWhere(Room fromWhere) {
        this.fromWhere = fromWhere;
    }
    
}
