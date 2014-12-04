package mygame;


import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.ui.Picture;


public class First extends SimpleApplication {
   Spatial tetris;
   Spatial box;
  public int dificultad;
  int level;
    String tex;


   
        public First(Node rootNode,AssetManager assetManager,Node guiNode, Camera cam,BitmapFont guiFont){
          this.assetManager=assetManager;
          this.rootNode=rootNode; 
          this.guiNode  =guiNode;
          this.cam=cam;
          this.guiFont=guiFont;
          
          }
        public void setTex(String tex){
        this.tex=tex;}
        
        
        public void setLevel(int level){
        this.level=level;
        }
         BitmapText helloText;
         
    public void Init() {
        
        
        this.tetris=maketetris(0,0,0);
        this.box=makecube(30.75f, 0, 9.5f);
        
        cam.setViewPort(0, 1, 0, 1);
        /*Camera cam2 = cam.clone();
        cam2.setViewPort(.7f, 1f, 0f, .3f);
        cam2.setLocation(new Vector3f(5.00f, 5.0f, 10.00f));
        cam2.setRotation(new Quaternion(0.00f, 0.99f, -0.04f, 0.02f));
        ViewPort viewPort2 = renderManager.createMainView("PiP", cam2);
        viewPort2.setBackgroundColor(ColorRGBA.Blue);
        viewPort2.setClearFlags(true, true, true);
        viewPort2.attachScene(rootNode);
           */
           cam.setLocation(new Vector3f(0,50,1));
           cam.lookAt(this.tetris.getLocalTranslation(), Vector3f.UNIT_Y);
           cam.setLocation(new Vector3f(12,50,1));
           rootNode.attachChild(tetris);
           rootNode.attachChild(box);

           guiNode.detachAllChildren();
            guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
            BitmapText helloText1 = new BitmapText(guiFont, false);
            helloText1.setSize(40);
            helloText1.setText("Level");
            helloText1.setLocalTranslation(974, 725, 0);
            guiNode.attachChild(helloText1);
            
           
           
            guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
             helloText = new BitmapText(guiFont, false);
            helloText.setSize(40);
            helloText.setText("1");
            helloText.setLocalTranslation(1010, 570, 0);
            guiNode.attachChild(helloText);
            
           Picture pic = new Picture("derecha");
            pic.setImage(assetManager, "Shaders/flecha-derecha.png", true);
            pic.setWidth(60);
            pic.setHeight(100);
            pic.setPosition(1150, 150);
            guiNode.attachChild(pic);
            
          Picture pic1 = new Picture("izquierda");
            pic1.setImage(assetManager, "Shaders/flecha-izquierda.png", true);
            pic1.setWidth(60);
            pic1.setHeight(100);
            pic1.setPosition(800, 150);
            guiNode.attachChild(pic1);
            
         Picture pic2 = new Picture("abajo");
            pic2.setImage(assetManager, "Shaders/flecha-derecha.png", true);
            pic2.rotate(0, 0, FastMath.PI / 2);
            pic2.setWidth(60);
            pic2.setHeight(100);
            pic2.setPosition(1075, 600);
            guiNode.attachChild(pic2);
            
         Picture pic3 = new Picture("arriba");
            pic3.setImage(assetManager, "Shaders/flecha-izquierda.png", true);
            pic3.rotate(0, 0, FastMath.PI / 2);
            pic3.setWidth(60);
            pic3.setHeight(100);
            pic3.setPosition(1075, 430);
            guiNode.attachChild(pic3);
        }

float irol=0;
float irol1=0;

    public void Update1(float tpf) {
        irol=irol+.003f;
       Quaternion rolt = new Quaternion();
       Quaternion rolb = new Quaternion();
       
        rolt.fromAngleAxis( irol , new Vector3f(1,.5f,1f) ); 
        tetris.setLocalRotation(rolt);
        
        irol1=irol1+.006f;
        rolb.fromAngleAxis( irol1 , new Vector3f(1,1,1) ); 
        box.setLocalRotation(rolb);
       
           helloText.setText(level+"");
        
        texture();
    }
    
    
            public void texture(){
             Material mat_default = new Material( 
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat_default.setTexture("ColorMap", 
                    assetManager.loadTexture(tex));
                box.setMaterial(mat_default);
            }
            
      public Spatial maketetris(float x, float y, float z){
                Spatial cube = assetManager.loadModel("Models/tetris.obj");
                cube.setName("tetris");
                Material mat_default = new Material( 
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat_default.setTexture("ColorMap", 
                    assetManager.loadTexture("Textures/space1.jpg"));
                cube.setMaterial(mat_default);
                //cube.setLocalScale(.1f);
                cube.setLocalScale(.08f, .1f, .1f);
                
                //cube.setTexture("ColorMap", 
                //    assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
                cube.setLocalTranslation(x,y,z);   
                return cube;
        }
      
       public Spatial makecube(float x, float y, float z){
                Spatial cube = assetManager.loadModel("Models/tetrisbox.obj");
                Material mat_default = new Material( 
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat_default.setTexture("ColorMap", 
                    assetManager.loadTexture(tex));
                cube.setMaterial(mat_default);
                cube.setLocalScale(.2f);
                
                cube.setLocalTranslation(x,y,z);   
                return cube;
        }

    @Override
    public void simpleInitApp() {
    }
        
    }


