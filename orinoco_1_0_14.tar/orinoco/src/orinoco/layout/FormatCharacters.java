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

/**
 * Contains the logic to determine whether any characters in a string are
 * special characters in the output format
 */
interface FormatCharacters
{
  /**
   * Sees if the character passed in is a special character in this particular
   * output format
   *
   * @param c the character
   * @return TRUE if the character is a special character, FALSE otherwise
   */
  boolean isSpecial(char c);

  /**
   * Gets the escape sequence necessary to render the special character in
   * the output file format
   *
   * @param c the character
   * @return the escape sequence
   */
  String getEscapeSequence(char c);
}
