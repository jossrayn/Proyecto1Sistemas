/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Queue;

/**
 *
 * @author rayn0
 */
public class Cores {
    private int number = 0;
    private ArrayList<String> files = new ArrayList<>();
    private Queue instructionList;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<String> getFiles() {
        return files;
    }

    public void addFiles(String file) {
        this.files.add(file);
    }
    
    public void removeFiles(String file) {
        for(String element:files){
            if(element.equals(file)){
                files.remove(element);
                break;
            }
        }
    }

    public Queue getInstructionList() {
        return instructionList;
    }

    public void setInstructionList(Queue instructionList) {
        this.instructionList = instructionList;
    }
    
    public Cores(int num,String file,Queue queue){
        this.number = num;
        this.files.add(file);
        this.instructionList = queue;
    }
}
