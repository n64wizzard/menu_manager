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
import java.util.TreeSet;
import java.util.Iterator;

/**
 * A component of a text block, consisting of the string and its font
 */
public class TextComponent
{
  /**
   * The string
   */
  private StringBuffer string;
  
  /**
   * The original string, without special replacements
   */
  private String originalString;

  /**
   * The font for the string
   */
  private BaseFont font;
  /**
   * The length (in points) of the string when it is displayed in its font
   */
  private double length;

  /**
   * A handle to the format characters to determine whether the 
   * characters is a special character in the output format
   */
  private FormatCharacters formatCharacters;

  /**
   * An array of special characters that need to be replaced
   * It is specified as member data to reduce the overhead of
   * repeatedly creating this object
   */
  private TreeSet specialCharacters;

  /**
   * Inner class dummy
   */
  private static class Initialized {}
  
  /**
   * Dummy variable
   */
  static final Initialized INITIALIZED = new Initialized();

  /**
   * Constructor
   * 
   * @param s the string
   * @param f the font
   * @param fc the format characters
   */
  TextComponent(String s, BaseFont f, FormatCharacters fc)
  {
    font = f;
    formatCharacters = fc;
    specialCharacters = new TreeSet();
    originalString = s;

    string = new StringBuffer(initString(s));
   }

  /**
   * Constructor
   * 
   * @param s the string
   * @param f the font
   * @param fc the format characters
   * @param init initialized dummy overload
   */
  TextComponent(String s, BaseFont f, FormatCharacters fc, Initialized i)
  {
    font = f;
    formatCharacters = fc;
    specialCharacters = new TreeSet();
    originalString = s;

    string = new StringBuffer(s); // no need to initialized
   }
  
  /**
   * Gets the original string, without any replacements
   *
   * @return the original string
   */
  public final String getOriginalString()
  {
    return originalString;
  }

  /**
   * Gets the string
   * 
   * @return the string
   */
  public final String getString()
  {
    return string.toString();
  }

  /**
   * Gets the font for the string
   * 
   * @return the font
   */
  public final BaseFont getFont()
  {
    return font;
  }

  /**
   * Appends the string on to the buffer and updates the required length
   * 
   * @param s the string
   */
  void append(String s)
  {
    string.append(initString(s));
  }

  /**
   * Gets the length required to display this string in its font
   * 
   * @return the length
   */
  public final double getLength()
  {
    return length;
  }
  
  /**
   * Single despatch method called from the writer
   * 
   * @exception IOException 
   * @param w the writer
   */
  public void write(BaseWriter w) throws IOException
  {
    w.getWriteFunctions().writeText(string.toString());
  }

  /** 
   * Method to calculate the string length and handle any special characters
   *
   * @param s the string
   * @return the initialized string, with any special characters replaced
   * by their escape sequences
   */
  private String initString(String s)
  {
    boolean containsSpecial = false;
    char[] characters = s.toCharArray();
    specialCharacters.clear();

    for (int i = 0 ; i < characters.length ; i++)
    {
      length += font.getCharacterWidth(characters[i]);

      if (formatCharacters.isSpecial(characters[i]))
      {
        containsSpecial = true;
        specialCharacters.add(new Character(characters[i]));
      }
    }

    if (!containsSpecial)
    {
      return s;
    }

    Iterator i = specialCharacters.iterator();
    String curString = s;

    while (i.hasNext())
    {
      char c =  ( (Character) i.next()).charValue();
      curString = replace(curString, c, formatCharacters.getEscapeSequence(c));
    }

    return curString;
  }

  /**
   * Replaces all instances of search with replace in the input.  Used for
   * replacing microsoft number formatting characters with java equivalents
   *
   * @param input the format string
   * @param search the special character to be replaced
   * @param replace the java equivalent
   * @return the input string with the specified substring replaced
   */
  private String replace(String input, char search, String replace)
  {
    String formatString = input;
    int pos = formatString.indexOf(search);
    while (pos != -1)
    {
      StringBuffer tmp = new StringBuffer(formatString.substring(0, pos));
      tmp.append(replace);
      tmp.append(formatString.substring(pos + 1));
      formatString = tmp.toString();
      pos = formatString.indexOf(search, pos+replace.length());
    }
    return formatString;
  }

}




