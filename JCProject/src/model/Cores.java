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
    private ArrayList<String> files = new ArrayList<>();

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
    
    public Cores(){
    }
}
