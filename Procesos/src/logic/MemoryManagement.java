/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rayn0
 */
public class MemoryManagement {
    private int option;
    private int memorySize;
    private int diskSize;
    private int block;
    private ArrayList<Integer> segmentSize;
    private ArrayList<Integer> virtualSize;

    public MemoryManagement(int option, int memorySize, int DiskSize, int block, ArrayList<Integer> segmentSize,ArrayList<Integer> virtual) {
        this.option = option;
        this.memorySize = memorySize;
        this.diskSize = DiskSize;
        this.block = block;
        this.segmentSize = segmentSize;
        this.virtualSize = virtual;
    }
    
    /*
    metodo encargado de la definicion de los espacios en memoria
    segun la configuracion seleccionada
    */
    public void set(JTable memory,JTable disk,ArrayList<ProcessNode> list){
        DefaultTableModel modelMemory = (DefaultTableModel) memory.getModel();
        DefaultTableModel modelDisk = (DefaultTableModel) disk.getModel();
        if(option == 0){//fija
            for (ProcessNode ele:list){
                if(modelMemory.getRowCount() + block < memorySize){
                    for(int j = 0;j <= block; j++){
                        if(j<=ele.getSize()){
                            if(j==0){
                                ArrayList<String> array = new ArrayList<String>();
                                int cont = modelMemory.getRowCount();
                                array.add(cont+"");
                                array.add("Nueva Partición");
                                modelMemory.addRow(array.toArray());
                                memorySize++;
                            }
                            else{
                                ArrayList<String> array = new ArrayList<String>();
                                int cont = modelMemory.getRowCount();
                                array.add(cont+"");
                                array.add(ele.getName());
                                modelMemory.addRow(array.toArray());
                            }
                        }
                        else{
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelMemory.getRowCount();
                            array.add(cont+"");
                            array.add("Free");
                            modelMemory.addRow(array.toArray());
                        }
                        
                    }
                }
                else{
                    for(int j = 0;j <= block; j++){
                        if(j==0){
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelDisk.getRowCount();
                            array.add(cont+"");
                            array.add("Nueva Partición");
                            modelDisk.addRow(array.toArray());
                            diskSize++;
                        }
                        else{
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelDisk.getRowCount();
                            array.add(cont+"");
                            array.add(ele.getName());
                            modelDisk.addRow(array.toArray());
                        }
                    }
                }
                
            } 
        }
        else if(option == 1){//dinamica
            for (ProcessNode ele:list){
                if(modelMemory.getRowCount() < memorySize && modelMemory.getRowCount() + ele.getSize() < memorySize){
                    for(int j = 0;j < ele.getSize(); j++){
                        if(j==0){
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelMemory.getRowCount();
                            array.add(cont+"");
                            array.add("Nueva Partición");
                            modelMemory.addRow(array.toArray());
                            memorySize++;
                        }else{
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelMemory.getRowCount();
                            array.add(cont+"");
                            array.add(ele.getName());
                            modelMemory.addRow(array.toArray());
                        }
                    }
                }
                else{
                    for(int j = 0;j <= ele.getSize(); j++){
                        if(j==0){
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelDisk.getRowCount();
                            array.add(cont+"");
                            array.add("Nueva Partición");
                            modelDisk.addRow(array.toArray());
                            diskSize++;
                        }else{
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelDisk.getRowCount();
                            array.add(cont+"");
                            array.add(ele.getName());
                            modelDisk.addRow(array.toArray());
                        }
                    }
                }
            }
        }
        else if(option == 2){//paginacion
            int memoryFrames = memorySize/block;
            int diskFrames = diskSize/block;
            ArrayList<String> fragProcess =new ArrayList<>();
            int busyFrames=0;
            int busyFramesV=0;
            for(ProcessNode pro :list ){
                boolean bandera=true;
                fragProcess=frag(pro);
                busyFrames+=fragProcess.size();
                if(busyFrames<(memoryFrames*block)){
                    bandera=false;
                    for(String ele:fragProcess){
                        ArrayList<String> array = new ArrayList<String>();
                        int cont = modelMemory.getRowCount();
                        array.add(cont+"");
                        array.add(ele);
                        modelMemory.addRow(array.toArray());
                    }
                }
                else{
                    busyFrames-=fragProcess.size()/block;
                    busyFramesV+=fragProcess.size()/block;
                    if(busyFramesV<diskFrames && bandera){
                        for(String ele:fragProcess){
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelDisk.getRowCount();
                            array.add(cont+"");
                            array.add(ele);
                            modelDisk.addRow(array.toArray());
                        }
                    }
                    else{
                        busyFramesV-=fragProcess.size();
                    }
                }
            }
        }
        else if(option == 3){//segmentacion
            ArrayList<Integer> temp = segmentSize;
            int counter = 0;
            for (ProcessNode ele:list){
                boolean flag = true;
                if ( modelMemory.getRowCount() < memorySize && modelMemory.getRowCount() + ele.getSize() < memorySize){
                for(int i:segmentSize){
                    if(i!=0){
                        if(ele.getSize()<=i){
                            flag = false;
                            for(int j = 0;j < i; j++){
                                if(j<=ele.getSize()){
                                    if(j==0){
                                        ArrayList<String> array = new ArrayList<String>();
                                        int cont = modelMemory.getRowCount();
                                        array.add(cont+"");
                                        array.add("Nuevo Segmento");
                                        modelMemory.addRow(array.toArray());
                                        memorySize++;
                                    }else{
                                        ArrayList<String> array = new ArrayList<String>();
                                        int cont = modelMemory.getRowCount();
                                        array.add(cont+"");
                                        array.add(ele.getName());
                                        modelMemory.addRow(array.toArray());
                                    }
                                }
                                else{
                                    ArrayList<String> array = new ArrayList<String>();
                                    int cont = modelMemory.getRowCount();
                                    array.add(cont+"");
                                    array.add("Free");
                                    modelMemory.addRow(array.toArray());
                                }
                            }
                            segmentSize.set(counter, 0);
                            counter++;
                            break;
                        }
                        else{
                            ArrayList<String> array = new ArrayList<String>();
                            int cont = modelMemory.getRowCount();
                            array.add(cont+"");
                            array.add("Free");
                            modelMemory.addRow(array.toArray());
                        }
                    }
                           
                }}
                else{
                counter = 0;
                for(int i:virtualSize){
                    if(i!=0){
                        if(ele.getSize()<=i && flag){
                            for(int j = 0;j <= i; j++){
                                if(j<=ele.getSize()){
                                    if(j==0){
                                        ArrayList<String> array = new ArrayList<String>();
                                        int cont = modelDisk.getRowCount();
                                        array.add(cont+"");
                                        array.add("Nuevo Segmento");
                                        modelDisk.addRow(array.toArray());
                                        diskSize++;
                                    }else{
                                        ArrayList<String> array = new ArrayList<String>();
                                        int cont = modelDisk.getRowCount();
                                        array.add(cont+"");
                                        array.add(ele.getName());
                                        modelDisk.addRow(array.toArray());
                                    }
                                }
                                else{
                                    if(modelMemory.getRowCount()<memorySize){
                                    ArrayList<String> array = new ArrayList<String>();
                                    int cont = modelMemory.getRowCount();
                                    array.add(cont+"");
                                    array.add("Free");
                                    modelMemory.addRow(array.toArray());}
                                }
                            }
                            virtualSize.set(counter, 0);
                            counter++;
                            break;
                        }
                    }
                }
                    
                }        
             }
        }
            
        
    }
    
    private ArrayList<String> frag(ProcessNode pro){
        ArrayList<String> result = new ArrayList();
        int fragSize=pro.getSize()/block;
        int cont=0;
        for(int i=0;i<fragSize;i++){
            result.add("Frame");
            for(int j=0;j<block;j++){
                result.add(pro.getName());
                cont++;
            }
            
        }
        if(cont<pro.getSize()){
            result.add("Page");
            for(int j=0;j<block;j++){
                if(cont<pro.getSize()){
                   result.add(pro.getName());
                   cont++;
               }else{
                   result.add("Free");
               }
            }
        }
        return result;
    }
    
     
    
}
