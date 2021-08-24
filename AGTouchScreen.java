/********************************************
 Class: AGTouchScreen
 Description: handle TouchScreen events
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package game.curso.cursogamesandroid2d.AndGraphics;

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
	private boolean clickState = false;
	private boolean clickPrevState = false;
	
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

		if (iCurrentEvent == MotionEvent.ACTION_UP)
		{
			clickPrevState = clickState;
			clickState = false;
		}
		else if (iCurrentEvent == MotionEvent.ACTION_DOWN)
		{
			clickState = true;
		}
		else if (iCurrentEvent == MotionEvent.ACTION_MOVE)
		{
			clickPrevState = false;
		}

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
		boolean res = (clickPrevState && !clickState);

		clickPrevState = false;

		return res;
	}
	
	/*******************************************
	* Name: screenDow()
	* Description: used to test screen press
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean screenDown()
	{
		return clickState;
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
	public void reset()
	{
		clickPrevState = false;
		clickState = false;
		bBackButtonClicked = false;
	}	
}
