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
    private String name = "";
    private int coreNumber = 0;
    private Path path;

    public FileManager(Path path,int core){
        this.name = path.getFileName().toString();
        this.path = path;
        this.coreNumber = core;
        read(this.path);
    }

    public int getCoreNumber() {
        return coreNumber;
    }

    public void setCoreNumber(int coreNumber) {
        this.coreNumber = coreNumber;
    }
    /*
    entrada: ruta del archivo
    salida: lista de instrucciones 
    objetivo: leer el archivo cargado y obtener todas las instrucciones para devolverlas y que sean manipuladas
    */
    public void read(Path path){
        try {
             instructions= (ArrayList<String>) Files.readAllLines(path, StandardCharsets.UTF_8);//lectura del archivo
        } catch (Exception ex) {
            instructions = null;
        }
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }
    
}
