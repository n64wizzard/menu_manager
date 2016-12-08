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

import java.io.OutputStreamWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The cross reference object.  Maintains a list of all pdf objects used
 * in this file
 */
class CrossReferencer
{
  /**
   * The list of object references
   */
  private ArrayList objects;

  /**
   * Handle to the PDF writer
   */
  private PDFWriter writer;

  /**
   * Constructor
   * 
   * @param w the pdf writer
   */
  public CrossReferencer(PDFWriter w)
  {
    objects = new ArrayList(100);
    writer = w;
  }

  /**
   * Initializes a new object number
   * 
   * @return the new object number
   */
  public ObjectNumber createObjectNumber()
  {
    // object numbers are 1-based
    ObjectNumber on = new ObjectNumber(getSize(), writer);
    objects.add(on);
    return on;
  }

  /**
   * Writes out the cross reference table
   * 
   * @exception IOException 
   * @param out the output stream
   */
  void write(OutputStreamWriter out) throws IOException
  {
    out.write("xref");
    out.write(PDFWriter.newLineChar);
    out.write("0 ");
    out.write(String.valueOf(getSize()));
    out.write(PDFWriter.newLineChar);

    // First entry is always the same
    out.write("0000000000 65535 f ");
    out.write(PDFWriter.newLineChar);

    DecimalFormat byteOffsetFormat = new DecimalFormat("0000000000 ");

    Iterator i = objects.iterator();
    ObjectNumber on = null;
    while (i.hasNext())
    {
      on = (ObjectNumber) i.next();
      out.write(byteOffsetFormat.format(on.getOffset()));
      out.write("00000 n ");
      out.write(PDFWriter.newLineChar);
    }
  }

  /**
   * Gets the size of the cross reference table.  This will be one more
   * than the number of objects on the list, to allow for the empty object
   * 
   * @return the number of objects
   */
  final int getSize()
  {
    return objects.size() + 1;
  }
}
