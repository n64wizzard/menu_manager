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

package orinoco.layout;

import java.io.IOException;

import orinoco.TextMacro;
import orinoco.Alignment;
import orinoco.Colour;
import orinoco.write.FormatWriter;

/**
 * Implementation of the AtomicWriteFunctions interface which writes out
 * the commands directly to the format writer
 */
class FlowWriteFunctions implements AtomicWriteFunctions
{
  /**
   * A handle to the object responsible for writing the output file code
   */
  private FormatWriter writer;

  /**
   * A handle to the layout writer, containing the current position on the
   * page etc
   */
  private BaseWriter layoutWriter;

  /**
   * Constructor
   * 
   * @param w the writer
   * @param bw the owning layout writer
   */
  public FlowWriteFunctions(FormatWriter w, BaseWriter bw)
  {
    writer = w;
    layoutWriter = bw;
  }

  /**
   * Sets the active font in the document
   * 
   * @exception IOException 
   * @param font the font
   */
  public void writeSetFont(BaseFont font) throws IOException
  {
    writer.writeFont(font);
  }

  /**
   * Adjusts the yposition by the number of points specified
   * 
   * @exception IOException 
   * @param amount the amount
   */
  public void writeAdjustY(int amount) throws IOException
  {
    writer.writeAdjustY(amount);
    layoutWriter.setYPos(layoutWriter.getYPos() + amount);
  }

  /**
   * Sets the absolute x position on the current line
   * 
   * @exception IOException 
   * @param x the new x position
   */
  public void writeSetX(double x) throws IOException
  {
    layoutWriter.setXPos(x);
    writer.writeSetX(x);
  }

  /**
   * Sets the y position on the page
   * 
   * @exception IOException 
   * @param y the new y position
   */
  public void writeSetY(double y) throws IOException
  {
    layoutWriter.setYPos(y);
    writer.writeSetY(y);
  }

  /**
   * Writes out an instruction to begin a new line
   * 
   * @exception IOException 
   */
  public void writeNewLine() throws IOException
  {
    // Move to the beginning of the next line.  Increment the y position
    // and set the x position to be the left margin
    writer.writeNewLine();
    layoutWriter.setLastNewLinePointSize
      (layoutWriter.getFont().getPointSize());
    layoutWriter.setXPos(0);
    layoutWriter.setYPos(layoutWriter.getYPos() + 
                         layoutWriter.getLastNewLinePointSize());
  }

  /**
   * Writes out the specified text at the current point
   * 
   * @exception IOException 
   * @param s the text
   */
  public void writeText(String s) throws IOException
  {
    writer.writeText(s);
  }

  /**
   * Writes out the specified text macro.  This should never get called,
   * because the writeMacro/writeMacroLine routines will convert to text
   * immediately
   * 
   * @exception IOException 
   * @param a the alignment
   * @param tm the macro
   */
  public void writeTextMacro(TextMacro tm, Alignment a) throws IOException
  {
    System.err.println("Warning:  writing a text macro from flow writer");
    writer.writeText(tm.getText());
  }

  /**
   * Move to the specified position on the page
   * 
   * @exception IOException 
   * @param y the y position
   * @param x the x position
   */
  public void writeMoveTo(double x, double y) throws IOException
  {
    layoutWriter.setXPos(x);
    layoutWriter.setYPos(y);
    writer.writeMoveTo(x, y);
  }

  /**
   * Draws a line on the page between the specified positions
   * 
   * @exception IOException 
   * @param x1 
   * @param ps the width in points of the line
   * @param y2 
   * @param x2 
   * @param y1 
   */
  public void writeDrawLine(double x1, double y1, 
                     double x2, double y2, int ps) throws IOException
  {
    writer.writeDrawLine(x1, y1, x2, y2, ps);
  }

  /**
   * Draws a box filled with the specified colour on the page
   * 
   * @param c the colour with which to fill the box
   * @param x1 the x position of the box
   * @param height the height of the box
   * @param width the width of the box
   * @param y1 the y position of the box
   * @exception IOException 
   */
  public void writeFillBox(double x1, double y1, double width, double height,
                    Colour c) throws IOException
  {
    writer.writeFillBox(x1, y1, width, height, c);
  }

  /**
   * Sees is the character passed in is a special character in this particular
   * output format
   *
   * @param c the character
   * @return TRUE if the character is a special character, FALSE otherwise
   */
  public boolean isSpecial(char c)
  {
    return writer.isSpecial(c);
  }

  /**
   * Gets the escape sequence necessary to render the special character in
   * the output file format
   *
   * @param c the character
   * @return the escape sequence
   */
  public String getEscapeSequence(char c)
  {
    return writer.getEscapeSequence(c);
  }
}

