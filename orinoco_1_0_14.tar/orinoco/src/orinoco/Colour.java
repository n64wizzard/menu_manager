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
 * Enumeration class listing the types of colour
 */
public class Colour
{
  /**
   * The value associated with this colour
   */
  private double value;

  /**
   * The red component of the rgb value
   */
  private double red;

  /**
   * The green component of the rgb value
   */
  private double green;

  /**
   * The blue component of the rgb value
   */
  private double blue;

  /**
   * The rgb string
   */
  private String rgbString;

  /**
   * Indicates whether this is a gray shade, or a full rgb colour
   */
  private boolean grey;

  /** 
   * Constructor
   *
   * @param val the value
   */
  private Colour(double val)
  {
    value = val;
    grey = true;
  }

  /** 
   * Constructor
   *
   * @param r the red intensity - a value between 0 and 1
   * @param g the green intensity - a value between 0 and 1
   * @param b the blue intensity - a value between 0 and 1
   * @deprecated use the standard integer constructor instead
   */
  public Colour(double r, double g, double b)
  {
    // Constrain the number passed in to fall between 0 and 255
    r = Math.max(0, r);
    g = Math.max(0, g);
    b = Math.max(0, b);
    r = Math.min(1, r);
    g = Math.min(1, g);
    b = Math.min(1, b);

    this.red = r;
    this.green = g;
    this.blue = b;
    grey = false;
    StringBuffer sb = new StringBuffer();
    sb.append(String.valueOf(red));
    sb.append(' ');
    sb.append(String.valueOf(green));
    sb.append(' ');
    sb.append(String.valueOf(blue));
    rgbString = sb.toString();
  }

  /** 
   * Constructor
   *
   * @param r the red intensity - a value between 0 and 255
   * @param g the green intensity - a value between 0 and 255
   * @param b the blue intensity - a value between 0 and 255
   */
  public Colour(int r, int g, int b)
  {
    // Constrain the number passed in to fall between 0 and 1
    r = Math.max(0, r);
    g = Math.max(0, g);
    b = Math.max(0, b);
    r = Math.min(255, r);
    g = Math.min(255, g);
    b = Math.min(255, b);

    red   = ((double) r)/255.0;
    green = ((double) g)/255.0;
    blue  = ((double) b)/255.0;
    grey  = false;
    StringBuffer sb = new StringBuffer();
    sb.append(String.valueOf(red));
    sb.append(' ');
    sb.append(String.valueOf(green));
    sb.append(' ');
    sb.append(String.valueOf(blue));
    rgbString = sb.toString();
  }

  /**
   * Gets the value associated with this colour
   *
   * @return the value
   */
  public double getValue()
  {
    return value;
  }

  /**
   * Gets the rgb values as a string
   */
  public String getRGB()
  {
    return rgbString;
  }

  /**
   * Indicates whether this colour is grey
   */
  public boolean isGrey()
  {
    return grey;
  }

  /**
   * Indicates whether this colour is RGB
   */
  public boolean isRGB()
  {
    return !grey;
  }

  /**
   * The list of greys
   */
  public static final Colour BLACK = new Colour(0);
  public static final Colour WHITE = new Colour(1);
  public static final Colour GREY_20 = new Colour(0.2);
  public static final Colour GREY_40 = new Colour(0.4);
  public static final Colour GREY_60 = new Colour(0.6);
  public static final Colour GREY_80 = new Colour(0.8);
  public static final Colour GREY_90 = new Colour(0.9);
  public static final Colour DEFAULT_BACKGROUND = WHITE;
  public static final Colour DEFAULT_FOREGROUND = BLACK;

  /**
   * The list of rgb colours
   */
  public static final Colour RED = new Colour(255,0,0);
  public static final Colour GREEN = new Colour(0,255,0);
  public static final Colour BLUE = new Colour(0,0,255);
}
