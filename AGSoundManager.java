/********************************************
 Class: AGSoundManager
 Description: used to manager Music and Sounds Effects
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Pacotes utilizados
import android.app.Activity;
import android.media.AudioManager;

public class AGSoundManager 
{
	//Atributos da classe
	public static AGSoundEffect vrSoundEffects = null;
	public static AGMusic vrMusic = null;
	
	/*******************************************
	* Name: init()
	* Description: init sound resources
	* Parameters: Activity
	* Returns: none
	*******************************************/
	public static void init(Activity vrActivity)
	{
		//Atribui ao Android o controle da aplicacao
		vrActivity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		
		//Cria a instancia do controlador de efeitos
		vrSoundEffects = new AGSoundEffect(vrActivity);
		
		//Cria e instancia o controlador de musicas
		vrMusic = new AGMusic(vrActivity);
	}
	
	/*******************************************
	* Name: release()
	* Description: free sound resources
	* Parameters: none
	* Returns: none
	******************************************/
	public static void release()
	{
		vrMusic.release();
		vrSoundEffects.release();
		vrMusic = null;
		vrSoundEffects = null;	
	}
}
