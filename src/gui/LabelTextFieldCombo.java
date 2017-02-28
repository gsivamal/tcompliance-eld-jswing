package gui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LabelTextFieldCombo {

    private HBox holder;
    private Label label;
    private TextField textField;

    public LabelTextFieldCombo(String labelText, int spacing) {

        this.label = new Label( labelText );
        this.textField = new TextField();
        this.textField.heightProperty().addListener( (observable, oldValue, newValue) -> {
            label.setPrefHeight( newValue.doubleValue() );
        } );
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }
}
