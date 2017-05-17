/********************************************
 Class: AGAccelerometer
 Description: Used to handler accel events
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Used Packages
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.WindowManager;

public class AGAccelerometer implements SensorEventListener
{
	//Class Attributes
	public float fAccelX = 0.0f, fAccelY = 0.0f, fAccelZ = 0.0f;
	private int iRotation = 0;
	private Display display = null;
	private int[] referenceVector = null;
	private boolean bAccelerated = false;
	private SensorManager vrSensorManager = null;
	private Sensor vrSensor = null;
	
	//Constants of Accelerometer (rotation array)
    private final int axisSwap[][] = {
    { -1,  -1,  0,  1  },     // ROTATION_0 
    {  1,  -1,  1,  0  },     // ROTATION_90 
    {  1,   1,  0,  1  },     // ROTATION_180 
    { -1,   1,  1,  0  }  }; // ROTATION_270 
	
	/*******************************************
	* Name: init()
	* Description: used to init accel resources
	* Parameters: Activity
	* Returns: none
	******************************************/
	public void init(Activity vrActivity)
	{
		//Gets a reference for the Android sensor manager
		vrSensorManager = (SensorManager)vrActivity.getSystemService(Context.SENSOR_SERVICE);
		
		//Discover if exists at least one sensor at device
		bAccelerated = vrSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() > 0;
		
		//If a sensor was located, gets a reference
		if(bAccelerated)
		{
			vrSensor = vrSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
			
			//Store the screen size
			display = ((WindowManager)vrActivity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			iRotation  = display.getRotation();
			
			//Init a rotation array
			referenceVector = axisSwap[iRotation]; 
		}
	}

	/*******************************************
	* Name: onSensorChanged()
	* Description: used to get accel events
	* Parameters: SendorEvent
	* Returns: none
	******************************************/
	public void onSensorChanged(SensorEvent event)
	{
		fAccelX =  (float)referenceVector[0] * event.values[referenceVector[2]]; 
	    fAccelY =  (float)referenceVector[1] * event.values[referenceVector[3]]; 
	    fAccelZ =  event.values[2];
	}
	
	/*******************************************
	* Name: onAccuracyChanged()
	* Description: used to get acuracy changed events
	* Parameters: Sensor, int
	* Returns: none
	******************************************/
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
	
	/*******************************************
	* Name: getAccelX()
	* Description: used to get X accel
	* Parameters: None
	* Returns: float
	******************************************/
	public float getAccelX()
	{
		return fAccelX;
	}
	
	/*******************************************
	* Name: getAccelY()
	* Description: used to get Y accel
	* Parameters: None
	* Returns: float
	******************************************/
	public float getAccelY()
	{
		return fAccelY;
	}
		
	/*******************************************
	* Name: getAccelZ()
	* Description: used to get z accel
	* Parameters: None
	* Returns: float
	******************************************/
	public float getAccelZ()
	{
		return fAccelZ;
	}
	
	/*******************************************
	* Name: isAccelerated()
	* Description: used to determ if app is accel or no
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean isAccelerated()
	{
		return bAccelerated;
	}
	
	/*******************************************
	* Name: onPause()
	* Description: used to call a pause
	* Parameters: none
	* Returns: none
	******************************************/
	public void onPause()
	{
		if (bAccelerated)
		{
			vrSensorManager.unregisterListener(this);
		}
	}
	
	/*******************************************
	* Name: onResume()
	* Description: used to resume Accel configs
	* Parameters: none
	* Returns: none
	******************************************/
	public void onResume()
	{
		if (bAccelerated)
		{
			vrSensorManager.registerListener(this, vrSensor, SensorManager.SENSOR_DELAY_GAME);
		}
	}
	
	/*******************************************
	* Name: release()
	* Description: free references
	* Parameters: none
	* Returns: none
	******************************************/
	public void release()
	{
		display = null;
		referenceVector = null;
		vrSensorManager = null;
		vrSensor = null;
	}
}
