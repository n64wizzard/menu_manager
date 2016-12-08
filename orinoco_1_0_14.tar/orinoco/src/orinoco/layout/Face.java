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

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/**
 * The font face.  Maintains a hashtable of the font's character widths
 */
class Face
{
  /**
   * The character widths for a given font face
   */
  private double[] characterWidths;
  
  /**
   * The faces currently read in
   */
  private static HashMap faces = new HashMap();

  /**
   * Reads the faces from the face file
   * 
   * @exception IOException 
   * @param font the font
   */
  private Face(String font) throws IOException
  {
    characterWidths = new double[256];
    String resourcesDir = Constants.RESOURCES_DIR != null ?
      Constants.RESOURCES_DIR : Constants.CURRENT_DIR;
    StringBuffer sb = new StringBuffer(resourcesDir);
    sb.append(File.separatorChar);
    sb.append(Constants.FONTS_DIR);
    sb.append(File.separatorChar);
    sb.append(font);
    sb.append(Constants.CHAR_WIDTHS_SUFFIX);
    File f = new File(sb.toString());
    BufferedReader br = new BufferedReader(new FileReader(f));
    
    int pos = 0;
    String width = br.readLine();

    while (width != null && pos < 256)
    {
      characterWidths[pos] = Double.parseDouble(width);
      pos++;
      width = br.readLine();
    }
  }

  /**
   * Gets the width required for the specified character in this font face
   * 
   * @param c the number of points
   * @return the character
   */
  public double getWidth(char c)
  {
    return characterWidths[(int) c];
  }

  /**
   * Gets the font face from the hash table of faces already read in
   * In the event of the face not being present, display the error and
   * return the empty set of widths, but otherwise processing will
   * continue
   * 
   * @param name the name of the font face
   * @return the face
   */
  public synchronized static Face getFace(String name)
  {
    Face face = (Face) faces.get(name);

    if (face == null)
    {
      try
      {
        face = new Face(name);
        faces.put(name, face);
      }
      catch (IOException e)
      {
        System.err.println(e.toString());
      }
    }

    return face;
  }
}
