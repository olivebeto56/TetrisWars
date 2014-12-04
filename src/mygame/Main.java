    package mygame;

    import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
    import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
  
    /**
     * test
     * 
     * @author normenhansen
     */
    public class Main extends SimpleApplication  { 
         //KeyPressed
	  boolean leftPressed=false;
	  boolean rightPressed=false;
	  boolean downPressed=false;
	  boolean upPressed=false;
          
          int pantalla=0;
          String[] tex={"Textures/texture1.jpg","Textures/texture2.jpg","Textures/texture3.jpg","Textures/texture4.jpg"
          ,"Textures/texture5.jpg","Textures/texture6.jpg"};
          
          Tetris game;
          First menu;
          int level=1;
          int ntex=0;

          
          public static  AppSettings pantalla() {
             AppSettings settings = new AppSettings(true);
             GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
             DisplayMode[] modes = device.getDisplayModes();
             int i=0; // note: there are usually several, let's pick the first
             settings.setResolution(modes[i].getWidth(),modes[i].getHeight());
             settings.setFrequency(modes[i].getRefreshRate());
             settings.setBitsPerPixel(modes[i].getBitDepth());
             settings.setFullscreen(device.isFullScreenSupported());
        return settings;
      }
          
        public static void main(String[] args) {
             Main app = new Main();
        
        AppSettings d;
    	d = pantalla();
        
        app.setShowSettings(false);
   	app.setSettings(d);
        
        app.start();
        }
        
        @Override
        public void simpleInitApp() {
            flyCam.setEnabled(false);//quitar botones predeterminados
            setDisplayFps(true);       // to hide the FPS
            setDisplayStatView(false); 
            
            game=new Tetris(rootNode,assetManager,guiNode,cam); 
            menu=new First(rootNode,assetManager,guiNode,cam,guiFont); 
             
            menu.setTex(tex[0]);
            
            menu.Init();
            
                  
            //cam.setViewPort(0f, .5f, 0f, .5f); tamaño de la pantalla de la camara
           //cam.setRotation(new Quaternion (1.00f, 0.00f, 0.00f, 0.00f));
           //cam.lookAt(new Vector3f(0,0,0), Vector3f.UNIT_Y);
           //cam.setLocation(new Vector3f(60,30,30));
           
           //cam.lookAt(new Vector3f(20,0,0), new Vector3f(0,-1,0));
            initKeys(); 
            
            
        }

         
        public void botton(){
            if(upPressed==true&&ayuda==0){
                ayuda++;
            game.spacePressed=upPressed;
            }else if(upPressed==false){
                ayuda=0;
            }

                game.rightPressed=rightPressed;
                game.leftPressed=leftPressed;
                game.downPressed=downPressed;
        }
        

        @Override
        public void simpleUpdate(float tpf) {

            botton();
            
            
            if(pantalla==0){
                menu.setLevel(level);
            menu.Update1(tpf);
            }else{
             camreset();
             game.init();
             game.tetris();
            }

          
        }
        
        public void camreset(){
             cam.setLocation(new Vector3f(0f, 0f, 10f));
             cam.lookAt(new Vector3f(0f, 0f, 0f), Vector3f.UNIT_Y);
        }
       
      


        
        
         
        
      

        @Override
        public void simpleRender(RenderManager rm) {
            //TODO: add render code
        }
        
        
////////////////////////////////////////////////////////////////////////////
        
        /** Custom Keybinding: Map named actions to inputs. */
    
  private void initKeys() {
    // You can map one or several inputs to one named action
    inputManager.addMapping("Pause",  new KeyTrigger(KeyInput.KEY_P));
    inputManager.addMapping("Left",   new KeyTrigger(KeyInput.KEY_LEFT));
    inputManager.addMapping("Right",  new KeyTrigger(KeyInput.KEY_RIGHT));
    inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_UP));
    inputManager.addMapping("fast", new KeyTrigger(KeyInput.KEY_DOWN));
    inputManager.addMapping("enter", new KeyTrigger(KeyInput.KEY_RETURN));
    // Add the names to the action listener.
    inputManager.addListener(actionListener,"Left", "Right", "Rotate","Pause","fast","enter");
 
  }
  
  
 int ayuda=0;
  private ActionListener actionListener = new ActionListener() {
    public void onAction(String name, boolean keyPressed, float tpf) {
      if (name.equals("Pause") && !keyPressed) {
          
      }
      
       if (name.equals("Rotate")&& keyPressed) {
                        if(pantalla==0&&level!=3){level++;}
          	      upPressed=true;
       }else{ upPressed=false;
       }
       
       
        if (name.equals("Left")&& keyPressed) {
                     if(pantalla==0&&ntex!=0){ntex--;}
                     menu.setTex(tex[ntex]);
          	  rightPressed=true;
        }else rightPressed=false;
        
        
        if (name.equals("Right")&& keyPressed) {
             if(pantalla==0&&ntex!=5){ntex++;}
             menu.setTex(tex[ntex]);
             leftPressed=true;
        }else leftPressed=false;
        
        
        
         if (name.equals("fast")&& keyPressed) {
          	      downPressed=true;
                      if(pantalla==0&&level!=1){level--;}
        }else downPressed=false;
         
         
         
          if (name.equals("enter")&& keyPressed) {
          	      pantalla=1;
                      game.setVel(level);
                      game.setTex(tex[ntex]);
          
          }
    }
  };

    
    }