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
class WriteSetY implements Command
{
  /**
   * the output writer
   */
  private FormatWriter writer;
  /**
   * the new y position
   */
  private double y;

  /**
   * Constructor
   * 
   * @param y the new y position
   * @param fw the output writer
   */
  public WriteSetY(FormatWriter fw, double y)
  {
    writer = fw;
    this.y = y;
  }

  /**
   * Executes the command
   * 
   * @exception IOException 
   */
  public void execute() throws IOException
  {
    writer.writeSetY(y);
  }
}
