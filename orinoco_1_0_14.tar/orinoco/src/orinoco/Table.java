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
 * A table.  Presents a set of methods which accepts client invocation
 * and lays the data out in a predefined column format, automatically
 * handling text wrapping within columns and page break.
 * After every page break, the table headings (if specified) will be
 * re-written to the document
 */
public interface Table
{
  /**
   * Sets the vertical spacing for all subsequent rows
   * 
   * @param amount the space in cm
   */
  public void setRowSpacing(double amount);

  /**
   * Sets the horizontal spacing between columns for all subsequent rows
   * 
   * @param amount the space in cm
   */
  public void setColumnSpacing(double amount);

  /**
   * Adds a row to the table, consisting of the specified strings, and writes
   * it out.  Equivalent to performing sequential calls to addCells and
   * writeRow
   * 
   * @exception IOException 
   * @param data the array of strings to be sequentially placed in each column
   */
  public void addRow(String[] data) throws IOException;

  /**
   * Adds a new row consisting of empty cells
   *
   * @exception IOException
   */
  public void addCells() throws IOException;

  /**
   * Adds cells to the table, but doesn't write out the row
   * 
   * @exception IOException 
   * @param data the array of strings which are seuentially written out to 
   *     each colulmn
   */
  public void addCells(String[] data) throws IOException;

  /**
   * Gets a writer for the specified cell, allowing more complex text to be
   * written to a cell, including fonts etc.
   * 
   * @param col the column number of the row to be written
   * @return a writer which writes to the specified cell
   */
  public TextWriter getCellWriter(int col);

  /**
   * Gets a writer for the specified cell, which spans several columns.  The
   * span parameter specifies the number of columns the cell data will span,
   * including the name column
   * 
   * @param col the zero-based column index
   * @param colspan the number of columns this text writer will span
   * @return the cell writer
   */
  public TextWriter getCellWriter(int col, int colspan);

  /**
   * Writes out the cells in the current row
   * 
   * @exception IOException 
   */
  public void writeRow() throws IOException;

  /**
   * Closes off the table
   * 
   * @exception IOException 
   */
  public void close() throws IOException;

  /**
   * Sets the background colour for the column headings
   *
   * @param c the background colour for the headings
   */
  public void setHeaderBackground(Colour c);

  /**
   * Sets the background colour for the current row.  This will only
   * affect the current row - subsequent rows will have the default background
   * unless setBackground is called again
   *
   * @param c the background colour for the current row
   */
  public void setBackground(Colour c);

  /**
   * Draws a horizontal line across the entire width of the table
   *
   * @exception IOException 
   */
  public void drawLine() throws IOException;

  /**
   * Draws a horizontal line across the width of the table
   * 
   * @exception IOException 
   * @param ps the width of the line to be drawn, in points
   */
  public void drawLine(int ps) throws IOException;

  /**
   * Increases the y position by the specified number of cms
   * Used for small gaps eg. between a row and an underlining
   * Any pending row is ejected first
   * 
   * @exception IOException 
   * @param amount the amount of vertical space in cms
   */
  public void space(double amount) throws IOException;

  /**
   * The border to be drawn after each row is written.  This is equivalent
   * to a client call to drawline after each row has been written
   * 
   * @param ps the width of the border
   * @exception IOException
   */
  public void setRowBorder(int ps) throws IOException;

  /**
   * The border to be drawn in between each column.  Setting a column 
   * is incompatible with row spans, as any spanned rows  will be dissected
   * by the column
   * 
   * @param ps the width of the border
   * @exception IOException
   */
  public void setColumnBorder(int ps) throws IOException;

  /**
   * The border to be drawn around the entire table when it is closed.  The
   * border is also drawn at each page break
   * 
   * @param ps the width of the border
   * @exception IOException
   */
  public void setBorder(int ps);

  /**
   * The border to be placed around the table heading only.  If the row border
   * has been set, then the values set as a result of this method will
   * be ignored
   * 
   * @param top the width of the border at the top of the table headings
   * @param bottom the width of the border at the bottom of the heading
   * @exception IOException
   */
  public void setHeaderBorder(int top, int bottom) throws IOException;

  /**
   * Indicates whether or not this tables has had any rows added to it
   *
   * @return TRUE if this table is not empty, FALSE if it is
   */
  public boolean hasRows();

  /**
   * Sets a flag to write the headings, even when the table is empty.
   * Default value is FALSE
   *
   * @param w TRUE to write the headings for an empty table, FALSE otherwise
   */
  public void setWriteHeadingsIfEmpty(boolean write);

}
