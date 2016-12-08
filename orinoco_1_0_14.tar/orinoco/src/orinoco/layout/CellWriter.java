/*************************************************************************
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
import orinoco.TextWriter;
import orinoco.write.FormatWriter;

/**
 * This class is a type of diversion used to write to a cell within a
 * table.  All text is added to the cell
 */
class CellWriter implements TextWriter
{
  /**
   * The cell we are writing to
   */
  private Cell cell;

  /**
   * Constructor
   * 
   * @param c the cell we are writing to
   */
  public CellWriter(Cell c)
  {
    cell = c;
  }

  /**
   * Writes all data in this font until the font is set to something else.
   * 
   * @exception IOException 
   * @param f the new font
   */
  public void setFont(Font f) throws IOException
  {
  }

  /**
   * Writes the string at the current position to the document, but without
   * new line
   * 
   * @exception IOException 
   * @param s the string to be added to the current line
   */
  public void write(String s) throws IOException
  {
    cell.add(s);
  }

  /**
   * Writes just the text passed in with the specified font
   * 
   * @exception IOException 
   * @param s the string to be appended to the current line
   * @param f the font in which the string should be displayed
   */
  public void write(String s, Font f) throws IOException
  {
    cell.add(s, f);
  }

  /**
   * Writes the string specified on a new line
   * 
   * @exception IOException 
   * @param s the string to write on its own line
   */
  public void writeLine(String s) throws IOException
  {
    cell.add(s);
  }

  /**
   * Writes just the text passed in on a new line in the specified font
   * 
   * @exception IOException 
   * @param s the text to write
   * @param f the font to write it in
   */
  public void writeLine(String s, Font f) throws IOException
  {
    cell.add(s, f);
  }

  /**
   * Writes the following line with the specified alignment on a new line
   * 
   * @exception IOException 
   * @param a the alignment for this line
   * @param s the text to write
   */
  public void writeLine(String s, Alignment a) throws IOException
  {
    cell.add(s);
  }

  /**
   * Writes the following lines with the specified alignment on a new line
   * 
   * @exception IOException 
   * @param a the alignment
   * @param s the text to write
   * @param f the font to in which to render the text
   */
  public void writeLine(String s, Font f, Alignment a) throws IOException
  {
    cell.add(s,f);
  }

  /**
   * Adds the text macro to the current line
   * 
   * @param tm the text macro to incorporate
   * @exception IOException 
   */
  public void writeMacro(TextMacro tm) throws IOException
  {
    cell.add(tm);
  }

  /**
   * Writes the text macro in the specified font with the specified
   * alignment on a new line
   * 
   * @param a the alignment
   * @param tm the text macro
   * @param f the font
   * @exception IOException 
   */
  public void writeMacroLine(TextMacro tm, Font f, 
                             Alignment a) throws IOException
  {
    cell.add(tm, f);
  }

  /**
   * Writes the text macro on its own line
   * 
   * @param tm the text macro
   * @param f the font to render the text in
   * @exception IOException 
   */
  public void writeMacro(TextMacro tm, Font f) 
    throws IOException
  {
    cell.add(tm, f);
  }

}


