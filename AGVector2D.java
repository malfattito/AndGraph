/********************************************
 Class: AGTouchScreen
 Description: handle TouchScreen events
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

public class AGVector2D 
{
	//Attributes
	public float fX;
	public float fY;
	
	/*******************************************
	* Name: AGVector2D()
	* Description: constructor
	* Parameters: none
	* Returns: none 
	******************************************/
	public AGVector2D()
	{
		setXY(0.0f, 0.0f);
	}
	
	/********************************************
	* Name: AGVector2D()
	* Description: constructor
	* Parameters: none
	* Returns: none
	******************************************/
	public AGVector2D(float pX, float pY)
	{
		setXY(pX, pY);
	}
	
	/*******************************************
	* Name: getX()
	* Description: returns X value
	* Parameters: none
	* Returns: float
	******************************************/
	public float getX()
	{
		return fX;
	}
	
	/*******************************************
	* Name: getY()
	* Description: returns Y value
	* Parameters: none
	* Returns: float
	******************************************/
	public float getY()
	{
		return fY;
	}
	
	/*******************************************
	* Name: setX()
	* Description: set X value
	* Parameters: float
	* Returns: none
	******************************************/
	public void setX(float pX)
	{
		fX = pX;
	}
	
	/*******************************************
	* Name: setY()
	* Description: set Y value
	* Parameters: float
	* Returns: none
	******************************************/
	public void setY(float pY)
	{
		fY = pY;
	}
	
	/*******************************************
	* Name: setXY()
	* Description: set X and Y values
	* Parameters: float, float
	* Returns: none
	******************************************/
	public void setXY(float pX, float pY)
	{
		fX = pX;
		fY = pY;
	}
	
	/***********************************************************
	*Nome: release()
	*Description: release resources
	*Parameters: None
	*Returns: None
	************************************************************/
	public void release()
	{}

	/***********************************************************
	*Nome: equals()
	*Description: compare two objetcs of CAGVector2D
	*Parameters: Object
	*Return: boolean
	************************************************************/
	public boolean equals(Object vrObjeto)
	{
		if (vrObjeto == this)
		{
			return true;
		}
		
		if (!(vrObjeto instanceof AGVector2D) )
		{
			return false;
		}
		
		return (((AGVector2D)vrObjeto).fX == fX && ((AGVector2D)vrObjeto).fY == fY);
	}
}
