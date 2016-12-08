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

/**
 * Contains the font details for displaying text items
 */
public class BaseFont
{
  /**
   * The font face
   */
  private Face face;
  /**
   * The point size
   */
  private int pointSize;
  /**
   * the font face
   */
  private FontFace fontFace;

  protected static class FontFace
  {
    /**
     * The name of this font, as it is displayed in the output format
     */
    public String name;

    /**
     * Constructor
     * 
     * @param s the name of this face
     */
    public FontFace(String s)
    {
      name = s;
    }
  }

  /**
   * The default font point size
   */
  private final static int defaultPointSize = 10;

  /**
   * Constructor
   * 
   * @param f the face of this font
   */
  protected BaseFont(FontFace f)
  {
    fontFace = f;
    face = Face.getFace(f.name);
    pointSize = defaultPointSize;
  }

  /**
   * Constructor
   * 
   * @param ps the point size
   * @param f the face
   */
  protected BaseFont(FontFace f, int ps)
  {
    fontFace = f;
    face = Face.getFace(f.name);
    pointSize = ps;
  }

  /**
   * Accessor for the point size of this font
   * 
   * @return the point size
   */
  public int getPointSize()
  {
    return pointSize;
  }

  /**
   * Gets the width in points of the specified characters.  This is used
   * in order to calculate the widths of strings so that their position
   * on the page may be calculated
   * 
   * @param c the character
   * @return the width of the character in points as it will be rendered in  
   *     this font
   */
  final double getCharacterWidth(char c)
  {
    return face.getWidth(c) * pointSize;
  }

  /**
   * Gets the face name of this font
   * 
   * @return the font face
   */
  public final String getFaceName()
  {
    return fontFace.name;
  }

  /**
   * Equals method
   * 
   * @param o the object to compare to
   * @return TRUE if the two fonts are equal, FALSE otherwise
   */
  public boolean equals(Object o)
  {
    if (o == this)
    {
      return true;
    }

    if (!(o instanceof BaseFont))
    {
      return false;
    }

    BaseFont compare = (BaseFont) o;

    return (fontFace == compare.fontFace &&
            pointSize == compare.pointSize);
  }
}
