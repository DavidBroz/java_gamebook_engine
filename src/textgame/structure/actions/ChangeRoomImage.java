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
public class ChangeRoomImage implements Action {
    private String newImagePath;
    private Room whereToChange;

    public ChangeRoomImage(String newImagePath, Room whereToChange) {
        this.newImagePath = newImagePath;
        this.whereToChange = whereToChange;
    }
    
    
    
    @Override
    public void act() {
        whereToChange.setImagePath(newImagePath);
    }

    @Override
    public String toString() {
        return "ChangeRoomImage{" +whereToChange+ ", Image: " + newImagePath + '}';
    }
    
}
