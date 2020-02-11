/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.Room;
import textgame.structure.StaticObject;
import textgame.structure.gameEvents.GameEvent;
import textgame.utility.Pair;

/**
 *
 * @author David Brož
 */
public class StaticObjectAddedToRoom extends GameEvent {

    private StaticObject staticObject;
    private Room room;

    public StaticObjectAddedToRoom() {
    }

    public StaticObjectAddedToRoom(StaticObject staticObject,Room room) {
        this.staticObject = staticObject;
        this.room = room;
    }

    @Override
    public GameEvent.GameEventType getEventType() {
        return GameEvent.GameEventType.STATIC_OBJECT_ADDED_TO_ROOM;
    }

    @Override
    public Class[] getReturnClasses() {
        return new Class[]{StaticObject.class, Room.class};
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{StaticObject.class, Room.class};
    }

    @Override
    public void setValues(Object[] o) {
        if(o[0] instanceof StaticObject) throw new IllegalArgumentException("Expected StaticObject, got " + o[0].getClass());
        if(o[1] instanceof Room) throw new IllegalArgumentException("Expected Room, got " + o[1].getClass());
        room = (Room) o[1];
        staticObject = (StaticObject) o[0];
    }

    @Override
    public Object[] getValues() {
        return new Object[]{staticObject,room};
    }

    @Override
    public String toString() {
        return "StaticObjectAddedToRoom{" + staticObject+", Room: " +room+ '}';
    }

}
