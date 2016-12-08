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

import orinoco.TextMacro;

/**
 * A text macro which displays the current page number
 */
public class PageNumberMacro implements TextMacro
{
  /**
   * Any preamble to the page number (eg. the string "page")
   */
  private String pre;
  /**
   * Any afterword to the page number
   */
  private String post;
  /**
   * The owning document
   */
  private DocumentLayout doc;

  /**
   * Constructor
   * 
   * @param dl the document
   */
  protected PageNumberMacro(DocumentLayout dl)
  {
    doc = dl;
    pre = "";
    post = "";
  }

  /**
   * Constructor
   * 
   * @param dl the document
   * @param pre preamble
   * @param post postamble, if thats a word
   */
  protected PageNumberMacro(String pre, String post, DocumentLayout dl)
  {
    this.pre = pre;
    this.post = post;
    doc = dl;
  }

  /**
   * Gets the dynamic text for this macro
   * 
   * @return the dynamically varying text
   */
  public String getText()
  {
    return pre + doc.getCurrentPage() + post;
  }
}
