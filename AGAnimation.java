/********************************************
 Class: AGAnimation
 Description: Used to handle animations
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

public class AGAnimation 
{
	//Atributes
	private int iFPS;
	private int iCurrentIndex;
	private boolean bRepeat;
	private AGTimer vrTimer = null; 
	private int[] vetFrames = null;
	
	/*******************************************
	* Name: AGAnimation()
	* Description: class constructor
	* Parameters: int[]
	* Returns: none
	*******************************************/
	public AGAnimation(int[] pFrames)
	{
		iFPS = 1;
		iCurrentIndex = 0;
		vetFrames = pFrames;
		bRepeat = true;
		vrTimer = new AGTimer();
	}
	
	/*******************************************
	* Name: setRepeat()
	* Description: used to set repetion mode
	* Parameters: boolean
	* Returns: none
	******************************************/
	public void setRepeat(boolean pRepeat)
	{
		bRepeat = pRepeat;
	}
	
	/*******************************************
	* Name: getTotalFrames()
	* Description: returns a count with total frames
	* Parameters: none
	* Returns: int
	******************************************/
	public int getTotalFrames()
	{
		return vetFrames.length;
	}
	
	/*******************************************
	* Name: isAnimationEnded()
	* Description: class constructor
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean isAnimationEnded()
	{
		if (bRepeat)
		{
			return false;
		}
		
		return (iCurrentIndex >= vetFrames.length);
	}
	
	/*******************************************
	* Name: getCurrentFrame()
	* Description: returns the current frame index
	* Parameters: none
	* Returns: int
	******************************************/
	public int getCurrentFrame()
	{
		if (iCurrentIndex >= 0 && iCurrentIndex < vetFrames.length)
		{
			return vetFrames[iCurrentIndex];
		}
		
		return vetFrames[vetFrames.length-1];
	}
	
	/*******************************************
	* Name: restart()
	* Description: restart animation
	* Parameters: none
	* Returns: none
	******************************************/
	public void restart()
	{
		vrTimer.restart(((iFPS > 0) ? 1000 / iFPS : 1));
		iCurrentIndex = 0;
	}
	
	/*******************************************
	* Name: seFPS()
	* Description: change timer interval
	* Parameters: int
	* Returns: none
	******************************************/
	public void setFPS(int pFPS)
	{
		iFPS = pFPS;
		vrTimer.restart((iFPS>0) ? 1000/iFPS : 1);
	}
	
	/*******************************************
	* Name: update()
	* Description: update animation data
	* Parameters: none
	* Returns: none
	******************************************/
	public void update()
	{
		vrTimer.update();
		if (vrTimer.isTimeEnded())
		{
			if(bRepeat)
			{
				iCurrentIndex++;
				iCurrentIndex %= vetFrames.length;
			}
			else
			{
				iCurrentIndex += (iCurrentIndex < vetFrames.length) ? 1: 0;
			}
			vrTimer.restart();
		}
	}
	
	/*******************************************
	* Name: release()
	* Description: free resources
	* Parameters: none
	* Returns: none
	******************************************/
	public void release()
	{
		vetFrames = null;
		vrTimer = null;
	}
}
