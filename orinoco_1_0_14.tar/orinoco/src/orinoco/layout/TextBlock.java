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

import java.util.ArrayList;
import java.util.Iterator;

import orinoco.Font;
import orinoco.Alignment;
import orinoco.TextMacro;

/**
 * A text block.  Consists of an array of strings and corresponding fonts,
 * together with the alignment of the block
 */
final class TextBlock
{
  /**
   * The first text component for the block - an optimization for blocks
   * which are in a consistent font
   */
  private TextComponent component;

  /**
   * The list of text components, different fonts etc
   */
  private ArrayList textComponents;

  /**
   * The alignment used for text macros
   */
  private Alignment alignment;

  /**
   * A handle to the atomic write functions, used to determine if the
   * text contains special characters which need to be escaped
   */
  private FormatCharacters formatCharacters;

  /**
   * Constructor
   * 
   * @param s the text
   * @param f the font
   * @param fc the atomic write functions
   */
  public TextBlock(String s, BaseFont f, FormatCharacters fc)
  {
    formatCharacters = fc;
    component = new TextComponent(s, f, formatCharacters);
  }

  /**
   * Constructor
   * 
   * @param a the alignment
   * @param tm the text macro
   * @param f the font
   * @param awf the atomic write functions
   */
  public TextBlock(TextMacro tm, BaseFont f, Alignment a, 
                   FormatCharacters fc)
  {
    alignment = a;
    formatCharacters = fc;
    component = new TextMacroComponent(tm, f, alignment, formatCharacters);
  }
  
  /**
   * Adds the text component to this block
   * 
   * @param s the text
   * @param f the font
   */
  public void add(String s, BaseFont f)
  {
    if (component != null)
    {
      if (f.equals(component.getFont()))
      {
        // Fonts are the same
        component.append(s);
        return;
      }
      else
      {
        textComponents = new ArrayList();
        textComponents.add(component);
        component = null;
      }
    }

    // See if the fonts are the same on the last TextComponent
    TextComponent tc = (TextComponent) 
      textComponents.get(textComponents.size() - 1);
    if (f.equals(tc.getFont()))
    {
      tc.append(s);
    }
    else
    {
      textComponents.add(new TextComponent(s, f, formatCharacters));
    }
  }

  /**
   * Adds the text component to this block
   * 
   * @param tm the text macro
   * @param f the font
   */
  public void add(TextMacro tm, BaseFont f)
  {
    if (component != null)
    {
      textComponents = new ArrayList();
      textComponents.add(component);
      component = null;
    }

    textComponents.add(new TextMacroComponent(tm, 
                                              f, 
                                              alignment, 
                                              formatCharacters));
  }

  /**
   * Splits the text block into lines not exceeding the length specified
   * 
   * @param width The width of the text block
   * @return the lines of text
   */
  public Line[] getLines(double width)
  {
    LineBreaker lb = new LineBreaker(width, textComponents, 
                                     component, formatCharacters);
    return lb.getLines();
  }  
}


