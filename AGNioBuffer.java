/********************************************
 Class: AGNioBuffer
 Description: used to create a NioBuffer
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package game.curso.cursogamesandroid2d.AndGraphics;

//Used packages
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

class AGNioBuffer 
{
	/********************************************
	* Name: generateNioBuffer()
	* Description: used to generate a FloatBuffer object
	* Parameters: float[]
	* Returns: FloatBuffer
	******************************************/
	static FloatBuffer generateNioBuffer(float[] vetorCoordenadas)
	{
		//Alloc Bytes in memory
		ByteBuffer vrByteBuffer = ByteBuffer.allocateDirect(vetorCoordenadas.length * 4);
		vrByteBuffer.order(ByteOrder.nativeOrder());
				
		//Create a FloatBuffer
		FloatBuffer vrFloatBuffer = vrByteBuffer.asFloatBuffer();
		vrFloatBuffer.clear();	
							
		//Insert a Java Array into Float Buffer
		vrFloatBuffer.put(vetorCoordenadas);
				
		//Reset FloatBuffer attribs
		vrFloatBuffer.flip();
		
		return vrFloatBuffer;
	}
}
