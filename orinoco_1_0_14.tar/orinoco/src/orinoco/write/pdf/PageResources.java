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

import java.util.HashSet;
import java.util.Iterator;
import java.io.OutputStreamWriter;
import java.io.IOException;

import orinoco.layout.BaseFont;

/**
 * The resources used by a page
 */
class PageResources extends PDFObject
{  
  /**
   * The fonts used on this page
   */
  private HashSet fonts;

  /**
   * Constructor
   * 
   * @param num object number
   */
  PageResources(ObjectNumber num)
  {
    super(num);
    fonts = new HashSet();
  }

  /**
   * Adds the font to the list of resources used by this page
   * 
   * @param f the font
   */
  void add(Font f)
  {
    fonts.add(f);
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
    out.write("<<");
    out.write(PDFWriter.newLineChar);
    out.write("/ProcSet [/PDF /Text]");
    out.write(PDFWriter.newLineChar);
    out.write("/Font <<");

    Iterator i = fonts.iterator();
    Font font = null;
    while (i.hasNext())
    {
      font = (Font) i.next();
      out.write(" /");
      out.write(font.getName());
      out.write(" ");
      font.getNumber().writeRef(out);
    }
    out.write(">>");
    out.write(PDFWriter.newLineChar);
    out.write(">>");
    out.write(PDFWriter.newLineChar);
    out.write("endobj");
    out.write(PDFWriter.newLineChar);
  }
}
