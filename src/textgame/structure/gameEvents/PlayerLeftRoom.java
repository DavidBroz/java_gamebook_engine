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
public class PlayerLeftRoom extends GameEvent {
    private Room room;

    public PlayerLeftRoom(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "PlayerLeftRoom: " + room;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.PLAYER_LEFT_ROOM;
    }
    
    @Override
    public Class[] getReturnClasses() {
        return new Class[]{Room.class};
    }
    
    @Override
    public Object[] getValues() {
        return new Object[]{getRoom()};
    }  

    public PlayerLeftRoom() {
    }
    
    @Override
    public void setValues(Object o[]) {
        if(!(o[0] instanceof Room))throw new IllegalArgumentException("Expected Room got " + o[0].getClass());
        setRoom((Room)o[0]);
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Room.class};
    }
    
}
