/********************************************
 Class: AGTextureManager
 Description: used to handle image and texture loading
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package android.cg.com.megavirada.AndGraph;

//Used packages

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

//Local class used to store texture data (Package Access)
class AGTextureData
{
	//Attributes
	int iImageRef 	=	0;
	int iTextCod 	=	0;
	int iNumRefs		= 	1;
	int iWidth 		= 	0;
	int iHeight 		= 	0;
	
	/*******************************************
	* Name: AGTextureData()
	* Description: class constructor
	* Parameters: int, int, int, int
	* Returns: none
	*******************************************/
	AGTextureData(int pRef, int pCod, int pWidth, int pHeight)
	{
		iImageRef = pRef;
		iTextCod = pCod;
		iWidth = pWidth;
		iHeight = pHeight;
	}
}

class AGTextureManager 
{
	//Attributes
	private static Activity vrActivity = null;
	private static ArrayList<AGTextureData> vrTextureList = null;
	
	/********************************************
	* Name: init()
	* Description: used to validate a context reference
	* Parameters: Activity
	* Returns: none
	******************************************/
	public static void init(Activity pActivity)
	{
		vrActivity = pActivity;
		vrTextureList = new ArrayList<AGTextureData>();
	}
	
	/*******************************************
	* Name: loadTexture()
	* Description: used to load and manage textures
	* Parameters: GL10, int
	* Returns: int
	******************************************/
	public static int loadTexture(GL10 vrOpenGL, int iReference)
	{
		//Verify if this texture already was loaded before (reuse)
		for (AGTextureData vrTexture:vrTextureList)
		{
			if (iReference == vrTexture.iImageRef)
			{
				vrTexture.iNumRefs++;
				return vrTexture.iTextCod;
			}
		}
		
		//Load a new Texture
		//Step 1 - Image load on RAM
		int[] vetTexturas = new int[1];
		Bitmap vrImage  = BitmapFactory.decodeResource(AGTextureManager.vrActivity.getResources(), iReference);
		
		//Step 2 - Creating a VRAM texture area
		vrOpenGL.glGenTextures(1, vetTexturas, 0);
				
		//Step 3 - Copy the loaded image to VRAM texture area
		vrOpenGL.glBindTexture(GL10.GL_TEXTURE_2D, vetTexturas[0]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, vrImage, 0);
				
		//Step 4 - Texture filters
		vrOpenGL.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		vrOpenGL.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
									
		//Step 5 - Free RAM memory
		vrOpenGL.glBindTexture(GL10.GL_TEXTURE_2D, 0);
		
		//Add a new texture object into array list
		vrTextureList.add(new AGTextureData(iReference, vetTexturas[0], vrImage.getWidth(), vrImage.getHeight()));
		
		//Release image data
		vrImage.recycle();
		
		return vetTexturas[0];
	}
	
	/*******************************************
	* Name: getTextureData()
	* Description: find a texture data using a texture code
	* Parameters: int
	* Returns: CTextureData
	******************************************/
	public static AGTextureData getTextureData(int pCodTexture)
	{
		//Try to find the Texture Object
		for (AGTextureData vrTexture:vrTextureList)
		{
			if (pCodTexture == vrTexture.iImageRef)
			{
				return vrTexture;
			}
		}
		
		return null;
	}

	/*******************************************
	* Name: release()
	* Description: free a texture with specific code
	* Parameters: int
	* Returns: none
	******************************************/
	public static void release(int pTexCode)
	{
		for (int iIndex=vrTextureList.size() - 1; iIndex >=0; iIndex--)
		{
			if (pTexCode == vrTextureList.get(iIndex).iTextCod)
			{
				if (vrTextureList.get(iIndex).iNumRefs > 1)
					vrTextureList.get(iIndex).iNumRefs--;
				else
					vrTextureList.remove(iIndex);
				return;
			}
		}
	}
	
	/*******************************************
	* Name: release()
	* Description: free all texture codes
	* Parameters: none
	* Returns: none
	******************************************/
	public static void release()
	{
		vrTextureList.clear();
		vrActivity = null;
	}
}
