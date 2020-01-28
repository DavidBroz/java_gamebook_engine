/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textgame.structure;

import textgame.structure.actions.Action;
import java.util.ArrayList;
import textgame.structure.actions.DescribeStaticObject;

/**
 *
 * @author David Brož
 */
public class StaticObject implements java.io.Serializable {

    private int id;
    private String name;
    private String desctiption;
    private ArrayList<Option> options;
    private ArrayList referencedBy;

    public StaticObject(String name, String desctiption) {
        referencedBy=new ArrayList<>();
        this.id = Game.getInstance().getStaticObjectMaxID();
        Game.getInstance().incStaticObjectMaxID();
        this.name = name;
        this.desctiption = desctiption;
        options = new ArrayList();
        Option tempOption = new Option("Prozkoumat " + name);
        tempOption.addAction(new DescribeStaticObject(this));
        Game.getInstance().addNewOption(tempOption);
        options.add(tempOption);
    }
    @Override
    public String toString(){
        return   id + "_" + name; 
    }

    public int getId() {
        return id;
    }

    public Iterable<Option> getAllOptions() {
        return options;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesctiption(String desctiption) {
        this.desctiption = desctiption;
    }

    public String getName() {
        return name;
    }

    public String getDesctiption() {
        return desctiption;
    }

    public void addOption(Option optionToAdd) {
        options.add(optionToAdd);
       
    }
    public void removeOption(Option optionToRemove) {
        options.remove(optionToRemove);
       
    }
    
    public void deleteAllReferencesToThis() {
        for (Object o : referencedBy) {
            if (o instanceof Room) {
                Room r = (Room) o;                
                r.removeStaticObjectFromRoom(this,false);
            }
            else if(o instanceof Action){
                Action a =(Action) o;
                a.setValidity(false);
            }
        }
    }

    void removeIsReferencedBy(Object o) {
        if(referencedBy.contains(o))referencedBy.remove(o);
        else System.out.println("-STATIC-OBJECT-: Referenced by was atempted to be removed but there was not isntance of: "+o.toString() );
    }

    void addIsReferencedBy(Object o) {
        referencedBy.add(o);
    }
    

}
