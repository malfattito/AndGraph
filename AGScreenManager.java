/********************************************
 Class: AGScreenManager
 Description: used to handle screen attibutes
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

public class AGScreenManager
{
	//Attributes
	public static int iScreenWidth = 0;
	public static int iScreenHeight = 0;
	
	/********************************************
	* Name: setScreenSize()
	* Description: store the new screen size
	* Parameters: int, int
	* Returns: none
	******************************************/
	public static void setScreenSize(int pWidth, int pHeight)
	{
		iScreenWidth = pWidth;
		iScreenHeight = pHeight;
	}
}
