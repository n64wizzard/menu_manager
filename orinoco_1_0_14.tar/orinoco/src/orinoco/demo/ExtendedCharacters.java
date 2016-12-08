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

package orinoco.demo;

import java.io.File;
import orinoco.Document;
import orinoco.Heading;
import orinoco.Column;
import orinoco.Font;
import orinoco.Table;
import orinoco.Alignment;
import orinoco.LayoutWriter;
import orinoco.TextWriter;
import orinoco.Paper;
import orinoco.PageNumber;
import orinoco.PostscriptWriter;
import orinoco.PDFWriter;
import orinoco.Colour;
import orinoco.OutputFormatWriter;

public class ExtendedCharacters
{
  /**
   * <code>main</code> method
   *
   * @param args[] a <code>String</code> value
   */
  public static void main(String args[])
  {
    if (args.length != 2 || 
        (!args[0].equals("-ps") && !args[0].equals("-pdf")))
    {
      System.out.println("Usage:  Test -ps|-pdf output");
      System.exit(1);
    }

    try
    {
      OutputFormatWriter ofw = null;

      if (args[0].equals("-ps"))
      {
        ofw = new PostscriptWriter(new File(args[1]));
      }
      else if (args[0].equals("-pdf"))
      {
        ofw = new PDFWriter(new File(args[1]));
      }

      long start = System.currentTimeMillis();

      // Create the document
      Document doc = new Document(Paper.LETTER, ofw);
      doc.open();

      doc.writeLine("The following are special characters supported by " +
                    "orinoco and conform to the first 256 characters of "+
                    "the unicode character set.");
      doc.writeLine("They may be embedded into a PDF document by embedding "+
                    "the indicated octal character code into the java "+
                    "string");
      doc.newLine();


      Font hdrFont = new Font(Font.HELVETICA_BOLD, 12);
      Font helveticaFont = new Font(Font.HELVETICA, 10);
      Font helveticaBold = new Font(Font.HELVETICA_BOLD, 10);
      Font timesFont = new Font(Font.TIMES, 10);
      Font timesBold = new Font(Font.TIMES_BOLD, 10);
      Font courierFont = new Font(Font.COURIER, 10);
      Column col1 = new Column(2.5, Alignment.CENTRE, helveticaFont);
      Column col2 = new Column(2.5, Alignment.CENTRE, helveticaFont);
      Column col3 = new Column(2.5, Alignment.CENTRE, helveticaBold);
      Column col4 = new Column(2.5, Alignment.CENTRE, timesFont);
      Column col5 = new Column(2.5, Alignment.CENTRE, timesBold);
      Column col6 = new Column(2.5, Alignment.CENTRE, courierFont);
      Heading hdr1 = new Heading("Octal code", Alignment.CENTRE, 
                                 hdrFont);
      Heading hdr2 = new Heading("Helvetica", Alignment.CENTRE, 
                                 hdrFont);

      Heading hdr3 = new Heading("Helvetica Bold", Alignment.CENTRE, 
                                 hdrFont);

      Heading hdr4 = new Heading("Times", Alignment.CENTRE, 
                                 hdrFont);

      Heading hdr5 = new Heading("Times Bold", Alignment.CENTRE, 
                                 hdrFont);

      Heading hdr6 = new Heading("Courier", Alignment.CENTRE, 
                                 hdrFont);

      Table table = doc.createTable
        (new Column[] {col1, col2, col3, col4, col5, col6},
         new Heading[] {hdr1, hdr2, hdr3, hdr4, hdr5, hdr6});
      table.setHeaderBackground(new Colour(230, 230, 230));
      table.setHeaderBorder(1, 1);
      table.setRowBorder(1);

      for (int charind = 128; charind < 256 ; charind++)
      {
        String octalCode = "\\\\" + Integer.toString(charind, 8);
        String character = "\\"+Integer.toString(charind,8);
        table.addRow(new String[] {octalCode, character, character, 
                                   character, character, character});
      }

      table.close();

      doc.newLine();
      doc.newLine();
      doc.writeLine("Some French phrases", helveticaBold);
      doc.newLine();
      doc.writeLine("Attention écoute!");
      doc.writeLine("Le silence va plus vite à reculons");
      doc.writeLine("Un seul verre d'eau éclaire le monde");
      doc.writeLine("L'oiseau chante avec ses doigts");
      doc.writeLine("Trois fois  Je repete.");

      doc.close();
      long stop = System.currentTimeMillis();
      System.out.println("Time taken:  " + (stop - start) + "ms");
    }
    catch (Throwable t)
    {
      t.printStackTrace();
    }
  }
}
