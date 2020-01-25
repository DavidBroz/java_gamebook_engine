/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;

/**
 *
 * @author David Brož
 */
public class ChangeCurrentImage implements Action {
    private String newImagePath;
    @Override
    
    public void act() {
        Game.getInstance().setCurrentImagePath(newImagePath);
    }

    public ChangeCurrentImage(String newImagePath) {
        this.newImagePath = newImagePath;
    }

    @Override
    public String toString() {
        return "ChangeCurrentImage{"+ newImagePath + '}';
    }
    
    
}
