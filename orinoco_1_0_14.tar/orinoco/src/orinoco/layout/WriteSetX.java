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
import orinoco.Alignment;
import orinoco.write.FormatWriter;

/**
 * Atomic command implementation
 */
class WriteSetX implements Command
{
  /**
   * The output writer
   */
  private FormatWriter writer;
  /**
   * the new x position
   */
  private double x;

  /**
   * Constructor
   * 
   * @param fw the output writer
   * @param x the new xposition
   */
  public WriteSetX(FormatWriter fw, double x)
  {
    writer = fw;
    this.x = x;
  }

  /**
   * Executes the command
   * 
   * @exception IOException 
   */
  public void execute() throws IOException
  {
    writer.writeSetX(x);
  }
}
