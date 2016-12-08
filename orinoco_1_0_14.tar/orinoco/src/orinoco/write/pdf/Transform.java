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

package orinoco.write.pdf;

/**
 * Performs one dimensional translation and scale operations.  An (x,y)
 * transform is composed of two different instances of this object
 */
class Transform
{
  /**
   * The amount by which additively modify the value
   */
  private double shift;
  /**
   * the amount by which to multiply the shifted value
   */
  private double scale;
  
  /**
   * Constructor
   * 
   * @param sh the shift
   * @param sc the scale
   */
  public Transform(double sh, double sc)
  {
    shift = sh; 
    scale = sc;
  }

  /**
   * Transforms the value
   * 
   * @param v the value to transform
   * @return the transformed value
   */
  public int transform(double v)
  {
    return (int) Math.round(v * scale + shift);
  }

  /**
   * Just alters the value passed in by the scale - does not perform any
   * shifting
   * 
   * @param v the value to adjust
   * @return the adjusted amount
   */
  public int adjust(double v)
  {
    return (int) Math.round(v * scale);
  }
}
