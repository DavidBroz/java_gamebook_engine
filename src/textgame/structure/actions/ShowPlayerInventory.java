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
public class ShowPlayerInventory implements Action, java.io.Serializable{

    @Override
    public void act() {
        Game.getInstance().setInfo_line(Game.getInstance().getPlayer().inventoryToSting());
    }

    @Override
    public String toString() {
        return "ShowPlayerInventory{" + '}';
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
