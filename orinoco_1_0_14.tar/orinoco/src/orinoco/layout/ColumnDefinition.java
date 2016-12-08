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
 * The column definition for a table
 */
public class ColumnDefinition
{
  /**
   * The width in points
   */
  private double width;

  /**
   * The alignment for entries within this table
   */
  private Alignment alignment;

  /**
   * The font for all items in this column
   */
  private BaseFont font;

  /**
   * Constructor
   * 
   * @param w the width in cms
   */
  protected ColumnDefinition(double w)
  {
    width = w * Constants.POINTS_PER_CM;
    alignment = Alignment.LEFT;
    font = Font.DEFAULT;
  }

  /**
   * Constructor
   * 
   * @param w the width
   * @param a the alignment of text
   */
  protected ColumnDefinition(double w, Alignment a)
  {
    width = w * Constants.POINTS_PER_CM;
    alignment = a;
    font = Font.DEFAULT;
  }

  /**
   * Constructor
   *
   * @param w the width of the column
   * @param f the font
   */
  public ColumnDefinition(double w, BaseFont f)
  {
    width = w * Constants.POINTS_PER_CM;
    alignment = Alignment.LEFT;
    font = f;
  }

  /**
   * Constructor
   * 
   * @param w the width in cms
   * @param a the alignment
   * @param f the font
   */
  protected ColumnDefinition(double w, Alignment a, BaseFont f)
  {
    width = w * Constants.POINTS_PER_CM;
    alignment = a;
    font = f;
  }

  /**
   * Gets the width of this column
   * 
   * @return the column width in points
   */
  final double getWidth()
  {
    return width;
  }

  /**
   * Gets the default font for this column
   * 
   * @return the font
   */
  final BaseFont getFont()
  {
    return font;
  }

  /**
   * Gets the alignment of this column
   * 
   * @return the alignment
   */
  final Alignment getAlignment()
  {
    return alignment;
  }
}
