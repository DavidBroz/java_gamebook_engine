/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Room;

/**
 *
 * @author David Brož
 */
public class PlayerEnteredRoom extends GameEvent {
    private Room room;

    public PlayerEnteredRoom(Room roomEntred) {
        this.room = roomEntred;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room roomEntred) {
        this.room = roomEntred;
    }

    @Override
    public String toString() {
        return "PlayerEnteredRoom: " +  room;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.PLAYER_ENTERED_ROOM;
    }

    @Override
    public Class getReturnClass() {
        return Room.class;
    }
    @Override
    public Object getValue() {
        return getRoom();
    }

    public PlayerEnteredRoom() {
    }
    
    @Override
    public void setValue(Object o) {
        if(!(o instanceof Room))throw new IllegalArgumentException("Expected Room got " + o.getClass());
        setRoom((Room)o);
    }

    @Override
    public Class getSettingClass() {
        return Room.class;
    }
    
}