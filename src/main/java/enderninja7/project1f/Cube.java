/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enderninja7.project1f;

import org.joml.Vector4f;

/**
 *
 * @author Ashish1
 */

/**
 * 
 * The Cube class stores the essential values for a cube.
 * Active determines whether the cube is solid enough to be rendered or not.
 * It is a C Boolean(Byte whose values should only be 0 or 1)
 * color determines the color in rgba form. It takes 4 bytes and the color data is Vector4f
 */
public class Cube {
    

public boolean active ;
public byte r,g,b,a;
public byte X1Positive,X1Negative,Y1Positive,Y1Negative,Z1Positive,Z1Negative;
public Cube(byte r,byte g, byte b, byte a, boolean isActive, byte X1PositiveSolid, byte X1NegativeSolid, byte Y1PositiveSolid, byte Y1NegativeSolid, byte Z1PositiveSolid, byte Z1NegativeSolid){
    active = isActive;
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
            
    if(active){
    X1Positive = X1PositiveSolid;X1Negative = X1NegativeSolid;Y1Positive = Y1PositiveSolid;Y1Negative = Y1NegativeSolid;Z1Positive = Z1PositiveSolid;Z1Negative = Z1NegativeSolid;
    }
    else{
        X1Positive = 0; Z1Positive = 0; X1Negative = 0; Z1Negative = 0;Y1Positive = 0; Y1Negative = 0; 
    }
}
public Cube(byte r, byte g, byte b, byte a, boolean active){
    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
    this.active = active;
}
public Cube(){
    active = false;
    this.r = 0;
    this.g = 0;
    this.b = 0;
    this.a = 0;
    X1Positive = 0; Z1Positive = 0; X1Negative = 0; Z1Negative = 0;Y1Positive = 0; Y1Negative = 0; 
}
    
}
