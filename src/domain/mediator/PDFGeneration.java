package domain.mediator;

import domain.mediator.easytable.Cell;
import domain.mediator.easytable.Row;
import domain.mediator.easytable.Table;
import domain.mediator.easytable.TableDrawer;
import domain.model.Logbook;
import domain.model.LogbookList;
import domain.model.Recap;
import domain.model.RecapList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PDFGeneration {

    public static Table drawTable(int[] colWidths, String [] headers, String [][] content, Cell.HorizontalAlignment alignment) throws IOException {
        Table.TableBuilder tableBuilder = new Table.TableBuilder();
        for (int colWidth : colWidths) {
            tableBuilder.addColumnOfWidth( colWidth );
        }
        tableBuilder.setFont( PDType1Font.HELVETICA_BOLD ).setFontSize( 12 );

        Row.RowBuilder headRowBuilder = new Row.RowBuilder();
        for (String headerText : headers) {
            headRowBuilder.add( Cell.withText( headerText ).setHorizontalAlignment( alignment ).withAllBorders() );
        }
        tableBuilder.addRow( headRowBuilder.build() );

        for (String[] contentRow : content) {
            Row.RowBuilder contentRowBuilder = new Row.RowBuilder();
            for (String contentRowText : contentRow) {
                contentRowBuilder.add( Cell.withText( contentRowText ).setHorizontalAlignment( alignment ).withAllBorders() );
            }
            tableBuilder.addRow( contentRowBuilder.build() );
        }

        return tableBuilder.build();
    }

    public static void printTodayPDF(List<Logbook> list) throws IOException {
        File file = new File( "todayTable.pdf" );
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage( page );
        PDPageContentStream pageContentStream = new PDPageContentStream( document, page );


        Table table = LogbookList.drawLogbookTable( list );
        new TableDrawer( pageContentStream, table, 40, 700 ).draw();


        pageContentStream.close();
        document.save( file );
        document.close();
        PDFGeneration.openFile( file );
    }

    public static void printRecapPDF(List<Recap> list) throws IOException{
        File saveFile = new File( "recapTable.pdf" );
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage( page );
        PDPageContentStream pageContentStream = new PDPageContentStream( document, page );


        Table table = RecapList.drawRecapTable( list );
        new TableDrawer( pageContentStream, table, 40, 700 ).draw();


        pageContentStream.close();
        document.save( saveFile );
        document.close();
        PDFGeneration.openFile( saveFile );
    }

    private static void openFile(File file) {
        if ( Desktop.isDesktopSupported() ) {
            new Thread( () -> {
                try {
                    Desktop.getDesktop().open( file );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } ).start();
        }
    }
}
