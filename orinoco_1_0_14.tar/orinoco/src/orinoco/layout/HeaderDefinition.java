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

import orinoco.Font;
import orinoco.Alignment;

/**
 * The table heading definition for a table
 */
public class HeaderDefinition
{
  /**
   * The alignment for entries within this table
   */
  private Alignment alignment;

  /**
   * The font for this heading
   */
  private BaseFont font;

  /**
   * The text for this column definition
   */
  private String text;

  /**
   * The column span for this heading
   */
  private int columnSpan;

  /**
   * Constructor
   * 
   * @param t the header text
   */
  protected HeaderDefinition(String t, int span)
  {
    text = t;
    columnSpan = span;
  }

  /**
   * Constructor
   * 
   * @param t the header text
   * @param a the header alignment
   * @param span the column span
   */
  protected HeaderDefinition(String t, Alignment a, int span)
  {
    text = t;
    alignment = a;
    columnSpan = span;
  }

  /**
   * Constructor
   * 
   * @param t the header text
   * @param f the header font
   * @param span the column span
   */
  protected HeaderDefinition(String t, BaseFont f, int span)
  {
    text = t;
    font = f;
    columnSpan = span;
  }

  /**
   * Constructor
   * 
   * @param t the header text
   * @param a the heaer alignment
   * @param f the header font
   * @param span the column span
   */
  protected HeaderDefinition(String t, Alignment a, BaseFont f, int span)
  {
    text = t;
    alignment = a;
    font = f;
    columnSpan = span;
  }

  /**
   * Gets the font for the header
   * 
   * @return the font
   */
  final BaseFont getFont()
  {
    return font;
  }

  /**
   * Gets the text alignment of the header
   * 
   * @return the alignment
   */
  final Alignment getAlignment()
  {
    return alignment;
  }

  /**
   * Gets the text of for the header
   * 
   * @return the header text
   */
  final String getText()
  {
    return text;
  }

  /**
   * Initializes any data for this header from the rest of the column
   * definition
   *
   * @param c the column definition
   */
  final void init(ColumnDefinition c)
  {
    if (font == null)
    {
      font = c.getFont();
    }

    if (alignment == null)
    {
      alignment = c.getAlignment();
    }
  }

  /**
   * Accessor for the column span
   *
   * @return the number of columns spanned by this header
   */
  final int getColumnSpan()
  {
    return columnSpan;
  }

  /**
   * Sets the column span in case the user has specified it incorrectly
   *
   * @param span the actual column span
   */
  final void setColumnSpan(int span)
  {
    columnSpan = span;
  }
}


