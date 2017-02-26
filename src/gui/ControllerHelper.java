package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;

import java.io.IOException;

public class ControllerHelper {

    private static Parent loginPage, startPage, hoSPage, changeStatusPage;


    public static String getUtilCSSFile(){
        return ControllerHelper.class.getResource( "util.css" ).toExternalForm();
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

    public static Parent getChangeStatusPage(){
        return loadPage( "page/changeStatusPage.fxml" );
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
        dialogPane.getStylesheets().add( ControllerHelper.class.getResource("util.css").toExternalForm());
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    public static void showErrorWindow(String title, String text) {
        Alert alert = new Alert( Alert.AlertType.ERROR, text );
        alert.setTitle( title );
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add( ControllerHelper.class.getResource("util.css").toExternalForm());
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


}
