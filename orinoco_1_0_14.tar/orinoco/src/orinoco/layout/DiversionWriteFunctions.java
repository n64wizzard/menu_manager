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

import java.io.OutputStream;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import orinoco.Font;
import orinoco.Alignment;
import orinoco.TextMacro;
import orinoco.Colour;
import orinoco.write.FormatWriter;

/**
 * Implements the atomic write function.  Instead of writing to the main 
 * flow,
 * this stores the atomic commands in a list, which can be replayed
 * at any time.  This is typically utilised when writing headers and footers
 */
class DiversionWriteFunctions implements AtomicWriteFunctions
{
  /**
   * The commands which comprise this diversion
   */
  private ArrayList commands;

  /**
   * A handle to the object responsible for writing the output file code
   */
  private FormatWriter writer;

  /**
   * A handle to the layout writer, containing the current position on the
   * page.  This class will perform any neccessary updates the current
   * position on the page
   */
  private BaseWriter layout;
  
  /**
   * Constructor
   * 
   * @param w the output format writer
   * @param bw the base writer which owns this set of write functions
   */
  protected DiversionWriteFunctions(FormatWriter w, BaseWriter bw)
  {
    writer = w;
    layout = bw;
    commands = new ArrayList(10);
  }

  /**
   * Changes the active font on the output page
   * 
   * @exception IOException 
   * @param f the font
   */
  public void writeSetFont(BaseFont f) throws IOException
  {    
    commands.add(new WriteFont(writer, f));
  }

  /**
   * Adjusts the yposition to take into account any disparity between
   * the default font and the height of the line
   * 
   * @exception IOException 
   * @param amount the amount
   */
  public void writeAdjustY(int amount) throws IOException
  {
    commands.add(new WriteAdjustY(writer, amount));
    layout.setYPos(layout.getYPos() + amount);
  }

  /**
   * Sets the absolute X position
   * 
   * @exception IOException 
   * @param x the new x position
   */
  public void writeSetX(double x) throws IOException
  {
    commands.add(new WriteSetX(writer, x));
    layout.setXPos(x);
  }

  /**
   * Sets the absolute Y position
   * 
   * @exception IOException 
   * @param y the new y position
   */
  public void writeSetY(double y) throws IOException
  {
    commands.add(new WriteSetY(writer, y));
    layout.setYPos(y);
  }

  /**
   * Move to the absolute position
   * 
   * @exception IOException 
   * @param y the new y position
   * @param x the new x position
   */
  public void writeMoveTo(double x, double y) throws IOException
  {
    commands.add(new WriteMoveTo(writer, x, y));
    layout.setXPos(x);
    layout.setYPos(y);
  }

  /**
   * Writes out the a new line
   * 
   * @exception IOException 
   */
  public void writeNewLine() throws IOException
  {
    // Move to the beginning of the next line.  Increment the y position
    // and set the x position to be the left margin
    commands.add(new WriteNewLine(writer));
    layout.setLastNewLinePointSize(layout.getFont().getPointSize());
    layout.setYPos(layout.getYPos() + layout.getLastNewLinePointSize());
    layout.setXPos(0);
  }

  /**
   * Writes out the specified text to the current cursor position
   * 
   * @exception IOException 
   * @param s the text
   */
  public void writeText(String s) throws IOException
  {
    commands.add(new WriteText(writer, s));
  }

  /**
   * Writes out the text macro
   * 
   * @exception IOException 
   * @param a the alignment
   * @param tm the text macro
   */
  public void writeTextMacro(TextMacro tm, Alignment a) throws IOException
  {
    commands.add(new WriteTextMacroLine(writer, tm, 
                                        layout.getFont(),
                                        a, 
                                        layout.getWidth(),
                                        layout.getWriteFunctions()));
  }

  /**
   * Draws a line on the page between the specified positions
   * 
   * @exception IOException 
   * @param x1 
   * @param ps the thickness of the line to be drawn
   * @param y2 
   * @param x2 
   * @param y1 
   */
  public void writeDrawLine(double x1, double y1, 
                            double x2, double y2, int ps) 
    throws IOException
  {
    commands.add(new WriteDrawLine(writer, x1, y1, x2, y2, ps));
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
    commands.add(new WriteFillBox(writer, x1, y1, width, height, c));
  }

  /**
   * Executes the stored sequence of commands
   * 
   * @exception IOException 
   */
  public void write() throws IOException
  {
    Iterator i = commands.iterator();
    Command c = null;
    while (i.hasNext())
    {
      c = (Command) i.next();
      c.execute();
    }
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


