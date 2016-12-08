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

import java.io.IOException;
import java.io.Writer;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

import orinoco.Alignment;
import orinoco.Document;
import orinoco.Colour;
import orinoco.Paper;
import orinoco.layout.Line;
import orinoco.layout.BaseFont;
import orinoco.layout.TextComponent;
import orinoco.layout.DocumentLayout;
import orinoco.layout.Constants;
import orinoco.write.FormatWriter;
import orinoco.write.WriterClosedException;

/**
 * Implementation of the FormatWriter class which writes out the detailed
 * items in post script format
 */
public class PDFWriter implements FormatWriter
{
  /** 
   * The real output stream
   */
  private OutputStreamWriter output;

  /**
   * Use a data output stream in order to keep track of the number
   * of bytes written out so far
   */
  private DataOutputStream dataOutput;
  
  /**
   * A handle to the document writing out the data
   */
  private DocumentLayout doc;

  /**
   * The PDF cross reference object
   */
  private CrossReferencer xref;

  /**
   * The pages container
   */
  private Pages pages;

  /**
   * The current page
   */
  private Page currentPage;

  /**
   * The fonts used within this document
   */
  private Fonts fonts;

  /**
   * The latest font
   */
  private BaseFont font;

  /**
   * The current x transform
   */
  private Transform xtransform;

  /**
   * The current y transform
   */
  private Transform ytransform;

  /**
   * The standard page x transform
   */
  private Transform pagexTransform;

  /**
   * The standard page y transform
   */
  private Transform pageyTransform;

  /**
   * The x transform used when writing the footer
   */
  private Transform footerxTransform;

  /**
   * The y transform used when writing the footer
   */
  private Transform footeryTransform;

  /**
   * The current x position in adobe space
   */
  private int x;

  /**
   * The current y position in adobe space
   */
  private int y;

  /**
   * Indicates that the position co-ordinates have been adjusted but not
   * yet written out
   */
  private boolean dirtyPos;

  /**
   * Indicates whether to close the stream or not when the close method is
   * called
   */
  private boolean closeStream;

  /**
   * Indicates whether this writer has been closed or not
   */
  private boolean closed;

  /**
   * The special characters, and their replacements
   */
  private String[] specialCharacters;

  /**
   * The newline character.  Cannot use BufferredReader.newLine because
   * on NT this writes out two characters and messes up the character
   * count
   */
  static final char newLineChar = '\n';

  /** 
   * Constructor.  The PDF document will be written to the specified
   * output stream.  The stream will not be closed when generation is
   * complete
   * @param os the output stream
   * @exception IOException
   */
  protected PDFWriter(OutputStream os) throws IOException
  {
    dataOutput = new DataOutputStream(os);
    output = new OutputStreamWriter(dataOutput);
    closeStream = false;
    closed = false;
    initSpecialCharacters();
  }

  /** 
   * Constructor.  The PDF document will be written to the specified
   * output stream.  The stream will be closed when generation is
   * complete
   * @param os the output stream
   * @exception IOException
   */
  protected PDFWriter(File f) throws IOException
  {
    dataOutput = new DataOutputStream(new FileOutputStream(f));
    output = new OutputStreamWriter(dataOutput);
    closeStream = true;
    closed = false;
    initSpecialCharacters();
  }

  /**
   * Initializes the array of special characters
   */
  private void initSpecialCharacters()
  {
    specialCharacters = new String[256];
    specialCharacters['('] = "\\(";
    specialCharacters[')'] = "\\)";
  }

  /**
   * Initializes the format writer and writes out some header information
   * 
   * @exception IOException 
   * @param dl the owning document
   */
  public void init(DocumentLayout dl) throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    // First write out the headings
    output.write("%PDF-1.2");
    output.write(PDFWriter.newLineChar);
    output.flush();

    // Initialize some objects
    doc = dl;
    dirtyPos = false;
    Paper p = doc.getPaper();
    int h = (int) Math.round(p.getHeight() * Constants.POINTS_PER_CM);
    int w = (int) Math.round(p.getWidth() * Constants.POINTS_PER_CM);

    pagexTransform = new Transform
      (p.getHorizontalMargin() * Constants.POINTS_PER_CM,1);
    pageyTransform = new Transform
      ((p.getHeight() - p.getVerticalMargin()) * Constants.POINTS_PER_CM, -1);

    footerxTransform = pagexTransform;
    footeryTransform = new Transform
      (doc.getFooterHeight() +
       (p.getVerticalMargin() * Constants.POINTS_PER_CM), -1);

    xtransform = pagexTransform;
    ytransform = pageyTransform;

    x = xtransform.transform(0);
    y = ytransform.transform(0);

    font = orinoco.Font.DEFAULT;

    // PDF Objects
    xref = new CrossReferencer(this);

    fonts = new Fonts(xref);
    pages = new Pages(xref.createObjectNumber(), w, h);

    writeNewPage();
  }

  /**
   * Ejects the specified string at the current cursor position
   * @param s the text to write
   * @exception IOException
   */
  public void writeText(String s) throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    if (dirtyPos)
    {
      currentPage.writeMoveTo(x, y);
      dirtyPos = false;
    }

    currentPage.writeText(s);
  }

  /** 
   * Writes out the setting of the x position on the current line to
   * that specified
   * @param xpos the new xposition
   * @exception IOException
   */
  public void writeSetX(double xpos) throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    x = xtransform.transform(xpos);
    dirtyPos = true;
  }

  /** 
   * Writes out the setting of the y position on the current page to
   * that specified
   * @param ypos the new y position
   * @exception IOException
   */
  public void writeSetY(double ypos) throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    y = ytransform.transform(ypos);
    dirtyPos = true;
  }

  /**
   * Moves to the absolute position on the page
   * 
   * @exception IOException 
   * @param y the y coordinate
   * @param x the x coordinate
   */
  public void writeMoveTo(double xpos, double ypos) throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    writeSetX(xpos);
    writeSetY(ypos);
    currentPage.writeMoveTo(x,y);
    dirtyPos = false;
  }

  /**
   * Writes out the specified font
   * @param f the font
   * @exception IOException
   */
  public void writeFont(BaseFont f) throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    currentPage.writeFont(f);
    font = f;
  }

  /**
   * Adjusts the yposition to take into account any disparity between
   * the default font and the height of the line
   * @param amount the amount by which to adjust
   * @exception IOException
   */
  public void writeAdjustY(int amount) throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    y += ytransform.adjust(amount);
    dirtyPos = true;
  }

  /**
   * Writes out the new line 
   * @xception IOException
   */
  public void writeNewLine() throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    writeAdjustY(font.getPointSize());
    writeSetX(0);
    dirtyPos = true;
  }

  /**
   * Writes an end of the current page, including the footer
   * 
   * @exception IOException 
   */
  public void writeEndOfPage() throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    // Write the footer
    writeFont(orinoco.Font.DEFAULT);

    if (doc.hasFooterChanged())
    {
      footeryTransform = new Transform
        (doc.getFooterHeight() +
         (doc.getPaper().getVerticalMargin() * Constants.POINTS_PER_CM), -1);
    }

    xtransform = footerxTransform;
    ytransform = footeryTransform;

    writeMoveTo(0,0); // move to the footer location
    doc.writeFooter();

    xtransform = pagexTransform;
    ytransform = pageyTransform;

    currentPage.close();
    currentPage.write(output);
  }

  /**
   * Writes the beginning of new page, including the headers
   * 
   * @exception IOException 
   */
  public void writeNewPage() throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    currentPage = new Page(xref.createObjectNumber(), 
                           pages, 
                           fonts, 
                           xref, 
                           this);
    pages.addPage(currentPage);

    // Write out the header
    writeFont(orinoco.Font.DEFAULT);
    writeSetY(0);
    doc.writeHeader();

    // Make sure that the default font is active
    writeFont(orinoco.Font.DEFAULT);
  }

  /**
   * Draws a line in the point size specified between the points specified
   * 
   * @exception IOException 
   * @param w 
   * @param x1 
   * @param y2 
   * @param x2 
   * @param y1 
   */
  public void writeDrawLine(double x1, double y1, double x2, double y2, int w)
    throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    currentPage.writeDrawLine(xtransform.transform(x1), 
                              ytransform.transform(y1), 
                              xtransform.transform(x2), 
                              ytransform.transform(y2), 
                              w);
  }

  /**
   * Fills a box between the points specified in the colour specified
   * 
   * @exception IOException 
   * @param c the colour to fill the box
   * @param x1 
   * @param height the height of the box
   * @param width the width of the box
   * @param y1 
   */
  public void writeFillBox(double x1, double y1, double width, double height,
                           Colour c) throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    currentPage.writeFillBox(xtransform.transform(x1),
                             ytransform.transform(y1),
                             xtransform.transform(x1 + width),
                             ytransform.transform(y1 + height),
                             c);
  }

  /**
   * Writes out the specific format command to prior to closing the
   * output file
   * 
   * @exception IOException 
   */
  public void writeClose() throws IOException
  {
    if (closed)
    {
      throw new WriterClosedException();
    }

    // Close off the current page
    writeEndOfPage();

    pages.write(output);
    fonts.write(output);

    Info info = new Info(xref.createObjectNumber());
    info.write(output);

    Catalog catalog = new Catalog(xref.createObjectNumber(), pages);
    catalog.write(output);

    output.flush();
    int xrefstart = dataOutput.size();
    xref.write(output);

    // Write out the trailer
    output.write("trailer");
    output.write(PDFWriter.newLineChar);
    output.write("<<");
    output.write(PDFWriter.newLineChar);
    output.write("/Size ");
    output.write(String.valueOf(xref.getSize()));
    output.write(PDFWriter.newLineChar);
    output.write("/Root ");
    catalog.getNumber().writeRef(output);
    output.write(PDFWriter.newLineChar);
    output.write("/Info ");
    info.getNumber().writeRef(output);
    output.write(PDFWriter.newLineChar);
    output.write(">>");
    output.write(PDFWriter.newLineChar);
    output.write("startxref");
    output.write(PDFWriter.newLineChar);
    output.write(String.valueOf(xrefstart));
    output.write(PDFWriter.newLineChar);
    output.write("%%EOF");
    output.write(PDFWriter.newLineChar);

    output.flush();

    if (closeStream)
    {
      output.close();
    }

    closed = true;
  }

  /**
   * Performs any necessary post processing on the output file.  PDF files
   * are generated in a single pass, so this is an empty implementation
   * 
   * @exception IOException 
   */
  public void postProcess() throws IOException
  {
  }

  /**
   * Gets the current offset into the output stream
   * @return the offset
   * @exception IOException
   */
  int getOffset() throws IOException
  {
    output.flush();
    return dataOutput.size();
  }

  /**
   * Gets the output
   * @return the output writer
   */
  OutputStreamWriter getOutput()
  {
    return output;
  }

  /**
   * Sees if the character passed in is a special character in this particular
   * output format
   *
   * @param c the character
   * @return TRUE if the character is a special character, FALSE otherwise
   */
  public boolean isSpecial(char c)
  {
    return specialCharacters[c] != null;
  }


  /**
   * Gets the escape sequence necessary to render the special character in   
   * the output file format
   *
   * @param c the character
   * @return the escape sequence
   */
  public String getEscapeSequence(char c)
  {
    return specialCharacters[c];
  }
}






