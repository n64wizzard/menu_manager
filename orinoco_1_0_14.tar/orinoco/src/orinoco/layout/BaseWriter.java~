/*************************************************************************
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

import java.io.OutputStream;
import java.io.File;
import java.io.BufferedWriter;
import java.io.IOException;

import java.util.TreeSet;
import java.util.Iterator;

import orinoco.Font;
import orinoco.Alignment;
import orinoco.Table;
import orinoco.Column;
import orinoco.Heading;
import orinoco.TextMacro;
import orinoco.TextWriter;
import orinoco.LayoutWriter;
import orinoco.write.FormatWriter;

/**
 * Contains the basic functionality for writing out.  This class contains
 * the logic to keep track of the position on a page and to
 * calculate the positioning of text on a page.
 * Once this has been determined, it invokes its
 * particular implementation of the various write functions in order to
 * output the commands as appropriate to the output file.
 * This class basis its calculations on a "virtual" page with the
 * origin at the top left, and positive increments in the y value will
 * move the position downwards.  The output file writer will transform
 * these virtual co-ordinates into actual write co-ordinates.
 */
abstract class BaseWriter implements TextWriter, LayoutWriter
{
  /**
   * A handle to the overall document
   */
  private DocumentLayout doc;

  /**
   * Writes out the basic write commands to the output file writer
   */
  private AtomicWriteFunctions writeFunctions;

  /**
   * The active font
   */
  private BaseFont font;

  /**
   * The currently active block of text
   */
  private TextBlock text;

  /**
   * The derived writable width in points, taking into account the margins
   */
  private double width;

  /**
   * The number of points moved on the last new line
   */
  private int lastNewLinePointSize;

  /**
   * The current x position.  Used when tabbing
   */
  private double xpos;

  /**
   * The current vertical displacement so far written by this BaseWriter
   */
  private double ypos;

  /**
   * The current table
   */
  private TableImpl table;

  /**
   * The defined tabs, in points
   */
  private TreeSet tabs;

  /**
   * The last point the tab took you to
   */
  private double lastTabPoint;

  /**
   * Indicates that the user has been tabbing on the current line
   */
  private boolean containsTabs;

  /**
   * The indent flag
   */
  private boolean indent;

  /**
   * The point to indent to
   */
  private double indentPoint;
 
  /**
   * Constructor.
   * Construction of this object is two-phase - first the constructor is 
   * invoked, and then a couple of set accessors must be invoked in order
   * to set up the document and the write functions
   * 
   * @param w the width of the page in points
   * @param dl a handle to the overall document
   */
  protected BaseWriter(double w,  DocumentLayout dl)
  {
    text = null;
    font = Font.DEFAULT;
    doc = dl;
    xpos = 0;
    ypos = 0;
    width = w;
    containsTabs = false;
    lastTabPoint = 0;
    indent = false;
    indentPoint = 0;

    tabs = new TreeSet();
    lastNewLinePointSize = Font.DEFAULT.getPointSize();
  }

  /**
   * Accessor used to set the document
   * 
   * @param dl the document
   */
  protected final void setDoc(DocumentLayout dl)
  {
    doc = dl;
  }

  /**
   * Accessor used by sub classes in order to set the atomic write functions
   * 
   * @param aw the atomic write functions
   */
  protected final void setWriteFunctions(AtomicWriteFunctions aw)
  {
    writeFunctions = aw;
  }

  /**
   * Accessor for the atomic write functions.  This is typically called
   * by tables in order to write out the data within cells
   * 
   * @return the atomic write functions
   */
  protected final AtomicWriteFunctions getWriteFunctions()
  {
    return writeFunctions;
  }

  /**
   * Accesor for the current x position
   * 
   * @return the current x position on the page
   */
  protected final double getXPos()
  {
    return xpos;
  }

  /**
   * Accessor for the curent y position on the page
   * 
   * @return the curernt y position on the page
   */
  protected final double getYPos()
  {
    return ypos;
  }

  /**
   * Accessor for the current text block
   * 
   * @return the active block of text
   */
  protected final TextBlock getText()
  {
    return text;
  }

  /**
   * Accessor for the width of the page
   * 
   * @return the width of the page
   */
  protected final double getWidth()
  {
    return width;
  }

  /**
   * Accessor for the available width left for the current line.
   * This takes tabs into account
   * 
   * @return the width of the page
   */
  protected final double getAvailableWidth()
  {
    double availableWidth = width;

    if (indent && containsTabs)
    {
      availableWidth = width - (Math.max(indentPoint, lastTabPoint));
    }
    else if (indent)
    {
      availableWidth = width - indentPoint;
    }
    else if (containsTabs)
    {
      availableWidth = width - lastTabPoint;
    }

    return availableWidth;
  }

  /**
   * Clears the active text block
   */
  protected final void clearText()
  {
    text = null;
  }

  /**
   * Accessor to get the point size of the last new line
   * 
   * @return the point size of the font the last time a new line was called
   */
  protected final int getLastNewLinePointSize()
  {
    return lastNewLinePointSize;
  }

  /**
   * Accessor to get the active font
   * 
   * @return the active font
   */
  protected final BaseFont getFont()
  {
    return font;
  }  

  /**
   * Sets the ypos position. Used by sub classes
   * 
   * @param y the new y position on the page
   */
  protected final void setYPos(double y)
  {
    ypos = y;
  }

  /**
   * Sets the xpos position.  Used by sub classes
   * 
   * @param x the new x position on the page
   */
  protected final void setXPos(double x)
  {
    xpos = x;
  }

  /**
   * Sets the last new line point size.  Used during diversions
   * 
   * @param ps the point size of the last new line
   */
  protected void setLastNewLinePointSize(int ps)
  {
    lastNewLinePointSize = ps;
  }

  /**
   * Writes all data in this font until the font is set to something else.
   * 
   * @exception IOException 
   * @param f the new font
   */
  public void setFont(Font f) throws IOException
  {    
    if (f != null)
    {
      baseSetFont(f);
    }
    else
    {
      System.err.println("Warning:  font is null");
    }
  }

  /**
   * Writes all data in this font until the font is set to something else.
   * 
   * @exception IOException 
   * @param f the new font
   */
  protected void baseSetFont(BaseFont f) throws IOException
  {    
    if (!f.equals(font))
    {
      font = f;
      writeFunctions.writeSetFont(font);
    }
  }

  /**
   * Adds the specified string to the active text block in the active font
   * 
   * @exception IOException 
   * @param s the string to add
   */
  public void write(String s) throws IOException
  {
    if (text == null)
    {
      text = new TextBlock(s, font, writeFunctions);
    }
    else
    {
      text.add(s, font);
    }
  }

  /**
   * Adds the text passed in to the active text block in with the specified 
   * font
   * 
   * @exception IOException 
   * @param s the text
   * @param f the font
   */
  public void write(String s, Font f) throws IOException
  {
    if (text == null)
    {
      text = new TextBlock(s, f, writeFunctions);
    }
    else
    {
      text.add(s, f);
    }
  }

  /**
   * Adds the text passed in to the active text block and then ejects the
   * entire block 
   *
   * @exception IOException 
   * @param s the text to add
   */
  public void writeLine(String s) throws IOException
  {
    write(s);
    newLine();
  }

  /**
   * Adds the text passed in to the text block in the specified font, and then
   * ejects the entire text block.
   * 
   * @exception IOException 
   * @param s the text to add
   * @param f the text font
   */
  public void writeLine(String s, Font f) throws IOException
  {
    write(s, f);
    newLine();
  }

  /**
   * Writes the following line with the specified alignment.  Any pending text
   * is ejected first
   * 
   * @exception IOException 
   * @param a the alignment
   * @param s the text
   */
  public void writeLine(String s, Alignment a) throws IOException
  {
    baseWriteLine(s, font, a);
  }

  /**
   * Add the specified text macro to the current text block
   * 
   * @exception IOException 
   * @param tm the text macro
   */
  public void writeMacro(TextMacro tm) 
    throws IOException
  {
    write(tm.getText());
  }

  /**
   * Adds the text macro to the current text block in the specified font
   * 
   * @exception IOException 
   * @param tm the text macro
   * @param f the font
   */
  public void writeMacro(TextMacro tm, Font f) 
    throws IOException
  {
    write(tm.getText(), f);
  }

  /**
   * Adds the text macro with the specified font and alignment on a 
   * new line.  The current text block is ejected first.
   * 
   * @exception IOException 
   * @param a the alignment
   * @param tm the text macro
   * @param f the font
   */
  public void writeMacroLine(TextMacro tm, Font f, Alignment a)
   throws IOException
  {
    writeLine(tm.getText(), f, a);
  }

  /**
   * Writes the following lines with the Alignment specified.  The active
   * text block is ejected first
   * 
   * @exception IOException 
   * @param a the alignment
   * @param s the text
   * @param f the font
   */
  public void writeLine(String s, Font f, Alignment a) throws IOException
  {
    baseWriteLine(s,f,a);
  }

  /**
   * Creates a new table with the specified column definitions.
   * Any tables that are currently being written are closed first
   * 
   * @param cols the specification for the columns
   * @return the new, empty table
   */
  public Table createTable(Column[] cols)
  {
    return createTable(cols, null);
  }

  /**
   * Creates a new table with the specified column definitions.
   * Any tables that are currently being written are closed first
   * 
   * @param hdrs the column headings
   * @param cols the column definitions
   * @return the new, empty table
   */
  public Table createTable(Column[] cols, Heading[] hdrs)
  {
    if (table != null)
    {
      System.err.println("Warning:  table not null");
    }
    
    table = new TableImpl(cols, hdrs, this, doc);

    return table;
  }

  /**
   * Closes this writer
   *
   * @exception IOException
   */
  protected void close() throws IOException
  {
    if (table != null)
    {
      table.close();
    }
  }

  /**
   * Closes down the active table
   */
  void closeTable()
  {
    table = null;
    xpos = 0;
  }

  /**
   * Internal method write the specified text on a new line.  The active
   * text block is ejected first
   * 
   * @exception IOException 
   * @param a the alignment
   * @param s the text
   * @param f the font
   */
  private void baseWriteLine(String s, BaseFont f, Alignment a) 
    throws IOException
  {
    if (text != null)
    {
      newLine();
    }

    text = new TextBlock(s, f, writeFunctions);

    Line[] lines = text.getLines(getAvailableWidth());

    for (int i = 0; i < lines.length ; i++)
    {
      writeFunctions.writeAdjustY(lines[i].getHeight());
      lastNewLinePointSize = lines[i].getHeight();

      if (a == Alignment.RIGHT)
      {
        writeFunctions.writeSetX(getWidth() - lines[i].getLength());
      }
      else if (a == Alignment.CENTRE)
      {
        writeFunctions.writeSetX((getWidth() - lines[i].getLength())/2.0);
      }

      writeLine(lines[i]);
    }
    
    text = null;
    containsTabs = false;
    lastTabPoint = 0;
  }

  /**
   * Ejects the active text block.  If the text block is empty, then
   * merely writes a blank line in the active font point size to the output
   * file
   * 
   * @exception IOException 
   */
  public void newLine() throws IOException
  {
    if (text == null)
    {
      writeFunctions.writeNewLine();
      return;
    }
    
    Line[] lines = text.getLines(getAvailableWidth());

    for (int i = 0; i < lines.length ; i++)
    {
      writeLine2(lines[i]);
    }

    text = null;
  }

  /**
   * Increases the y position by the specified number of cms
   * Used for small gaps eg. between headers/footers and the body of the text
   * Any pending text is ejected first
   * 
   * @exception IOException 
   * @param amount the amount of vertical space in cms
   */
  public void space(double amount) throws IOException
  {
    if (text != null)
    {
      writeFunctions.writeNewLine();
    }
    text = null;

    writeFunctions.writeAdjustY((int) (amount * Constants.POINTS_PER_CM));
  }

  /**
   * Adds a tab to the list of tabs
   * 
   * @param t the new tab position in cms
   */
  public void addTab(double t)
  {
    tabs.add(new Double(t*Constants.POINTS_PER_CM));
  }

  /**
   * Clears all the tabs
   */
  public void clearTabs()
  {
    tabs.clear();
  }

  /**
   * Moves to the next tab which is greater than the current x position
   * 
   * @exception IOException 
   */
  public void tab() throws IOException
  {
    // Eject any text currently stored up
    if (text != null)
    {
      Line[] lines = text.getLines(getWidth());

      // Just write out the first bit
      if (lines.length > 0)
      {
        double y = ypos;
        writeFunctions.writeAdjustY(lines[0].getHeight());
        writeLine(lines[0]);
        writeFunctions.writeSetY(y);
      }

      text = null;
    }

    boolean found = false;
    Iterator i = tabs.iterator();
    double tab=0;
    while (i.hasNext() && !found)
    {
      tab = ( (Double) i.next()).doubleValue();
      if (tab > xpos)
      {
        writeFunctions.writeSetX(tab);
        found = true;
        containsTabs = true;
        lastTabPoint = tab;
      }
    }
  }

  /**
   * Sets the indent level to the current tab position.  
   * Every call of newLine will move to this point until the releaseIndent
   * method is called
   * 
   * @exception IOException 
   */
  public void setIndent() throws IOException
  {
    indent = true;
    indentPoint = lastTabPoint;
  }

  /**
   * Releases the indent level back to zero
   * 
   * @exception IOException 
   */
  public void releaseIndent() throws IOException
  {
    indent = false;
    indentPoint = 0;
  }

  /**
   * Draws a horizontal line across the entire available width.  For documents
   * this will be across the whole page, for table cells, across
   * the width of the cell etc.
   * 
   * @exception IOException 
   */
  public void drawLine() throws IOException
  {
    drawLine(Constants.DEFAULT_LINE_WIDTH);
  }

  /**
   * Draws a horizontal line across the entire available width.  For documents
   * this will be across the whole page, for table cells, across
   * the width of the cell etc.
   * 
   * @exception IOException 
   * @param ps the width of the line to be drawn, in points
   */
  public void drawLine(int ps) throws IOException
  {
    if (text != null)
    {
      newLine();
    }

    double y = getYPos() + 2;
    writeFunctions.writeDrawLine(0, y, getWidth(), y, ps);
    writeFunctions.writeSetY(getYPos()+ps+2);
  }

  /**
   * Draws a horizontal line between the two x positions specified. 
   * 
   * @exception IOException 
   * @param from the start x position in cms
   * @param length the length of the line to draw
   */
  public void drawLine(double from, double length) throws IOException
  {
    drawLine(from, length, Constants.DEFAULT_LINE_WIDTH);
  }
  
  /**
   * Draws a horizontal line between the two x positions specified.  The
   * line drawn will have the point width thickness specified
   * 
   * @exception IOException 
   * @param ps the point size of the line
   * @param from the start position of the line in cms
   * @param length the length of the line in cms
   */
  public void drawLine(double from, double length, int ps) throws IOException
  {
    if (text != null)
    {
      newLine();
    }

    double y = getYPos() + 2;
    writeFunctions.writeDrawLine(from * Constants.POINTS_PER_CM, 
                         y,  
                         length * Constants.POINTS_PER_CM, 
                         y, 
                         ps);
    writeFunctions.writeSetY(getYPos()+ps+2);
  }

  /**
   * Adjusts the x and y components and writes out the line
   * 
   * @exception IOException 
   * @param l the line to write
   */
  protected void writeLine2(Line l) throws IOException
  {
    writeFunctions.writeAdjustY(l.getHeight());

    if (indent)
    {
      writeFunctions.writeSetX(indentPoint);
    }

    writeLine(l);
    lastNewLinePointSize = l.getHeight();

    if (containsTabs)
    {
      containsTabs = false;
      lastTabPoint = 0;
    }

    writeFunctions.writeSetX(0);
  }

  /**
   * Writes out the line. The x and y positions have already been set
   * at this point
   * 
   * @exception IOException 
   * @param l the line to write
   */
  protected void writeLine(Line l) throws IOException
  {
    // Save the default font
    BaseFont f = font;
    TextComponent[] tc = l.getText();

    for (int i = 0; i < tc.length; i++)
    {
      baseSetFont(tc[i].getFont());
      tc[i].write(this);
    }

    xpos += l.getLength();

    // Reset the font
    baseSetFont(f);
  }

  /**
   * Accessor for the indent flag
   * 
   * @return TRUE if we are currently in indent mode, FALSE otherwise
   */
  protected boolean getIndent()
  {
    return indent;
  }

  /**
   * Accessor for the amount to indent
   *
   * @return the amount to indent
   */
  protected double getIndentPoint()
  {
    return indentPoint;
  }
}




