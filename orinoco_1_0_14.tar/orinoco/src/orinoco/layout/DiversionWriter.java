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

import java.io.IOException;

import orinoco.Font;
import orinoco.Alignment;
import orinoco.TextMacro;
import orinoco.write.FormatWriter;

/**
 * This class is used to write to a diversion instead of the main flow eg.
 * for headers and footers.  Instead of streaming out the commands to the
 * writer, it stores the commands for "replay" at a later date.  This allows
 * information to be gathered - such as the height of the particular diversion
 * and allows the main flow to decide how the data contained here needs to
 * be split up in order to write page breaks etc.
 */
class DiversionWriter extends BaseWriter
{
  /**
   * The atomic write functions - shadowed for convenience
   */
  private DiversionWriteFunctions divWriteFunctions;

  /**
   * Constructor
   * 
   * @param w the available width for this diversion
   * @param wr the output writer
   * @param d the owning document
   */
  protected DiversionWriter(double w, FormatWriter wr, DocumentLayout d)
  {
    super(w, d);
    divWriteFunctions = new DiversionWriteFunctions(wr, this);
    setWriteFunctions(divWriteFunctions);
  }

  /**
   * Writes the text macro with the specified alignment font etc
   * 
   * @exception IOException 
   * @param a the alignment
   * @param tm the text macro
   * @param f the font
   */
  public void writeMacroLine(TextMacro tm, Font f, Alignment a)
   throws IOException
  {
    // Clear any pending text
    if (getText() != null)
    {
      newLine();
    }

    clearText();

    // Take care of the font
    BaseFont oldfont = getFont();
    baseSetFont(f);

    // Create text block in order to compute the line height etc
    TextBlock text = new TextBlock(tm.getText(), f, divWriteFunctions);

    Line[] lines = text.getLines(getWidth());

    double lineHeight = 0.0;

    for (int i = 0; i < lines.length ; i++)
    {
      lineHeight += lines[i].getHeight();
    }
    
    // Adjust the y position by the line height and a newline, so that
    // subsequent text will be written in the correct place
    divWriteFunctions.writeAdjustY((int) lineHeight);

    divWriteFunctions.writeTextMacro(tm, a);

    baseSetFont(oldfont);
  }

  /**
   * Executes the sequence of commands.  Merely invokes the write functions
   * on the diversion write functions, which replays the stored atomic
   * commands
   * 
   * @exception IOException 
   */
  void write() throws IOException
  {
    divWriteFunctions.write();
  }
}


