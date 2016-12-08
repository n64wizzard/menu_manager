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
import orinoco.write.FormatWriter;

/**
 * Atomic command implementation
 */
class WriteTextMacroLine implements Command
{
  /**
   * The output command
   */
  private FormatWriter writer;
  /**
   * The alignment of the macro text
   */
  private Alignment alignment;
  /**
   * The macro
   */
  private TextMacro macro;
  /**
   * The font
   */
  private BaseFont font;
  /**
   * The available width
   */
  private double width;

  /** 
   * A handle to the format characters
   */
  private FormatCharacters formatCharacters;

  /**
   * Constructor
   * 
   * @param w the width
   * @param a the alignment
   * @param tm the macro
   * @param f the font
   * @param fw 
   * @param fc the format characters
   */
  public WriteTextMacroLine(FormatWriter fw, TextMacro tm, BaseFont f,
                            Alignment a, double w,
                            FormatCharacters fc)
  {
    writer = fw;
    alignment = a;
    macro = tm;
    width = w;
    font = f;
    formatCharacters = fc;
  }

  /**
   * Executes this command
   * 
   * @exception IOException 
   */
  public void execute() throws IOException
  {
    // Fonts, newlines have already been taken care of.  We only 
    // need be concerned with the text and the alignment

    TextBlock text = new TextBlock(macro.getText(), font, 
                                   formatCharacters);

    Line[] lines = text.getLines(width);

    for (int i = 0; i < lines.length ; i++)
    {
      if (alignment == Alignment.RIGHT)
      {
        writer.writeSetX(width - lines[i].getLength());
      }
      else if (alignment == Alignment.CENTRE)
      {
        writer.writeSetX((width - lines[i].getLength())/2.0);
      }

      TextComponent[] tc = lines[i].getText();
      
      for (int j = 0; j < tc.length; j++)
      {
        writer.writeText(tc[j].getString());
      }
    }
  }
}
