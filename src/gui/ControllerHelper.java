package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

import java.io.IOException;

public class ControllerHelper {

    private static Parent loginPage, startPage;
    static {
        loginPage = loadPage( "page/loginPage.fxml" );
        startPage = loadPage( "page/startPage.fxml" );
    }


    public static Parent getLoginPage() {
        return loginPage;
    }

    public static Parent getStartPage(){
        return startPage;
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
            Parent root = loader.load();
            return root;
        } catch (IOException e) {
            ControllerHelper.showErrorWindow( "Error", "There was an error accessing page" );
            return null;
        }
    }
}
