/***********************************************************************
*Name: JGFrameIndex
*Description: represents a frame that will compose one animation sequence
*Author: Silvano Malfatti
*Date: 01/05/20
************************************************************************/

//Package declaration
package game.curso.cursogamesandroid2d.AndGraphics;

public class AGFrameIndex
{
	//Class attributes
	private int frameIndex = 0;
	
	/***********************************************************
	*Name: JFrameIndex
	*Description: No parameters constructor
	*Parameters: None
	*Return: None
	************************************************************/
	public AGFrameIndex()
	{
		frameIndex = 0;
	}
	
	/***********************************************************
	*Name: setFrameIndex()
	*Description: quadIndex setter
	*Parameters: int
	*Return: none
	************************************************************/
	public void setFrameIndex(int index)
	{
		frameIndex = index;
	}
	
	/***********************************************************
	*Name: getQuadIndex()
	*Description: quadIndex getter
	*Parameters: none
	*Return: int
	************************************************************/
	public int getFrameIndex()
	{
		return frameIndex;
	}
	
	/*******************************************
   	* Name: free
   	* Description: free resources
   	* Parameters: none
   	* Returns: none
   	******************************************/
    public void free() 
    {
    }
}
