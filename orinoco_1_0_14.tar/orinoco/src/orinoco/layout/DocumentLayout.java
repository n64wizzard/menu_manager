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

package orinoco.layout;

import java.io.IOException;

import orinoco.Paper;
import orinoco.Font;
import orinoco.OutputFormatWriter;
import orinoco.LayoutWriter;
import orinoco.write.FormatWriter;

/**
 * The main document
 */
public class DocumentLayout extends BaseWriter
{
  /**
   * Information about the paper that this document will be written to
   */
  private Paper paper;

  /**
   * The header to be written at the top of each new page
   */
  private DiversionWriter header;
  /**
   * The new header to be written, starting at the next page
   */
  private DiversionWriter newHeader;
  /**
   * The footer to be written at the end of every page
   */
  private DiversionWriter footer;
  /**
   * The new footer to be written, starting at the next page
   */
  private DiversionWriter newFooter;
  /**
   * Writes to the output file in the appropriate format
   */
  private FormatWriter writer;

  /**
   * The current page number
   */
  private int currentPage;

  /**
   * The height of the header in points
   */
  private double headerHeight;

  /**
   * The height of the footer in points
   */
  private double footerHeight;

  /**
   * Flag indicating that this has been initialized, and we can write
   * out
   */
  private boolean initialized;

  /**
   * The height of the page, taking into account margins, but not headers and
   * footers
   */
  private double height;

  /**
   * Constructor
   * 
   * @exception IOException 
   * @param w the output writer
   * @param p the paper
   */
  protected DocumentLayout(Paper p, FormatWriter w) throws IOException
  {
    super((p.getWidth() - 2 * p.getHorizontalMargin()) *
          Constants.POINTS_PER_CM, null);
    setDoc(this);
    setWriteFunctions(new FlowWriteFunctions(w, this));
    paper = p;
    writer = w;
    initialized = false;

    height = (paper.getHeight() - 2 * paper.getVerticalMargin()) *
                 Constants.POINTS_PER_CM;
  }

  /**
   * Initializes information about this document, such as header and footer
   * heights.  Also writes out any preamble required by the output format
   * 
   * @exception IOException 
   */
  protected final void init() throws IOException
  {
    currentPage = 1;
    initialized = true;

    // Keep the new line from the header, but get rid of it from the footer
    if (header != null)
    {
      headerHeight = header.getYPos();
    }

    if (footer != null)
    {
      footerHeight = footer.getYPos() - footer.getLastNewLinePointSize();
    }

    writer.init(this);
    getWriteFunctions().writeSetY(headerHeight);
    getWriteFunctions().writeSetX(0);
  }

  /**
   * Gets the number of the current page
   * 
   * @return the current page number
   */
  public final int getCurrentPage()
  {
    return currentPage;
  }

  /**
   * The paper for this document
   * 
   * @return the paper
   */
  public final Paper getPaper()
  {
    return paper;
  }

  /**
   * Gets the height required by the footer, in points
   * 
   * @return the height of the footer
   */
  public final double getFooterHeight()
  {
    return footerHeight;
  }
  
  /**
   * Gets the height of the page including margins but excluding footers
   * 
   * @return the height of the paper
   */
  final double getHeight()
  {
    return height;
  }


  /**
   * Creates a new page for this document.  Writes out the footer, and the 
   * header on the new page
   * 
   * @exception IOException 
   */
  protected void newPage() throws IOException
  {
    if (getText() != null)
    {
      newLine();
    }

    writeNewPage();
  }

  /**
   * Overrides the method in the base class to throw a new page if we are
   * too close to the bottom
   * 
   * @exception IOException 
   */
  public void newLine() throws IOException
  {
    if (getText() == null)
    {
      if (getYPos() + getFont().getPointSize() > 
          height - footerHeight)
      {
        writeNewPage();
      }
      else
      {
        getWriteFunctions().writeNewLine();
      }
      return;
    }

    Line[] lines = getText().getLines(getAvailableWidth());

    for (int i = 0; i < lines.length ; i++)
    {
      if (getYPos() + lines[i].getHeight() > height - footerHeight)
      {
        writeNewPage();
      }

      writeLine2(lines[i]);
    }

    clearText();
  }

  /**
   * Closes this document, and writes any necessary trailer information
   * to the output format.
   * 
   * @exception IOException 
   */
  protected void close() throws IOException
  {
    super.close();

    writer.writeClose();
    writer.postProcess();
  }

  /**
   * Writes a footer to the current page, ejects the page and writes
   * out the header to the new page
   * 
   * @exception IOException 
   */
  protected void writeNewPage() throws IOException
  {
    writer.writeEndOfPage();
    currentPage++;

    // Check to see if there is a new header and footer
    if (newHeader != null)
    {
      header = newHeader;
      headerHeight = header.getYPos();
      newHeader = null;
    }

    if (newFooter != null)
    {
      footer = newFooter;
      footerHeight = footer.getYPos() - footer.getLastNewLinePointSize();
      newFooter = null;
    }

    writer.writeNewPage();
    getWriteFunctions().writeSetY(headerHeight);

    // use xpos in case of any pending tabs
    getWriteFunctions().writeSetX(getXPos()); 

    // re-establish the current font, in case the header routine has mucked it
    // up
    getWriteFunctions().writeSetFont(getFont());
  }

  /**
   * Accessor to see if the footer has changed since the last page break
   * 
   * @return TRUE if the footer has changed since the last page break, 
   *         FALSE otherwise
   */
  public boolean hasFooterChanged()
  {
    return newFooter != null;
  }

  /**
   * Gets the header for this document.  This enables client applications
   * to set up the exact header they want
   * 
   * @exception IOException 
   * @return the header writer
   */
  public LayoutWriter getHeader() throws IOException
  {

    if (header == null && !initialized)
    {
      header = new DiversionWriter(getWidth(), writer, this);
      return header;
    }
    else if (initialized) 
    {
      newHeader = new DiversionWriter(getWidth(), writer, this);
      return newHeader;
    }
    else
    {
      return header;
    }
  }

  /**
   * Gets the footer writer for this document
   * 
   * @exception IOException 
   * @return the footer writer
   */
  public LayoutWriter getFooter() throws IOException
  {
    if (footer == null && !initialized)
    {
      footer = new DiversionWriter(getWidth(), writer, this);
      return footer;
    }
    else if (initialized) 
    {
      newFooter = new DiversionWriter(getWidth(), writer, this);
      return newFooter;
    }
    else
    {
      return footer;
    }
  }

  /**
   * Writes out all the cached commands in the header
   * 
   * @exception IOException 
   */
  public void writeHeader() throws IOException
  {
    if (header != null)
    {
      header.write();
    }
  }

  /**
   * Writes out all the cached commands in the footer
   * 
   * @exception IOException 
   */
  public void writeFooter() throws IOException
  {
    if (footer != null)
    {
      footer.write();
    }
  }

  /**
   * Gets the space left on the current page in cm
   * 
   * @return the space available on the current page
   */
  public double getRemainingSpaceOnPage()
  {
    return (height - footerHeight - getYPos()) / Constants.POINTS_PER_CM;
  }

  /**
   * Accessor to get the current page number
   *
   * @return the current page number
   */
  public int getPageNumber()
  {
    return currentPage;
  }

  /**
   * Overrides the base tab functionality to throw a new page if there
   * is insufficient room on this one
   * 
   * @exception IOException 
   */
  public void tab() throws IOException
  {
    // Eject any text currently stored up
    if (getText() != null)
    {
      Line[] lines = getText().getLines(getWidth());

      if (getYPos() + lines[0].getHeight() > 
          height - footerHeight)
      {
        writeNewPage();
      }
    }

    super.tab();
  }
}
