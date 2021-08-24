/********************************************
 Class: AGScene
 Description: used to represent a scene
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package game.curso.cursogamesandroid2d.AndGraphics;

//Used packages
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public abstract class AGScene
{
	//Attibutes
	private ArrayList<AGSprite> vrSprites = null;
	private ArrayList<AGLayer> vrLayers = null;

	protected AGGameManager vrGameManager = null;
	public boolean bSceneStarted = false;
	public float fRed = 0.0f;
	public float fGreen = 0.0f;
	public float fBlue = 0.0f;

	//CONSTS
	private int FRAME_SIZE = 1;

	private float[] coordSprites = new float[]{
			-FRAME_SIZE, -FRAME_SIZE,
			-FRAME_SIZE,  FRAME_SIZE,
			FRAME_SIZE, -FRAME_SIZE,
			FRAME_SIZE,  FRAME_SIZE};

	private float[] coordLayers = new float[]{
			0, 0,
			0,  FRAME_SIZE,
			FRAME_SIZE,0,
			FRAME_SIZE,  FRAME_SIZE};
	
	/*******************************************
	* Name: CAGScene()
	* Description: Scene construtor
	* Parameters: CAGameManager
	* Returns: none
	******************************************/
	public AGScene()
	{
		//Instancia o vetor de sprites
		vrSprites = new ArrayList<AGSprite>();

		//Instancia o vetor de layers
		vrLayers = new ArrayList<AGLayer>();
	}
	
	//abstract methods
	public abstract void init();
	public abstract void stop();
	public abstract void restart();
	public abstract void loop();

	/*******************************************
	 * Name: setCurrentScene
	 * Description: change the current game Scene
	 * Parameters: int,
	 * Returns: none
	 ******************************************/
	public void setCurrentScene(int sceneIndex)
	{
		vrGameManager.setCurrentScene(sceneIndex);
	}

	/*******************************************
	 * Name: playSoundEffect()
	 * Description: play a quickly sound
	 * Parameters: int
	 * Returns: none
	 ******************************************/
	public void playSoundEffectVolume(int code, float left, float right)
	{
		AGSoundManager.vrSoundEffects.setVolumeSound(code, left, right);
	}

	/*******************************************
	 * Name: playSoundEffect()
	 * Description: play a quickly sound
	 * Parameters: int
	 * Returns: none
	 ******************************************/
	public void playSoundEffect(int code)
	{
		AGSoundManager.vrSoundEffects.play(code);
	}

	/*******************************************
	 * Name: loadSoundEffect()
	 * Description: loads a quickly sound
	 * Parameters: none
	 * Returns: int
	 ******************************************/
	public int loadSoundEffect(String name)
	{
		return AGSoundManager.vrSoundEffects.loadSoundEffect(name);
	}

	/*******************************************
	 * Name: setMusicVolume()
	 * Description: ajust the volume of music
	 * Parameters: float, float
	 * Returns: void
	 ******************************************/
	public void setMusicVolume(float left, float right)
	{
		AGSoundManager.vrMusic.setVolume(left, right);
	}

	/*******************************************
	 * Name: stopMusic()
	 * Description: stop the music reprodution
	 * Parameters: none
	 * Returns: boolean
	 ******************************************/
	public void stopMusic()
	{
		AGSoundManager.vrMusic.stop();
	}

	/*******************************************
	 * Name: pauseMusic()
	 * Description: pause the music reprodution
	 * Parameters: none
	 * Returns: boolean
	 ******************************************/
	public void pauseMusic()
	{
		AGSoundManager.vrMusic.pause();
	}

	/*******************************************
	 * Name: isMusicPlaying()
	 * Description: try if music is playing
	 * Parameters: none
	 * Returns: boolean
	 ******************************************/
	public boolean isMusicPlaying()
	{
		return AGSoundManager.vrMusic.isPlaying();
	}

	/*******************************************
	 * Name: playMusic()
	 * Description: reproduces a music sound
	 * Parameters: none
	 * Returns: none
	 ******************************************/
	public void playMusic()
	{
		AGSoundManager.vrMusic.play();
	}

	/*******************************************
	 * Name: loadMusic()
	 * Description: load a music file
	 * Parameters: String, boolean
	 * Returns: none
	 ******************************************/
	public void loadMusic(String name, boolean repeat)
	{
		AGSoundManager.vrMusic.loadMusic(name, repeat);
	}
	
	/*******************************************
	* Name: createSprite()
	* Description: insert a new sprite into scene sprite list
	* Parameters: int, int, int, int, int
	* Returns: CAGSprite
	******************************************/
	public AGSprite createSprite(int pCodImage, int pFrameWidth, int pFrameHeight, int pImageWidth, int pImageHeight)
	{
		AGSprite vrNewSprite = new AGSprite(AGGameManager.vrOpenGL, pCodImage, pFrameWidth, pFrameHeight, pImageWidth, pImageHeight);
		vrSprites.add(vrNewSprite);
		return vrNewSprite;
	}
	
	/*******************************************
	* Name: createSprite()
	* Description: insert a new sprite into scene sprite list
	* Parameters: int, int, int
	* Returns: CAGSprite
	******************************************/
	public AGSprite createSprite(int pCodImage, int pTotalLin, int pTotalCol)
	{
		AGSprite vrNewSprite = new AGSprite(AGGameManager.vrOpenGL, pCodImage, pTotalLin, pTotalCol);
		vrSprites.add(vrNewSprite);
		return vrNewSprite;
	}

	/*******************************************
	 * Name: exitGame()
	 * Description: finaliza a execucao do App
	 * Parameters: none
	 * Returns: none
	 ******************************************/
	public void exitGame()
	{
		vrGameManager.vrActivity.finish();
	}

	/*******************************************
	 * Name: getScreenWidth()
	 * Description: returns the width of screen
	 * Parameters: none
	 * Returns: int
	 ******************************************/
	public int getScreenWidth()
	{
		return AGScreenManager.iScreenWidth;
	}

	/*******************************************
	 * Name: getScreenHeight()
	 * Description: returns the height of screen
	 * Parameters: none
	 * Returns: int
	 ******************************************/
	public int getScreenHeight()
	{
		return AGScreenManager.iScreenHeight;
	}


	/*******************************************
	 * Name: screenClicked()
	 * Description: returns true if screen was clicked
	 * Parameters: none
	 * Returns: boolean
	 ******************************************/
	public boolean screenClicked()
	{
		return AGInputManager.vrTouchEvents.screenClicked();
	}

	/*******************************************
	 * Name: screenDown
	 * Description: returns true if screen is touched
	 * Parameters: none
	 * Returns: boolean
	 ******************************************/
	public boolean screenDown()
	{
		return AGInputManager.vrTouchEvents.screenDown();
	}

	/*******************************************
	 * Name: backButtonClicked()
	 * Description: returns true if backButton was clicked
	 * Parameters: none
	 * Returns: boolean
	 ******************************************/
	public boolean backButtonClicked()
	{
		return AGInputManager.vrTouchEvents.backButtonClicked();
	}

	/*******************************************
	 * Name: getLastTouchPosition()
	 * Description: returns the last touch event position
	 * Parameters: none
	 * Returns: AGVector2D
	 ******************************************/
	public AGVector2D getLastTouchPosition()
	{
		return AGInputManager.vrTouchEvents.getLastPosition();
	}

	/*******************************************
	 * Name: getRotX()
	 * Description: returns the x Accel value
	 * Parameters: none
	 * Returns: float
	 ******************************************/
	public float getAccelX()
	{
		return AGInputManager.vrAccelerometer.getAccelX();
	}

	/*******************************************
	 * Name: getRotY()
	 * Description: returns the y Accel value
	 * Parameters: none
	 * Returns: float
	 ******************************************/

	public float getAccelY()
	{
		return AGInputManager.vrAccelerometer.getAccelY();
	}

	/*******************************************
	 * Name: getRotZ()
	 * Description: returns the z Accel value
	 * Parameters: none
	 * Returns: float
	 ******************************************/
	public float getAcellZ()
	{
		return AGInputManager.vrAccelerometer.getAccelZ();
	}

	/*******************************************
	 * Name: createLayer()
	 * Description: insert a new layer into scene sprite list
	 * Parameters: int, int, int, int, int
	 * Returns: AGLayer
	 ******************************************/
	public AGLayer createLayer(int pCodImage, int tileBlockWidth, int tileBlockHeight, int totalLin, int totalCol)
	{
		AGLayer vrNewLayer = new AGLayer(pCodImage, tileBlockWidth, tileBlockHeight, totalCol, totalLin, AGGameManager.vrOpenGL);
		vrLayers.add(vrNewLayer);
		return vrNewLayer;
	}
	
	/*******************************************
	* Name: setBackgroundColor()
	* Description: sets background color for scene
	* Parameters: float, float, float
	* Returns: void
	******************************************/
	public void setSceneBackgroundColor(float pRed, float pGreen, float pBlue)
	{
		fRed = pRed;
		fGreen = pGreen;
		fBlue = pBlue;
		AGGameManager.vrOpenGL.glClearColor(fRed, fGreen, fBlue, 1.0f);
	}
	
	/*******************************************
	* Name: render()
	* Description: Render scene elements
	* Parameters: none
	* Returns: none
	******************************************/
	public void render()
	{
		//Send the vertex array to OpenGL
		vrGameManager.vrOpenGL.glVertexPointer(2, GL10.GL_FLOAT, 0, AGNioBuffer.generateNioBuffer(coordLayers));
		for (AGLayer vrLayerList: vrLayers)
		{
			if (vrLayerList.autoRender)
			{
				vrLayerList.render();
			}
		}

		vrGameManager.vrOpenGL.glVertexPointer(2, GL10.GL_FLOAT, 0, AGNioBuffer.generateNioBuffer(coordSprites));
		for (AGSprite vrSpriteList:vrSprites)
		{
			if (vrSpriteList.bAutoRender)
			{
				vrSpriteList.render();
			}
		}
	}
	
	/********************************************
	* Name: removeSprite()
	* Description: remove a sprite from scene
	* Parameters: AGSprite
	* Returns: none
	******************************************/
	public void removeSprite(AGSprite pSprite)
	{
		for (int iIndex=vrSprites.size() - 1; iIndex >=0; iIndex--)
		{
			if (pSprite == vrSprites.get(iIndex))
			{
				vrSprites.get(iIndex).release();
				vrSprites.remove(iIndex);
				return;
			}
		}
	}
	
	/*******************************************
	* Name: release()
	* Description: clear all scene elements
	* Parameters: none
	* Returns: none
	******************************************/
	public void release()
	{
		for (int iIndex=0; iIndex < vrSprites.size(); iIndex++)
		{
			vrSprites.get(iIndex).release();
		}
		vrSprites.clear();

		for (int iIndex=0; iIndex < vrLayers.size(); iIndex++)
		{
			vrLayers.get(iIndex).release();
		}
		vrLayers.clear();
	}
	
	/*******************************************
	* Name: releaseSpritesTextures()
	* Description: clear all sprites textures
	* Parameters: none
	* Returns: none
	******************************************/
	public void releaseSpritesTextures()
	{
		for (int iIndex=0; iIndex < vrSprites.size(); iIndex++)
		{
			AGTextureManager.release(vrSprites.get(iIndex).getTextureCode());
		}
	}

	/*******************************************
	 * Name: releaseLayersTextures()
	 * Description: clear all Layers textures
	 * Parameters: none
	 * Returns: none
	 ******************************************/
	public void releaseLayersTextures()
	{
		for (int iIndex=0; iIndex < vrLayers.size(); iIndex++)
		{
			AGTextureManager.release(vrLayers.get(iIndex).getTileImageCode());
		}
	}

	/*******************************************
	* Name: reloadSpritesTextures()
	* Description: reload all sprite textures
	* Parameters: none
	* Returns: none
	******************************************/
	public void reloadSpritesTextures()
	{
		for (int iIndex=0; iIndex < vrSprites.size(); iIndex++)
		{
			vrSprites.get(iIndex).reloadTexture(AGGameManager.vrOpenGL);
		}
	}

	/*******************************************
	 * Name: reloadSpritesTextures()
	 * Description: reload all sprite textures
	 * Parameters: none
	 * Returns: none
	 ******************************************/
	public void reloadLayersTextures()
	{
		for (int iIndex=0; iIndex < vrLayers.size(); iIndex++)
		{
			vrLayers.get(iIndex).reloadTileImageTexture(AGGameManager.vrOpenGL);
		}
	}

	/*******************************************
	 * Name: finalize()
	 * Description: Erase vector pointers
	 * Parameters: none
	 * Returns: none
	 ******************************************/
	public void finalize()
	{
		vrSprites = null;
		vrLayers = null;
		vrGameManager = null;
	}
}
