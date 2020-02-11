/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import textgame.structure.GameEventListener;

/**
 *
 * @author David Brož
 */
public class GameEventListenerActed extends GameEvent{

    GameEventListener listener;
    
    public GameEventListenerActed() {
    }

    public GameEventListenerActed(GameEventListener listener) {
        this.listener = listener;
    }
    
    @Override
    public GameEventType getEventType() {
        return GameEventType.EVENT_LISTENER_ACTED;
    }

    @Override
    public Class[] getReturnClasses() {
        return new Class[]{GameEventListener.class};
    }

    @Override
    public void setValues(Object[] o) {
        if(!(o[0] instanceof GameEventListener))throw new IllegalArgumentException("Expected GameEventListener got " + o[0].getClass());
        listener =((GameEventListener)o[0]);
    }

    @Override
    public Object[] getValues() {
        return new Object[]{listener};
    }

    @Override
    public Class[] getSettingClasses() {
         return new Class[]{GameEventListener.class};
    }

    @Override
    public String toString() {
        return "GameEventListenerActed: "+ listener;
    }
    
}
