/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure.gameEvents;

import java.util.Random;

/**
 *
 * @author David Brož
 */
public class RandomNumber extends GameEvent {
    
    private static Random rand = new Random();
    private int min,max;
    
    public RandomNumber(int number) {
        max = number;
        min = number;
    }

    public RandomNumber(int max, int min) {
        this.max = max;
        this.min = min;
    }
    public RandomNumber() {
    }
    
    public int getNumber() {
        return new Integer(rand.nextInt((max - min) + 1) + min);
    }

    public void setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "RandomNumber";
    }

    @Override
    public GameEventType getEventType() {
        return GameEventType.RANDOM_NUMBER;
    }
    
    @Override
    public Class[] getReturnClasses() {
        return new Class[]{Integer.class};
    }

    @Override
    public Object[] getValues() {
        return new Object[] {getNumber()};
    }
    
    @Override
    public void setValues(Object[] o) {
        throw new UnsupportedOperationException("This function is not implemented.");
    }

    @Override
    public Class[] getSettingClasses() {
        return new Class[]{Integer.class, Integer.class};
    }
}
