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

import java.sql.SQLException;
import java.time.LocalDateTime;

public class StartApplication extends Application {

    public static void main(String[] args) {
        try {
            DbUtil.initializeTables( DbUtil.getConnection() );
        } catch (SQLException e) {
            e.printStackTrace();
        }
/*        Mediator mediator = Mediator.getInstance();
        User john = UserFactory.getInstance().createUser( "John", "test", "test", "John", "", "Peter", "12345", "Active", "LA", "US", true, null );
        Service service = ServiceFactory.getInstance().createService( john, null );
        john.setService( service );
        service.setNotificationMessage( "There is a log update pending approval" );
        RulesList rules = new RulesList();
        rules.add( new Rule( "Rule 1" ) );
        rules.add( new Rule( "Rule 2" ) );
        rules.add( new Rule( "Rule 3" ) );
        rules.add( new Rule( "Rule 4" ) );
        service.setRules( rules );
        mediator.addService(service);
        mediator.addUser( john );*/
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
