/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author armando
 */
public class Tetris extends SimpleApplication{
     int[][] occupied = new int[10][20];
	int score=0;  // score
	int lineCompleted = 0;   // number of lines completed
	int level=0;
        int velocidad;
        String tex;
	
	//FallingToken
	int x,y;
        int tokenNumber, rotationNumber;
        int[] xArray;
        int[] yArray;
        boolean gameOver=false;
        int delay=15;
	
    //shiftDown
	 int frame=0;
	  boolean reachFloor=false;
          
          
         //KeyPressed
	  boolean leftPressed=false;
	  boolean rightPressed=false;
	  boolean downPressed=false;
	  boolean spacePressed=false;
          
          
          //node
          
          
          
          
          Tetris (Node rootNode,AssetManager assetManager,Node guiNode, Camera cam){
          this.assetManager=assetManager;
          this.rootNode=rootNode; 
          this.guiNode=guiNode;
          this.cam=cam;
          }
	
	 static int[][][] xRotationArray = {
	       { {0,0,1,2}, {0,0,0,1}, {2,0,1,2}, {0,1,1,1} },  // token number 0
	       { {0,0,1,1}, {1,2,0,1}, {0,0,1,1}, {1,2,0,1} },  // token number 1
	       { {1,1,0,0}, {0,1,1,2}, {1,1,0,0}, {0,1,1,2} },  // token number 2
	       { {0,1,2,2}, {0,1,0,0}, {0,0,1,2}, {1,1,0,1} },  // token number 3
	       { {1,0,1,2}, {1,0,1,1}, {0,1,1,2}, {0,0,1,0} },  // token number 4
	       { {0,1,0,1}, {0,1,0,1}, {0,1,0,1}, {0,1,0,1} },  // token number 5
	       { {0,1,2,3}, {0,0,0,0}, {0,1,2,3}, {0,0,0,0} }   // token number 6
	    };
	 
	    static int[][][] yRotationArray = {
	       { {0,1,0,0}, {0,1,2,2}, {0,1,1,1}, {0,0,1,2} },  // token number 0
	       { {0,1,1,2}, {0,0,1,1}, {0,1,1,2}, {0,0,1,1} },  // token number 1
	       { {0,1,1,2}, {0,0,1,1}, {0,1,1,2}, {0,0,1,1} },  // token number 2
	       { {0,0,0,1}, {0,0,1,2}, {0,1,1,1}, {0,1,2,2} },  // token number 3
	       { {0,1,1,1}, {0,1,1,2}, {0,0,1,0}, {0,1,1,2} },  // token number 4
	       { {0,0,1,1}, {0,0,1,1}, {0,0,1,1}, {0,0,1,1} },  // token number 5
	       { {0,0,0,0}, {0,1,2,3}, {0,0,0,0}, {0,1,2,3} }   // token number 6
	    };
	    
	    

	    public void drawCell(int x,int y){
	      occupied[x][y] = 1;
	    }
	   
	    
	    public void eraseCell(int x,int y){
	      occupied[x][y] = 0;
	    }
	    
	    
	    public void drawToken(int x, int y, int[] xArray, int[] yArray){
	      for (int i=0;i<4;i++)
	      {
	        drawCell(x+xArray[i],y+yArray[i]);
	      }
	    }
	   
	    
	    public void eraseToken(int x, int y, int[] xArray, int[] yArray){
	      for (int i=0;i<4;i++)
	      {
	        eraseCell(x+xArray[i],y+yArray[i]);
	      }
	    }
	    
	  
	    
	    public boolean isValidPosition(int x,int y, int tokenNumber, int rotationNumber)
	    {
	      int[] xArray1 = xRotationArray[tokenNumber][rotationNumber];
	      int[] yArray1 = yRotationArray[tokenNumber][rotationNumber];
	       
	      for (int i=0;i<4;i++)  // loop over the four cells 
	      {
	        int xCell = x+xArray1[i];
	        int yCell = y+yArray1[i];
	   
	        // range check
	        if (xCell<0) return false;
	        if (xCell>=10) return false;
	        if (yCell<0) return false;
	        if (yCell>=20) return false;
	   
	        // occupancy check
	        if (occupied[xCell][yCell]==1) return false;
	      }
	      return true;
	    }
	    
	    public int[] checkRowCompletion()
	    {
	      int[] complete1 = new int[20];
	      for (int y1=0;y1<20;y1++)  // 20 rows
	      {
	        int filledCell = 0;
	        for (int x1=0;x1<10;x1++)  // 10 columns
	        {
	          if (occupied[x1][y1]==1) filledCell++;
	          if (filledCell==10) // row completed 
	          {
	            complete1[y1]=1;
	          }
	        }
	      }
	   return complete1;
	   
	    }
	   
	    public void clearCompleteRow(int[] completed)
	    {
	      // must loop for odd number of times.
	      // toggle sequence : 0,1,0,1,0
	      for (int blinking=0;blinking<5;blinking++)
	      {
	        for (int i=0;i<completed.length;i++)
	        {
	          if (completed[i]==1)
	          {
	            for (int x1=0;x1<10;x1++)
	            {
	              // toggle the occupancy array
	              occupied[x1][i]=1-occupied[x1][i];
	            }
	          }
	        }
	      }
	    }
	    public void setTex(String tex){
            this.tex=tex;
            }
            
	    public void setVel(int vel){
            this.velocidad=vel;
            }
	    public void shiftDownComplete(int[] completed)
	    {
	      for (int row=0;row<completed.length;row++)
	      {
	        if (completed[row]==1)
	        {
	          for (int y1=row;y1>=1;y1--)
	          {
	            for (int x1=0;x1<10;x1++)
	            {
	              occupied[x1][y1] = occupied[x1][y1-1];
	            }
	          }
	        }
	      }
	    }
	    
	    
	    void addScore(int[] complete){
	    	
	      int bonus=10;  // score for the first completed line
	      for (int row=0;row<complete.length;row++){
	        if (complete[row]==1){
	          lineCompleted += 1;
	          score+=bonus;
	          bonus*=2;  // double the bonus for every additional line
	        }
	      }
	      
	      level = lineCompleted/3;  
	      if (level>30) { lineCompleted=0; level=0; }  // MAX LEVEL
	   
	    }
	    
	    
	   
	    
	    public void FallingToken(){
	       x=5;y=0;
	      
	   
	   
	        tokenNumber = (int) (7*Math.random());
	        rotationNumber = (int) (4*Math.random());
	   
	   
	   
	        xArray = xRotationArray[tokenNumber][rotationNumber];
	        yArray = yRotationArray[tokenNumber][rotationNumber];
	   
	      if (!isValidPosition(x,y,tokenNumber,rotationNumber)) 
	      {
	        gameOver=true;
	        drawToken(x,y,xArray,yArray);
	        return;
	      }
	   
	      drawToken(x,y,xArray,yArray);
	    }
	    
	    int x2=0,xn2=0;
	    //!reachFloor
	   public void shiftDown(){

		      eraseToken(x,y,xArray,yArray);
		 
		      // add keyboard control
		      if (leftPressed && isValidPosition(x-1,y,tokenNumber,rotationNumber))xn2++; if(xn2==3){x-= 1;xn2=0;}
		      if (rightPressed && isValidPosition(x+1,y,tokenNumber,rotationNumber)) x2++;if(x2==3){x += 1;x2=0;}
		      if (downPressed && isValidPosition(x,y+1,tokenNumber,rotationNumber)) y += 1;
		      if (spacePressed && isValidPosition(x,y,tokenNumber,(rotationNumber+1)%4)) 
		      {
		        rotationNumber = (rotationNumber+1)%4;
		        xArray = xRotationArray[tokenNumber][rotationNumber];
		        yArray = yRotationArray[tokenNumber][rotationNumber];
		        spacePressed=false;  
		      }
		 
		      int f=31-level;   // fall for every 31 frames, this value is decreased when level up
		      if (frame % f==0) y += 1;  
		      if (!isValidPosition(x,y,tokenNumber,rotationNumber)) // reached floor
		      {
		        reachFloor=true;
		        y -= 1;  // restore position
		      }
		      drawToken(x,y,xArray,yArray);
		      frame++;
	   }
	   
	   public void init(){
               guiNode.detachAllChildren();
               cam.setLocation(new Vector3f(80,38,53));
               
               if(velocidad==1)delay=15;
               else if(velocidad==2)delay=7;
               else if(velocidad==3)delay=7;

           }
	   public void printGameOver()
	   {
	     System.out.print("Perdedor");
	   }
                int cont=0;
                int [] complete = null; 
                
            public void tetris(){
             
            if (!gameOver){
                              if(cont==0){
                                      FallingToken();
                                      cont++;
                              }
                              else if(cont==1){
                                      if(!reachFloor){    
                                            try { Thread.sleep(delay); } catch (Exception ignore) {}
                                              shiftDown();
                                      }
                                      else{ 
                                              cont++;
                                              reachFloor=false;
                                      }
                              }
                              else if(cont==2){
                                      complete=checkRowCompletion();
                                      clearCompleteRow(complete);
                                      cont++;
                              }
                              else if(cont==3){
                                  try { Thread.sleep(100); } catch (Exception ignore) {}
                                      shiftDownComplete(complete);
                                      cont=0;
                              }
                              else{

                              }

                              
                            paint();
                          }else printGameOver();
            
            movecamera();
        }
            
            public void movecamera(){
            }
            
            
            public void paint(){
                        rootNode.detachAllChildren();
                         rootNode.attachChild(makebase(62, 0, -40f));
                         
		    for (int x1=0;x1<occupied.length;x1++)
		      for (int y1=0;y1<occupied[0].length;y1++)
		        if (occupied[x1][y1]==1)
		        {
		         rootNode.attachChild(makecube(80-(x1*4),80-(y1*4),-40f)); 
                           
		        }
		       // matrix();
	    }
            
            public Spatial makecube(float x, float y, float z){
                Spatial cube = assetManager.loadModel("Models/tetrisbox.obj");
                Material mat_default = new Material( 
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat_default.setTexture("ColorMap", 
                    assetManager.loadTexture(tex));
                cube.setMaterial(mat_default);
                cube.setLocalScale(.1f);
                //cube.setTexture("ColorMap", 
                //    assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
                cube.setLocalTranslation(x,y,z);   
                return cube;
        }
            
            public Spatial makebase(float x, float y, float z){
                Spatial cube = assetManager.loadModel("Models/base.obj");
                Material mat_default = new Material( 
                assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                mat_default.setTexture("ColorMap", 
                    assetManager.loadTexture("Textures/base.jpg"));
                cube.setMaterial(mat_default);
                cube.setLocalScale(.1f);
                //cube.setTexture("ColorMap", 
                //    assetManager.loadTexture("Textures/Terrain/BrickWall/BrickWall.jpg"));
                cube.setLocalTranslation(x,y,z);   
                return cube;
        }
            

    @Override
    public void simpleInitApp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
        
    
}
