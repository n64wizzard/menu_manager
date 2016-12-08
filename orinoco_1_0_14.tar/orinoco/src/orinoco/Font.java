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

import orinoco.layout.BaseFont;

/**
 * An enumeration containing the available fonts
 */
public class Font extends BaseFont
{
  /**
   */
  public final static FontFace TIMES =
    new FontFace("Times-Roman");
  /**
   */
  public final static FontFace TIMES_BOLD =
    new FontFace("Times-Bold");
  /**
   */
  public final static FontFace TIMES_ITALIC =
    new FontFace("Times-Italic");
  /**
   */
  public final static FontFace TIMES_BOLD_ITALIC =
    new FontFace("Times-Bold-Italic");
  /**
   */
  public final static FontFace COURIER = 
    new FontFace("Courier");
  /**
   */
  public final static FontFace HELVETICA =  
    new FontFace("Helvetica");
  /**
   */
  public final static FontFace HELVETICA_BOLD =  
    new FontFace("Helvetica-Bold");
  /**
   */
  public final static FontFace HELVETICA_BOLD_ITALIC =  
    new FontFace("Helvetica-BoldOblique");
  /**
   */
  public final static FontFace HELVETICA_ITALIC =  
    new FontFace("Helvetica-Oblique");


  /**
   */
  public final static Font DEFAULT = new Font(TIMES);

  /**
   * Constructor
   * 
   * @param f the font face
   */
  public Font(FontFace f)
  {
    super(f);
  }

  /**
   * Constructor
   * 
   * @param ps the point size for the font
   * @param f the font face
   */
  public Font(FontFace f, int ps)
  {
    super(f, ps);
  }
}

