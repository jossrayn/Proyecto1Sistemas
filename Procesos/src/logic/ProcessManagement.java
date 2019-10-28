 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rayn0
 */
public class ProcessManagement extends Thread{
    
    private ArrayList<String[]> procesos = new ArrayList<>();
    private JTable coreProcess,tableCore,memory,disk,tableProcess;
    private int algorithm,memoryOption,memorySize,diskSize,quantum,coreNumber,counter,linea;
    
    public ProcessManagement(int pcoreNumber,ArrayList<String[]> pProcesos,JTable pcoreProcess,JTable pcore,JTable pmemory,JTable pdisk,
            JTable ptableProcess,int palgorithm,int pmemoryOption,int pmemorySize,int pdiskSize,int pquantum){
        coreNumber = pcoreNumber;
        procesos = pProcesos;
        coreProcess= pcoreProcess;
        tableCore=pcore;
        memory=pmemory;
        disk=pdisk;
        tableProcess =ptableProcess;
        algorithm=palgorithm;
        memoryOption=pmemoryOption;
        memorySize=pmemorySize;
        diskSize=pdiskSize;
        quantum=pquantum;
        counter = 0;
        linea = 0;
     /*   if(procesos.size() > 6){
            procesos = (ArrayList<String[]>) procesos.subList(0,5);
        }*/
        
    }
    public void OrdenarArrival(){

    Collections.sort(procesos, new SortArrival());

    } 
    public void OrdenarPrioridad(){

    Collections.sort(procesos, new SortPrioridad());

    } 
    @Override
    public void run(){
        int contlines = 0,total =0;
        int n = procesos.size();
        DefaultTableModel modelProcessCore = (DefaultTableModel) coreProcess.getModel();
        for( int t = modelProcessCore.getRowCount() - 1; t >= 0; t-- )
        {
            modelProcessCore.removeRow(t);
        }
        for(String[] proc: procesos){
            ArrayList<String> array = new ArrayList<String>();
            int cont = modelProcessCore.getRowCount();
            array.add(cont+"");
            array.add(proc[0]);
            modelProcessCore.addRow(array.toArray());
        }
        if(algorithm == 0){
            for(int i = 0;i < procesos.size();i++){
                DefaultTableModel modelCore = (DefaultTableModel) tableCore.getModel();
                int rafaga = Integer.parseInt(procesos.get(i)[1]);
                total+=rafaga;
                for(int j = contlines;j < total;j++){
                    modelCore.setValueAt("  -  ",i, j);
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProcessManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                contlines+=rafaga;
                //funcion para manejar el espacio de la memoria y el disco
                CleanMemory(i);

            }
        }
        if(algorithm == 1){
            int wt[] = new int[n];
            SJFAlgorithm(wt); 
        }
        if(algorithm == 2){
            int wt[] = new int[n];
            int processes[] = new int[n]; 
            int burst_time[] = new int[n]; 
            for(int i = 0;i< n;i++){
                processes[i] = i;
                burst_time[i] = Integer.parseInt(procesos.get(i)[1]);
                System.out.println("proceso "+i+" remaining "+burst_time[i]);
            }
 
            RRAlgorithm(processes, n, burst_time, wt, quantum); 
        }
        if(algorithm == 3){
            FeedbackAlgorithm (n,quantum);
        
        
        }
        //
        if(algorithm == 4){
            OrdenarArrival();
            int totalTime = 0;
            for(int i = 0;i< n;i++){
                totalTime += Integer.parseInt(procesos.get(i)[1]);
            }
            HRRNAlgorithm(totalTime);  
        
        }
        //prioridad
        if(algorithm == 5){
        OrdenarPrioridad();
        PriorityAlgorithm(n);
        }
        
        
        Thread.currentThread().stop();
          
    }
    
    public void PriorityAlgorithm(int n){
        DefaultTableModel modelProcessCore = (DefaultTableModel) coreProcess.getModel();
        for( int t = modelProcessCore.getRowCount() - 1; t >= 0; t-- )
        {
            modelProcessCore.removeRow(t);
        }
        for(String[] proc: procesos){
            ArrayList<String> array = new ArrayList<String>();
            int cont = modelProcessCore.getRowCount();
            array.add(cont+"");
            array.add(proc[0]);
            modelProcessCore.addRow(array.toArray());
        }
        LinkedList result = new LinkedList();
        for (int i = 0; i < n; i++){
            Process proc = new Process(i,Integer.parseInt(procesos.get(i)[2]),Integer.parseInt(procesos.get(i)[1]),Integer.parseInt(procesos.get(i)[3]));
            result.add(proc);
        }
        DefaultTableModel modelCore = (DefaultTableModel) tableCore.getModel();
        findGc(result, modelCore);
    
    
    }
    void findGc(LinkedList queue,DefaultTableModel modelCore) 
    { 
  
        // initial time = 0 
        int time = 0; 
  
        // priority Queue sort data according 
        // to arrival time or priority (ready queue) 
        TreeSet prique = new TreeSet(new MyComparator()); 
  
        // link list for store processes data 
        LinkedList result = new LinkedList(); 
  
        // process in ready queue from new state queue 
        while (queue.size() > 0) 
            prique.add((Process)queue.removeFirst()); 
  
        Iterator it = prique.iterator(); 
  
        // time set to according to first process 
        time = ((Process)prique.first()).at; 
        
        int total = 0,contlines = 0;
        // scheduling process 
        while (it.hasNext()) { 
            
            // dispatcher dispatch the 
            // process ready to running state 
            Process obj = (Process)it.next(); 
            System.out.println("process "+ obj.pno+"  burst"+obj.bt);
                int rafaga = obj.bt;
                total+=rafaga;
                for(int j = contlines;j < total;j++){
                    modelCore.setValueAt("  -  ",obj.pno, j);
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ProcessManagement.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                contlines+=rafaga;
            CleanMemory(obj.pno);
            GChart gc1 = new GChart(); 
            gc1.pno = obj.pno; 
            gc1.stime = time; 
            time += obj.bt; 
            gc1.ctime = time; 
            gc1.ttime = gc1.ctime - obj.at; 
            gc1.wtime = gc1.ttime - obj.bt; 
  
            /// store the exxtreted process 
            result.add(gc1); 
        } 
  
        // create object of output class and call method 
 
    } 
    public void HRRNAlgorithm(int total){
        DefaultTableModel modelProcessCore = (DefaultTableModel) coreProcess.getModel();
        for( int t = modelProcessCore.getRowCount() - 1; t >= 0; t-- )
        {
            modelProcessCore.removeRow(t);
        }
        for(String[] proc: procesos){
            ArrayList<String> array = new ArrayList<String>();
            int cont = modelProcessCore.getRowCount();
            array.add(cont+"");
            array.add(proc[0]);
            modelProcessCore.addRow(array.toArray());
        }
        int i, j, t;  
        DefaultTableModel modelCore = (DefaultTableModel) tableCore.getModel();
        int n = procesos.size();
        t = Integer.parseInt(procesos.get(0)[2]);
        System.out.println("total "+total);
        while( t < total) {  
    
        // Set lower limit to response ratio  
        float hrr = -9999;  
    
        // Response Ratio Variable  
        float temp = 0;  
    
        // Variable to store next processs selected  
        int loc = 0;  
        for (i = 0; i < n; i++) {  
    
            // Checking if process has arrived and is Incomplete  
            if (Integer.parseInt(procesos.get(i)[2]) <= t && procesos.get(i)[5].equals("pending")) {  
    
                // Calculating Response Ratio  
                temp = (Integer.parseInt(procesos.get(i)[1]) + (t - Integer.parseInt(procesos.get(i)[2]))) / Integer.parseInt(procesos.get(i)[1]);  
                
                // Checking for Highest Response Ratio  
                if (hrr < temp) {  
    
                    // Storing Response Ratio  
                    hrr = temp;  
    
                    // Storing Location  
                    loc = i; 
                    
                }  
            }  
        }  
    
        // Updating time value  
         
        for(int k = t;k < t + Integer.parseInt(procesos.get(loc)[1]); k++){
        modelCore.setValueAt("  -  ",loc, k);
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ProcessManagement.class.getName()).log(Level.SEVERE, null, ex);
        }                        
        }
        t += Integer.parseInt(procesos.get(loc)[1]); 
        procesos.get(loc)[5] = "completed"; 
        CleanMemory(loc);
        System.out.println("proceso "+loc+" remaining "+procesos.get(loc)[1] + " tiempo "+t);
     
    }  
    }
    public void SJFAlgorithm(int wt[]) { 
        DefaultTableModel modelCore = (DefaultTableModel) tableCore.getModel();
        int n = procesos.size();
        int rt[] = new int[n]; 
       
        // Copy the burst time into rt[] 
        for (int i = 0; i < n; i++) 
            rt[i] = Integer.parseInt(procesos.get(i)[1]); 
       
        int complete = 0, t = 0, minm = Integer.MAX_VALUE; 
        int shortest = 0, finish_time; 
        boolean check = false; 
       int contlines = 0;
        // Process until all processes gets 
        // completed 
        while (complete != n) { 
       
            // Find process with minimum 
            // remaining time among the 
            // processes that arrives till the 
            // current time` 
            for (int j = 0; j < n; j++)  
            { 
                if ((Integer.parseInt(procesos.get(j)[2]) <= t) && (rt[j] < minm) && rt[j] > 0) { 
                    minm = rt[j]; 
                    shortest = j; 
                    check = true; 
                } 
            } 
       
            if (check == false) { 
                t++; 
                continue; 
            } 
       
            // Reduce remaining time by one 
            rt[shortest]--; 
       
            // Update minimum 
            minm = rt[shortest]; 
            if (minm == 0) 
                minm = Integer.MAX_VALUE; 
       
            //System.out.println(contlines+ " Short in this moment "+shortest+" tiempo restante"+rt[shortest]);
            modelCore.setValueAt("  -  ",shortest, contlines);
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProcessManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
            contlines++;
            // If a process gets completely                  
            // executed 
            if (rt[shortest] == 0) { 
                CleanMemory(shortest);
                // Increment complete 
                complete++; 
                check = false; 
       
                // Find finish time of current 
                // process 
                finish_time = t + 1; 
       
                // Calculate waiting time 
                wt[shortest] = finish_time - Integer.parseInt(procesos.get(shortest)[1]) -  Integer.parseInt(procesos.get(shortest)[2]); 
       
                if (wt[shortest] < 0) 
                    wt[shortest] = 0; 
            } 
            // Increment time 
            t++; 
        } 
    } 
	public void FeedbackAlgorithm (int n, int quantum) {
            DefaultTableModel modelCore = (DefaultTableModel) tableCore.getModel();
            // Make a copy of burst times bt[] to store remaining 
            // burst times. 
            
            ArrayList<int []> lista = new ArrayList<>();
            int contlines = 0;
            for (int i = 0; i < n; i++){
                int proceso[] = new int[3];
                proceso[0] = i;
                proceso[1] = Integer.parseInt(procesos.get(i)[1]);
                proceso[2] = Integer.parseInt(procesos.get(i)[2]);
                lista.add(proceso);
            }
            while(true){
                boolean done = true;
                for (int i = 0; i < n; i++){
                    int proceso[] = lista.get(i);
                    if(proceso[1] > 0){
                        done = false;
                        if (proceso[1] > quantum) 
                        { 
                            System.out.println("proceso "+i+" remaining "+proceso[1]);
                        for(int k = contlines;k < contlines+quantum; k++){
                        modelCore.setValueAt("  -  ",i, k);
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ProcessManagement.class.getName()).log(Level.SEVERE, null, ex);
                        }                        
                        }
                            contlines+=quantum;
                            proceso[1] -= quantum;
                            lista.remove(i);
                            lista.add(proceso);
                        }
                        else{
                        System.out.println("proceso "+i+" remaining "+proceso[1]);
                        for(int k = contlines;k < contlines+quantum; k++){
                        modelCore.setValueAt("  -  ",i, k);
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ProcessManagement.class.getName()).log(Level.SEVERE, null, ex);
                        }                        
                        }
                        contlines+=quantum;
                        proceso[1] = 0;
                        lista.remove(i);
                        lista.add(proceso);
                        CleanMemory(i);
                        }
                    }
                }
            if (done == true) 
              break; 
            }

        }
    
    
    
    public void RRAlgorithm(int processes[], int n,int bt[], int wt[], int quantum) 
    { 
        DefaultTableModel modelCore = (DefaultTableModel) tableCore.getModel();
        // Make a copy of burst times bt[] to store remaining 
        // burst times. 
        int rem_bt[] = new int[n];
        int contlines = 0;
        for (int i = 0 ; i < n ; i++) 
            rem_bt[i] =  bt[i]; 
        
        
        int t = 0; // Current time 
       System.out.println("quantum "+quantum);
        // Keep traversing processes in round robin manner 
        // until all of them are not done. 
        while(true) 
        { 
            boolean done = true; 
            
            // Traverse all processes one by one repeatedly 
            for (int i = 0 ; i < n; i++) 
            { 
                // If burst time of a process is greater than 0 
                // then only need to process further 
                if (rem_bt[i] > 0) 
                { 
                    done = false; // There is a pending process 
       
                    if (rem_bt[i] > quantum) 
                    { 
                        System.out.println("proceso "+i+" remaining "+rem_bt[i]);
                        for(int k = contlines;k < contlines+quantum; k++){
                        modelCore.setValueAt("  -  ",i, k);
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ProcessManagement.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                        contlines+=quantum;
                        // Increase the value of t i.e. shows 
                        // how much time a process has been processed 
                        t += quantum; 
       
                        // Decrease the burst_time of current process 
                        // by quantum 
                        rem_bt[i] -= quantum; 
                        
                    } 
       
                    // If burst time is smaller than or equal to 
                    // quantum. Last cycle for this process 
                    else
                    { 
                        System.out.println("proceso "+i+" remaining "+rem_bt[i]);
                        for(int k = contlines;k < contlines+quantum; k++){
                        modelCore.setValueAt("  -  ",i, k);
                        try {
                            Thread.currentThread().sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ProcessManagement.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        }
                        contlines+=quantum;
                        // Increase the value of t i.e. shows 
                        // how much time a process has been processed 
                        t = t + rem_bt[i]; 
       
                        // Waiting time is current time minus time 
                        // used by this process 
                        wt[i] = t - bt[i]; 
       
                        // As the process gets fully executed 
                        // make its remaining burst time = 0 
                        rem_bt[i] = 0; 
                        CleanMemory(i);
                    } 
                } 
            } 
       
            // If all processes are done 
            if (done == true) 
              break; 
        } 
    }      
    // Method to calculate turn around time 
 /*   public void findTurnAroundTime( int wt[], int tat[]) 
    { 
        int n = procesos.size();
        // calculating turnaround time by adding 
        // bt[i] + wt[i] 
        for (int i = 0; i < n; i++) 
            tat[i] = Integer.parseInt(procesos.get(i)[1]) + wt[i]; 
    } 
       
    // Method to calculate average time 
    public void SJF() 
    { 
        int n = procesos.size();
        
        int wt[] = new int[n], tat[] = new int[n]; 
        int  total_wt = 0, total_tat = 0; 
       
        // Function to find waiting time of all 
        // processes 
        SJFAlgorithm(wt); 
        
       
        // Function to find turn around time for 
        // all processes 
        findTurnAroundTime(wt, tat); 
       
        // Display processes along with all 
        // details 
        System.out.println("Processes " + 
                           " Burst time " + 
                           " Waiting time " + 
                           " Turn around time"); 
       
        // Calculate total waiting time and 
        // total turnaround time 
        for (int i = 0; i < n; i++) { 
            total_wt = total_wt + wt[i]; 
            total_tat = total_tat + tat[i]; 
            System.out.println(" " + Integer.parseInt(procesos.get(i)[1])+ "\t\t"
                             + Integer.parseInt(procesos.get(i)[2]) + "\t\t " + wt[i] 
                             + "\t\t" + tat[i]); 
        } 
       
        System.out.println("Average waiting time = " + 
                          (float)total_wt / (float)n); 
        System.out.println("Average turn around time = " + 
                           (float)total_tat / (float)n); 
    } */
    public void CleanMemory(int i){
        int freeSpace = 0;
        DefaultTableModel modelMemory = (DefaultTableModel) memory.getModel();
        DefaultTableModel modelDisk = (DefaultTableModel) disk.getModel();
        DefaultTableModel modelTable = (DefaultTableModel)tableProcess.getModel();
        //eliminar el espacio de memoria de los procesos terminados

        for( int k = modelMemory.getRowCount() - 1; k >= 0; k-- ){
            String nameProcess = modelMemory.getValueAt(k, 1).toString();
            if(procesos.get(i)[0].equals(nameProcess)){
                modelMemory.removeRow(k);
                
                
            }
        }
        for(int l = 0 ;l < modelTable.getRowCount();l++){
            String nameProcess = modelTable.getValueAt(l, 0).toString();
            if(procesos.get(i)[0].equals(nameProcess)){
                modelTable.setValueAt("Completed",l, 5);
            }
        }

        //renombrar los indices de la memoria liberada
        for(int l = 0 ;l < modelMemory.getRowCount();l++){
            modelMemory.setValueAt(l,l, 0);
        }
        //condicion para mover los procesos de disco a memoria
        if(modelDisk.getRowCount() > 0){
            int size = 0;
            String nombre = "";
            for(int l = 0 ;l < modelDisk.getRowCount();l++){
               String nameProcess = modelDisk.getValueAt(l, 1).toString();
               for(int j = 0;j < procesos.size();j++){
                   if(procesos.get(j)[0].equals(nameProcess)){
                       size = Integer.parseInt(procesos.get(j)[4]);
                       nombre = procesos.get(j)[0];
                   }
               }

               freeSpace = memorySize - modelMemory.getRowCount();
               System.out.println("Size "+size+" Free "+freeSpace);
               if(size <= freeSpace){
                   for(int h = 0;h < size;h++){
                       ArrayList<String> array = new ArrayList<String>();
                       int cont = modelMemory.getRowCount();
                       array.add(cont+"");
                       array.add(nombre);
                       modelMemory.addRow(array.toArray());
                   }

               }
               for( int k = modelDisk.getRowCount() - 1; k >= 0; k-- ){
                   if(procesos.get(i)[0].equals(nombre)){
                       modelDisk.removeRow(k);
                   }
               }

            }                   
        }
    }
}
class SortArrival implements Comparator<String[]> {

    @Override
    public int compare(String[] o1, String[] o2) {
        int op1= Integer.parseInt(o1[2]);
        int op2= Integer.parseInt(o2[2]);
        return op1 - op2;
    }
    
}
class SortPrioridad implements Comparator<String[]> {

    @Override
    public int compare(String[] o1, String[] o2) {
        int op1= Integer.parseInt(o1[3]);
        int op2= Integer.parseInt(o2[3]);
        return op1 - op2;
    }
    
}
 class Process { 
    int at, bt, pri, pno; 
    Process(int pno, int at, int bt, int pri) 
    { 
        this.pno = pno; 
        this.pri = pri; 
        this.at = at; 
        this.bt = bt; 
    } 
} 
  
/// Gantt chart structure 
class GChart { 
    // process number, start time, complete time, 
    // turn around time, waiting time 
    int pno, stime, ctime, wtime, ttime; 
} 
  
// user define comparative method (first arrival first serve, 
// if arrival time same then heigh priority first) 
class MyComparator implements Comparator { 
  
    public int compare(Object o1, Object o2) 
    { 
  
        Process p1 = (Process)o1; 
        Process p2 = (Process)o2; 
        if (p1.at < p2.at) 
            return (-1); 
  
        else if (p1.at == p2.at && p1.pri > p2.pri) 
            return (-1); 
  
        else
            return (1); 
    } 
} 
