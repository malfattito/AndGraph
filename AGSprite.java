/********************************************
 Class: AGSprite
 Description: Used to create visual and animated objects
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Used Packages

import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class AGSprite 
{
	//Constants of the class
	public static final int NONE = 0, HORIZONTAL = 1, VERTICAL = 2, VERTICAL_HORIZONTAL = 3;
	
	//Public attributes
	public AGVector2D vrDirection = null;
	public AGVector2D vrPosition = null;
	public AGVector2D vrScale = null;
	public int iFrameWidth = 0, iFrameHeight = 0;
	public float fAlpha = 1.0f;
	public float fAngle = 0.0f;
	public int iMirror = NONE;
	public boolean bVisible = true;
	public boolean bRecycled = false;
	public boolean bAutoRender = true;
	
	//Private attributes
	private ArrayList<AGAnimation> vetAnimations = null;
	private int iImageCode = 0;
	private int iImageWidth = 0;
	private int iImageHeight = 0;
	private int iCurrentAnimation = 0;
	private int iTotalCol = 0;
	private int iTotalLin = 0;
	private float fCoordX1 = 0;
	private float fCoordY1 = 0;
	private float fCoordX2 = 0;
	private float fCoordY2 = 0;
	private float fFadeTo = 0;
	private FloatBuffer[][] vetTextures = null;
	private float[] vetCoords = null;
	private GL10 vrOpenGL = null;
	private AGVector2D vrInitMove = null;
	private AGVector2D vrEndMove = null;
	private AGTimer vrMoveTimer = null;
	private AGTimer vrFadeTimer = null;
	private boolean bIsFadeOut = false;
	private int iTextureCode = 0;
	private int iCurrentFrame = 0;
	
	
	/*******************************************
	* Name: AGSprite()
	* Description: Sprite constructor
	* Parameters: GL10, int, int, int, int, int
	* Returns: none
	******************************************/
	public AGSprite(GL10 pOpenGL, int pCodImage, int pFrameWidth, int pFrameHeight, int pImageWidth, int pImageHeight)
	{
		vrOpenGL = pOpenGL;
		iFrameWidth = 	pFrameWidth;
		iFrameHeight = 	pFrameHeight;
		iImageWidth = 	pImageWidth;
		iImageHeight = 	pImageHeight;
		iTotalCol = iImageWidth / iFrameWidth;
		iTotalLin = iImageHeight / iFrameHeight;
		iImageCode = pCodImage;
		iTextureCode = 	AGTextureManager.loadTexture(vrOpenGL, iImageCode);
		vetAnimations = new ArrayList<AGAnimation>();
		vrDirection = new AGVector2D();
		vrPosition = new AGVector2D();
		vrInitMove = new AGVector2D();
		vrEndMove = new AGVector2D();
		vrScale = new AGVector2D(1.0f,1.0f);
		vrFadeTimer = new AGTimer();
		vrMoveTimer = new AGTimer();
		vrMoveTimer.restart(0);
		vrFadeTimer.restart(0);
		generateFrames();
	}
	
	/*******************************************
	* Name: AGSprite()
	* Description: Sprite constructor
	* Parameters: GL10, int, int, int
	* Returns: none
	******************************************/
	public AGSprite(GL10 pOpenGL, int pCodImage, int pTotalCol, int pTotalLin)
	{
		vrOpenGL = pOpenGL;
		iTotalCol = pTotalCol;
		iTotalLin = pTotalLin;
		iImageCode = pCodImage;
		iTextureCode = 	AGTextureManager.loadTexture(vrOpenGL, iImageCode);
		iImageWidth = 	AGTextureManager.getTextureData(iImageCode).iWidth;
		iImageHeight = 	AGTextureManager.getTextureData(iImageCode).iHeight;
		iFrameWidth = 	iImageWidth / iTotalCol;
		iFrameHeight = 	iImageHeight / iTotalLin;
		vetAnimations = new ArrayList<AGAnimation>();
		vrDirection = new AGVector2D();
		vrPosition = new AGVector2D();
		vrInitMove = new AGVector2D();
		vrEndMove = new AGVector2D();
		vrScale = new AGVector2D(1.0f,1.0f);
		vrFadeTimer = new AGTimer();
		vrMoveTimer = new AGTimer();
		vrMoveTimer.restart(0);
		vrFadeTimer.restart(0);
		generateFrames();
	}
	
	/*******************************************
	* Name: getTextureCode()
	* Description: returns texture code
	* Parameters: none
	* Returns: int
	******************************************/
	public int getTextureCode()
	{
		return iTextureCode;
	}
	
	/*******************************************
	* Name: reloadTexture()
	* Description: reload a texture image id
	* Parameters: GL10
	* Returns: none
	******************************************/
	public void reloadTexture(GL10 pOpenGL)
	{
		vrOpenGL = pOpenGL;
		iTextureCode = AGTextureManager.loadTexture(vrOpenGL, iImageCode);
	}
	
	/*******************************************
	* Name: generateFrames()
	* Description: used to generate texture coords frames
	* Parameters: none
	* Returns: none
	******************************************/
	private void generateFrames()
	{
		vetCoords = new float[8];
		vetTextures = new FloatBuffer[1 + (iTotalCol * iTotalLin)][4];
		
		for (int iIndex = 0; iIndex < vetTextures.length - 1; iIndex++)
		{
			//Calc the frame coords
			fCoordX1 = (iIndex % iTotalCol) * (1.0f / iTotalCol);
			fCoordY1 = (iIndex / iTotalCol) * (1.0f/ iTotalLin);
			fCoordX2 = fCoordX1 + 1.0f/(iImageWidth/iFrameWidth);
			fCoordY2 = fCoordY1 + 1.0f/(iImageHeight/iFrameHeight);
			
			//Normal mirror
			vetCoords[0] = fCoordX1;
			vetCoords[1] = fCoordY2;
			vetCoords[2] = fCoordX1;
			vetCoords[3] = fCoordY1;
			vetCoords[4] = fCoordX2;
			vetCoords[5] = fCoordY2;
			vetCoords[6] = fCoordX2;
			vetCoords[7] = fCoordY1;
			vetTextures[iIndex][NONE] = AGNioBuffer.generateNioBuffer(vetCoords);
				
			//Horizontal mirror
			vetCoords[0] = fCoordX2;
			vetCoords[1] = fCoordY2;
			vetCoords[2] = fCoordX2;
			vetCoords[3] = fCoordY1;
			vetCoords[4] = fCoordX1;
			vetCoords[5] = fCoordY2;
			vetCoords[6] = fCoordX1;
			vetCoords[7] = fCoordY1;	
			vetTextures[iIndex][HORIZONTAL] = AGNioBuffer.generateNioBuffer(vetCoords);
			
			//Vertical mirror
			vetCoords[0] = fCoordX1;
			vetCoords[1] = fCoordY1;
			vetCoords[2] = fCoordX1;
			vetCoords[3] = fCoordY2;
			vetCoords[4] = fCoordX2;
			vetCoords[5] = fCoordY1;
			vetCoords[6] = fCoordX2;
			vetCoords[7] = fCoordY2;
			vetTextures[iIndex][VERTICAL] = AGNioBuffer.generateNioBuffer(vetCoords);
			
			//Horizontal and Vertical mirror
			vetCoords[0] = fCoordX2;
			vetCoords[1] = fCoordY1;
			vetCoords[2] = fCoordX2;
			vetCoords[3] = fCoordY2;
			vetCoords[4] = fCoordX1;
			vetCoords[5] = fCoordY1;
			vetCoords[6] = fCoordX1;
			vetCoords[7] = fCoordY2;
			vetTextures[iIndex][VERTICAL_HORIZONTAL] = AGNioBuffer.generateNioBuffer(vetCoords);
		}
		
		//Normal mirrors without animation
		vetCoords[0] = 0;
		vetCoords[1] = 1;
		vetCoords[2] = 0;
		vetCoords[3] = 0;
		vetCoords[4] = 1;
		vetCoords[5] = 1;
		vetCoords[6] = 1;
		vetCoords[7] = 0;
		vetTextures[vetTextures.length - 1][NONE] = AGNioBuffer.generateNioBuffer(vetCoords);
		
		vetCoords[0] = 1;
		vetCoords[1] = 1;
		vetCoords[2] = 1;
		vetCoords[3] = 0;
		vetCoords[4] = 0;
		vetCoords[5] = 1;
		vetCoords[6] = 0;
		vetCoords[7] = 0;
		vetTextures[vetTextures.length - 1][HORIZONTAL] = AGNioBuffer.generateNioBuffer(vetCoords);
		
		vetCoords[0] = 0;
		vetCoords[1] = 0;
		vetCoords[2] = 0;
		vetCoords[3] = 1;
		vetCoords[4] = 1;
		vetCoords[5] = 0;
		vetCoords[6] = 1;
		vetCoords[7] = 1;
		vetTextures[vetTextures.length - 1][VERTICAL] = AGNioBuffer.generateNioBuffer(vetCoords);
		
		vetCoords[0] = 1;
		vetCoords[1] = 0;
		vetCoords[2] = 1;
		vetCoords[3] = 1;
		vetCoords[4] = 0;
		vetCoords[5] = 0;
		vetCoords[6] = 0;
		vetCoords[7] = 1;
		vetTextures[vetTextures.length - 1][VERTICAL_HORIZONTAL] = AGNioBuffer.generateNioBuffer(vetCoords);
	}
	
	/*******************************************
	* Name: setScreenPercent()
	* Description: usedo to calcule the size of sprite over a screen percent
	* Parameters: int, int
	* Returns: none
	******************************************/
	public void setScreenPercent(int pWidth, int pHeight)
	{
		float fPercentX = (AGScreenManager.iScreenWidth * pWidth) / 100;
		float fPercentY = (AGScreenManager.iScreenHeight * pHeight) / 100;
		
		vrScale.fX = (fPercentX / iFrameWidth) / 2;
		vrScale.fY = (fPercentY / iFrameHeight) / 2;
	}
	
	/*******************************************
	* Name: fade()
	* Description: used to init a fade transition
	* Parameters: float, float, int
	* Returns: none
	******************************************/
	private void fade(float pFadeFrom, float pFadeTo, int pTime)
	{
		vrFadeTimer.restart(pTime);
		fFadeTo = pFadeTo;
		fAlpha = pFadeFrom;
		bIsFadeOut = (pFadeFrom > pFadeTo);

		if (vrFadeTimer.iEndTime == 0)
		{
			fAlpha = pFadeTo;
		}
	}
	
	/***********************************************************
	*Name: FadeOut()
	*Description: decrease alpha
	*Params: int
	*Return: none
	************************************************************/
	public void fadeOut(int time)
	{
		fade(1.0f,0.0f,time);
	}

	/***********************************************************
	*Name: FadeIn()
	*Description: increase alpha
	*Params: int
	*Return: none
	************************************************************/
	public void fadeIn(int time)
	{
		bVisible = true;
		fade(0.0f,1.0f,time);
	}
	
	/***********************************************************
	*Name: fadeEnded()
	*Description: updates the alpha value
	*Params: none
	*Return: boolean
	************************************************************/
	public boolean fadeEnded()
	{
		return (vrFadeTimer.iEndTime == 0);
	}

	/***********************************************************
	*Name: UpdateFade()
	*Description: updates alpha value
	*Params: none
	*Return: none
	************************************************************/
	private void updateFade()
	{
		if (vrFadeTimer.iEndTime == 0)
			return;
		
		if (!vrFadeTimer.isTimeEnded())
		{				
			vrFadeTimer.update();
			fAlpha = (float)vrFadeTimer.iCurrentTime / vrFadeTimer.iEndTime;
			fAlpha = (bIsFadeOut) ? 1.0f - fAlpha : fAlpha;
		}
		else
		{
			fAlpha = fFadeTo;
			vrFadeTimer.restart(0);
			if (fAlpha == 0.0f)
			{
				bVisible = false;
			}
		}
	}
	
	/*******************************************
	* Name: addAnimation()
	* Description: usedo to create a new animation to Sprite
	* Parameters: int, boolean, int...
	* Returns: none
	******************************************/
	public void addAnimation(int iFPS, boolean bRepeat, int... pVetFrames)
	{
		//Creates and configure a Animation Object
		AGAnimation vrNewAnimation = new AGAnimation(pVetFrames);
		vrNewAnimation.setRepeat(bRepeat);
		vrNewAnimation.setFPS(iFPS);

		//Set the first animation as default
		vetAnimations.add(vrNewAnimation);
	}
	
	/*******************************************
	* Name: addAnimation()
	* Description: used to create a new animation to Sprite
	* Parameters: int, boolean, int, int
	* Returns: none
	******************************************/
	public void addAnimation(int iFPS, boolean bRepete, int initFrame, int endFrame)
	{
		//Try the correct sequency
		if (endFrame <= initFrame)
			return;
		
		//Creates the array frame
		int[] vetFrames = new int[1 + endFrame - initFrame];
		for (int iIndex = 0; iIndex < vetFrames.length; iIndex++)
		{
			vetFrames[iIndex] = initFrame + iIndex;
		}
		
		//Creates and configure the animation object
		AGAnimation vrNewAnimation= new AGAnimation(vetFrames);
		vrNewAnimation.setRepeat(bRepete);
		vrNewAnimation.setFPS(iFPS);

		//The first animation set as default
		vetAnimations.add(vrNewAnimation);
	}
	
	/*******************************************
	* Name: getTotalAnimations()
	* Description: returns a total animation int value
	* Parameters: none
	* Returns: int
	******************************************/
	public int getTotalAnimations()
	{
		return vetAnimations.size();
	}
	
	/*******************************************
	* Name: setCurrentAnimation()
	* Description: configures the current animation index
	* Parameters: int
	* Returns: none
	*******************************************/
	public void setCurrentAnimation(int iAnim)
	{
		//Test if Sprite has animations to be set
		if (vetAnimations.size() == 0)
		{
			return;
		}
		
		//Test the animation index
		if(iAnim != iCurrentAnimation && iAnim < vetAnimations.size())
		{
			iCurrentAnimation = iAnim;
			vetAnimations.get(iCurrentAnimation).restart();
		}
	}
	
	/*********************************************
	* Name: restartAnimation()
	* Description: restart current animation
	* Parameters: none
	* Returns: none
	******************************************/
	public void restartAnimation()
	{
		if (iCurrentAnimation < vetAnimations.size())
		{
			vetAnimations.get(iCurrentAnimation).restart();
		}
	}
	
	/*******************************************
	* Name: getCurrentAnimation()
	* Description: returns the current animation object
	* Parameters: none
	* Returns: AGAnimation
	******************************************/
	public AGAnimation getCurrentAnimation()
	{
		return vetAnimations.get(iCurrentAnimation);
	}
	
	
	/*******************************************
	* Name: getCurrentAnimationIndex()
	* Description: returns the index of current animation
	* Parameters: none
	* Returns: int
	******************************************/
	public int getCurrentAnimationIndex()
	{
		return iCurrentAnimation;
	}
	
	/*******************************************
	* Name: getCurrentAnimationFrame()
	* Description: returns the current animation frame
	* Parameters: none
	* Returns: int
	******************************************/
	public int getCurrentAnimationFrame()
	{
		if (vetAnimations.size() > 0)
		{
			return vetAnimations.get(iCurrentAnimation).getCurrentFrame();
		}
		
		return 0;
	}
	
	/*******************************************
	* Name: updateAnimation()
	* Description: updates the Sprite state animation texture
	* Parameters: none
	* Returns: none
	******************************************/
	private void updateAnimation()
	{
		//Updates the animation and the current frame
		if (vetAnimations.size() > 0)
		{
			vetAnimations.get(iCurrentAnimation).update();
			
			iCurrentFrame = vetAnimations.get(iCurrentAnimation).getCurrentFrame();
			
			//Updates the array of coords
			vrOpenGL.glTexCoordPointer(2, GL10.GL_FLOAT, 0, vetTextures[iCurrentFrame][iMirror]);
			return;
		}
		
		//If no animation use the entire frame
		vrOpenGL.glTexCoordPointer(2, GL10.GL_FLOAT, 0, vetTextures[vetTextures.length-1][iMirror]);
	}
	
	/*******************************************
	* Name: moveTo()
	* Description: usedo to interpolate sprite positions 
	* Parameters: int, float, float
	* Returns: none
	******************************************/
	public void moveTo(int pTime, float posX, float posY)
	{
		vrInitMove.setXY(vrPosition.getX(), vrPosition.getY());
		vrEndMove.setXY(posX, posY);
		
		if (pTime<=0 || (vrInitMove.equals(vrEndMove)))
		{
			vrPosition.setX(vrEndMove.getX());
			vrPosition.setY(vrEndMove.getY());
			vrMoveTimer.restart(0);
		}
		else
		{
			vrMoveTimer.restart(pTime);
		}
	}
	
	/*******************************************
	* Name: moveTo()
	* Description: usedo to interpolate sprite positions 
	* Parameters: int, AGVector2D
	* Returns: none
	******************************************/
	public void moveTo(int pTime, AGVector2D vNewPos)
	{
		vrInitMove.setXY(vrPosition.getX(), vrPosition.getY());
		vrEndMove = vNewPos;
		
		if (pTime<=0 || (vrInitMove.equals(vrEndMove)))
		{
			vrPosition.setX(vrEndMove.getX());
			vrPosition.setY(vrEndMove.getY());
			vrMoveTimer.restart(0);
		}
		else
		{
			vrMoveTimer.restart(pTime);
		}
	}
	
	/*******************************************
	* Name: updateMove()
	* Description: updates sprites positions while moving
	* Parameters: none
	* Returns: none
	******************************************/
	private void updateMove()
	{
		if (vrMoveTimer.iEndTime !=0)
		{
			if (!vrMoveTimer.isTimeEnded())
			{
				vrMoveTimer.update();
				
				vrPosition.setX(vrInitMove.getX());
				vrPosition.setX(vrPosition.getX() + ((vrInitMove.fX < vrEndMove.fX) ? - ((vrInitMove.fX - vrEndMove.fX) * vrMoveTimer.iCurrentTime)/vrMoveTimer.iEndTime : -(Math.abs(vrInitMove.fX - vrEndMove.fX) * vrMoveTimer.iCurrentTime)/vrMoveTimer.iEndTime));

				vrPosition.setY(vrInitMove.getY());
				vrPosition.setY(vrPosition.getY() + ((vrInitMove.fY < vrEndMove.fY) ? - ((vrInitMove.fY - vrEndMove.fY) * vrMoveTimer.iCurrentTime)/vrMoveTimer.iEndTime : -(Math.abs(vrInitMove.fY - vrEndMove.fY) * vrMoveTimer.iCurrentTime)/vrMoveTimer.iEndTime));
			}
			else
			{
				vrPosition.fX = vrEndMove.fX;
				vrPosition.fY = vrEndMove.fY;
				vrMoveTimer.restart(0);
			}
		}
	}
	
	/*******************************************
	* Name: moveEnded()
	* Description: returns true if sprite is not moving
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean moveEnded()
	{
		return (vrMoveTimer.iEndTime == 0);
	}	

	/*******************************************
	* Name: render()
	* Description: updates the Sprite state
	* Parameters: none
	* Returns: none
	******************************************/
	public void render()
	{
		//Test if Sprite is visible
		if (!bVisible)
			return;
		
		//Uptates ths Sprite states
		updateAnimation();
		updateMove();
		updateFade();
		
		//Sign texture and call OpenGL draw methods
		vrOpenGL.glBindTexture(GL10.GL_TEXTURE_2D, iTextureCode);
		vrOpenGL.glLoadIdentity();
		vrOpenGL.glColor4f(1.0f, 1.0f, 1.0f, fAlpha);
		vrOpenGL.glTranslatef(vrPosition.fX, vrPosition.fY, 0);
		vrOpenGL.glRotatef(fAngle, 0.0f, 0.0f, 1.0f);
		vrOpenGL.glScalef(vrScale.fX * iFrameWidth, vrScale.fY * iFrameHeight, 1.0f);
		vrOpenGL.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
	}
	
	/*******************************************
	* Name: collide()
	* Description: used to test collision between Sprites
	* Parameters: CAGSprite
	* Returns: boolean
	******************************************/
	public boolean collide(AGSprite vSprite)
	{
		//Calc the X coords
		float A = vrPosition.fX - (iFrameWidth * vrScale.fX);
		float B = vrPosition.fX + (iFrameWidth * vrScale.fX);
		float C = vSprite.vrPosition.fX - (vSprite.iFrameWidth * vSprite.vrScale.fX);
		float D = vSprite.vrPosition.fX + (vSprite.iFrameWidth * vSprite.vrScale.fX);
		
		//Test collision in X axis
		if (A < D && B > C)
		{
			//Calc Y coords
			A = vrPosition.fY - (iFrameHeight * vrScale.fY);
			B = vrPosition.fY + (iFrameHeight * vrScale.fY);
			C = vSprite.vrPosition.fY - (vSprite.iFrameHeight * vSprite.vrScale.fY);
			D = vSprite.vrPosition.fY + (vSprite.iFrameHeight * vSprite.vrScale.fY);
			
			//Test colligion in Y axis
			if (A < D && B > C)
			{
				return true;
			}
		}
		return false;
	}
	
	/*******************************************
	* Name: collide()
	* Description: used to test collision sprite and a point
	* Parameters: AGVector2D
	* Returns: boolean
	******************************************/
	public boolean collide(AGVector2D vrVector)
	{
		return collide(vrVector.fX, vrVector.fY);
	}
	
	/*******************************************
	* Name: collide()
	* Description: used to test collision sprite and a point
	* Parameters: float, float
	* Returns: boolean
	******************************************/
	public boolean collide(float fX, float fY)
	{
		float A = vrPosition.fX - (vrScale.fX * iFrameWidth);
		float B = vrPosition.fX + (vrScale.fX * iFrameWidth);
		float C = vrPosition.fY - (vrScale.fY * iFrameHeight);
		float D = vrPosition.fY + (vrScale.fY * iFrameHeight);
		
		//Test is the point are inside Sprite Frame 
		if (fX >= A && fX <= B)
		{
			if (fY >= C && fY <= D)
			{
				return true;
			}
		}
			
		return false;
	}
	
	/*******************************************
	* Name: getSpriteWidth()
	* Description: returns Sprite Height considering the scale applied
	* Parameters: none
	* Returns: float
	******************************************/
	public float getSpriteWidth()
	{
		return 2 * (vrScale.fX * iFrameWidth);
	}
	
	/*******************************************
	* Name: getSpriteHeight()
	* Description: returns Sprite Height considering the scale applied
	* Parameters: none
	* Returns: float
	******************************************/
	public float getSpriteHeight()
	{
		return 2 * (vrScale.fY * iFrameHeight);
	}

	/*******************************************
	* Name: releaseAnimations()
	* Description: free animations resources
	* Parameters: none
	* Returns: nome
	******************************************/
	private void releaseAnimations()
	{
		for (int iIndex=0; iIndex < vetAnimations.size(); iIndex++)
		{
			vetAnimations.get(iIndex).release();
		}
		vetAnimations.clear();
		vetAnimations = null;
	}
	
	/*******************************************
	* Name: release()
	* Description: free resources
	* Parameters: int, int
	* Returns: boolean
	******************************************/
	public void release()
	{
		AGTextureManager.release(iTextureCode);
		releaseAnimations();
		vetTextures = null;
		vetCoords = null;
		vrDirection = null;
		vrPosition = null;
		vrInitMove = null;
		vrEndMove = null;
		vrMoveTimer = null;
		vrFadeTimer = null;
		vrScale = null;
		vrOpenGL = null;
	}
}
