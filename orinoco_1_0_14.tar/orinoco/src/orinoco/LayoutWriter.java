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

import java.io.IOException;

/**
 * An interface containing the various methods enabling client applications to
 * invoke operations which layout text
 */
public interface LayoutWriter extends TextWriter
{
  /**
   * Writes a carriage return to the document
   * 
   * @exception IOException 
   */
  public void newLine() throws IOException;

  /**
   * Adds vertical white space after the current line.  The amount of 
   * vertical space is specified in cms
   * 
   * @exception IOException 
   * @param amount the amount of white space to add in cms
   */
  public void space(double amount) throws IOException;

  /**
   * Creates a new table with the specified column definitions.
   * Any tables that are currently being written are closed first
   * 
   * @param cols the column definitions
   * @return the table
   */
  public Table createTable(Column[] cols);

  /**
   * Creates a new table with the specified column definitions.
   * Any tables that are currently being written are closed first
   * 
   * @param hdrs the column headings
   * @param cols the column definitions
   * @return the table
   */
  public Table createTable(Column[] cols, Heading[] hdrs);

  /**
   * Draws a horizontal line across the entire available width.  For documents
   * this will be across the whole page, for table cells, across
   * the width of the cell etc
   * 
   * @exception IOException 
   */
  public void drawLine() throws IOException;

  /**
   * Draws a horizontal line across the entire available width.  For documents
   * this will be across the whole page, for table cells, across
   * the width of the cell etc
   * 
   * @exception IOException 
   * @param ps the point size in which to render the line
   */
  public void drawLine(int ps) throws IOException;

  /**
   * Draws a horizontal line across from the specified horizontal position
   * for the specified length
   * 
   * @exception IOException 
   * @param from the x position in cms
   * @param length the length of the line in cms
   */
  public void drawLine(double from, double length) throws IOException;
  
  /**
   * Draws a horizontal line across from the specified horizontal position
   * for the specified length
   * 
   * @exception IOException 
   * @param ps the point size of the line
   * @param from the x position of the line
   * @param length the length of the line
   */
  public void drawLine(double from, double length, int ps) throws IOException;

  /**
   * Adds a tab to the document
   * 
   * @param t the tab position in cms
   */
  public void addTab(double t);

  /**
   * Clears all the tabs
   */
  public void clearTabs();

  /**
   * Moves to the next tab which is greater than the current x position
   * 
   * @exception IOException 
   */
  public void tab() throws IOException;

  /**
   * Sets the indent level to the current tab position.  
   * Every call of newLine will move to this point until the releaseIndent
   * method is called
   * 
   * @exception IOException 
   */
  public void setIndent() throws IOException;

  /**
   * Releases the indent level back to zero
   * 
   * @exception IOException 
   */
  public void releaseIndent() throws IOException;
}


