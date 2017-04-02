package domain.mediator;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PDFGenerationOld {



    public static float drawTableDefault(PDPage page, PDPageContentStream pageContentStream, String[] headers, String[][] content, float[] colWidths) throws IOException {
        PDFont headerFont = PDType1Font.HELVETICA_BOLD_OBLIQUE;
        int headerTextSize = 14;
        PDFont contentFont = PDType1Font.HELVETICA_BOLD;
        int contentTextSize = 12;
        float startingY = page.getMediaBox().getHeight() - 100;
        float sideMargin = 100;
        return drawTable( page, pageContentStream, startingY, sideMargin, colWidths, headerFont, headerTextSize, contentFont, contentTextSize, headers, content );
    }

    public static float drawTable(PDPage page, PDPageContentStream contentStream, float startingY, float sideMargin, float[] colWidths,
                                  PDFont headerFont, int headerFontSize, PDFont contentFont, int contentFontSize,
                                  String[] headers, String[][] content) throws IOException {
        final int rowCount = content.length + 1;
        final int colCount = headers.length;
        final float rowHeight = 20f;
        final float tableWidth = page.getMediaBox().getWidth() - 2 * sideMargin;
        final float tableHeight = rowHeight * rowCount;
        final float colWidth = tableWidth / (float) colCount;
        final float cellMargin = 5f;

        drawTableContours( contentStream, startingY, sideMargin, tableWidth, rowHeight, colWidth, rowCount, colCount, colWidths );

        //now add the text
        float textx = sideMargin + cellMargin;
        float texty = startingY - 15;
        contentStream.setFont( headerFont, headerFontSize );
        for (int a = 0; a < headers.length; a++) {
            String headerText = headers[a];
            contentStream.beginText();
            contentStream.moveTextPositionByAmount( textx, texty );
            contentStream.showText( headerText );
            contentStream.endText();
            if ( a < headers.length - 1 ) {
                textx += colWidths[a];
            }
        }
        texty -= rowHeight;
        textx = sideMargin + cellMargin;


        contentStream.setFont( contentFont, contentFontSize );
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content[i].length; j++) {
                String text = content[i][j];
                contentStream.beginText();
                contentStream.moveTextPositionByAmount( textx, texty );
                contentStream.showText( text );
                contentStream.endText();
                if ( j < content[i].length - 1 ) {
                    textx += colWidths[j];
                }
            }
            texty -= rowHeight;
            textx = sideMargin + cellMargin;
        }
        return tableHeight;
    }

    public static void drawTableContours(PDPageContentStream contentStream, float startingY, float sideMargin, float tableWidth,
                                         float rowHeight, float colWidth, int rowCount, int colCount, float[] colWidths) throws IOException {
        //draw the rows
        float rowY = startingY;
        for (int a = 0; a <= rowCount; a++) {
            contentStream.moveTo( sideMargin, rowY );
            contentStream.lineTo( sideMargin + tableWidth, rowY );
            rowY -= rowHeight;
        }
        //draw the columns
        float rowX = sideMargin;
        float lineHeight = rowHeight * rowCount;
        for (int i = 0; i <= colCount; i++) {
            contentStream.moveTo( rowX, startingY );
            contentStream.lineTo( rowX, startingY - lineHeight );
            if ( i < colCount - 1 ) {
                rowX += colWidths[i];
                System.out.println( "Adding" + colWidths[i] );
            } else {
                rowX = sideMargin + tableWidth;
            }
        }
        contentStream.stroke();
    }

    public static float[] getColWidths(String[] headers, String[][] content, PDFont font) throws IOException {
        float[] colWidths = new float[headers.length];
        for (int a = 0; a < headers.length; a++) {
            float colWidthMax = font.getStringWidth( headers[a] );
            for (int b = 0; b < content.length; b++) {
//                float stringFontWidth = font.getStringWidth( content[b][a] );
                float stringFontWidth = content[b][a].length() * 2;
                if ( stringFontWidth > colWidthMax ) {
                    colWidthMax = stringFontWidth;
                }
            }

            //colWidths[a] = (float)Math.sqrt( colWidthMax ) + (float)Math.sqrt( Math.sqrt( colWidthMax ) );
            colWidths[a] = colWidthMax;
        }
        return colWidths;
    }
}
