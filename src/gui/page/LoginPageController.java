package gui.page;

import gui.ControllerHelper;
import gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginPageController {


    @FXML
    private Label labelUsername, labelPassword;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;

    private PageController pageController = PageController.getInstance();

    @FXML
    private void initialize(){
        labelUsername.setPrefHeight( textFieldUsername.getPrefHeight() );
        textFieldUsername.heightProperty().addListener( (observable, oldValue, newValue) -> {
            labelUsername.setPrefHeight( newValue.doubleValue() );
        } );
        labelPassword.setPrefHeight( passwordFieldPassword.getHeight() );
        passwordFieldPassword.heightProperty().addListener( (observable, oldValue, newValue) -> {
            labelPassword.setPrefHeight( newValue.doubleValue() );
        } );
    }

    @FXML
    private void buttonLoginClicked(ActionEvent actionEvent) {
        pageController.setPage( ControllerHelper.getStartPage() );
    }
}
