/*********************************************************************
*
*      Copyright (C) 2005 Andrew Khan
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


/**
 * Class used to break up longer lines at convenient points so that they
 * span several rendered lines without exceeded the page width
 */
class LineBreaker
{
  /**
   * The list of text components
   */
  private ArrayList textComponents;

  /**
   * The active text component
   */
  private TextComponent component;

  /**
   * The available width for the lines
   */
  private double width;

  /**
   * A handle to the atomic write functions, used to determine if the
   * text contains special characters which need to be escaped
   */
  private FormatCharacters formatCharacters;

  // Data items used during the breaking calculation
  private TextComponent tc;
  private ArrayList lines;
  private Line curline;
  private double curwidth;

  private String text;
  private char[] characters;
  private double charwidth;
  private int lastSpacePos;
  private int lastBreakPos;
  private int pos;
  private boolean broken;
  private double lengthWritten;

  public LineBreaker(double w, ArrayList tc, TextComponent comp, 
                     FormatCharacters fc)
  {
    width = w;
    textComponents = tc;
    component = comp;
    formatCharacters = fc;
  }

  public Line[] getLines()
  {
    if (textComponents == null)
    {
      return getQuickLines();
    }
    else
    {
      return getMultiFontLines();
    }
  }

  /**
   * Quick method, because there is only one a single text component
   * 
   * @param width the available width
   * @return the line
   */
  private Line[] getQuickLines()
  {
    // If the component fits on one line, then simply return it and finish
    if (component.getLength() < width)
    {
      return new Line[] {new Line(component)};
    }

    // Go through each character in turn and find out where the breaks can 
    // occur.  Use a while loop to do this, because we have to fiddle
    // with the indexes a bit
    ArrayList lines = new ArrayList();
    int pos = 0;
    int lastBreakPos = 0; // the first character after a break
    int lastSpacePos = 0; // the last character before a space
    String text = component.getString();
    char[] characters = text.toCharArray();
    double curwidth = 0;
    double charwidth = 0;

    while (pos < characters.length)
    {
      if (characters[pos] == ' ' && pos > 0)
      {
        lastSpacePos = pos - 1;

        // consume any preceding empty spaces
        while (lastSpacePos >= 0 && characters[lastSpacePos] == ' ')
        {
          lastSpacePos--;
        }
      }

      charwidth = component.getFont().getCharacterWidth(characters[pos]);

      if (curwidth + charwidth > width)
      {
        // This is the break point
        if (lastSpacePos != lastBreakPos)
        {
          // There have been spaces since the last line break
          TextComponent tc = new TextComponent 
            (text.substring(lastBreakPos,lastSpacePos+1), 
             component.getFont(), formatCharacters,
             TextComponent.INITIALIZED);
          lines.add(new Line(tc));

          lastBreakPos = lastSpacePos+1;
          while (lastBreakPos < characters.length && 
                 characters[lastBreakPos] == ' ')
          {
            lastBreakPos++;
          }
          lastSpacePos = lastBreakPos;
          pos = lastBreakPos;
        }
        else
        {
          // There have been no spaces - we'll just have to break at the
          // previous character.  Make sure there is always one char
          int breakpos = Math.max(pos - 1, lastBreakPos);
          TextComponent tc = new TextComponent
            (text.substring(lastBreakPos, breakpos+1), component.getFont(),
             formatCharacters, TextComponent.INITIALIZED);
          lines.add(new Line(tc));

          lastBreakPos = pos;
          lastSpacePos = lastBreakPos;
        }
        curwidth = 0;
      }
      else
      {
        curwidth += charwidth;
        pos++;
      }
    }

    // Eject out the last line
    if (lastBreakPos < characters.length)
    {
      TextComponent tc = new TextComponent 
        (text.substring(lastBreakPos), component.getFont(), formatCharacters,
         TextComponent.INITIALIZED);
      lines.add(new Line(tc));
    }

    Line[] lns = new Line[lines.size()];
    for (int j = 0 ; j < lns.length; j++)
    {
      lns[j] = (Line) lines.get(j);
    }

    return lns;
  }

  /**
   * Method for more than one text component ie. if the line contains text
   * of more than one font type
   * 
   * @param width the available width
   * @return the lines
   */
  private Line[] getMultiFontLines()
  {
    Iterator i = textComponents.iterator();

    tc = null;
    lines = new ArrayList();
    curline = null;
    curwidth = 0;
    
    while (i.hasNext())
    {
      tc = (TextComponent) i.next();

      // If the current text component fits on the current line, then add it
      // and continue
      if (curwidth + tc.getLength() < width)
      {
        if (curline == null)
        {
          curline = new Line(tc);
          lines.add(curline);
        }
        else
        {
          curline.add(tc);
        }

        curwidth += tc.getLength();
      }
      else
      {
        // The text component goes over the end of line.  We have to decide
        // where to break
        breakTextComponent();
        
        curwidth = tc.getLength();
        if (curline == null)
        {
          curline = new Line(tc);
          lines.add(curline);
        }
        else
        {
          curline.add(tc);
        }
      }
    }

    if (lines.size() == 1)
    {
      return new Line[] {curline};
    }
      
    Line[] lns = new Line[lines.size()];
    for (int j = 0 ; j < lns.length; j++)
    {
      lns[j] = (Line) lines.get(j);
    }
    
    return lns;
  }

  /**
   * The text component goes over the end of the line.  Decide where to
   * break it
   */
  private void breakTextComponent()
  {
    while (curwidth + tc.getLength() > width)
    {
      text = tc.getOriginalString();
      characters = text.toCharArray();
      charwidth = 0;
      lastSpacePos = 0;
      lastBreakPos = 0;
      pos = 0;
      broken = false;
      lengthWritten = 0;

      while (pos < characters.length && !broken)
      {
        if (characters[pos] == ' ' && pos > 0)
        {
          lastSpacePos = pos - 1;
              
          // consume any preceding empty spaces
          while (lastSpacePos >= 0 && characters[lastSpacePos] == ' ')
          {
            lastSpacePos--;
          }
        }
            
        charwidth = tc.getFont().getCharacterWidth(characters[pos]);
            
        if (curwidth + charwidth > width)
        {
          if (lastSpacePos > lastBreakPos)
          {
            // There have been spaces since the last line break.  Find a 
            // convenient word to place the carriage return
            findWordBreak();
          }
          else
          {
            // There have been no spaces.  If there is already text on 
            // the line, then break at the beginning of the text 
            // component, otherwise  we'll just have to break at the
            // previous character.  Make sure there is always one char
            findComponentBreak();
          }
        }
        else
        {
          curwidth += charwidth;
        }
        pos++;
      }

      // Eject out the last bit
      if (pos <= characters.length)
      {
        tc = new TextComponent 
          (text.substring(lastBreakPos), 
           tc.getFont(), formatCharacters);
      }
      else
      {
        // create an empty text component
        tc = new TextComponent("", tc.getFont(), formatCharacters);
      }
    }
  }
  
  private void findWordBreak()
  {
    TextComponent tc2 = new TextComponent 
      (text.substring(lastBreakPos,lastSpacePos + 1),
       tc.getFont(), 
       formatCharacters);

    if (curline == null)
    {
      curline = new Line(tc2);
      lines.add(curline);
    }
    else
    {
      curline.add(tc2);
    }

    lengthWritten += tc2.getLength();
    curline = null;  
    curwidth = 0;

    // Consume any spaces
    lastBreakPos = lastSpacePos+1;
    while (lastBreakPos < characters.length &&
           characters[lastBreakPos] == ' ')
    {
      lastBreakPos++;
    }
    lastSpacePos = lastBreakPos;
    pos = lastBreakPos;
    // See if the remaining text will fit on the line or not
    broken = ((tc.getLength() - lengthWritten) < width);
  }

  private void findComponentBreak()
  {
    // If there is a current line, and the text can fit on a 
    // complete line, then break at the beginning of the text
    // component
    if (curline != null && tc.getLength() < width)
    {
      // line was added to lines when it was created, so don't
      // add it again
      curline = null;
      curwidth = 0;
      broken = true;
      lastBreakPos = pos;
      pos = characters.length;
    }
    else
    {
      // Break at the previous character
      int breakpos = pos - 1;
      TextComponent tc2 = new TextComponent
        (text.substring(0, breakpos+1), tc.getFont(),
         formatCharacters, TextComponent.INITIALIZED);
                
      if (curline == null)
      {
        curline = new Line(tc2);
        lines.add(curline);
      }
      else
      {
        curline.add(tc2);
      }
                
      lines.add(curline);
      curline = null;
      curwidth = 0;
      broken = true;
      lastBreakPos = pos;
    }
  }
}
