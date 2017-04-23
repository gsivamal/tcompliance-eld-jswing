package domain.mediator;

import domain.mediator.easytable.Cell;
import domain.mediator.easytable.Row;
import domain.mediator.easytable.Table;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;

public class InformationTable {

    private Table.TableBuilder tableBuilder;

    private Row.RowBuilder headerRowBuilder;
    private Row.RowBuilder contentRowBuilder;

    private PDFont headerFont;
    private int headerFontSize;
    private Color headerRowColor;
    private Color headerRowBackgroundColor;

    private PDFont contentFont;
    private int contentFontSize;
    private Color contentRowColor;
    private Color contentRowBackgroundColor;

    public InformationTable(int [] colWidths, PDFont headerFont, int headerFontSize, Color headerRowColor, Color headerRowBackgroundColor,
                            PDFont contentFont, int contentFontSize, Color contentRowColor, Color contentRowBackgroundColor) {
        this.headerFont = headerFont;
        this.headerFontSize = headerFontSize;
        this.headerRowColor = headerRowColor;
        this.headerRowBackgroundColor = headerRowBackgroundColor;
        this.contentFont = contentFont;
        this.contentFontSize = contentFontSize;
        this.contentRowColor = contentRowColor;
        this.contentRowBackgroundColor = contentRowBackgroundColor;
        initializeRowVariables();
        tableBuilder = new Table.TableBuilder();
        tableBuilder.setFont( headerFont );
        tableBuilder.setFontSize( headerFontSize );
        for (int colWidth : colWidths) {
            tableBuilder.addColumnOfWidth( colWidth );
        }
    }

    public InformationTable(int[] colWidths, Color headerRowBackgroundColor, Color contentRowBackgroundColor) {
        this( colWidths, PDType1Font.HELVETICA, 6, Color.BLACK, headerRowBackgroundColor,
                PDType1Font.HELVETICA, 6, Color.BLACK, contentRowBackgroundColor );
    }

    public void insertCellToRow(String header, String content) {
        headerRowBuilder.add( Cell.withText( header ) );
        contentRowBuilder.add( Cell.withText( content ) );
    }

    public void newRow() {
        Row headerRow = headerRowBuilder.build();
        Row contentRow = contentRowBuilder.build();
        tableBuilder.addRow( headerRow );
        tableBuilder.addRow( contentRow );
        initializeRowVariables();
    }

    private void initializeRowVariables(){
        headerRowBuilder = new Row.RowBuilder();
        contentRowBuilder = new Row.RowBuilder();
        headerRowBuilder.setBackgroundColor( headerRowBackgroundColor );
        contentRowBuilder.setBackgroundColor( contentRowBackgroundColor );
    }


    public Table buildTable(){
        return tableBuilder.build();
    }


}
