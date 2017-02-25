package gui;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PageController {

    @FXML
    private Parent page;

    private Parent contentPage = new Pane();
    private Node titleNode;

    @FXML
    private BorderPane header, footer;

    @FXML
    private StackPane contentPageWrapper, headerTitleWrapper;

    @FXML
    private void initialize(){
        contentPageWrapper.getChildren().add( contentPage );
    }

    public void setPage(Parent page) {
        FadeTransition out = new FadeTransition( Duration.millis(1000), this.contentPage);
        out.setFromValue( 1 );
        out.setToValue( 0 );
        out.play();
        this.contentPage = page;
        contentPageWrapper.getChildren().clear();
        contentPageWrapper.getChildren().add( page );
        FadeTransition in = new FadeTransition( Duration.millis( 1000 ), this.contentPage );
        in.setFromValue( 0 );
        in.setToValue( 1 );
        in.play();
    }

    public void setTitle(Node titleNode) {
        FadeTransition out = new FadeTransition( Duration.millis(1000), this.headerTitleWrapper);
        out.setFromValue( 1 );
        out.setToValue( 0 );
        out.play();
        this.titleNode = titleNode;
        headerTitleWrapper.getChildren().clear();
        headerTitleWrapper.getChildren().add( titleNode );
        FadeTransition in = new FadeTransition( Duration.millis( 1000 ), this.headerTitleWrapper );
        in.setFromValue( 0 );
        in.setToValue( 1 );
        in.play();
    }


    public static PageController getInstance(){
        return StartApplication.getPageController();
    }

    @FXML
    private void menuItemSwitchAccountClicked(ActionEvent actionEvent) {
        if ( ControllerHelper.showConfirmationWindow( "Switch Account", "Are you sure you want to switch account?" ) ) {
            setPage( ControllerHelper.getLoginPage() );
        }
    }

    @FXML
    public void menuItemLogOutClicked(ActionEvent actionEvent) {
        closeApplication();
    }

    public void closeApplication(){
        if ( ControllerHelper.showConfirmationWindow( "Exit", "Are you sure you want to exit the application?" ) ) {
            ((Stage) (page.getScene().getWindow())).close();
        }
    }
}
