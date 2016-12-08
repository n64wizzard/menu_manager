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

package orinoco.write.ps;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;

import orinoco.Alignment;
import orinoco.Document;
import orinoco.Font;
import orinoco.Colour;
import orinoco.layout.Line;
import orinoco.layout.BaseFont;
import orinoco.layout.TextComponent;
import orinoco.layout.DocumentLayout;
import orinoco.layout.Constants;
import orinoco.write.FormatWriter;
/**
 * Implementation of the FormatWriter class which writes out the detailed
 * items in post script format
 */
public class PostscriptWriter implements FormatWriter
{
  /**
   * The working, temporary output stream
   */
  private BufferedWriter output;

  /**
   * The original output writer is held here whilst we are writing a diversion
   */
  private BufferedWriter originalOutput;

  /**
   * The temporary output file
   */
  private File tempFile;

  /** 
   * The real output stream
   */
  private java.io.Writer outputWriter;

  /**
   * A handle to the document writing out the data
   */
  private DocumentLayout doc;

  /**
   * The font faces used within this document
   */
  private HashSet fonts;

  protected PostscriptWriter(OutputStream os) throws IOException
  {
    outputWriter = new OutputStreamWriter(os);
    fonts = new HashSet(10);
  }

  protected PostscriptWriter(File f) throws IOException
  {
    outputWriter = new FileWriter(f);
    fonts = new HashSet(10);
  }

  public void init(DocumentLayout dl) throws IOException
  {
    doc = dl;

    String workdir = Constants.WORK_DIR != null ? 
                     Constants.WORK_DIR : Constants.CURRENT_DIR;
    tempFile = File.createTempFile("orinoco", ".ps", new File(workdir));
    tempFile.deleteOnExit();
    output = new BufferedWriter(new FileWriter(tempFile));

    output.write("%%EndProlog");
    output.newLine();
    output.write("%%Page: 1 1");
    output.newLine();
    output.write("%%BeginPageSetup");

    output.newLine();
    writeFont(Font.DEFAULT);
    output.write("newPage");
    output.newLine();
    output.write("%%EndPageSetup");
    output.newLine();

    // Write out the header
    output.write("%header");
    output.newLine();
    dl.writeHeader();
    output.write("%end of header");
    output.newLine();
  }

  /**
   * Ejects the specified string at the current cursor position
   */
  public void writeText(String s) throws IOException
  {
    output.write("(");
    output.write(s);
    output.write(") show");
    output.newLine();
  }

  /** 
   * Writes out the setting of the x position on the current line to
   * that specified
   */
  public void writeSetX(double xpos) throws IOException
  {
    output.write(Long.toString(Math.round(xpos)));
    output.write(" setX");
    output.newLine();
  }

  /** 
   * Writes out the setting of the y position on the current page to
   * that specified
   */
  public void writeSetY(double ypos) throws IOException
  {
    output.write(Long.toString(Math.round(ypos)));
    output.write(" setY");
    output.newLine();
  }

  public void writeMoveTo(double x, double y) throws IOException
  {
    output.write(Long.toString(Math.round(x)));
    output.write(" ");
    output.write(Long.toString(Math.round(y)));
    output.write(" moveto");
    output.newLine();
  }

  /**
   * Writes out the specified font
   */
  public void writeFont(BaseFont f) throws IOException
  {
    output.write(Integer.toString(f.getPointSize()));
    output.write(" /");
    output.write(f.getFaceName());
    output.write(" sf");
    output.newLine();

    // Add the font to font resources
    fonts.add(f.getFaceName());
  }

  /**
   * Adjusts the yposition to take into account any disparity between
   * the default font and the height of the line
   */
  public void writeAdjustY(int amount) throws IOException
  {
    output.write(Double.toString(amount));
    output.write(" adjustY");
    output.newLine();
  }

  /**
   * Writes out the new line postscript
   */
  public void writeNewLine() throws IOException
  {
    // Move to the beginning of the next line.  Increment the y position
    // and set the x position to be the left margin
    output.write("newLine");
    output.newLine();
  }

  public void writeEndOfPage() throws IOException
  {
    // Write out the footer and eject the page
    output.write("%footer");
    output.newLine();
    output.write("gsave 0 pageLength footerHeight sub translate");
    output.newLine();
    doc.writeFooter();
    output.write("grestore");
    output.newLine();
    output.write("%end of footer");
    output.newLine();
    output.write("showpage");
    output.newLine();
  }

  public void writeNewPage() throws IOException
  {
    int newpagenum = doc.getCurrentPage();
    output.write("%%Page: ");
    output.write(Integer.toString(newpagenum));
    output.write(" ");
    output.write(Integer.toString(newpagenum));
    output.newLine();
    output.write("%%BeginPageSetup");
    output.newLine();
    output.write("newPage");
    output.newLine();
    output.write("%%EndPageSetup");
    output.newLine();

    // Write out the header
    output.write("%header");
    output.newLine();
    doc.writeHeader();
    output.write("%end of header");
    output.newLine();
  }

  public void writeDrawLine(double x1, double y1, double x2, double y2, int w)
    throws IOException
  {
    output.write("gsave newpath ");
    output.write(Long.toString(Math.round(x1)));
    output.write(" ");
    output.write(Long.toString(Math.round(y1)));
    output.write(" moveto ");
    output.write(Long.toString(Math.round(x2)));
    output.write(" ");
    output.write(Long.toString(Math.round(y2)));
    output.write(" lineto ");
    output.write(Integer.toString(w));
    output.write(" setlinewidth stroke grestore");
    output.newLine();
  }

  /**
   * Draws a box with the given width and height at the specified x and y
   * position, and fills it with the specified colour
   */
  public void writeFillBox(double x1, double y1, double width, double height,
                           Colour c) throws IOException
  {
    output.write("gsave newpath ");
    output.write(Long.toString(Math.round(x1)));
    output.write(" ");
    output.write(Long.toString(Math.round(y1)));
    output.write(" moveto ");
    output.write(Long.toString(Math.round(width)));
    output.write(" 0  rlineto 0 ");
    output.write(Long.toString(Math.round(height)));
    output.write(" rlineto ");
    output.write(Long.toString(Math.round(-width)));
    output.write(" 0 rlineto closepath ");
    
    if (c.isGrey())
    {
      output.write(Double.toString(c.getValue()));
      output.write(" setgray ");
    }
    output.write("fill grestore");
    output.newLine();
  }

  public void writeClose() throws IOException
  {
    writeEndOfPage();

    // Write out the trailer
    output.write("%%Trailer");
    output.newLine();
    output.write("%%EOF");
    output.newLine();
    output.flush();
    output.close();
  }

  public void postProcess() throws IOException
  {
    // Now add the dsc comments and the common prologue
    BufferedWriter dscOutput = new BufferedWriter(outputWriter);
    SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

    dscOutput.write("%%Creator:  orinoco v");
    dscOutput.write(Document.getVersion());    
    dscOutput.newLine();
    dscOutput.write("%%CreationDate:  " + 
            sdf.format(Calendar.getInstance().getTime()));
    dscOutput.newLine();
    dscOutput.write("%%DocumentNeededResources:  ");
    
    Iterator i = fonts.iterator();
    boolean first = true;

    while (i.hasNext())
    {
      if (!first)
      {
        dscOutput.write("%%+ ");
      }
      else
      {
        first = false;
      }

      dscOutput.write("font ");
      dscOutput.write((String) i.next());
      dscOutput.newLine();
    }
    dscOutput.write("%%Pages:  ");
    dscOutput.write(Integer.toString(doc.getCurrentPage()));
    dscOutput.newLine();
    dscOutput.write("%%PageOrder:  Ascend");
    dscOutput.newLine();
    dscOutput.write("%%Orientation:  Portrait");
    dscOutput.newLine();
    dscOutput.write("%%EndComments");
    dscOutput.newLine();
   
    // The prolog
    dscOutput.write("%%BeginProlog");
    dscOutput.newLine();

    // Define the page dimension, taking the margins into account
    double pageLength = doc.getPaper().getHeight() - 
                     2 * doc.getPaper().getVerticalMargin();
    double pageWidth = doc.getPaper().getWidth() - 
      2 * doc.getPaper().getHorizontalMargin();
    double leftMargin = doc.getPaper().getHorizontalMargin();
    double topMargin = doc.getPaper().getVerticalMargin();

    dscOutput.write("%Page details");
    dscOutput.newLine();
    dscOutput.write("/pageLength " + (pageLength * Constants.POINTS_PER_CM) + 
                    " def");
    dscOutput.newLine();
    dscOutput.write("/pageWidth " + (pageWidth * Constants.POINTS_PER_CM) + 
                    " def");
    dscOutput.newLine();
    dscOutput.write("/leftMargin " + (leftMargin * Constants.POINTS_PER_CM) + 
                    " def");
    dscOutput.newLine();
    dscOutput.write("/topMargin " + (topMargin * Constants.POINTS_PER_CM) +
                    " def");
    dscOutput.newLine();
    dscOutput.write("/footerHeight " + (doc.getFooterHeight()) + " def");
    dscOutput.newLine();

    // Read in the prologue file
    String resourcesDir = Constants.RESOURCES_DIR != null ?
      Constants.RESOURCES_DIR : Constants.CURRENT_DIR;
    StringBuffer sb = new StringBuffer(resourcesDir);
    sb.append(File.separatorChar);
    sb.append(Constants.POSTSCRIPT_PROLOGUE);
    File f = new File(sb.toString());
    FileReader fr = new FileReader(f);

    char[] buf = new char[1000];
    int read = fr.read(buf);

    while (read != -1)
    {
      dscOutput.write(buf, 0, read);
      read = fr.read(buf);
    }

    fr.close();


    // Now read in all the previously generated postscript and append it
    // to the current output
    fr = new FileReader(tempFile);
    read = fr.read(buf);
    while (read != -1)
    {
      dscOutput.write(buf, 0, read);
      read = fr.read(buf);
    }

    fr.close();
    
    dscOutput.flush();
    dscOutput.close();
  }

  /**
   * Sees is the character passed in is a special character in this particular
   * output format
   *
   * @param c the character
   * @return TRUE if the character is a special character, FALSE otherwise
   */
  public boolean isSpecial(char c)
  {
    return false;
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
    return null;
  }

    /*
    // Invoke the shell command to generate the PDF
    StringBuffer command = new StringBuffer("genpdf.sh ");
    command.append(psFile.getPath());
    command.append(' ');
    command.append("work");
    command.append(' ');
    command.append(getPaper().getName());
    command.append(' ');
    command.append(outputFile.getPath());

    try
    {
      Process p = Runtime.getRuntime().exec(command.toString());

      GroffErrors ge = new GroffErrors(p.getErrorStream());
      ge.start();
      
      int exitValue = p.waitFor();

      ge.setGenerated();

    }
    catch (InterruptedException e)
    {
      System.err.println("Groff to PDF conversion process interrupted");
    }
    */
}






