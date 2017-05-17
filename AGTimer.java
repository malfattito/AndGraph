/************************************************
 Class: AGTimer
 Description: used to handle any interval of time
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ************************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

public class AGTimer 
{
	//Attributes
	public int iEndTime;
	public int iCurrentTime; 
	
	/*******************************************
	* Name: AGTimer()
	* Description: class constructor
	* Parameters: none
	* Returns: none
	******************************************/
	public AGTimer()
	{
		iCurrentTime = 0;
		iEndTime = 1000;
	}
	
	/********************************************
	* Name: AGTimer()
	* Description: class constructor
	* Parameters: int
	* Returns: none
	******************************************/
	public AGTimer(int pInterval)
	{
		iCurrentTime = 0;
		iEndTime = pInterval;
	}
	
	/*******************************************
	* Name: update()
	* Description: performs a update on timer according the last frame time
	* Parameters: none
	* Returns: none
	******************************************/
	public void update()
	{
		iCurrentTime += (int)AGTimeManager.getCurrentFrameTime();
	}
	
	/*******************************************
	* Name: isTimeEnded()
	* Description: used to test if time is ended
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean isTimeEnded()
	{
		if (iCurrentTime >= iEndTime)
			return true;

		return false;
	}
	
	/*******************************************
	* Name: restart()
	* Description: restart timer with previous interval
	* Parameters: none
	* Returns: none
	******************************************/
	public void restart()
	{
		iCurrentTime = 0;
	}
	
	/*******************************************
	* Name: restart()
	* Description: restart timer with a new interval
	* Parameters: int
	* Returns: none
	******************************************/
	public void restart(int pNewTime)
	{
		iCurrentTime = 0;
		iEndTime = pNewTime;
	}
	
	/*******************************************
	* Name: getCurrentTime()
	* Description: returns the current time value
	* Parameters: none
	* Returns: int
	******************************************/
	public int getCurrentTime()
	{
		return iCurrentTime;
	}
	
	/*******************************************
	* Name: getEndTime()
	* Description: returns the end time value
	* Parameters: none
	* Returns: int
	******************************************/
	public int getEndTime()
	{
		return iEndTime;
	}
}
