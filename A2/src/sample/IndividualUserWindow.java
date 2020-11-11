package sample;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IndividualUserWindow implements IndividualUserWindowInterface {

    private TextField findUserToFollowTextField;
    private TextField tweetMessageTextField;
    private PopUpAlert alert;
    private User currentUser;
    private ListView<String> listOfFollowing;


    public IndividualUserWindow(IndividualUser user, Stage primaryStage)
    {
        currentUser = user;

        Label followExplainLabel = new Label("Enter the user id you want to follow");
        HBox findToFollowContainer = findToFollow();
        VBox currentFollowingListContainer = currentFollowingList(user);
        VBox followContainer = new VBox(followExplainLabel, findToFollowContainer, currentFollowingListContainer);
        followContainer.setAlignment(Pos.CENTER);
        followContainer.setPadding(new Insets(20));

        Label tweetExplainLabel = new Label("Enter the message you want to tweet");
        VBox tweetMessageContainer = tweetMessageLayout(user);
        VBox tweetContainer = new VBox(tweetExplainLabel, tweetMessageContainer);
        tweetContainer.setAlignment(Pos.CENTER);
        tweetContainer.setPadding(new Insets(20));

        VBox overallContainer = new VBox(followContainer, tweetContainer);
        overallContainer.setAlignment(Pos.CENTER);
        overallContainer.setPadding(new Insets(10));

        Scene secondScene = new Scene(overallContainer, 400, 500);

        // New window (Stage)
        Stage newWindow = new Stage();
        newWindow.setTitle("Mini Twitter - User: " + user.getId());
        newWindow.setScene(secondScene);

        // Set position of second window, related to primary window.
        newWindow.setX(primaryStage.getX() + 200);
        newWindow.setY(primaryStage.getY() + 100);

        newWindow.show();
    }

    public VBox tweetMessageLayout(IndividualUser user)
    {
        tweetMessageTextField = new TextField();
        Button postTweetButton = new Button("Post Tweet");
        postTweetButton.setOnAction(new ButtonClickHandler());

        HBox postContainer = new HBox(tweetMessageTextField, postTweetButton);

        ListView<String> listOfMessage = new ListView<>();
        listOfMessage.setItems(user.getNewsFeed());

        return new VBox(postContainer, listOfMessage);
    }

    @Override
    public void follow(IndividualUser user) {
        ((IndividualUser)currentUser).setFollowing(user);
        alert = new PopUpAlert("You are now following "+ user.getId());
        listOfFollowing.getItems().add(user.getId());
    }

    @Override
    public void tweet(String message) {
        ((IndividualUser)currentUser).tweet(message);
    }

    public VBox currentFollowingList(IndividualUser user){
        listOfFollowing = new ListView<>();
        listOfFollowing.setItems(user.getFollowing());
        return new VBox(listOfFollowing);
    }

    public HBox findToFollow()
    {
        findUserToFollowTextField = new TextField();
        Button followUserButton = new Button("Follow User");
        followUserButton.setOnAction(new ButtonClickHandler());

        return new HBox(findUserToFollowTextField, followUserButton);
    }

    private class ButtonClickHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Button currentEvent = (Button)event.getSource();

            //action for following a user
            if(currentEvent.getText().equals("Follow User"))
            {
                String id = findUserToFollowTextField.getText().trim();
                User userToFollow = TreeViewObject.getTreeViewObject().getUser(id);

                if(!id.isEmpty())
                {
                    if(userToFollow == null)
                    {
                        alert = new PopUpAlert("User not found.");
                    }
                    else if(userToFollow instanceof UserGroup){
                        alert = new PopUpAlert("This is not a user");
                    }
                    else{
                        follow((IndividualUser) userToFollow);
                    }
                } else
                {
                    alert = new PopUpAlert("Please enter an ID.");
                }
                findUserToFollowTextField.clear();
            }


            //action for posting tweet
            if(currentEvent.getText().equals("Post Tweet"))
            {
                String message = tweetMessageTextField.getText().trim();
                tweet(message);
                tweetMessageTextField.clear();
            }
        }
    }
}


