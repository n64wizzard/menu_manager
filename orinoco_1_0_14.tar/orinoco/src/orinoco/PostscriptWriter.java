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

import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

import orinoco.write.FormatWriter;

/**
 * Outputs the requested data in postscript format
 */
public class PostscriptWriter extends orinoco.write.ps.PostscriptWriter
  implements OutputFormatWriter
{
  /**
   * Constructor
   * 
   * @exception IOException 
   * @param os the output stream
   */
  public PostscriptWriter(OutputStream os) throws IOException
  {
    super(os);
  }

  /**
   * Constructor
   * 
   * @exception IOException 
   * @param f the file to write the generated postscript to
   */
  public PostscriptWriter(File f) throws IOException
  {
    super(f);
  }
}
