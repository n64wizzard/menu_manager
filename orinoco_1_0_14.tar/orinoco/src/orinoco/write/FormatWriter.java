/*********************************************************************
*
*      Copyright (C) 2002 Andrew Khan
*
* This library is free software; you can redistribute it and/or
* modify it under the terms of the GNU Lesser General Public
* License as published by the Free Software Foundation; either
* version 2.1 of the License, or (at your option) any later version.
*
* This library is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this library; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
***************************************************************************/

package orinoco.write;

import java.io.IOException;
import java.io.Writer;

import orinoco.Colour;
import orinoco.layout.BaseFont;
import orinoco.layout.DocumentLayout;

/**
 * Interface which the various file format writers must adhere to.  Writes
 * out a particular action in the appropriate file format (postscript, pdf etc)
 */
public interface FormatWriter
{
  /**
   * Writes out the specific format command to prior to closing the
   * output file
   * 
   * @exception IOException 
   */
  public void writeClose() throws IOException;
  /**
   * Writes an end of the current page, including the footer
   * 
   * @exception IOException 
   */
  public void writeEndOfPage() throws IOException;
  /**
   * Writes the beginning of new page, including the headers
   * 
   * @exception IOException 
   */
  public void writeNewPage() throws IOException;
  /**
   * Writes out the specified text at the current point
   * 
   * @exception IOException 
   * @param s the text to write
   */
  public void writeText(String s) throws IOException;
  /**
   * Sets the x position on the current line
   * 
   * @exception IOException 
   * @param xpos the x position
   */
  public void writeSetX(double xpos) throws IOException;
  /**
   * Sets the y position on the current line
   * 
   * @exception IOException 
   * @param ypos the y position
   */
  public void writeSetY(double ypos) throws IOException;
  /**
   * Sets the active font
   * 
   * @exception IOException 
   * @param f the font
   */
  public void writeFont(BaseFont f) throws IOException;
  /**
   * Adjusts the y position of the current point by the amount
   * specified
   * 
   * @exception IOException 
   * @param amount the amount by which to adjust the y position
   */
  public void writeAdjustY(int amount) throws IOException;
  /**
   * Writes a new line and moves.  Adjusts the x position and y position
   * accordingly
   * 
   * @exception IOException 
   */
  public void writeNewLine() throws IOException;
  /**
   * Moves to the absolute position on the page
   * 
   * @exception IOException 
   * @param y the y coordinate
   * @param x the x coordinate
   */
  public void writeMoveTo(double x, double y) throws IOException;
  /**
   * Draws a line in the point size specified between the points specified
   * 
   * @exception IOException 
   * @param w 
   * @param x1 
   * @param y2 
   * @param x2 
   * @param y1 
   */
  public void writeDrawLine(double x1, double y1, double x2, double y2, int w)
    throws IOException;
  /**
   * Fills a box between the points specified in the colour specified
   * 
   * @exception IOException 
   * @param c the colour to fill the box
   * @param x1 
   * @param height the height of the box
   * @param width the width of the box
   * @param y1 
   */
  public void writeFillBox(double x1, double y1, double width, double height,
                           Colour c) throws IOException;
  /**
   * Performs any necessary post processing on the output file
   * 
   * @exception IOException 
   */
  public void postProcess() throws IOException;
  /**
   * Initializes the format write
   * 
   * @exception IOException 
   * @param dl the owning document
   */
  public void init(DocumentLayout dl) throws IOException;

  /**
   * Sees is the character passed in is a special character in this particular
   * output format
   * 
   * @param c the character
   * @return TRUE if the character is a special character, FALSE otherwise
   */
  public boolean isSpecial(char c);


  /**
   * Gets the escape sequence necessary to render the special character in
   * the output file format
   * 
   * @param c the character
   * @return the escape sequence
   */
  public String getEscapeSequence(char c);
}


