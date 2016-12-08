/*********************************************************************
*
*      Copyright (C) 2003 Andrew Khan
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

/**
 * The Unicode compatible font encoding
 */
class FontEncoding extends PDFObject
{
  /**
   * The encoding mapping
   */
  private final static String[] names =
  {
    "bullet",
    "dagger",
    "daggerdbl",
    "ellipsis",
    "emdash",
    "endash",
    "florin",
    "fraction",
    "guilsinglleft",
    "guilsinglright",
    "minus",
    "perthousand",
    "quotedblbase",
    "quotedblleft",
    "quotedblright",
    "quoteleft",
    "quoteright",
    "quotesinglbase",
    "trademark",
    "fi",
    "fl",
    "Lslash",
    "OE",
    "Scaron",
    "Ydieresis",
    "Zcaron",
    "dotlessi",
    "lslash",
    "oe",
    "scaron",
    "zcaron",
    "",
    "Euro",
    "exclamdown",
    "cent",
    "sterling",
    "currency",
    "yen",
    "brokenbar",
    "section",
    "dieresis",
    "copyright",
    "ordfeminine",
    "guillemotleft",
    "logicalnot",
    "",
    "registered",
    "macron",
    "degree",
    "plusminus",
    "twosuperior",
    "threesuperior",
    "acute",
    "mu",
    "paragraph",
    "periodcentered",
    "cedilla",
    "onesuperior",
    "ordmasculine",
    "guillemotright",
    "onequarter",
    "onehalf",
    "threequarters",
    "questiondown",
    "Agrave",
    "Aacute",
    "Acircumflex",
    "Atilde",
    "Adieresis",
    "Aring",
    "AE",
    "Ccedilla",
    "Egrave",
    "Eacute",
    "Ecircumflex",
    "Edieresis",
    "Igrave",
    "Iacute",
    "Icircumflex",
    "Idieresis",
    "Eth",
    "Ntilde",
    "Ograve",
    "Oacute",
    "Ocircumflex",
    "Otilde",
    "Odieresis",
    "multiply",
    "Oslash",
    "Ugrave",
    "Uacute",
    "Ucircumflex",
    "Udieresis",
    "Yacute",
    "Thorn",
    "germandbls",
    "agrave",
    "aacute",
    "acircumflex",
    "atilde",
    "adieresis",
    "aring",
    "ae",
    "ccedilla",
    "egrave",
    "eacute",
    "ecircumflex",
    "edieresis",
    "igrave",
    "iacute",
    "icircumflex",
    "idieresis",
    "eth",
    "ntilde",
    "ograve",
    "oacute",
    "ocircumflex",
    "otilde",
    "odieresis",
    "divide",
    "oslash",
    "ugrave",
    "uacute",
    "ucircumflex",
    "udieresis",
    "yacute",
    "thorn",
    "ydieresis"
  };
  /**
   * Constructor
   *
   * @param on
   */
  public FontEncoding(ObjectNumber on)
  {
    super(on);
  }

  /**
   * Writes out all the font encodings
   * 
   * @exception IOException 
   * @param out the output stream
   */
  void write(OutputStreamWriter out) throws IOException
  {
    getNumber().writeObj(out);
    out.write("<<");
    out.write(PDFWriter.newLineChar);
    out.write("/Type /Encoding");
    out.write(PDFWriter.newLineChar);
    out.write("/BaseEncoding /PDFDocEncoding");
    out.write(PDFWriter.newLineChar);
    out.write("/Differences [");
    out.write(PDFWriter.newLineChar);
    out.write("128 ");
    for (int i = 0; i < names.length ; i++)
    {
      out.write("/");
      out.write(names[i]);
      out.write(" ");
    }
    out.write("]");
    out.write(PDFWriter.newLineChar);
    out.write(">>");
    out.write(PDFWriter.newLineChar);
    out.write("endobj");
    out.write(PDFWriter.newLineChar);
  }
}
