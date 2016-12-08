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

import orinoco.Paper;
import orinoco.Colour;
import orinoco.layout.BaseFont;

/**
 * A single page of a PDF document
 */
class Page extends PDFObject
{
  /**
   * Reference to the parent pages object
   */
  private ObjectNumber pages;

  /**
   * A handle to the fonts object
   */
  private Fonts fonts;

  /**
   * The resources used by this page
   */
  private PageResources resources;

  /**
   * The contents of this page.  This is opened as a continuous stream
   */
  private PageContents contents;

  /**
   * Constructor
   * 
   * @param w the writer
   * @param xref the cross referencer
   * @param num the object number
   * @param f the list of fonts
   * @param p the list of pages
   * @exception IOException 
   */
  Page(ObjectNumber num, Pages p, Fonts f, CrossReferencer xref, 
       PDFWriter w) throws IOException
  {
    super(num);
    pages = p.getNumber();
    fonts = f;
    resources = new PageResources(xref.createObjectNumber());
    contents = new PageContents(xref.createObjectNumber(), xref, w);
    contents.open();
  }

  /**
   * Writes out the new font
   * 
   * @param f the new font
   * @exception IOException 
   */
  void writeFont(BaseFont f) throws IOException
  {
    Font font = fonts.getFont(f.getFaceName());
    resources.add(font);
    contents.writeFont(font, f.getPointSize());
  }

  /**
   * Writes out the new position
   * 
   * @param y the y position
   * @param x the x position
   * @exception IOException 
   */
  void writeMoveTo(int x, int y) throws IOException
  {
    contents.writeMoveTo(x,y);
  }

  /**
   * Writes out the text at the current position
   * 
   * @param text the text
   * @exception IOException 
   */
  void writeText(String text) throws IOException
  {
    contents.writeText(text);
  }

  /**
   * Draws a line of the width specified  between the points specified
   * 
   * @param w the width of the line
   * @param x1 
   * @param y2 
   * @param x2 
   * @param y1 
   * @exception IOException 
   */
  public void writeDrawLine(int x1, int y1, int x2, int y2, int w)
    throws IOException
  {
    contents.writeDrawLine(x1, y1, x2, y2, w);
  }

  /**
   * Draws a box with the given width and height at the specified x and y
   * position, and fills it with the specified colour
   * 
   * @param c the fill colour
   * @param x1 the x position
   * @param y2 the y position
   * @param x2 the x position
   * @param y1 the y position
   * @exception IOException 
   */
  public void writeFillBox(int x1, int y1, int x2, int y2,
                           Colour c) throws IOException
  {
    contents.writeFillBox(x1, y1, x2, y2, c);
  }


  /**
   * Closes this page.  Calls close on the page contents
   * 
   * @exception IOException 
   */
  void close() throws IOException
  {
    contents.close();
  }

  /**
   * Writes out this page
   * 
   * @exception IOException 
   * @param out the output writer
   */
  void write(OutputStreamWriter out) throws IOException
  {
    getNumber().writeObj(out);
    out.write("<<");
    out.write(PDFWriter.newLineChar);
    out.write("/Type /Page");
    out.write(PDFWriter.newLineChar);
    out.write("/Parent ");
    pages.writeRef(out);
    out.write(PDFWriter.newLineChar);
    out.write("/Resources ");
    resources.getNumber().writeRef(out);
    out.write(PDFWriter.newLineChar);
    out.write("/Contents ");
    contents.getNumber().writeRef(out);
    out.write(PDFWriter.newLineChar);
    out.write(">>");
    out.write(PDFWriter.newLineChar);
    out.write("endobj");
    out.write(PDFWriter.newLineChar);

    resources.write(out);
  }
}
