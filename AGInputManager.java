/********************************************
 Class: AGInputManager
 Description: handle application events
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Used packages
import android.opengl.GLSurfaceView;


public class AGInputManager
{
	//Public static attributes do handle input events
	public static AGTouchScreen vrTouchEvents = null;
	public static AGAccelerometer vrAccelerometer = null;
	
	/********************************************
	* Name: init()
	* Description: used to init input resources
	* Parameters: GLSurfaceView
	* Returns: none
	******************************************/
	public static void init(GLSurfaceView vrDrawSurface)
	{
		//Inits accelerometer and TouchScreen objects
		vrAccelerometer = new AGAccelerometer();
		
		vrTouchEvents = new AGTouchScreen();
		
		//Register the Surface draw on the TouchListener class
      	vrDrawSurface.setOnTouchListener(AGInputManager.vrTouchEvents);
	}
	
	/*******************************************
	* Name: Release()
	* Description: used to free resources
	* Parameters: none
	* Returns: none
	******************************************/
	public static void release()
	{
		vrAccelerometer.release();
		vrTouchEvents = null;
		vrAccelerometer = null;
	}
}
