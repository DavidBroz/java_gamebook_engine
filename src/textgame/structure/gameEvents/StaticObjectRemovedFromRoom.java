/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.StaticObject;
import textgame.structure.gameEvents.GameEvent;

/**
 *
 * @author David Brož
 */
public class StaticObjectRemovedFromRoom extends GameEvent {

    private StaticObject staticObject;

    public StaticObjectRemovedFromRoom() {
    }

    public StaticObjectRemovedFromRoom(StaticObject staticObject) {
        this.staticObject = staticObject;
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.STATIC_OBJECT_REMOVED_FROM_ROOM;
    }

    @Override
    public Class getReturnClass() {
        return StaticObject.class;
    }

    @Override
    public Class getSettingClass() {
        return StaticObject.class;
    }

    @Override
    public void setValue(Object o) {
        if (!(o instanceof StaticObject)) {
            throw new IllegalArgumentException("Expected StaticObject got " + o.getClass());
        }
        staticObject = ((StaticObject) o);
    }

    @Override
    public Object getValue() {
        return staticObject;
    }

    @Override
    public String toString() {
        return "StaticObjectRemovedFromRoom{" + staticObject + '}';
    }

}
