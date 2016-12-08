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

import orinoco.layout.HeaderDefinition;

/**
 * A column heading for a table.  If font or alignment are not specified for
 * this heading, then the heading will assume the heading to have the same
 * font or alignment as the rest of the column
 */
public class Heading extends HeaderDefinition
{
  /**
   * Constructor
   * 
   * @param t the heading text
   */
  public Heading(String t)
  {
    this(t,1);
  }

  /**
   * Constructor
   * 
   * @param a the heading alignment
   * @param t the heading text
   */
  public Heading(String t, Alignment a)
  {
    this(t, a, 1);
  }

  /**
   * Constructor
   * 
   * @param f the heading font
   * @param t the heading text
   */
  public Heading(String t, Font f)
  {
    this(t, f, 1);
  }

  /**
   * Constructor
   * 
   * @param a the alignment
   * @param t the heading text
   * @param f the heading font
   */
  public Heading(String t, Alignment a, Font f)
  {
    this(t, a, f, 1);
  }

  /**
   * Constructor
   * 
   * @param t the heading text
   * @param span the column span
   */
  public Heading(String t, int span)
  {
    super(t,span);
  }

  /**
   * Constructor
   *
   * @param t the heading text
   * @param a the heading alignment
   * @param span the column span
   */
  public Heading(String t, Alignment a, int span)
  {
    super(t, a, span);
  }

  /**
   * Constructor
   * 
   * @param t the heading text
   * @param f the heading font
   * @param span the column span
   */
  public Heading(String t, Font f, int span)
  {
    super(t, f, span);
  }

  /**
   * Constructor
   * 
   * @param t the heading text
   * @param a the alignment
   * @param f the heading font
   * @param span the column span
   */
  public Heading(String t, Alignment a, Font f, int span)
  {
    super(t, a, f, span);
  }
}
