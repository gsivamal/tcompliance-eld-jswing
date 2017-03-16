package gui;

import dao.DbUtil;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Clock;
import model.GPSLocation;
import model.Mediator;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class StartApplication extends Application {

    public static void main(String[] args) {
        try {
            DbUtil.initializeTables( DbUtil.getConnection() );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        GPSLocation.updateLatestGPSLocation( "Austin AX", 15.22, 16.34 );
        Mediator mediator = Mediator.getInstance();
        //Uncomment for first run.
/*        Driver john = DriverFactory.getInstance().getDriver( "John", "test", "test", "John", "", "Peter", "12345", "Active", "LA", "US", true );
        mediator.addDriver( john );*/


        launch();
    }

    private static PageController pageController;
    private boolean clockRun = true;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Thread currentTimeThread = new Thread( () -> {
            while(clockRun) {
                Platform.runLater( () -> {
                    Clock.setCurrentTime( LocalDateTime.now() );
                });
                try {
                    Thread.sleep( 1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } );
        currentTimeThread.start();
        Image appIcon = new Image( "window_icon.png" );
        primaryStage.getIcons().add( appIcon );

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "page.fxml" ) );
        Parent root = loader.load();
        pageController = loader.getController();
        pageController.setPage( ControllerHelper.getLoginPage() );
        primaryStage.setTitle("Driver's system");
        primaryStage.setScene( new Scene( root, 960, 670 ) );
        primaryStage.show();
    }

    @Override
    public void stop(){
        clockRun = false;
    }

    public static PageController getPageController(){
        return pageController;
    }
}
