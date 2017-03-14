package gui.page;

import gui.ControllerHelper;
import gui.PageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import model.Mediator;
import model.User;

public class LoginPageController {


    @FXML
    private Parent loginPage;
    @FXML
    private Label labelUsername, labelPassword;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private CheckBox checkBoxAdminLogin;

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
        loginPage.setOnKeyPressed( event -> {
            if ( event.getCode() == KeyCode.ENTER ) {
                login();
            }
        } );

    }

    private String getUserName(){
        return textFieldUsername.getText();
    }

    private String getPassword(){
        return passwordFieldPassword.getText();
    }

    @FXML
    private void buttonLoginClicked(ActionEvent actionEvent) {
        login();
    }



    private void login(){
        User user = Mediator.getInstance().getUserList().getUser( getUserName(), getPassword() );
        if( user == null){
            ControllerHelper.showErrorWindow( "No user", "Make sure you entered right username and password\n\n\n\n\n" );
            return;
        }
        pageController.login( user, isAdminLogin() );
    }

    private boolean isAdminLogin(){
        return checkBoxAdminLogin.isSelected();
    }
}
