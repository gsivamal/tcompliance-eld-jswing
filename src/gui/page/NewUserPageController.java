package gui.page;

import gui.ControllerHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import domain.model.Driver;
import domain.model.Mediator;
import domain.model.factory.DriverFactory;

public class NewUserPageController {

    @FXML
    private PasswordField passwordFieldPassword, passwordFieldConfirmPassword;
    @FXML
    private Parent newUserPage;
    @FXML
    private Label labelLicNumber, labelID, labelFirstName, labelMiddleName, labelLastName, labelStatus, labelIssuedState, labelUsername, labelIssuedCountry, labelPassword, labelConfirmPassword;
    @FXML
    private TextField textFieldLicNumber, textFieldIssuedState, textFieldIssuedCountry, textFieldUsername, textFieldFirstName, textFieldID, textFieldMiddleName, textFieldLastName;
    @FXML
    private ComboBox<String> comboBoxStatus;
    @FXML
    private Button buttonSaveNew;

    @FXML
    private void initialize(){
        group( labelID, textFieldID );
        group( labelFirstName, textFieldFirstName );
        group( labelLastName, textFieldLastName );
        group( labelMiddleName, textFieldMiddleName );
        group( labelLicNumber, textFieldLicNumber );
        group( labelStatus, comboBoxStatus );
        group( labelIssuedState, textFieldIssuedState );
        group( labelIssuedCountry, textFieldIssuedCountry );
        group( labelUsername, textFieldUsername );
        group( labelPassword, passwordFieldPassword );
        group( labelConfirmPassword, passwordFieldConfirmPassword );
        sizeLabels( labelID, labelFirstName, labelLastName, labelMiddleName, labelLicNumber, labelStatus, labelIssuedState, labelIssuedCountry, labelUsername, labelPassword, labelConfirmPassword );
    }

    private void group(Label label, TextField textField) {
        textField.heightProperty().addListener( (observable, oldValue, newValue) -> {
            label.setPrefHeight( newValue.doubleValue() );
        } );
    }

    private void group(Label label, ComboBox comboBox) {
        comboBox.heightProperty().addListener( (observable, oldValue, newValue) -> {
            label.setPrefHeight( newValue.doubleValue() );
        } );
    }

    private void sizeLabels(Label... labels) {
        double maxWidth = 150;
        for (Label label : labels) {
            if ( label.getWidth() > maxWidth ) {
                maxWidth = label.getWidth();
            }
        }
        for (Label label : labels) {
            label.setPrefWidth( maxWidth );
        }
    }


    @FXML
    private void buttonSaveNewClicked(ActionEvent actionEvent) {
        if ( ControllerHelper.showConfirmationWindow( "Save", "Are you sure you want to save this user?" ) ) {
            try {
                saveUser();
                close();
            } catch (Exception e) {
                ControllerHelper.showErrorWindow( "Error", e.getMessage() );
            }
        }
    }

    @FXML
    private void buttonCancelNewClicked(ActionEvent actionEvent) {
        if ( ControllerHelper.showConfirmationWindow( "Cancel", "Are you sure you want to cancel user saving?" ) ) {
            close();
        }
    }

    private void close(){
        ( (Stage)(newUserPage.getScene().getWindow()) ).close();
    }

    private void saveUser(){
        Mediator.getInstance().addDriver( getUser() );
    }


    public Driver getUser(){
        String ID = textFieldID.getText();
        String firstName = textFieldFirstName.getText();
        String middleName = textFieldMiddleName.getText();
        String lastName = textFieldLastName.getText();
        String licNumber = textFieldLicNumber.getText();
        String status = comboBoxStatus.getValue();
        String issuedCountry = textFieldIssuedCountry.getText();
        String issuedState = textFieldIssuedCountry.getText();
        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();
        String confirmPassword = passwordFieldConfirmPassword.getText();
        return DriverFactory.getInstance().getDriver( username, password, confirmPassword, firstName, middleName, lastName, licNumber, status, issuedState, issuedCountry, false );
    }
}
