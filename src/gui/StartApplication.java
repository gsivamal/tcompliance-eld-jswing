package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Clock;
import model.Service;
import model.User;
import model.UserList;

import java.time.LocalDateTime;

public class StartApplication extends Application {

    public static void main(String[] args) {
        UserList userList = UserList.getInstance();
        Service service = new Service( "John", "" );
        service.setNotificationMessage( "There is a log update pending approval" );
        User john = new User("1",  "John", "test", "John", "", "Peter", "12345", "Active", "LA", "US", true, service );
        userList.add( john );
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
        pageController.setPage( ControllerHelper.getAdminPage() );
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
