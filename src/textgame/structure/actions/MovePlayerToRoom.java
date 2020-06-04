/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;
import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class MovePlayerToRoom implements Action, java.io.Serializable {

    private Room whereToMove;

    @Override
    public void act() {
        //Game.getInstance().throwGameEvent(new GameEvent(new Object[]{whereToMove},GameEvent.GameEventType.PLAYER_ENTERED_ROOM));
        //Game.getInstance().throwGameEvent(new GameEvent(new Object[]{Game.getInstance().getPlayer().getCurrentRoom()},GameEvent.GameEventType.PLAYER_LEFT_ROOM));
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
