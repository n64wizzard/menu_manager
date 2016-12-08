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

import orinoco.Alignment;
import orinoco.TextMacro;

/**
 * A cell within a row within a table
 */
class Cell
{
  /**
   * The contents of the cell
   */
  private TextBlock text;

  /**
   * The lines within the text block
   */
  private Line[] lines;

  /**
   * The amount of space required for this cell
   */
  private double height;

  /**
   * The width of this cell
   */
  private double width;

  /**
   * The alignment of the contents within this cell
   */
  private Alignment alignment;

  /**
   * The specified font for this cell
   */
  private BaseFont font;
  
  /**
   * The initialized flag
   */
  private boolean initialized;

  /**
   * A handle to the format characters
   */
  private FormatCharacters formatCharacters;

  /**
   * Constructor
   * 
   * @param w the width of the cell
   * @param a the alignment of text within the cell
   * @param t the text
   * @param f the font
   * @param fc the format characters
   */
  public Cell(String t, double w, BaseFont f, Alignment a, FormatCharacters fc)
  {
    formatCharacters = fc;
    text = new TextBlock(t, f, formatCharacters);
    width = w;
    alignment = a;
    font = f;    
  }

  /**
   * Consructs an empty cell
   * 
   * @param w the width of the cell
   * @param a the alignment
   * @param f the font
   * @param fc the format chars
   */
  public Cell(double w, BaseFont f, Alignment a, FormatCharacters fc)
  {
    formatCharacters = fc;
    width = w;
    alignment = a;
    font = f;
  }

  /**
   * Performs any calculations associated with this cell
   */
  private void init()
  {
    if (text == null)
    {
      lines = new Line[0];
      initialized = true;
      return;
    }

    lines = text.getLines(width);
    
    for (int i = 0; i < lines.length; i++)
    {
      height += lines[i].getHeight();
    }

    if (height == 0)
    {
      height = font.getPointSize();
    }

    initialized = true;
  }

  /**
   * Gets the height of this cell
   * 
   * @return the height required for this cell
   */
  public double getHeight()
  {
    if (!initialized)
    {
      init();
    }

    return height;
  }

  /**
   * Gets the lines of text
   * 
   * @return the lines of text within this cell
   */
  public Line[] getLines()
  {
    if (!initialized)
    {
      init();
    }

    return lines;
  }

  /**
   * Gets the alignment
   * 
   * @return the text alignment within this cell
   */
  public Alignment getAlignment()
  {
    return alignment;
  }

  /**
   * Gets the font for this cell
   * 
   * @return the font used for this cell's contents
   */
  public BaseFont getFont()
  {
    return font;
  }

  /**
   * Adds the specified string to the text block in this cell
   * 
   * @param s the text
   */
  public void add(String s)
  {
    add(s, font);
  }

  /**
   * Adds the specified string to the text block in this cell
   * 
   * @param s the text
   * @param f the font
   */
  public void add(String s, BaseFont f)
  {
    if (text == null)
    {
      text = new TextBlock(s, f, formatCharacters);
    }
    else
    {
      text.add(s, f);
    }
  }


  /**
   * Adds the specified string macro to the cell
   * 
   * @param tm the text macro
   */
  public void add(TextMacro tm)
  {
    add(tm, font);
  }

  /**
   * Adds the specified string macro to the cell
   * 
   * @param tm the text macro
   * @param f the font
   */
  public void add(TextMacro tm, BaseFont f)
  {
    if (text == null)
    {
      text = new TextBlock(tm, f, alignment, formatCharacters);
    }
    else
    {
      text.add(tm, f);
    }
  }
}
