/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.actions;

import textgame.structure.Game;
import textgame.structure.StaticObject;

/**
 *
 * @author David Brož
 */
public class DescribeStaticObject extends Action {
    private StaticObject whatToDescribe;
    @Override
    public void act() {
        Game.getInstance().setInfo_line(whatToDescribe.getName());
    }

    public DescribeStaticObject(StaticObject whatToDescribe) {
        this.whatToDescribe = whatToDescribe;
    }

    @Override
    public String toString() {
        return "DescribeStaticObject{" + whatToDescribe + '}';
    }

    public StaticObject getWhatToDescribe() {
        return whatToDescribe;
    }

    public void setWhatToDescribe(StaticObject whatToDescribe) {
        this.whatToDescribe = whatToDescribe;
    }
    
    
    
}