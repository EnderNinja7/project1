/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enderninja7.project1f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 *
 * @author Ashish1
 */
public class j3D {
        private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private Matrix4f projectionMatrix;
    
    private Matrix4f worldMatrix;
    
    private Matrix4f viewMatrix;
    
    private Window win;
    
    public j3D(){
        
        
        projectionMatrix = new Matrix4f();
        worldMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        
    }
    public final Matrix4f getWorldMatrixForQuadXP1(){
       Matrix4f x = this.getWorldMatrix(new Vector3f(1,0,0), new Vector3f(0,90,0), 1);
       return x;
    }
    public final Matrix4f getWorldMatrixForQuadXM1(){
        Matrix4f x = this.getWorldMatrix(new Vector3f(-1,0,0), new Vector3f(0,90,0), 1);
        return x;
    }
    public final Matrix4f getWorldMatrixForQuadZP1(){
        Matrix4f x = this.getWorldMatrix(new Vector3f(0,1,0), new Vector3f(0,0,0), 1);
        return x;
    }
    public final Matrix4f getWorldMatrixForQuadZM1(){
        Matrix4f x = this.getWorldMatrix(new Vector3f(0,-1,0), new Vector3f(0,0,0), 1);
        return x;
    }
    public final Matrix4f getWorldMatrixForQuadYP1(){
        //TODO
        Matrix4f x = this.getWorldMatrix(new Vector3f(0,0,1), new Vector3f(90,0,0), 1);
        return x;
    }
    public final Matrix4f getWorldMatrixForQuadYM1(){
        //TODO
        Matrix4f x = this.getWorldMatrix(new Vector3f(0,0,1), new Vector3f(90,0,0), 1);
        return x;
    }
        public final Matrix4f getProjectionMatrix(float width, float height) {
        float aspectRatio = width / height;        
        projectionMatrix.identity();
        projectionMatrix.perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
        return projectionMatrix;
    }

    public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
        worldMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    }
        public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f rotation = camera.getRotation();
        
        viewMatrix.identity();
        // First do the rotation so camera rotates over its position
        viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
        // Then do the translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }



    
    
}
