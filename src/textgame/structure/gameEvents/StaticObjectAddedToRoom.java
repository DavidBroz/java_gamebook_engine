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
    public Class getReturnClass() {
        return Pair.class;
    }

    @Override
    public Class getSettingClass() {
        return Pair.class;
    }

    @Override
    public void setValue(Object o) {
        if(!(o instanceof Pair))throw new IllegalArgumentException("Expected Pair got " + o.getClass());
        Pair temp = (Pair) o;
        if(temp.first instanceof StaticObject) throw new IllegalArgumentException("Expected first StaticObject in Pair got " + temp.first.getClass());
        if(temp.second instanceof Room) throw new IllegalArgumentException("Expected second Room in Pair got " + temp.second.getClass());
        room = (Room) temp.second;
        staticObject = (StaticObject) temp.first;
    }

    @Override
    public Object getValue() {
        return new Pair<>(staticObject,room);
    }

    @Override
    public String toString() {
        return "StaticObjectAddedToRoom{" + staticObject+", Room: " +room+ '}';
    }

}
