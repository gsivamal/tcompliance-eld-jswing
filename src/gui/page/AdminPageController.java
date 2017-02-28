package gui.page;

import gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class AdminPageController {



    private PageController pageController = PageController.getInstance();
    @FXML
    private TableView tableUserList;
    @FXML
    private TableColumn tableColumnID, tableColumnFirstName, tableColumnLastName, tableColumnLicNumber, tableColumnUsername, tableColumnPassword, tableColumnStatus;
    @FXML
    private Button buttonNew, buttonDisable, buttonDelete, buttonSaveNew;
    @FXML
    private Label labelLicNumber, labelID, labelFirstName, labelMiddleName, labelLastName, labelStatus, labelIssuedState, labelUsername, labelIssuedCountry, labelPassword;
    @FXML
    private TextField textFieldLicNumber, textFieldStatus, textFieldIssuedState, textFieldIssuedCountry, textFieldUsername, textFieldPassword,  textFieldFirstName, textFieldID, textFieldMiddleName, textFieldLastName;

    @FXML
    private void initialize(){
        pageController.setTitle( new Label( "Driver/User List" ) );
        tableColumnID.prefWidthProperty().bind( tableUserList.widthProperty().multiply( 0.06 ) );
        tableColumnFirstName.prefWidthProperty().bind( tableUserList.widthProperty().multiply( 0.2 ) );
        tableColumnLastName.prefWidthProperty().bind( tableUserList.widthProperty().multiply( 0.2 ) );
        tableColumnLicNumber.prefWidthProperty().bind( tableUserList.widthProperty().multiply( 0.12 ) );
        tableColumnUsername.prefWidthProperty().bind( tableUserList.widthProperty().multiply( 0.1699 ) );
        tableColumnPassword.prefWidthProperty().bind( tableUserList.widthProperty().multiply( 0.15 ) );
        tableColumnStatus.prefWidthProperty().bind( tableUserList.widthProperty().multiply( 0.1 ) );

        group( labelID, textFieldID );
        group( labelFirstName, textFieldFirstName );
        group( labelLastName, textFieldLastName );
        group( labelMiddleName, textFieldMiddleName );
        group( labelLicNumber, textFieldLicNumber );
        group( labelStatus, textFieldStatus );
        group( labelIssuedState, textFieldIssuedState );
        group( labelIssuedCountry, textFieldIssuedCountry );
        group( labelUsername, textFieldUsername );
        group( labelPassword, textFieldPassword );
    }

    private void group(Label label, TextField textField) {
        textField.heightProperty().addListener( (observable, oldValue, newValue) -> {
            label.setPrefHeight( newValue.doubleValue() );
        } );
    }

    @FXML
    private void buttonNewClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void buttonDisableClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void buttonDeleteClicked(ActionEvent actionEvent) {
    }

    @FXML
    private void buttonSaveNewClicked(ActionEvent actionEvent) {
    }
}
