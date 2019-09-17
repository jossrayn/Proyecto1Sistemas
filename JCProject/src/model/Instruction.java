/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author rayn0
 */
public class Instruction {
    private String type = "";
    private int weight = 0;
    
    public Instruction(String type,int wgth){
        this.type = type;
        this.weight = wgth;
        
    }
}
