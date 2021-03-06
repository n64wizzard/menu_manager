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
import java.util.Iterator;
import java.io.OutputStreamWriter;
import java.io.IOException;

import orinoco.Paper;

/**
 * The PDF Pages objects.  Contains the list of pages in this document
 */
class Pages extends PDFObject
{
  /**
   * The list of page objects
   */
  private ArrayList pages;

  /**
   * The paper width in points
   */
  private int paperWidth;

  /**
   * The paper height in points
   */
  private int paperHeight;

  /**
   * Constructor
   * 
   * @param w the width
   * @param num the object number
   * @param h the height
   */
  Pages(ObjectNumber num, int w, int h)
  {
    super(num);
    pages = new ArrayList(10);
    paperWidth = w;
    paperHeight = h;
  }

  /**
   * Adds a new page to the list of pages
   * 
   * @param p the new page
   */
  void addPage(Page p)
  {
    pages.add(p.getNumber());
  }

  /**
   * Writes out the list of pages
   * 
   * @exception IOException 
   * @param out output stream
   */
  void write(OutputStreamWriter out) throws IOException
  {
    getNumber().writeObj(out);
    out.write("<<");
    out.write(PDFWriter.newLineChar);
    out.write("/Type /Pages");
    out.write(PDFWriter.newLineChar);
    out.write("/Kids [");
    
    Iterator i = pages.iterator();
    ObjectNumber on = null;
    while (i.hasNext())
    {
      out.write(PDFWriter.newLineChar);
      on = (ObjectNumber) i.next();
      on.writeRef(out);
    }
    out.write(PDFWriter.newLineChar);
    out.write("] ");
    out.write("/Count ");
    out.write(String.valueOf(pages.size()));
    out.write(PDFWriter.newLineChar);
    out.write("/MediaBox [0 0 ");
    out.write(String.valueOf(paperWidth));
    out.write(" ");
    out.write(String.valueOf(paperHeight));
    out.write("]");
    out.write(PDFWriter.newLineChar);
    out.write(">>");
    out.write(PDFWriter.newLineChar);
    out.write("endobj");
    out.write(PDFWriter.newLineChar);
  }
}
