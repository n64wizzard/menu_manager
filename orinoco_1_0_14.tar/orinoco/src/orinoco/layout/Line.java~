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

import java.util.ArrayList;

/**
 * A line of a text block.  Consists of one or more text componennts
 */
public class Line
{
  /**
   * An optimization if the line only consists of one text component
   */
  private TextComponent text;

  /**
   * The array list of text components
   */
  private ArrayList textComponents;

  /**
   * The height of the line ie. the max point size of all the fonts in the
   * text component
   */
  private int height;

  /**
   * The length of the line
   */
  private double length;

  /**
   * Constructor
   * 
   * @param t the first text component
   */
  public Line(TextComponent t)
  {
    text = t;
    height = text.getFont().getPointSize();
    length = t.getLength();
  }

  /**
   * Gets the height of this line
   * 
   * @return the height of the line, in points
   */
  public int getHeight()
  {
    return height;
  }

  /**
   * Gets the length of this line, in points
   * 
   * @return the line length
   */
  public final double  getLength()
  {
    return length;
  }

  /**
   * Adds the text component to this line
   * 
   * @param tc the text component to add
   */
  public void add(TextComponent tc)
  {
    if (textComponents == null)
    {
      textComponents = new ArrayList();
      textComponents.add(text);
      text = null;
    }

    textComponents.add(tc);
    length += tc.getLength();
    height = Math.max(height, tc.getFont().getPointSize());
  }

  /**
   * Gets all the text components on the line.  If the line consists of text
   * in the same font, then a single text component will be returned
   * 
   * @return the text components on this line
   */
  public TextComponent[] getText()
  {
    if (text != null)
    {
      return new TextComponent[] { text };
    }

    TextComponent[] texts = new TextComponent[textComponents.size()];

    for (int i = 0; i < texts.length; i++)
    {
      texts[i] = (TextComponent) textComponents.get(i);
    }

    return texts;
  }
}
