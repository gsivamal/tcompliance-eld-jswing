package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;

public class ControllerHelper {

    private static Parent loginPage, startPage, hoSPage, changeStatusPage, adminPage;


    public static String getUtilCSSFile(){
        return ControllerHelper.class.getResource( "util.css" ).toExternalForm();
    }

    public static String getPageCSSFile(){
        return ControllerHelper.class.getResource( "page.css" ).toExternalForm();
    }

    public static Parent getLoginPage() {
        if(loginPage == null)
            loginPage = loadPage( "page/loginPage.fxml" );
        return loginPage;
    }

    public static Parent getStartPage(){
        if ( startPage == null ) {
            startPage = loadPage( "page/startPage.fxml" );
        }
        return startPage;
    }

    public static Parent getHoSPage(){
        if ( hoSPage == null ) {
            hoSPage = loadPage( "page/hosPage.fxml" );
        }
        return hoSPage;
    }

    public static Parent getAdminPage(){
        if ( adminPage == null ) {
            adminPage = loadPage( "page/adminPage.fxml" );
        }
        return adminPage;
    }

    public static Parent getChangeStatusPage(){
        return loadPage( "page/changeStatusPage.fxml" );
    }

    public static Parent getAddUserPage(){
        return loadPage( "page/newUserPage.fxml" );
    }

    public static Parent getAddEquipmentPage(){
        return loadPage( "page/newEquipmentPage.fxml" );
    }

    public static void clearAllPages(){
        loginPage = null;
        startPage = null;
        hoSPage = null;
        PageController.getInstance().setTitle( new Label("") );
    }

    public static boolean showConfirmationWindow(String title, String text) {
        Alert alert = new Alert( Alert.AlertType.CONFIRMATION, text );
        alert.setTitle( title );
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add( ControllerHelper.getUtilCSSFile() );
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    public static void showErrorWindow(String title, String text) {
        Alert alert = new Alert( Alert.AlertType.ERROR, text );
        alert.setTitle( title );
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add( ControllerHelper.getUtilCSSFile() );
        alert.showAndWait();
    }

    public static void showInformationWindow(String title, String text) {
        Alert alert = new Alert( Alert.AlertType.INFORMATION, text );
        alert.setTitle( title );
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add( ControllerHelper.getUtilCSSFile() );
        alert.showAndWait();
    }

    private static Parent loadPage(String fileName){
        try {
            FXMLLoader loader = new FXMLLoader( ControllerHelper.class.getResource( fileName ) );
            return loader.load();
        } catch (IOException e) {
            ControllerHelper.showErrorWindow( "Error", "There was an error accessing page" );
            e.printStackTrace();
            return null;
        }
    }

    public static void alignHeights(Label labelNode, Control controlNode, double initialSize) {
        labelNode.setPrefHeight( initialSize );
        controlNode.heightProperty().addListener( (observable, oldValue, newValue) -> {
            labelNode.setPrefHeight( newValue.doubleValue() );
        } );
    }


}
