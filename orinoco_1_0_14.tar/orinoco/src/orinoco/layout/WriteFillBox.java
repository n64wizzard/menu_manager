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

import java.io.IOException;
import orinoco.Colour;
import orinoco.write.FormatWriter;

/**
 * Atomic command implementation
 */
class WriteFillBox implements Command
{
  /**
   * The output writer
   */
  private FormatWriter writer;
  /**
   */
  private double x1;
  /**
   */
  private double y1;
  /**
   */
  private double width;
  /**
   */
  private double height;
  /**
   * The point size of the line
   */
  private Colour colour;

  /**
   * Constructor
   * 
   * @param x1 
   * @param c 
   * @param h 
   * @param fw 
   * @param w 
   * @param y1 
   */
  public WriteFillBox(FormatWriter fw, 
                      double x1, double y1,
                      double w, double h,
                      Colour c)
  {
    writer = fw;
    this.x1 = x1;
    this.y1 = y1;
    this.width = w;
    this.height = h;
    this.colour = c;
  }

  /**
   * Executes this command
   * 
   * @exception IOException 
   */
  public void execute() throws IOException
  {
    writer.writeFillBox(x1, y1, width, height, colour);
  }
}
