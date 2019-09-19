/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Random;

/**
 *
 * @author rayn0
 */
public class controller {
    
    public controller(){
        
    }
    
    public int getCore(){
        Random randNumber = new Random();
        int core = randNumber.nextInt(2);//generacion del numero random para el inicio en la seccion de memoria
        return core;
    }
}
