/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enderninja7.project1f;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import java.nio.FloatBuffer;
import java.util.List;
import org.joml.SimplexNoise;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;


/**
 *
 * @author Ashish1
 */
public class Chunk {
    public int CHUNK_SIZE = 16;
    
    private final FloatBuffer fb = MemoryUtil.memAllocFloat( CHUNK_SIZE * 3);
    private final FloatBuffer cfb = MemoryUtil.memAllocFloat( CHUNK_SIZE * 3 * 3);
    private byte zero = 0;
    
    
    public Chunk(int xof, int yof, int zof){
        boolean be = false;
                  byte r = 0;
                  byte g  =0;
                  byte b = 0;
                  byte a = 0;
                  
        Cube[][][] Cubes = new Cube[xof + CHUNK_SIZE][yof + CHUNK_SIZE][zof + CHUNK_SIZE] ;
       for(int x = xof; x < (xof + CHUNK_SIZE); x++) {
           for(int y = yof; y < (yof + CHUNK_SIZE); y++){
              for(int z = zof; z < (zof + CHUNK_SIZE); z++){
                  
                  Cubes[x][y][z] = new Cube();
                  float ae = SimplexNoise.noise((float)x, (float)y, (float)z);
                  if(ae < 0){
                      return; 
                      }//isSolid is false, Just continue.
                  else{
                      if(ae < 0.5f){
                          r = 0;
                          g = 20;
                          b = 80;
                          a = 100;
                      }
                      else{
                          r = 50;
                          g = 100;
                          b = 10;
                          a = 100;
                      }
                      be = true;
                  Cubes[x][y][z] = new Cube(r,g,b,a,be);
                  fb.put(x).put(y).put(z);
                  cfb.put(r).put(g).put(b).put(a);
                  
              } 
                  
           }
       }
       }
       
       for(int x = xof; x < (xof + CHUNK_SIZE); x++){
           for(int y = yof; y <(yof + CHUNK_SIZE); y++){
               for(int z = zof; z < (zof + CHUNK_SIZE); z++){
                   if(!Cubes[x][y][z].active)
                       return;
                   else{
                       byte xp1 = 0;
                  byte xm1 = 0;
                  byte yp1 = 0;
                  byte ym1 = 0;
                  byte zp1 = 0;
                  byte zm1 = 0;
                       if(!Cubes[x+1][y][z].active)
                           xp1 = 1;
                       if(!Cubes[x-1][y][z].active)
                           xm1 = 1;
                       if(!Cubes[x][y+1][z].active)
                           yp1 = 1;
                       if(!Cubes[x][y-1][z].active)
                           ym1 = 1;
                       if(!Cubes[x][y][z+1].active)
                           zp1 = 1;
                       if(!Cubes[x][y][z-1].active)
                           zm1 = 1;
                       Cubes[x][y][z] = new Cube(r, g, b, a, be, xp1, xm1, yp1, ym1, zp1, zm1);
                   
                   }
               }
           }
       }
       
    }
public void Render(Renderer r){
    r.renderCubes(cfb, fb);
    MemoryUtil.memFree(fb);
    MemoryUtil.memFree(cfb);
}

    
}
