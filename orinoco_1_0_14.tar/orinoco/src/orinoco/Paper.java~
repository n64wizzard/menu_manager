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

/**
 * An enumeration type containing the available paper dimension.
 * Paper objects may be cloned, and margins set to a different values to
 * the default paper sizes.  
 * The default margins on the predefined paper sizes are 1cm all the way around
 */
public final class Paper
{
  /**
   * The width of the paper in cms
   */
  private double width;
  /**
   * The height of the paper (in cms)
   */
  private double height;
  /**
   * The vertical margin in cms.  Applies to both the top and bottom of
   * each page
   */
  private double verticalMargin;
  /**
   * The horizontal margin in cms.  Applies to both the left and right of
   * the page
   */
  private double horizontalMargin;

  /**
   * Constructor
   * 
   * @param w the width
   * @param vm the vertical margin
   * @param h the height
   * @param hm the horizontal margin
   */
  private Paper(double w, double h, double vm, double hm)
  {
    width = w;
    height = h;
    verticalMargin = vm;
    horizontalMargin = hm;
  }

  /**
   * A copy constructor, taking in an existing paper.  By invoking this
   * constructor with a standard paper size, client applications are able
   * to modify the default margin settings 	
   * 
   * @param p the paper to bopy
   */
  public Paper(Paper p)
  {
    width = p.width;
    height = p.height;
    verticalMargin = p.verticalMargin;
    horizontalMargin = p.horizontalMargin;
  }

  /**
   * Accessor for the paper height
   * 
   * @return the paper height (in cms)
   */
  public double getHeight() { return height; }
  /**
   * Accessor for the paper width
   * 
   * @return the paper width in cms
   */
  public double getWidth()  { return width; }

  /**
   * Accessor for the top and bottom page margins
   * 
   * @return the vertical margin in cms
   */
  public double getVerticalMargin() { return verticalMargin; }
  /**
   * Accessor for the page's horizontal margins
   * 
   * @return the horizontal margin in cms
   */
  public double getHorizontalMargin() { return horizontalMargin; }

  /**
   * Sets the horizontal margin 
   * 
   * @param hm the width of the left and right margins, in cms
   */
  public void setHorizontalMargin(double hm) { horizontalMargin = hm; }
  /**
   * Sets the vertical margins
   * 
   * @param vm the vertical margin in cms
   */
  public void setVerticalMargin(double vm) { verticalMargin = vm; }
 
  /**
   * Standard UK A4 paper
   */
  public static final Paper A4 = new Paper(21, 29.7, 1, 1);
  /**
   * A4 landscape
   */
  public static final Paper A4_LANDSCAPE = new Paper(29.7, 21, 1, 1);

  /**
   * Standard US letter size
   */
  public static final Paper LETTER = new Paper(21.59, 27.94, 1, 1);
  /**
   * US Letter size in landscape
   */
  public static final Paper LETTER_LANDSCAPE = new Paper(27.94, 21.59, 1, 1);
}
