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
public class MovePlayerToRoom implements Action {

    private Room whereToMove;

    @Override
    public void act() {
        Game.getInstance().throwGameEvent(new PlayerEnteredRoom(whereToMove));
        Game.getInstance().throwGameEvent(new PlayerLeftRoom(Game.getInstance().getPlayer().getCurrentRoom()));
        Game.getInstance().getPlayer().MovePlayer(whereToMove);
    }

    public MovePlayerToRoom(Room whereToMove) {
        this.whereToMove = whereToMove;
        Game.getInstance().setCurrentImagePath(whereToMove.getImagePath());
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

}
