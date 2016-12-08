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

package orinoco;

import java.io.IOException;

/**
 * The basic interface for writing text to a document
 */
public interface TextWriter
{
  /**
   * Writes all data in this font until the font is set to something else.
   * 
   * @param f the new font
   * @exception IOException 
   */
  public void setFont(Font f) throws IOException;

  /**
   * Writes the string at the current position to the document, but without
   * new line
   * 
   * @param s the string to be added to the current line
   * @exception IOException 
   */
  public void write(String s) throws IOException;

  /**
   * Writes just the text passed in with the specified font
   * 
   * @param s the string to be appended to the current line
   * @param f the font in which the string should be displayed
   * @exception IOException 
   */
  public void write(String s, Font f) throws IOException;

  /**
   * Writes the string specified on a new line
   * 
   * @param s the string to write on its own line
   * @exception IOException 
   */
  public void writeLine(String s) throws IOException;

  /**
   * Writes just the text passed in on a new line in the specified font
   * 
   * @param s the text to write
   * @param f the font to write it in
   * @exception IOException 
   */
  public void writeLine(String s, Font f) throws IOException;

  /**
   * Writes the following line with the specified alignment on a new line
   * 
   * @param a the alignment for this line
   * @param s the text to write
   * @exception IOException 
   */
  public void writeLine(String s, Alignment a) throws IOException;

  /**
   * Writes the following lines with the specified alignment on a new line
   * 
   * @param a the alignment
   * @param s the text to write
   * @param f the font to in which to render the text
   * @exception IOException 
   */
  public void writeLine(String s, Font f, Alignment a) throws IOException;

  /**
   * Adds the text macro to the current line
   * 
   * @exception IOException 
   * @param tm the text macro to incorporate
   */
  public void writeMacro(TextMacro tm) throws IOException;

  /**
   * Writes the text macro on its own line
   * 
   * @exception IOException 
   * @param tm the text macro
   * @param f the font to render the text in
   */
  public void writeMacro(TextMacro tm, Font f)
    throws IOException;

  /**
   * Writes the text macro in the specified font with the specified
   * alignment on a new line
   * 
   * @exception IOException 
   * @param a the alignment
   * @param tm the text macro
   * @param f the font
   */
  public void writeMacroLine(TextMacro tm, Font f, Alignment a)
    throws IOException;
}
