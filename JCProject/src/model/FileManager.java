/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author rayn0
 */

// 

public class FileManager {

    private ArrayList<String> instructions = new ArrayList<String>();//lista de instrucciones obtenidas del archivo
    

    public FileManager(){
    }
    /*
    entrada: ruta del archivo
    salida: lista de instrucciones 
    objetivo: leer el archivo cargado y obtener todas las instrucciones para devolverlas y que sean manipuladas
    */
    public ArrayList<String> read(Path path){
        try {
             instructions= (ArrayList<String>) Files.readAllLines(path, StandardCharsets.UTF_8);//lectura del archivo
        } catch (Exception ex) {
            instructions = null;
        }
        return instructions;
    }
    
}
