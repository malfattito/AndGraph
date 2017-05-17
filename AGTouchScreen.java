/********************************************
 Class: AGTouchScreen
 Description: handle TouchScreen events
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Used packages
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class AGTouchScreen implements OnTouchListener
{
	//Attributes
	public float fPosX = 0, fPosY = 0;
	private int iCurrentEvent = 0;
	public boolean bBackButtonClicked = false;
	
	/********************************************
	* Name: getLasPosition()
	* Description: returns the last event position
	* Parameters: none
	* Returns: CAGVector2D
	******************************************/
	public AGVector2D getLastPosition()
	{
		return new AGVector2D(fPosX, fPosY);
	}
	
	/*******************************************
	* Name: onTouch()
	* Description: method defined by OnTouchListener interface
	* Parameters: View, MotionEvent
	* Returns: boolean
	******************************************/
	public boolean onTouch(View vrComponente, MotionEvent vrEventoMovimento)
	{
		//Store the last generated event
		iCurrentEvent = vrEventoMovimento.getAction();
		fPosX = vrEventoMovimento.getX();
		fPosY = AGScreenManager.iScreenHeight -  vrEventoMovimento.getY();
		
		return true;
	}
	
	/*******************************************
	* Name: screenClicked()
	* Description: used to test screen click
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean screenClicked()
	{
		//Try if Screen was clicked
		if (iCurrentEvent == MotionEvent.ACTION_UP)
		{
			return true;
		}
		
		return false;
	}
	
	/*******************************************
	* Name: screenDow()
	* Description: used to test screen press
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean screenDown()
	{
		//Try if Screen was pressed
		if (iCurrentEvent == MotionEvent.ACTION_DOWN)
		{
			return true;
		}
		
		return false;
	}
	
	/*******************************************
	* Name: screenDragged()
	* Description: used to test screen drag
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean screenDragged()
	{
		//Try if Screen was clicked
		if (iCurrentEvent == MotionEvent.ACTION_MOVE)
		{
			return true;
		}
		
		return false;
	}
	
	/*******************************************
	* Name: backButtonClicked()
	* Description: used to test if back button was pressed
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean backButtonClicked()
	{
		if (bBackButtonClicked)
		{
			bBackButtonClicked = false;
			return true;
		}
		
		return false;
	}
	
	/*******************************************
	* Name: update()
	* Description: updates touchscreen data
	* Parameters: none
	* Returns: none
	******************************************/
	public void update()
	{
		if (iCurrentEvent == MotionEvent.ACTION_UP)
			iCurrentEvent = -1;	
	}	
}
