/********************************************
 Class: AGTimerManager
 Description: used to handle all timers update
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

public class AGTimeManager 
{
	//Attibutes
	private static long 	lLastFrameTime = 0;
	private static long 	lCurrentFrameTime = 0;
	private static long 	lCurrentTime = 0;
	private static final int MININTERVAL = 20;
	
	/*******************************************
	* Name: AGTimeManager()
	* Description: class constructor
	* Parameters: none
	* Returns: none
	*******************************************/
	private AGTimeManager()
	{ 
		lLastFrameTime = System.currentTimeMillis();
		lCurrentFrameTime = 0;
	}
	
	/*******************************************
	* Name: getCurrentFrameTime()
	* Description: returns the current frame time
	* Parameters: none
	* Returns: long
	******************************************/
	public static long getCurrentFrameTime()
	{
		return lCurrentFrameTime;
	}
	
	/*******************************************
	* Name: restart()
	* Description: restart the last frame time
	* Parameters: none
	* Returns: none
	******************************************/
	public static void restart()
	{
		lLastFrameTime = System.currentTimeMillis();
	}
	
	/*******************************************
	* Name: update()
	* Description: handle time events
	* Parameters: none
	* Returns: none
	******************************************/
	public static void update()
	{	
		//Verify the minimum frame time
		do
		{
			lCurrentTime = System.currentTimeMillis();
			lCurrentFrameTime = (lCurrentTime > lLastFrameTime) ? (lCurrentTime - lLastFrameTime) : 0;
			lLastFrameTime = (lCurrentTime >= lLastFrameTime) ? lLastFrameTime : lCurrentTime;
		}
		while(!(lCurrentFrameTime >= MININTERVAL));
		
		lLastFrameTime = lCurrentTime;
	}
}
