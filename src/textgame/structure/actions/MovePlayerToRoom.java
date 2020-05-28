/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;
import textgame.structure.Room;
import textgame.structure.gameEvents.PlayerEnteredRoom;
import textgame.structure.gameEvents.PlayerLeftRoom;

/**
 *
 * @author David Brož
 */
public class MovePlayerToRoom implements Action, java.io.Serializable {

    private Room whereToMove;

    @Override
    public void act() {
        Game.getInstance().throwGameEvent(new PlayerEnteredRoom(whereToMove));
        Game.getInstance().throwGameEvent(new PlayerLeftRoom(Game.getInstance().getPlayer().getCurrentRoom()));
        Game.getInstance().getPlayer().move(whereToMove);
    }

    public MovePlayerToRoom(Room whereToMove) {
        this.whereToMove = whereToMove;
    }

    @Override
    public String toString() {
        return "MovePlayerToRoom{" + whereToMove + '}';
    }

    public Room getWhereToMove() {
        return whereToMove;
    }

    public void setWhereToMove(Room whereToMove) {
        this.whereToMove = whereToMove;
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
