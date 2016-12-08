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

/**
 * A text component which contains a macro. This is normally used when
 * writing to cells in diversions
 */
public class TextMacroComponent extends TextComponent
{
  /**
   * The text macro
   */
  private TextMacro macro;

  /**
   * The alignment
   */
  private Alignment alignment;

  /**
   * Constructor
   * 
   * @param a the alignment
   * @param tm the text macro
   * @param f the font
   * @param fc the format characters
   */
  public TextMacroComponent(TextMacro tm, BaseFont f, Alignment a, 
                            FormatCharacters fc)
  {
    super(tm.getText(), f, fc);
    macro = tm;
    alignment = a;
  }

  /**
   * Single despatch method called from the writer
   * 
   * @exception IOException 
   * @param w the writer
   */
  public void write(BaseWriter w) throws IOException
  {
    w.getWriteFunctions().writeTextMacro(macro, alignment);
  }

  /**
   * Append doesn't do anything for now
   * 
   * @param s the text to append
   */
  void append(String s)
  {
    System.err.println("Warning:  cannot append to a text macro");
  }
}
