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

import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

/**
 * The fonts used in this document
 */
class Fonts
{
  /**
   * The fonts so far used in this document
   */
  private HashMap fonts;

  /**
   * A handle to the cross reference object
   */
  private CrossReferencer xref;

  /**
   * The font encoding object
   */
  private FontEncoding encoding;

  /**
   * Constructor
   * 
   * @param xref cross reference table
   */
  Fonts(CrossReferencer xref)
  {
    fonts = new HashMap();
    this.xref = xref;
    encoding = new FontEncoding(xref.createObjectNumber());
  }

  /**
   * Gets the font off the hash map given the face name.  If the font
   * is not yet used in this document, it is initialized and put on
   * the list
   * 
   * @param fn the font name
   * @return the font for the given name
   */
  Font getFont(String fn)
  {
    Font f = (Font) fonts.get(fn);

    if (f != null)
    {
      return f;
    }

    // Font does not exist - create it
    String fontName = "F" + fonts.size();
    f = new Font(xref.createObjectNumber(), fn, fontName, encoding);

    fonts.put(fn, f);

    return f;
  }

  /**
   * Writes out all the font objects
   * 
   * @exception IOException 
   * @param out the output stream
   */
  void write(OutputStreamWriter out) throws IOException
  {
    encoding.write(out);

    Collection c = fonts.values();
    Iterator i = c.iterator();
    Font font = null;

    while (i.hasNext())
    {
      font = (Font) i.next();
      font.write(out);
    }
  }
}
