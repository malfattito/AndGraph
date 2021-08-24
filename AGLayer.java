/********************************************
 Class: AGLayer
 Description: used to represent a scene
 Author: Silvano Maneck Malfatti
 Date: 05/11/2013
 ********************************************/

//Engine Package
package game.curso.cursogamesandroid2d.AndGraphics;

//Used Packages
import android.util.Log;

import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class AGLayer
{
    //Class attributes
    public boolean autoRender = true;
    private AGFrameIndex[] vetBlocks = null;
    private int tileImageCode = 0;
    private int imageCode = 0;
    private boolean visible = false;
    private GL10 vrOpenGL = null;
    private FloatBuffer[] vetTextures = null;
    private float[] vetCoords = null;
    private float fCoordX1 = 0;
    private float fCoordY1 = 0;
    private float fCoordX2 = 0;
    private float fCoordY2 = 0;
    private int iTotalBlocksWidth = 0;
    private int iTotalBlocksHeight = 0;
    private int iFrameWidth = 0;
    private int iFrameHeight = 0;
    private int iImageWidth = 0;
    private int iImageHeight = 0;
    private int iTotalLinTile = 0;
    private int iTotalColTile = 0;

    //DESENHO DA LAYER
    private int offsetX = 0;
    private int offsetY = 0;
    private int speedX = 0;
    private int speedY = 0;
    private int xBlock = 0;
    private int yBlock = 0;
    private int ofsX = 0;
    private int ofsY = 0;
    private int iXpos = 0;
    private int startPosX = 0;
    private int xSize = 0;
    private int ySize = 0;


    /***********************************************************
     *Name: JGLayer
     *Description: constructor
     *Parameters: JGGameManager, JGVector2D, JGVector2D
     *Return: none
     ************************************************************/
    public AGLayer(int imageCode, int frameWidth, int frameHeight, int layerWidth, int layerHeight, GL10 vrOpenGL)
    {
        this.vrOpenGL = vrOpenGL;

        this.imageCode = imageCode;

        iTotalBlocksWidth = layerWidth;
        iTotalBlocksHeight = layerHeight;

        visible = true;
        autoRender = true;

        vetBlocks = new AGFrameIndex[(iTotalBlocksWidth * iTotalBlocksHeight)];

        tileImageCode = AGTextureManager.loadTexture(vrOpenGL, imageCode);

        iImageWidth = 	AGTextureManager.getTextureData(imageCode).iWidth;
        iImageHeight = 	AGTextureManager.getTextureData(imageCode).iHeight;
        iFrameWidth = frameWidth;
        iFrameHeight = frameHeight;

        generateFrames();
    }

    /*******************************************
     * Name: setScreenPercent()
     * Description: usedo to calcule the size of sprite over a screen percent
     * Parameters: int, int
     * Returns: none
     ******************************************/
    public void setBrickScreenPercent(int pWidth, int pHeight)
    {
        iFrameWidth = (AGScreenManager.iScreenWidth * pWidth) / 100;
        iFrameHeight = (AGScreenManager.iScreenHeight * pHeight) / 100;
    }

    /***********************************************************
     *Name: getTotalBlocks
     *Description: returns the total of blocks in the layer
     *Parameters: none
     *Return: int
     ************************************************************/
    public int getTotalBlocks()
    {
        return vetBlocks.length;
    }

    /***********************************************************
     *Name: getTileImageCode
     *Description: returns code of tileimage
     *Parameters: none
     *Return: int
     ************************************************************/
    public int getTileImageCode()
    {
        return tileImageCode;
    }

    /*******************************************
     * Name: reloadTileImageTexture()
     * Description: reload a texture image id
     * Parameters: GL10
     * Returns: none
     ******************************************/
    public void reloadTileImageTexture(GL10 pOpenGL)
    {
        vrOpenGL = pOpenGL;
        tileImageCode = AGTextureManager.loadTexture(vrOpenGL, imageCode);
    }

    /***********************************************************
     *Name: getOffset
     *Description: returns the offset of the layer
     *Parameters: none
     *Return: JGVector2D
     ************************************************************/
    public AGVector2D getOffset()
    {
        return new AGVector2D(offsetX, offsetY);
    }

    /***********************************************************
     *Name: setFrameIndex
     *Description:sets the frame index by a specific position
     *Parameters: int, int
     *Return: none
     ************************************************************/
    public void setFrameIndex(int index, int tileFrameIndex)
    {
        if (vetBlocks[index] == null)
        {
            vetBlocks[index] = new AGFrameIndex();
        }
        vetBlocks[index].setFrameIndex(tileFrameIndex);
    }

    /***********************************************************
     *Name: setFrameIndex
     *Description:sets the frame index by a specific position
     *Parameters: int, int, int
     *Return: none
     ************************************************************/
    public void setFrameIndex(int lin, int col, int tileFrameIndex)
    {
        if (vetBlocks[lin * iTotalBlocksWidth + col] == null)
        {
            vetBlocks[lin * iTotalBlocksWidth + col] = new AGFrameIndex();
        }
        vetBlocks[lin * iTotalBlocksWidth + col].setFrameIndex(tileFrameIndex);
    }

    /***********************************************************
     *Name: getFrameIndexByPosition
     *Description:returns the frame to the position index
     *Parameters: int
     *Return: int
     ************************************************************/
    public int getFrameIndexByPosition(int position)
    {
        return vetBlocks[position].getFrameIndex();
    }

    /***********************************************************
     *Name: setVisible
     *Description: configures the visibility of the layer
     *Parameters: boolean
     *Return: void
     ************************************************************/
    public void setVisible(boolean pVisivel)
    {
        visible = pVisivel;
    }

    /***********************************************************
     *Name: getVisible
     *Description: getter of visibility of the layer
     *Parameters: none
     *Return: boolean
     ************************************************************/
    boolean getVisible()
    {
        return visible;
    }


    /***********************************************************
     *Name: scrollLayer
     *Description: scroll the layer with the current speed
     *Parameters: none
     *Return: void
     ************************************************************/
    public void scrollLayer()
    {
        offsetX = (offsetX + speedX) % (iTotalBlocksWidth * iFrameWidth);
        offsetY = (offsetY + speedY) % (iTotalBlocksHeight * iFrameHeight);
    }

    /***********************************************************
     *Name: generateFrames
     *Description: generate the texture coord frames
     *Parameters: none
     *Return: void
     ************************************************************/
    private void generateFrames()
    {
        iTotalLinTile = iImageHeight / iFrameHeight;
        iTotalColTile = iImageWidth / iFrameWidth;

        vetCoords = new float[8];
        vetTextures = new FloatBuffer[iTotalLinTile * iTotalColTile];

        for (int iIndex = 0; iIndex < vetTextures.length; iIndex++)
        {
            //Calc the frame coords
            fCoordX1 = (iIndex % iTotalColTile) * (1.0f / iTotalColTile);
            fCoordY1 = (iIndex / iTotalColTile) * (1.0f / iTotalLinTile);
            fCoordX2 = fCoordX1 + 1.0f / iTotalColTile;
            fCoordY2 = fCoordY1 + 1.0f / iTotalLinTile;

            //Normal mirror
            vetCoords[0] = fCoordX1;
            vetCoords[1] = fCoordY2;
            vetCoords[2] = fCoordX1;
            vetCoords[3] = fCoordY1;
            vetCoords[4] = fCoordX2;
            vetCoords[5] = fCoordY2;
            vetCoords[6] = fCoordX2;
            vetCoords[7] = fCoordY1;
            vetTextures[iIndex] = AGNioBuffer.generateNioBuffer(vetCoords);
        }
    }

    /***********************************************************
     *Name: drawBlock
     *Description: method used to draw a specific Block of layer
     *Parameters: int, int, int
     *Return: void
     ************************************************************/
    private void drawBlock(int frameIndex, int x, int y)
    {
        vrOpenGL.glTexCoordPointer(2, GL10.GL_FLOAT, 0, vetTextures[frameIndex]);

        //Sign texture and call OpenGL draw methods
        vrOpenGL.glBindTexture(GL10.GL_TEXTURE_2D, tileImageCode);
        vrOpenGL.glLoadIdentity();
        vrOpenGL.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        vrOpenGL.glTranslatef(x,  y, 0);
        vrOpenGL.glScalef(iFrameWidth, iFrameHeight, 1.0f);
        vrOpenGL.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
    }

    /***********************************************************
     *Name: setSpeed()
     *Description: set the speed of scroll layer
     *Parameters: int, int
     *Return: void
     ************************************************************/
    public void  setSpeed(int speedX, int speedY)
    {
        this.speedX = speedX;
        this.speedY = speedY;
    }

    /***********************************************************
     *Name: render()
     *Description: create a layer based in a color image
     *Parameters: none
     *Return: void
     ************************************************************/
    public void  render()
    {
        //Returns if layer is not visible
        if (!visible)
        {
            return;
        }

        //Return if blocks array are null
        if (vetBlocks == null)
        {
            return;
        }

        scrollLayer();

        ofsX = offsetX;
        ofsY = offsetY;
        xBlock = 0;
        yBlock = 0;

        while(ofsX > 0)
        {
            ofsX -= iFrameWidth;
            xBlock--;
            xBlock = (xBlock < 0 ) ? (iTotalBlocksWidth) - 1 : xBlock;
        }

        while(ofsY > 0)
        {
            ofsY -= iFrameHeight;
            yBlock--;
            yBlock = (yBlock < 0 ) ? (iTotalBlocksHeight) - 1 : yBlock;
        }

        startPosX = xBlock;
        xSize = iTotalBlocksWidth;
        ySize = iTotalBlocksHeight;

        for (; ofsY < AGScreenManager.iScreenHeight; yBlock = (yBlock + 1) % ySize)
        {
            for (; ofsX < AGScreenManager.iScreenWidth; xBlock = (xBlock + 1) % xSize)
            {
                if (vetBlocks[xBlock + (yBlock * xSize)] != null &&
                        insideScreen((int) ofsX, (int) ofsY, (int) ofsX + iFrameWidth, (int) ofsY+iFrameHeight))
                {
                    drawBlock(vetBlocks[xBlock + (yBlock * xSize)].getFrameIndex(), (int) ofsX, (int) ofsY);
                }
                ofsX += iFrameWidth;
            }
            ofsY += iFrameHeight;
            xBlock = startPosX;
            ofsX = iXpos;
        }
    }

    private boolean insideScreen(int x1, int y1, int x2, int y2)
    {
        if (0 < x2 && x1 < AGScreenManager.iScreenWidth &&
            0 < y2 && y1 < AGScreenManager.iScreenHeight)
        {
            return true;
        }

        return false;
    }

    /*******************************************
     * Name: release
     * Description: free resources
     * Parameters: none
     * Returns: none
     ******************************************/
    public void release()
    {
        AGTextureManager.release(tileImageCode);
        vetBlocks = null;
        tileImageCode = 0;
        vrOpenGL = null;
        vetTextures = null;
        vetCoords = null;
    }

}
