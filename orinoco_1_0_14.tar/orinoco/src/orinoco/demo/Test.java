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

/**
 * A demo program to test the full gamut of orinoco's pdf generation
 * capabilties
 */
public class Test
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

      // Define a header
      Font titleFont = new Font(Font.HELVETICA_BOLD, 14);
      LayoutWriter header = doc.getHeader();
      //      header.writeLine("orinoco Test Document", titleFont, Alignment.CENTRE);

      Column[] headercols = new Column[4];
      headercols[0] = new  Column(2);
      headercols[1] = new Column(13, Alignment.CENTRE, titleFont);
      headercols[2] = new Column(2, Alignment.RIGHT);
      headercols[3] = new Column(2);

      Table ht = header.createTable(headercols);
      String[] titlerow = new String[]{"col1", 
                                       "orinoco Test Document", 
                                       "page"};
      ht.addCells(titlerow);
      TextWriter w = ht.getCellWriter(3);
      w.writeMacro(new PageNumber(" ", "", doc));
      ht.writeRow();
      ht.close();
      
      header.space(0.2);

      // Define a footer
      Font footerFont = new Font(Font.TIMES, 8);
      LayoutWriter footer= doc.getFooter();
      footer.space(0.1);
      footer.drawLine();
      footer.space(0.1);
      footer.writeMacroLine(new PageNumber("page ", "", doc), 
                            footerFont, Alignment.RIGHT);

      doc.open();
      
      doc.writeLine("Default Font");

      Font f1 = new Font(Font.TIMES_BOLD, 14);
      doc.setFont(f1);
      doc.writeLine("14 point times bold");
      doc.newLine();

      Font f2 = new Font(Font.TIMES_ITALIC, 10);
      doc.setFont(f2);
      doc.writeLine("10 point times italic");

      doc.setFont(Font.DEFAULT);
      doc.writeLine("Back to default");
      doc.newLine();

      Font f3 = new Font(Font.HELVETICA, 13);
      Font f4 = new Font(Font.COURIER, 11);
      Font f5 = new Font(Font.HELVETICA_BOLD, 8);
      doc.writeLine("Various fonts on one line:");

      doc.write("Helvetica 13pt", f3);
      doc.write("Courier 11pt", f4);
      doc.writeLine("Default font");
      doc.drawLine();
      doc.newLine();
      doc.writeLine("This is a very long line, that will take more than " +
                    "one a4 line to display.  Cold are the hands of time " +
                    "that creep along relentlessly, destroying slowly " +
                    "but without pity that which yesterday was young.  " +
                    "Alone our memories resist this disintegration and " +
                    "grow more lovely with the passing years.");
      doc.drawLine();
      doc.writeLine("This is right aligned", Alignment.RIGHT);
      doc.writeLine("This piece of text is also right aligned", 
                    Alignment.RIGHT);
      doc.writeLine("Normal again", Alignment.LEFT);
      doc.writeLine("Centred", Alignment.CENTRE);
      doc.newLine();
      doc.writeLine("Some tabs");
      doc.addTab(3);
      doc.addTab(9);
      doc.addTab(11);
      doc.write("Some preamble", f5);
      doc.tab();
      doc.write("tab1");
      doc.tab();
      doc.write("tab2");
      doc.tab();
      doc.write("tab3");
      doc.newLine();
      doc.tab();
      doc.write("another tab");
      doc.tab();
      doc.write("yet another tab");
      doc.newLine();
      doc.writeLine("Not tabbed");
      doc.newLine();
      doc.writeLine("(Text in round brackets)");
      doc.writeLine("(More text in round brackets) outside brackets");
      doc.writeLine("[Text in square brakets]");
      doc.writeLine("{Text in curly brackets}");
      doc.writeLine("Open a single bracket (and write some text");
      doc.writeLine("close the above bracket)");
      doc.newLine();
      doc.newLine();
      doc.tab();
      doc.setIndent();
      doc.writeLine("This is a block of text that has been indented at " +
                    "the current tab position so that every line break " +
                    "automatically shifts inwards.  This remains in " +
                    "effect until the the indent is explicitly released");
      doc.releaseIndent();
      doc.writeLine("The indentation has now been released");
      doc.newPage();
      doc.writeLine("New page");

      // Create a table
      Font dataFont = new Font(Font.HELVETICA, 8);
      Column col1 = new Column(3, dataFont);
      Column col2 = new Column(3, Alignment.CENTRE, dataFont);
      Column col3 = new Column(4, Alignment.RIGHT, dataFont);
      Column col4 = new Column(2, Alignment.LEFT, dataFont);

      Column[] cols = new Column[] {col1, col2, col3, col4};

      Font tableHeaderFont = new Font(Font.TIMES_BOLD, 13);
      Heading hdr1 = new Heading("Header 1", Alignment.CENTRE, 
                                 tableHeaderFont);
      Heading hdr2 = new Heading("Heading 2", Alignment.CENTRE, 
                                 tableHeaderFont);
      Heading hdr3 = new Heading("Header 3", Alignment.CENTRE, 
                                 tableHeaderFont);
      Heading hdr4 = new Heading("Header 4", Alignment.CENTRE, 
                                 tableHeaderFont);
      
      Heading[] hdrs = new Heading[] {hdr1, hdr2, hdr3, hdr4};

      Table table = doc.createTable(cols, hdrs);
      table.setColumnSpacing(0.25);
      table.setHeaderBackground(Colour.GREY_80);

      String[] firstRow = new String[] {"op(en", "clo)se", "(enclosed)",
                                        "Some (wrapped parentheses)"};
      table.addRow(firstRow);

      for (int i = 0; i < 100; i++)
      {
        String[] data = new String[] {"left " + i, 
                                      "Some data", 
                                      "Some more data", 
                                      "Some wrapped text"};
        table.addRow(data);
      }

      table.close();
      doc.writeLine("The table has ended.  There now follows a table with "+
                    "horizontal borders");
      doc.newLine();

      col1 = new Column(3);
      col2 = new Column(3);
      cols = new Column[] { col1, col2 };
      hdr1 = new Heading("Header 1", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr2 = new Heading("Heading 2", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdrs = new Heading[] {hdr1, hdr2};

      table = doc.createTable(cols, hdrs);
      table.setRowBorder(1);
      table.setHeaderBackground(Colour.GREY_80);

      for (int i = 0; i < 20 ; i++)
      {
        String[] data = new String[] {"Data item " + i,
                                      "Data value"};
        table.addRow(data);
      }
      table.close();

      doc.writeLine("And now a table demonstrating column spanning and "+
                    "underlining on alternate rows");
      doc.newLine();


      col1 = new Column(3);
      col2 = new Column(3);
      col3 = new Column(3);
      cols = new Column[] { col1, col2, col3 };
      hdr1 = new Heading("Header 1", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr2 = new Heading("Heading 2", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr3 = new Heading("Header3", Alignment.CENTRE, 
                         tableHeaderFont);

      hdrs = new Heading[] {hdr1, hdr2, hdr3};

      table = doc.createTable(cols, hdrs);
      table.setHeaderBackground(Colour.GREY_80);

      for (int i = 0; i < 10 ; i++)
      {
        String[] data = new String[] {"Data item " + i,
                                      "Data value", "Data value"};
        table.addRow(data);

        data = new String[] {"Span", null, null};
        table.addCells(data);

        TextWriter tw = table.getCellWriter(1,2);
        tw.write("This text spans two columns");
        table.writeRow();
        
        table.drawLine();
      }

      String[] newLineData = new String[] 
        {"New lines embedded in the text are ignored", 
         "Data item with\\nembedded\\nnew line\\ncharacters"};
      table.addRow(newLineData);
      table.writeRow();
      table.drawLine();
      
      table.close();
      
      doc.newLine();
      doc.writeLine("Table with a border");
      doc.newLine();

      col1 = new Column(3);
      col2 = new Column(3, Alignment.RIGHT);
      col3 = new Column(3, Alignment.RIGHT);
      cols = new Column[] { col1, col2, col3 };
      hdr1 = new Heading("Header 1", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr2 = new Heading("Heading 2", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr3 = new Heading("Header3", Alignment.CENTRE, 
                         tableHeaderFont);

      hdrs = new Heading[] {hdr1, hdr2, hdr3};

      table = doc.createTable(cols, hdrs);
      table.setHeaderBackground(Colour.GREY_80);
      table.setBorder(2);

      for (int i = 0; i < 10 ; i++)
      {
        String[] data = new String[] {"Data item " + i,
                                      "Data value", "Data value"};
        table.addRow(data);
      }

      table.close();

      doc.newLine();
      doc.writeLine("Table with a full border, header border, row border "+
                    "and column border");
      doc.newLine();

      col1 = new Column(3);
      col2 = new Column(3, Alignment.LEFT);
      col3 = new Column(3, Alignment.RIGHT);
      cols = new Column[] { col1, col2, col3 };
      hdr1 = new Heading("Header 1", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr2 = new Heading("Heading 2", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr3 = new Heading("Header3", Alignment.CENTRE, 
                         tableHeaderFont);

      hdrs = new Heading[] {hdr1, hdr2, hdr3};

      table = doc.createTable(cols, hdrs);
      table.setHeaderBackground(Colour.GREY_80);
      table.setBorder(2);
      table.setHeaderBorder(2,2);
      table.setColumnBorder(1);
      table.setRowBorder(1);

      for (int i = 0; i < 50 ; i++)
      {
        String[] data = new String[] {"Data item " + i,
                                      "Data value", "Data value"};
        table.addRow(data);
      }

      table.close();

      doc.newLine();
      doc.newLine();
      doc.newLine();
      doc.writeLine("Table with a full border, header border, row border "+
                    "and column border, but no data");
      doc.newLine();

      col1 = new Column(3);
      col2 = new Column(3, Alignment.LEFT);
      col3 = new Column(3, Alignment.RIGHT);
      cols = new Column[] { col1, col2, col3 };
      hdr1 = new Heading("Header 1", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr2 = new Heading("Heading 2", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr3 = new Heading("Header3", Alignment.CENTRE, 
                         tableHeaderFont);

      hdrs = new Heading[] {hdr1, hdr2, hdr3};

      table = doc.createTable(cols, hdrs);
      table.setHeaderBackground(Colour.GREY_80);
      table.setBorder(2);
      table.setHeaderBorder(2,2);
      table.setColumnBorder(1);
      table.setRowBorder(1);

      table.close();

      doc.writeLine("Table with a full border and "+
                    "column border, but no data.  Writes out just the " +
                    "headings");
      doc.newLine();

      col1 = new Column(3);
      col2 = new Column(3, Alignment.LEFT);
      col3 = new Column(3, Alignment.RIGHT);
      cols = new Column[] { col1, col2, col3 };
      hdr1 = new Heading("Header 1", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr2 = new Heading("Heading 2", Alignment.CENTRE, 
                                 tableHeaderFont);
      hdr3 = new Heading("Header3", Alignment.CENTRE, 
                         tableHeaderFont);

      hdrs = new Heading[] {hdr1, hdr2, hdr3};

      table = doc.createTable(cols, hdrs);
      table.setHeaderBackground(Colour.GREY_80);
      table.setBorder(1);
      table.setColumnBorder(1);
      table.setWriteHeadingsIfEmpty(true);

      table.close();


      doc.writeLine("Table with a full border, header border  and "+
                    "column border.  Headings are spanned across multiple "+
                    "columns");
      doc.newLine();

      col1 = new Column(2);
      col2 = new Column(2, Alignment.LEFT);
      col3 = new Column(1.2, Alignment.RIGHT);
      col4 = new Column(2, Alignment.RIGHT);
      Column col5 = new Column(1.1, Alignment.RIGHT);
      Column col6 = new Column(1.8, Alignment.RIGHT);
      cols = new Column[] { col1, col2, col3, col4, col5, col6 };
      hdr1 = new Heading("Header 1", Alignment.CENTRE, 
                         tableHeaderFont, 2);
      hdr2 = new Heading("Heading 2", Alignment.LEFT, 
                         tableHeaderFont, 2);
      hdr3 = new Heading("Header3", Alignment.RIGHT, 
                         tableHeaderFont, 4);

      //      hdrs = new Heading[] {hdr1, null, hdr2, null, hdr3};
      hdrs = new Heading[] {hdr1, hdr2, hdr3};

      table = doc.createTable(cols, hdrs);
      table.setHeaderBackground(Colour.GREY_80);
      table.setBorder(2);
      table.setColumnBorder(1);
      //      table.setColumnSpacing(0.5);
      table.setHeaderBorder(2, 2);
      table.setRowBorder(1);

      for (int i = 0; i < 100 ; i++)
      {
        String[] data = new String[] {"Data item " + i,
                                      "Data value", 
                                      Integer.toString(i),
                                      Integer.toString(i+1),
                                      Integer.toString(-i),
                                      Integer.toString(-i-1)};
        table.addRow(data);
      }


      table.close();

      // Create a new header
      header = doc.getHeader();

      headercols    = new Column[4];
      headercols[0] = new Column(2);
      headercols[1] = new Column(13, Alignment.CENTRE, titleFont);
      headercols[2] = new Column(2, Alignment.RIGHT);
      headercols[3] = new Column(2);

      Table headerTable = header.createTable(headercols);
      titlerow = new String[]{"", 
                              "Special Characters", 
                              "page"};
      headerTable.addCells(titlerow);
      TextWriter headerWriter = headerTable.getCellWriter(3);
      headerWriter.writeMacro(new PageNumber(" ", "", doc));
      headerTable.writeRow();
      headerTable.close();
      header.space(0.2);
      header.drawLine(1);
      header.space(0.2);

      // Create a new footer
      footer= doc.getFooter();
      footer.writeMacroLine(new PageNumber("page ", "", doc), 
                            footerFont, Alignment.CENTRE);

      doc.newPage();
      doc.writeLine("The following are special characters supported by " +
                    "orinoco and conform to the first 256 characters of "+
                    "the unicode character set.");
      doc.writeLine("They may be embedded into a PDF document by embedding "+
                    "the indicated octal character code into the java "+
                    "string");
      doc.newLine();


      Font hdrFont = new Font(Font.HELVETICA_BOLD, 12);
      Font charDataFont = new Font(Font.HELVETICA, 10);
      col1 = new Column(2.5, Alignment.CENTRE, charDataFont);
      col2 = new Column(2.5, Alignment.CENTRE, charDataFont);
      hdr1 = new Heading("Octal code", Alignment.CENTRE, 
                         hdrFont);
      hdr2 = new Heading("Character", Alignment.CENTRE, 
                         hdrFont);

      table = doc.createTable(new Column[] {col1, col2},
                              new Heading[] {hdr1, hdr2});
      table.setHeaderBackground(new Colour(230, 230, 230));
      table.setHeaderBorder(1, 1);
      table.setRowBorder(1);

      for (int charind = 128; charind < 256 ; charind++)
      {
        String octalCode = "\\\\" + Integer.toString(charind, 8);
        String character = "\\"+Integer.toString(charind,8);
        table.addRow(new String[] {octalCode, character});
      }

      table.close();

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





