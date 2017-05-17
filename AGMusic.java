/********************************************
 Class: AGMusic
 Description: used to handle music files
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Used Packages
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class AGMusic 
{
	//Attributes
	private MediaPlayer vrMedia = null;
	private Activity vrContext = null;
	
	/********************************************
	* Name: AGMusic()
	* Description: Music class constructor
	* Parameters: Activity
	* Returns: none
	******************************************/
	public AGMusic(Activity pActivity)
	{
		vrContext = pActivity;
		vrMedia = new MediaPlayer();
	}

	/*******************************************
	* Name: loadMusic()
	* Description: Music class constructor
	* Parameters: String, boolean
	* Returns: none
	******************************************/
	public void loadMusic(String sMusicName, boolean bRepeat)
	{
		AssetFileDescriptor vrDescritor = null;
		try
		{
			vrDescritor = vrContext.getAssets().openFd(sMusicName);
						
			if (vrMedia != null)
			{
				vrMedia.stop();
				vrMedia.reset();
			}
				
			vrMedia.setDataSource(vrDescritor.getFileDescriptor(), vrDescritor.getStartOffset(), vrDescritor.getLength());
			vrMedia.prepare();
			vrMedia.setLooping(bRepeat);
		}
		catch(Exception e)
		{
		}
	}
	
	/*******************************************
	* Name: stop()
	* Description: stop music reproduction
	* Parameters: none
	* Returns: none
	******************************************/
	public void stop()
	{
		vrMedia.stop();
	}
	
	/*******************************************
	* Name: play()
	* Description: starts music reproduction
	* Parameters: none
	* Returns: none
	******************************************/
	public void play()
	{
		vrMedia.start();
	}
	
	/*******************************************
	* Name: pause()
	* Description: pause music reproduction
	* Parameters: none
	* Returns: none
	******************************************/
	public void pause()
	{
		vrMedia.pause();
	}
	
	/*******************************************
	* Name: setVolume()
	* Description: sets music volume
	* Parameters: int, int
	* Returns: none
	******************************************/
	public void setVolume(float iLeft, float iRight)
	{
		vrMedia.setVolume(iLeft, iRight);
	}
	
	/*******************************************
	* Name: isPlaying()
	* Description: if music is playing
	* Parameters: none
	* Returns: boolean
	******************************************/
	public boolean isPlaying()
	{
		return vrMedia.isPlaying();
	}
	
	/*******************************************
	* Name: release()
	* Description: free resources
	* Parameters: none
	* Returns: none
	******************************************/
	public void release()
	{
		vrMedia.stop();
		vrMedia.release();
		vrMedia = null;
		vrContext = null;
	}
}
