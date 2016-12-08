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

import orinoco.Colour;

/**
 * The contents of a page
 */
class PageContents extends PDFObject
{  
  /**
   * A handle to the PDFWriter
   */
  private PDFWriter writer;

  /**
   * The length of the stream
   */
  private Length length;

  /**
   * The start offset of the stream
   */
  private int streamStartPos;

  /**
   * The current state of the stream
   */
  private StreamState streamState;
  
  /**
   * Private class indicating the state
   */
  private static final class StreamState {};

  // The stream states
  private static final StreamState none = new StreamState();
  private static final StreamState text = new StreamState();
  private static final StreamState graphics = new StreamState();

  /**
   * Constructor
   *
   * @param num the object number
   * @param xref the cross referencer
   * @param w the pdf writer
   */
  PageContents(ObjectNumber num, CrossReferencer xref, PDFWriter w)
  {
    super(num);
    writer = w;
    length = new Length(xref.createObjectNumber());
    streamState = none;
  }

  /**
   * Opens the output stream for this page
   *
   * @exception IOException
   */
  void open() throws IOException
  {
    getNumber().writeObj(writer.getOutput());
    writer.getOutput().write("<<");
    writer.getOutput().write(PDFWriter.newLineChar);
    writer.getOutput().write("/Length ");
    length.getNumber().writeRef(writer.getOutput());
    writer.getOutput().write(PDFWriter.newLineChar);
    writer.getOutput().write(">>");
    writer.getOutput().write(PDFWriter.newLineChar);
    writer.getOutput().write("stream");
    writer.getOutput().write(PDFWriter.newLineChar);
    streamStartPos = writer.getOffset();
  }

  /**
   * Closes off the stream for this page
   *
   * @exception IOException
   */
  void close() throws IOException
  {
    endTextState();
    length.setLength(writer.getOffset() - streamStartPos);
    writer.getOutput().write(PDFWriter.newLineChar);
    writer.getOutput().write("endstream");
    writer.getOutput().write(PDFWriter.newLineChar);
    writer.getOutput().write("endobj");
    writer.getOutput().write(PDFWriter.newLineChar);
    length.write(writer.getOutput());
  }

  /**
   * Empty implementation. This object writes stuff out as
   * a continuous stream until it is closed
   */
  void write(OutputStreamWriter out) throws IOException
  {
  }

  /**
   * Makes sure the stream state is text.  Closes off any other stream
   * states as necessary
   * @exception IOException
   */
  private void setTextState() throws IOException
  {
    if (streamState == text)
    {
      return;
    }
    else if (streamState == graphics)
    {
      writer.getOutput().write("Q");
      writer.getOutput().write(PDFWriter.newLineChar);
      writer.getOutput().write("BT");
      writer.getOutput().write(PDFWriter.newLineChar);
      streamState = text;
    }
    else if (streamState == none)
    {
      writer.getOutput().write("BT");
      writer.getOutput().write(PDFWriter.newLineChar);
      streamState = text;
    }
  }

  /**
   * Makes sure the stream state is text.  Closes off any other stream
   * states as necessary
   * @exception IOException
   */
  private void endTextState() throws IOException
  {
    if (streamState == none)
    {
      return;
    }
    else if (streamState == text)
    {
      writer.getOutput().write("ET");
      writer.getOutput().write(PDFWriter.newLineChar);
      streamState = none;
    }
  }

  /**
   * Writes out the new font
   * @param f the font
   * @param ps the size of the font
   * @exception IOException
   */
  void writeFont(Font f, int ps) throws IOException
  {
    setTextState();
    writer.getOutput().write("/");
    writer.getOutput().write(f.getName());
    writer.getOutput().write(" ");
    writer.getOutput().write(String.valueOf(ps));
    writer.getOutput().write(" Tf");
    writer.getOutput().write(PDFWriter.newLineChar);
  }

  /**
   * Writes out the new position
   * @param x the x position
   * @param y the y position
   * @exception IOException
   */
  void writeMoveTo(int x, int y) throws IOException
  {
    setTextState();
    writer.getOutput().write("1 0 0 1 ");
    writer.getOutput().write(String.valueOf(x));
    writer.getOutput().write(" ");
    writer.getOutput().write(String.valueOf(y));
    writer.getOutput().write(" Tm");
    writer.getOutput().write(PDFWriter.newLineChar);
  }

  /**
   * Writes out the text at the current position
   * @param text the text to write
   * @exception IOException
   */
  void writeText(String text) throws IOException
  {
    setTextState();
    writer.getOutput().write("(");
    writer.getOutput().write(text);
    writer.getOutput().write(")Tj");
    writer.getOutput().write(PDFWriter.newLineChar);
    
    /*
          writer.getOutput().write("<");
          writer.getOutput().write("feff><");
          for (int i = 0 ; i < text.length() ; i++)
          {
            int ch = (int) text.charAt(i);
            String s = Integer.toHexString((int) ch);
            if (ch < 256)
            {
              writer.getOutput().write("00");
            }
            writer.getOutput().write(s);

          }
          writer.getOutput().write(">Tj");
          writer.getOutput().write(PDFWriter.newLineChar);
    */
  }

  /**
   * Draws a line of the width specified  between the points specified
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @param w the width of the line
   * @exception IOException
   */
  public void writeDrawLine(int x1, int y1, 
                            int x2, int y2, int w)
    throws IOException
  {
    endTextState();
    writer.getOutput().write("q");
    writer.getOutput().write(PDFWriter.newLineChar);
    writer.getOutput().write(String.valueOf(w));
    writer.getOutput().write(" w ");
    writer.getOutput().write(String.valueOf(x1));
    writer.getOutput().write(" ");
    writer.getOutput().write(String.valueOf(y1));
    writer.getOutput().write(" m ");
    writer.getOutput().write(String.valueOf(x2));
    writer.getOutput().write(" ");
    writer.getOutput().write(String.valueOf(y2));
    writer.getOutput().write(" l s ");
    writer.getOutput().write(PDFWriter.newLineChar);
    writer.getOutput().write("Q");
    writer.getOutput().write(PDFWriter.newLineChar);
  }

  /**
   * Draws a box with the given width and height at the specified x and y
   * position, and fills it with the specified colour
   * @param x1
   * @param y1
   * @param x2
   * @param y2
   * @param c the colour to fill the box
   * @exception IOException
   */
  public void writeFillBox(int x1, int y1, int x2, int y2,
                           Colour c) throws IOException
  {
    endTextState();
    writer.getOutput().write("q");
    writer.getOutput().write(PDFWriter.newLineChar);
    if (c.isGrey())
    {
      writer.getOutput().write(String.valueOf(c.getValue()));
      writer.getOutput().write(" g ");
    }
    else
    {
      writer.getOutput().write(c.getRGB());
      writer.getOutput().write(" rg ");
    }
    writer.getOutput().write(String.valueOf(x1));
    writer.getOutput().write(" ");
    writer.getOutput().write(String.valueOf(y1));
    writer.getOutput().write(" m ");
    writer.getOutput().write(String.valueOf(x2));
    writer.getOutput().write(" ");
    writer.getOutput().write(String.valueOf(y1));
    writer.getOutput().write(" l ");
    writer.getOutput().write(String.valueOf(x2));
    writer.getOutput().write(" ");
    writer.getOutput().write(String.valueOf(y2));
    writer.getOutput().write(" l ");
    writer.getOutput().write(String.valueOf(x1));
    writer.getOutput().write(" ");
    writer.getOutput().write(String.valueOf(y2));
    writer.getOutput().write(" l h f");
    writer.getOutput().write(PDFWriter.newLineChar);
    writer.getOutput().write("Q");
    writer.getOutput().write(PDFWriter.newLineChar);
  }
}






