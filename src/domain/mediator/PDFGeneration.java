package domain.mediator;

import domain.mediator.easytable.Cell;
import domain.mediator.easytable.Row;
import domain.mediator.easytable.Table;
import domain.mediator.easytable.TableDrawer;
import domain.model.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class PDFGeneration {


    public static void printTodayPDF(List<Logbook> logbookList, Driver driver, Load load,
                                     GPSLocation currentLocation)
    throws IOException{

        Table logbookTable = LogbookList.drawLogbookTable( logbookList );

        File saveFile = new File( "todayTable.pdf" );
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage( page );
        PDPageContentStream pageContentStream = new PDPageContentStream( document, page );

        float contentPositionX = 40;
        float contentPositionY = page.getMediaBox().getHeight() - 50;

        PDFGeneration.printHeaderAndDataTable( pageContentStream, contentPositionX,
                contentPositionY, driver, load, currentLocation, logbookTable );

        pageContentStream.close();

        document.save( saveFile );
        document.close();
        PDFGeneration.openFile( saveFile );
    }

    public static void printRecapPDF(List<Recap> recapList, Driver driver, Load load,
                                     GPSLocation currentLocation) throws IOException {

        Table recapTable = RecapList.drawRecapTable( recapList );

        File saveFile = new File( "recapTable.pdf" );
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage( page );
        PDPageContentStream pageContentStream = new PDPageContentStream( document, page );

        float contentPositionX = 40;
        float contentPositionY = page.getMediaBox().getHeight() - 50;

        PDFGeneration.printHeaderAndDataTable( pageContentStream, contentPositionX,
                contentPositionY, driver, load, currentLocation, recapTable );

        pageContentStream.close();

        document.save( saveFile );
        document.close();
        PDFGeneration.openFile( saveFile );
    }

    public static float printHeaderAndDataTable(PDPageContentStream contentStream,
                                                float startingX, float startingY,
                                                Driver driver, Load load,
                                                GPSLocation currentLocation, Table dataTable)
    throws IOException {

        float contentPositionX = startingX;
        float contentPositionY = startingY;
        float headerHeight = PDFGeneration.printHeader( contentStream, contentPositionX,
                contentPositionY,
                driver, load, currentLocation );
        contentPositionY -= headerHeight + 50;
        new TableDrawer( contentStream, dataTable, contentPositionX, contentPositionY  ).draw();

        return headerHeight + dataTable.getHeight();
    }


    public static float printHeader(PDPageContentStream contentStream, float startPositionX,
                                   float startPositionY, Driver driver, Load load,
                                   GPSLocation currentLocation) throws IOException {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate recordDate = LocalDate.now();
        Table headerTable = PDFGeneration.printHeaderTable( zoneId, recordDate,
                "123456", driver, load, currentLocation );

        contentStream.beginText();
        contentStream.newLineAtOffset( startPositionX, startPositionY );
        String headerText = String.format( "Logbook for %s %s",
                driver.getFirstName(), driver.getLastName() );
        contentStream.setFont( PDType1Font.TIMES_ROMAN, 12 );
        contentStream.setNonStrokingColor( Color.BLACK );
        contentStream.showText( headerText );
        contentStream.endText();
        startPositionY -= 30;
        new TableDrawer( contentStream, headerTable, startPositionX, startPositionY ).draw();

        return headerTable.getHeight() + 50;
    }

    public static Table printTable(int[] colWidths, String [] headers,
                                   String [][] content,
                                   Cell.HorizontalAlignment alignment) throws IOException {
        Table.TableBuilder tableBuilder = new Table.TableBuilder();
        for (int colWidth : colWidths) {
            tableBuilder.addColumnOfWidth( colWidth );
        }
        tableBuilder.setFont( PDType1Font.TIMES_ROMAN ).setFontSize( 10 );

        Row.RowBuilder headRowBuilder = new Row.RowBuilder();
        for (String headerText : headers) {
            headRowBuilder.add(
                    Cell.withText( headerText ).
                    setHorizontalAlignment( alignment ).
                    setBackgroundColor( PDFGeneration.getTableBackgroundColor() )
            );
        }
        tableBuilder.addRow( headRowBuilder.build() );

        for (int rowIndex = 0; rowIndex < content.length; rowIndex++){
            String [] contentRow = content[rowIndex];
            Row.RowBuilder contentRowBuilder = new Row.RowBuilder();
            Color rowBackgroundColor = rowIndex % 2 == 0 ? Color.WHITE : getTableSecondaryBackgroundColor();
            for (int rowElementIndex = 0; rowElementIndex < contentRow.length; rowElementIndex++) {
                String contentRowText = contentRow[rowElementIndex];
                Cell rowCell = Cell.withText( contentRowText ).
                        setHorizontalAlignment( alignment );
                contentRowBuilder.add( rowCell );
            }
            contentRowBuilder.setBackgroundColor( rowBackgroundColor );
            tableBuilder.addRow( contentRowBuilder.build() );
        }

        return tableBuilder.build();
    }

    public static Color getTableBackgroundColor(){
        return Color.decode( "#7296C1" );
    }

    public static Color getTableSecondaryBackgroundColor(){
        return Color.decode( "#E5E5E5" );
    }

    public static Table printHeaderTable(ZoneId zoneId, LocalDate recordDate, String usDOTNumber, Driver driver, Load load, GPSLocation currentLocation) {
        InformationTable informationTable = new InformationTable( new int[]{80, 90, 90, 90, 90, 90},
                PDFGeneration.getTableBackgroundColor(), Color.WHITE );

        informationTable.insertCellToRow( "Record Date", Clock.localDateToString( recordDate ) );
        informationTable.insertCellToRow( "USDOT #", usDOTNumber );
        informationTable.insertCellToRow( "Driver License Number", driver.getLicNumber() );
        informationTable.insertCellToRow( "Driver License State", driver.getIssuedState() );
        informationTable.insertCellToRow( "ELD ID", "123456" ); // TODO fix
        String trailerID = load == null ? "" : String.valueOf( load.getTrailer1().getID() );
        informationTable.insertCellToRow( "Trailer ID",  trailerID );
        informationTable.newRow();
        informationTable.insertCellToRow( "Time Zone", zoneId.toString() );
        informationTable.insertCellToRow( "Driver Name", String.format( "%s, %s", driver.getLastName(), driver.getFirstName() ) );
        informationTable.insertCellToRow( "Co-Driver Name", "" ); // TODO fix
        informationTable.insertCellToRow( "ELD Manufacturer", "Acme" ); //TODO Fix
        String blNumber = load == null ? "" : String.valueOf( load.getBlNumber() );
        informationTable.insertCellToRow( "Shipping ID", blNumber );
        informationTable.insertCellToRow( "Data Diagnostic Indicators", "No" ); //TODO Fix
        informationTable.newRow();
        informationTable.insertCellToRow( "24 Period Starting Time", "Midnight" ); // TODO Fix
        informationTable.insertCellToRow( "Driver ID", String.valueOf( driver.getID() ) );
        informationTable.insertCellToRow( "Co-Driver ID", "" ); // TODO Fix
        String truckID = load == null ? "" : String.valueOf( load.getTruck().getID() );
        informationTable.insertCellToRow( "Truck Tractor ID", truckID );
        informationTable.insertCellToRow( "Unidentified Driver Records", "No" ); //TODO Fix
        informationTable.insertCellToRow( "ELD Malfunction Indications", "No" ); //TODO Fix
        informationTable.newRow();
        informationTable.insertCellToRow( "Carrier", "Acme" ); // TODO Fix
        informationTable.insertCellToRow( "Start End Odometer", "12345-54321" ); // TODO Fix
        informationTable.insertCellToRow( "Miles Today", "420" ); //TODO Fix
        String truckVIN = load == null ? "" : load.getTruck().getVin();
        informationTable.insertCellToRow( "Truck Tractor VIN", truckVIN );
        informationTable.insertCellToRow( "Exempt Driver Status", "No" ); //TODO Fix
        informationTable.insertCellToRow( "Start End Engine Hours", "123-321" ); //TODO Fix
        informationTable.newRow();
        informationTable.insertCellToRow( "Current Location", "" + currentLocation );
        informationTable.insertCellToRow( "", "" );
        informationTable.insertCellToRow( "File Comment", "" ); // TODO Fix
        informationTable.insertCellToRow( "", "" );
        informationTable.insertCellToRow( "", "" );
        informationTable.insertCellToRow( "Print/Display Date", Clock.localDateToString( LocalDate.now() ) );
        informationTable.newRow();
        return informationTable.buildTable();
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
