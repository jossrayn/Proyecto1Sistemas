/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author rayn0
 */
public class ProcessNode {
    private String name = "";
    private int burst;
    private int arrive;
    private int priority;
    private int size;
    private String state;

    public ProcessNode(String name,int burst, int arrive, int priority, int size, String state) {
        this.name = name;
        this.burst = burst;
        this.arrive = arrive;
        this.priority = priority;
        this.size = size;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBurst() {
        return burst;
    }

    public void setBurst(int burst) {
        this.burst = burst;
    }

    public int getArrive() {
        return arrive;
    }

    public void setArrive(int arrive) {
        this.arrive = arrive;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    
}
