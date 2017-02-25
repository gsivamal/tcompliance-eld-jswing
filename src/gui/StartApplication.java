package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    private static PageController pageController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader( getClass().getResource( "page.fxml" ) );
        Parent root = loader.load();
        pageController = loader.getController();
        pageController.setPage( ControllerHelper.getLoginPage() );
        primaryStage.setTitle("Driver's system");
        primaryStage.setScene( new Scene( root, 960, 610 ) );
        primaryStage.show();
    }

    public static PageController getPageController(){
        return pageController;
    }
}
