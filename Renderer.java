/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enderninja7.project1f;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import org.lwjgl.opengl.GL20;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL45.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL44.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.opengl.GL42.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL41.GL_FRAGMENT_SHADER_BIT;
import static org.lwjgl.opengl.GL41.GL_VERTEX_SHADER_BIT;
import org.joml.Vector3f
;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL31.glDrawElementsInstanced;

import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.MemoryUtil;

/**
 *
 * @author Ashish1
 */
public class Renderer {
    
        private final int programId;

    private int vertexShaderId;
     

    private int fragmentShaderId;
    
    private j3D j3d;
    
    private final IntBuffer vertexCount;
    
    public Renderer(String fragmentShaderCode, String vertexShaderCode, j3D j3d, Camera cam, Window window){
        this.j3d = j3d;
        
        programId = glCreateProgram();
       /* int fragshaProgID = GL41.glCreateShaderProgramv(GL_FRAGMENT_SHADER, fragmentShaderCode);
        int vertshaProgID = GL41.glCreateShaderProgramv(GL_VERTEX_SHADER, fragmentShaderCode);
        int pipeline = GL45.glCreateProgramPipelines();
        GL41.glUseProgramStages(pipeline, GL_VERTEX_SHADER_BIT, vertshaProgID);
        GL41.glUseProgramStages(pipeline, GL_FRAGMENT_SHADER_BIT, fragshaProgID);
        GL41.glBindProgramPipeline(pipeline);
        int a = GL45.glCreateBuffers();*/
       vertexShaderId = glCreateShader(GL_VERTEX_SHADER);
       glShaderSource(vertexShaderId, vertexShaderCode);
       fragmentShaderId = glCreateShader(GL_FRAGMENT_SHADER);
       glShaderSource(fragmentShaderId, fragmentShaderCode);
       glCompileShader(vertexShaderId);
       glCompileShader(fragmentShaderId);
            
                createUniform("pers");
                createUniform("view");
                setUniform(0, j3d.getProjectionMatrix(window.getWidth(), window.getHeight()));
                setUniform(1, j3d.getViewMatrix(cam));
                
            
       glAttachShader(programId, vertexShaderId);
       glAttachShader(programId, fragmentShaderId);
       glLinkProgram(programId);
       glUseProgram(programId); 
       
       
              float[] vertices = {
                       // V0
            -0.5f, 0.5f, 0.0f,
            // V1
            -0.5f, -0.5f, 0.0f,
            // V2
            0.5f, -0.5f, 0.0f,
            // V3
            0.5f, 0.5f, 0.0f,
       }  ;
       int[] indices = {
                       // Front face
            0, 1, 3, 3, 1, 2,
       };

       float[] normals = {
           //F1
        0.0f, -1.0f, 0.0f,
       };
       int vao = glGenVertexArrays();
       glBindVertexArray(vao);
       //Position VBO
       int vbo = glGenBuffers();
       FloatBuffer fb = MemoryUtil.memAllocFloat(vertices.length);
       fb.put(vertices).flip();
       glBindBuffer(GL_ARRAY_BUFFER, vbo);
       glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
       glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
       //Elements VBO
       int evbo = glGenBuffers();
       vertexCount = BufferUtils.createIntBuffer(indices.length);
       vertexCount.put(indices).flip();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, evbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, vertexCount, GL_STATIC_DRAW);

    }
        public int createUniform(String uniformName){
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            System.err.println("Could not find uniform");
        }
        return uniformLocation;
    }

    public void setUniform(int uniformName, Matrix4f value) {
        // Dump the matrix into a float buffer
        FloatBuffer fb = MemoryUtil.memAllocFloat(16);
        value.get(fb);
        glUniformMatrix4fv(uniformName, false, fb);
    }

    public void setUniform(int uniformName, int value) {
        glUniform1i(uniformName, value);
    }

    public void setUniform(int uniformName, Vector3f value) {
        glUniform3f(uniformName, value.x, value.y, value.z);
    }
    public void renderCubes(FloatBuffer colors,FloatBuffer ifb){
               //COLORS VBO
       int colVbo = glGenBuffers();
       
       glBindBuffer(GL_ARRAY_BUFFER, colVbo);
       glBufferData(GL_ARRAY_BUFFER, colors, GL_STATIC_DRAW);
       glVertexAttribPointer(1, 3, GL_FLOAT, false, 0,0);
       //Instanced VBO
       int instVbo = glGenBuffers();
       glBindBuffer(GL_ARRAY_BUFFER, colVbo);
       glBufferData(GL_ARRAY_BUFFER, ifb, GL_STATIC_DRAW);
       glVertexAttribPointer(2, 3, GL_FLOAT, false, 0,0);
       glVertexAttribDivisor(2, 1);
       //Enable vertex attribs
       glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        //Draw the mesh
       glDrawElementsInstanced(GL_TRIANGLES, vertexCount, 100);
       //Restore state
       glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
    }
}
