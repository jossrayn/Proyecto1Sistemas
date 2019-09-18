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
    private ArrayList<FileManager> files = new ArrayList<>();

    public ArrayList<FileManager> getFiles() {
        return files;
    }

    public void addFiles(FileManager file) {
        this.files.add(file);
    }
    
    public void removeFiles(String file) {
        for(FileManager element:files){
            if(element.equals(file)){
                files.remove(element);
                break;
            }
        }
    }
    
    public Cores(){
    }
}
