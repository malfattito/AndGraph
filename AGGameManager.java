/********************************************
 Class: AGGameManager
 Description: Main manager of AG
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Used packages

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class AGGameManager implements Renderer
{
	//Attibutes
	public Activity vrActivity = null;
	public static GL10 vrOpenGL = null;
	private GLSurfaceView vrDrawSurface = null;
	private ArrayList<AGScene> vrScenes = null;
	private int FRAME_SIZE = 1;
	private int iPause = 20;
	private AGScene vrCurrentScene = null;
	
	/********************************************
	* Name: AGGameManager()
	* Description: Class constructor
	* Parameters: Activity, boolean
	* Returns: none
	******************************************/
	public AGGameManager(Activity pActivity, boolean bAccelerometer)
	{
		//Creates a SurfaceView used to draw over the screen
		 vrDrawSurface = new GLSurfaceView(pActivity);
        
        //Set the fullscreen mode
        pActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		                  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		pActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        //Inits the texture and sound managers
        AGTextureManager.init(pActivity);
		AGSoundManager.init(pActivity);
		AGInputManager.init(vrDrawSurface);
		
		//Create the ArrayList responsable to keep the references to Scenes
		vrScenes = new ArrayList<AGScene>();
		
		//Assign the Activity local reference
		vrActivity = pActivity;
		
		//Sets the renderer of surface
		vrDrawSurface.setRenderer(this);
		
		//Sets this content to surface
		vrActivity.setContentView(vrDrawSurface);
		
		//Tests if Accelerometer was requested
		if (bAccelerometer)
		{
			AGInputManager.vrAccelerometer.init(vrActivity);
		}
	}
	
	/*******************************************
	* Name: init()
	* Description: inits game manager resources
	* Parameters: GL10, int, int
	* Returns: none
	******************************************/
	private void init(GL10 pOpenGL, int pWidth, int pHeight)
	{	
		//If size still same return
		if (AGScreenManager.iScreenWidth == pWidth && AGScreenManager.iScreenHeight == pHeight)
			return;
		
		//Assign a local reference to OpenGL
		vrOpenGL = pOpenGL;
		
		//Set the viewport
		vrOpenGL.glViewport(0, 0, pWidth, pHeight);
				
		//Store the screen size
		AGScreenManager.setScreenSize(pWidth, pHeight);
										
		//Activates the Projection Matrix to configure a rendering volume
		vrOpenGL.glMatrixMode(GL10.GL_PROJECTION);
										
		//Init the projection matrix with identity
		vrOpenGL.glLoadIdentity();
										
		//Sets the volume rendering
		vrOpenGL.glOrthof(0.0f, pWidth, 0.0f, pHeight,  1.0f, -1.0f);
								
		//Activates the ModelView Matrix
		vrOpenGL.glMatrixMode(GL10.GL_MODELVIEW);
													
		//Inits the ModelView matrix with identity
		vrOpenGL.glLoadIdentity();
								
		//Config defaults to color draw and blending parameters
		vrOpenGL.glColor4f(1.0f, 1.0f,1.0f,1.0f);

		vrOpenGL.glEnable(GL10.GL_BLEND);
		vrOpenGL.glEnable(GL10.GL_ALPHA_TEST);
		vrOpenGL.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		//Enables use of Texture 2D, VertexArray and Coord Array
		vrOpenGL.glEnable(GL10.GL_TEXTURE_2D);
		vrOpenGL.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vrOpenGL.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
								
		//Send the vertex array to OpenGL
		vrOpenGL.glVertexPointer(2, GL10.GL_FLOAT, 0,
				AGNioBuffer.generateNioBuffer(new float[]{	-FRAME_SIZE, -FRAME_SIZE, 
														    -FRAME_SIZE,  FRAME_SIZE, 
															 FRAME_SIZE, -FRAME_SIZE, 
															 FRAME_SIZE,  FRAME_SIZE}));
		//Try if existe a scene registered
		if (vrCurrentScene != null)
		{
			//Inits the current scene
			if (!vrCurrentScene.bSceneStarted)
			{
				vrCurrentScene.init();
				vrCurrentScene.bSceneStarted = true;
			}
			else
			{
				vrOpenGL.glClearColor(vrCurrentScene.fRed, vrCurrentScene.fGreen, vrCurrentScene.fBlue, 1.0f);
				vrCurrentScene.reloadSpritesTextures();
				vrCurrentScene.restart();
			}
		}
		
		//Reset the time manager
		AGTimeManager.restart();
	}
	
	/*******************************************
	* Name: addScene()
	* Description: add a new scene to game manager
	* Parameters: AGScene
	* Returns: none
	******************************************/
	public void addScene(AGScene pNewScene)
	{
		//If the first scene, set currentScene
		if (vrScenes.size() == 0)
		{
			vrScenes.add(pNewScene);
			vrCurrentScene = pNewScene;
			return;
		}
		
		vrScenes.add(pNewScene);
	}
	
	/*******************************************
	* Name: setCurrentScene()
	* Description: set the current render scene
	* Parameters: int
	* Returns: none
	******************************************/
	public void setCurrentScene(int iIndex)
	{	
		//Try if scene index is valid
		if (iIndex >= 0 && iIndex < vrScenes.size())
		{
			if (vrCurrentScene == vrScenes.get(iIndex))
				return;
			
			vrCurrentScene.release();
			System.gc();
			vrCurrentScene.bSceneStarted = false;
			vrCurrentScene = vrScenes.get(iIndex);
			vrCurrentScene.init();
			vrCurrentScene.bSceneStarted = true;
			AGInputManager.vrTouchEvents.bBackButtonClicked = false;
		}
	}
	
	/*******************************************
	* Name: onPause()
	* Description: used to pause application
	* Parameters: none
	* Returns: none
	******************************************/
	public void onPause()
	{
		//Pause the scene execution
		if (vrCurrentScene != null)
		{
			vrCurrentScene.stop();
			vrCurrentScene.releaseSpritesTextures();
		}
		
		//Accelerometer pause
		AGInputManager.vrAccelerometer.onPause();
		
		//Reset screen size
		AGScreenManager.setScreenSize(0, 0);
		vrDrawSurface.onPause();
	}
	
	/*******************************************
	* Name: onResume()
	* Description:  used to restart application
	* Parameters: none
	* Returns: none
	******************************************/
	public void onResume()
	{
		//Restart scene execution
		AGInputManager.vrAccelerometer.onResume();
		AGTimeManager.restart();
		vrDrawSurface.onResume();
	}
	
	/*******************************************
	* Name: pause()
	* Description: used to pause renderer
	* Parameters: none
	* Returns: none
	******************************************/
	protected void pause()
	{
		try
		{
			Thread.sleep(iPause);
		}
		catch(Exception e)
		{}
	}
	
	/*******************************************
	* Name: setPause()
	* Description: used to set pause timer
	* Parameters: int
	* Returns: none
	******************************************/
	public void setPauseTime(int pPause)
	{
		if (pPause > 0)
		{
			iPause = pPause;
		}
	}

	/*******************************************
	* Name: onSurfaceCreated()
	* Description: used to create surface
	* Parameters: GL10, EGLConfig
	* Returns: none
	******************************************/
	public void onSurfaceCreated(GL10 vrOpenGL, EGLConfig vrConfig)
	{	
	}
	
	/*******************************************
	* Name: onSurfaceChanged()
	* Description: used to change surface
	* Parameters: GL10, int, int
	* Returns: none
	******************************************/
	public void onSurfaceChanged(GL10 vrOpenGL, int pWidth, int pHeight)
	{
		init(vrOpenGL, pWidth, pHeight);	
	}
	
	/*******************************************
	* Name: onDrawFrame()
	* Description: used to draw a frame of current scene
	* Parameters: GL10
	* Returns: none
	******************************************/
	public void onDrawFrame(GL10 vrOpenGL)
	{
		vrOpenGL.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (vrCurrentScene== null)
			return;
		
		AGTimeManager.update();
		
		vrCurrentScene.loop();
		vrCurrentScene.render();
		
		AGInputManager.vrTouchEvents.update();
		
		pause();
	}
	
	/*******************************************
	* Name: release()
	* Description:  used to release the engine
	* Parameters: none
	* Returns: none
	******************************************/
	public void release()
	{
		releaseScenes();
		AGTextureManager.release();
		AGSoundManager.release();
		AGInputManager.release();
		vrDrawSurface = null;
		vrCurrentScene = null;
		vrOpenGL = null;
		vrActivity = null;
	}
	
	/*******************************************
	* Name: releaseScenes()
	* Description:  used to release all scenes
	* Parameters: none
	* Returns: none
	******************************************/
	public void releaseScenes()
	{
		for (int iIndex=0; iIndex < vrScenes.size(); iIndex++)
		{
			vrScenes.get(iIndex).release();
		}
		vrScenes.clear();
		vrScenes = null;
	}
}
