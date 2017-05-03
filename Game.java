/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enderninja7.project1f;

import java.util.ArrayList;
import java.util.List;
import org.joml.Matrix4f;
import org.joml.MatrixStackf;
import org.joml.Vector3f;
import org.joml.SimplexNoise;
import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;
import org.lwjgl.opengl.GL;


/**
 *
 * @author Ashish1
 */
public class Game implements Runnable {
    public List<Chunk> cx = new ArrayList<Chunk>();
    public List<Chunk> cy = new ArrayList<Chunk>();
    public List<Chunk> cz = new ArrayList<Chunk>();
    
    List[]l = new List[3];
    public Camera camera = new Camera(new Vector3f(17, 0, 0), new Vector3f(0, 0,0));
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float CAMERA_POS_STEP = 0.05f;
    private final Vector3f cameraInc = new Vector3f(0, 0, 0);
    public String vertexShaderCode =
              "version 330 core \n"
            + ""
            + "layout(location=0) in vec3 position; \n"
            + ""
            + "layout (location = 1) in vec3 color; \n"
            + ""
            + "layout (location = 2) in vec3 offset; \n"
            + ""
            + "layout (location = 3) in vec3 normals ; \n"
            + ""
            + "uniform mat4 pers ; \n"
            + ""
            + "uniform mat4 view ; \n"
            + ""
            + "out vec3 iColor; \n"
            + ""
            + "void main(){"
            + ""
            + "gl_Position = pers * view * vec4(position + offset, 1.0); \n"
            + ""
            + "iColor = color; \n"
            + ""
            + "}";
    public String fragmentShaderCode = 
            "version 330 core \n"
            + ""
            + "in vec3 iColor; \n"
            + ""
            + "out vec4 fragColor \n"
            + ""
            + "void main(){\n"
            + ""
            + "fragColor = vec4(iColor, 1.0);\n"
            + ""
            + "}";
    public int FPS = 180;
    public int UPS = 90;
    private final Thread th;
    private final Window win = new Window("Title",400, 400,true,true );
    private final MouseInput input = new MouseInput();
    private final Timer time = new Timer();
    public Game(){
        th = new Thread(this, "GAME_CORE_THREAD");
    }
    public void start(){
        String osname = System.getProperty("os.name");
        if(osname.contains("Mac"))
            th.run();
        else
            th.start();
        
    }
        @Override
    public void run() {
        try {
            init();
            gameLoop();
        } catch (Exception excp) {
            excp.printStackTrace();
        } finally {
            cleanup();
        }
    }

    protected void init() throws Exception{
        win.init();
        time.init();
        input.init(win);
        win.setClearColor(0.0f, 0.5f, 1.0f, 1.0f);
        
    }
    protected void gameLoop(){
                float elapsedTime;
        float accumulator = 0f;
        float interval = 1f / UPS;

        boolean running = true;
        while (running && !win.windowShouldClose()) {
            elapsedTime = time.getElapsedTime();
            accumulator += elapsedTime;

            input();

            while (accumulator >= interval) {
                update(interval, input);
                accumulator -= interval;
            }

            render();

            if ( !win.isvSync() ) {
                sync();
            }
        }
        
    }
    protected void cleanup(){
        
    }
    protected void sync(){
        
    }
    protected void input(){
        input.input(win);
        cameraInc.set(0, 0, 0);
        if (win.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (win.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (win.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (win.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (win.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (win.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
        
        
    }
        public void update(float interval, MouseInput mouseInput) {
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse            
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }
    protected void render(){
       Renderer r  = new Renderer (fragmentShaderCode, vertexShaderCode, new j3D(), camera, win); 
        int i = 1;
        Chunk[][][] chunks = new Chunk[i][i][i];
        for(int x = 0; x < i; x++ ){
            for(int y = 0; y < i ; y++){
                for(int z = 0; z < i ; z++){
                    chunks[x][y][z] = new Chunk(x, y, z);  
                    chunks[x][y][z].Render(r);
                }
            }
        }
        
       // r.renderCubes(c_vecs);
        
    }
}
