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

import orinoco.layout.PageNumberMacro;

/**
 * A macro used to include the page number.  By placing this macro into
 * headers/footers etc. the current page number will be displayed every
 * time a new page occurs
 */
public class PageNumber extends PageNumberMacro implements TextMacro
{
  /**
   * Constructor
   * 
   * @param doc the owning document
   */
  public PageNumber(Document doc)
  {
    super(doc);
  }

  /**
   * Constructor
   * 
   * @param pre a textual preamble to the macro
   * @param post a text post script appended to the macro
   * @param doc a handle to the document
   */
  public PageNumber(String pre, String post, Document doc)
  {
    super(pre, post, doc);
  }
}
