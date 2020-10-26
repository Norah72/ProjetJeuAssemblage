/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author leovi
 */
public class Coordonnees {
    public int x,y;
    
    public Coordonnees(int x, int y){
        this.x=x;
        this.y=y;
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    public int setX(int newx) {
        return newx;
    }
    public int setY(int newy) {
        return newy;
    }
}