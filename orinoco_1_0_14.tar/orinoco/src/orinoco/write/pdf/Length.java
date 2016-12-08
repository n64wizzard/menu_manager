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

import java.util.ArrayList;
import java.io.OutputStreamWriter;
import java.io.IOException;

import orinoco.layout.BaseFont;

/**
 * Object used to contain the length of a object
 */
class Length extends PDFObject
{
  /**
   * The length of the stream
   */
  private int length;

  /**
   * Constructor
   * 
   * @param num the object number
   */
  Length(ObjectNumber num)
  {
    super(num);
  }

  /**
   * Sets the length when the length is actually known
   * 
   * @param l the length
   */
  void setLength(int l)
  {
    length = l;
  }

  /**
   * Write method
   * 
   * @exception IOException 
   * @param out the output stream
   */
  void write(OutputStreamWriter out) throws IOException
  {
    getNumber().writeObj(out);
    out.write(String.valueOf(length));
    out.write(PDFWriter.newLineChar);
    out.write("endobj");
    out.write(PDFWriter.newLineChar);
  }
}
