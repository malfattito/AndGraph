/********************************************
 Class: AGSoundEffect
 Description: used to play effect sounds
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Pacotes utilizados

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.ArrayList;

//Armazena os dados dos efeitos de som
class AGSoundData
{
	String sName;
	int iSoundCode;
	
	AGSoundData(String pName, int pCode)
	{
		sName = pName;
		iSoundCode = pCode;
	}
}

public class AGSoundEffect
{
	//Atributes
	private AssetFileDescriptor vrDescritor = null;
	private ArrayList<AGSoundData> vrSoundList = null;
	private SoundPool vrPool = null;
	private Activity vrContext = null;
	
	/*********************************************
	* Name: AGSoundEffect()
	* Description: Sound effect constructor
	* Parameters: Activity
	* Returns: none
	******************************************/
	public AGSoundEffect(Activity pActivity)
	{
		vrContext = pActivity;
		vrSoundList = new ArrayList<AGSoundData>();
		vrPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
	}
	
	/*******************************************
	* Name: loadSoundEffect)
	* Description: used to load a new sound
	* Parameters:String
	* Returns: none
	******************************************/
	public int loadSoundEffect(String sSoundName)
	{
		for (AGSoundData vrSound:vrSoundList)
		{
			if (sSoundName.equals(vrSound.sName))
			{
				return vrSound.iSoundCode;
			}
		}
		try
		{
			vrDescritor = vrContext.getAssets().openFd(sSoundName);
		}
		catch(Exception e)
		{
			
		}
		
		vrSoundList.add(new AGSoundData(sSoundName, vrPool.load(vrDescritor, 1)));
		return vrSoundList.get(vrSoundList.size() - 1).iSoundCode;
	}


	/*******************************************
	 * Name: setSoundVolume()
	 * Description: used to set a sound volume
	 * Parameters:int, float, float
	 * Returns: none
	 ******************************************/
	public void setVolumeSound(int id, float right, float left)
	{
		vrPool.setVolume(id, left, right);
	}
	
	/*******************************************
	* Name: play()
	* Description: used to load start sound reproduction
	* Parameters:int
	* Returns: none
	******************************************/
	public void play(int iCodSom)
	{
		vrPool.play(iCodSom, 1.0f, 1.0f, 0, 0, 1);
	}
	
	/*******************************************
	* Name: releaseSound()
	* Description: used to release a sound
	* Parameters:int
	* Returns: none
	******************************************/
	public void releaseSound(int iCodSom)
	{
		for (AGSoundData vrSound:vrSoundList)
		{
			if (iCodSom == vrSound.iSoundCode)
			{
				vrSoundList.remove(vrSound);
				vrPool.unload(iCodSom);
				return;
			}
		}
	}
	
	/*******************************************
	* Name: releaseSound()
	* Description: used to release a sound
	* Parameters:int
	* Returns: none
	******************************************/
	public void releaseSounds()
	{
		for (int iIndex = vrSoundList.size() - 1; iIndex >=0; iIndex--)
		{
			{
				vrPool.unload(vrSoundList.get(iIndex).iSoundCode);
				vrSoundList.remove(vrSoundList.get(iIndex));
			}
		}
		vrSoundList.clear();
	}
	
	/*******************************************
	* Name: release()
	* Description: used to release all references
	* Parameters:int
	* Returns: none
	******************************************/
	public void release()
	{
		releaseSounds();
		vrPool.release();
		vrSoundList = null;
		vrPool = null;
		vrContext = null;
	}
}
