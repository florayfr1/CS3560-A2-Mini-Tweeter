package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;


public class AdminWindow {

    private static AdminWindow adminWindow;
    private TextField userIdTextField;
    private TextField groupIdTextField;
    private ArrayList<String> userGroup;
    private PopUpAlert alert;
    private Label resultLabel;

    public static AdminWindow getInstance(Stage primaryStage){
        if(adminWindow == null)
        {
            adminWindow = new AdminWindow(primaryStage);
        }
        return adminWindow;
    }

    private AdminWindow(Stage primaryStage)
    {
        primaryStage.setTitle("Mini Twitter - Admin");

        //provide the tree container

        VBox treeContainer = TreeViewObject.getTreeViewObject().render();
        treeContainer.setAlignment(Pos.CENTER);
        treeContainer.setPadding(new Insets(20));

        VBox editTreeContainer = editTree();

        //provide container for open user view button
        Label openUserWindowLabel = new Label("Select a user to open user window");
        Button openUserViewButton = new Button("Open User View");
        openUserViewButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Action for open user view

                TreeItem<String> selectedItem = TreeViewObject.getTreeViewObject().getTreeView()
                        .getSelectionModel().getSelectedItem();

                if(selectedItem!=null && selectedItem.isLeaf()) {
                    User selectedUser = TreeViewObject.getTreeViewObject().getIdToUser().get(selectedItem.getValue());
                    if(selectedUser instanceof IndividualUser) {
                        new IndividualUserWindow((IndividualUser) selectedUser, primaryStage);
                    } else {
                        alert = new PopUpAlert("This is not a user.");
                    }
                } else {
                    alert = new PopUpAlert("Please select a user.");
                }
            }
        });
        VBox openUserViewContainer = new VBox(openUserWindowLabel, openUserViewButton);
        openUserViewContainer.setPadding(new Insets(20));

        //Label for displaying the result of button click
        resultLabel = new Label("The following buttons will display the status of users in the system.");

        //create the show user total button
        Button showUserTotalButton = new Button("Show User Total");
        showUserTotalButton.setOnAction(new ButtonClickHandler());

        //create the show group total button
        Button showGroupTotalButton = new Button("Show Group Total");
        showGroupTotalButton.setOnAction(new ButtonClickHandler());

        //create the show message total button
        Button showMessageTotalButton = new Button("Show Messages Total");
        showMessageTotalButton.setOnAction(new ButtonClickHandler());

        //create the show user total button
        Button showPositivePercentageButton = new Button("Show Positive Percentage");
        showPositivePercentageButton.setOnAction(new ButtonClickHandler());

        //create the verfication button
        Button validateIDButton = new Button("Validate IDs");
        validateIDButton.setOnAction(new ButtonClickHandler());

        Button showLastUpdateButton = new Button("Show Last Updated ID");
        showLastUpdateButton.setOnAction(new ButtonClickHandler());

        //Creating a Grid Pane
        GridPane bottomFourButtonsContainer = new GridPane();
        bottomFourButtonsContainer.setPadding(new Insets(10));
        bottomFourButtonsContainer.setVgap(10);
        bottomFourButtonsContainer.setHgap(10);

        bottomFourButtonsContainer.add(showUserTotalButton, 0, 0);
        bottomFourButtonsContainer.add(showGroupTotalButton, 1, 0);
        bottomFourButtonsContainer.add(showMessageTotalButton, 0, 1);
        bottomFourButtonsContainer.add(showPositivePercentageButton, 1, 1);
        bottomFourButtonsContainer.add(validateIDButton, 0, 2);
        bottomFourButtonsContainer.add(showLastUpdateButton, 1, 2);


        VBox rightControlsContainer = new VBox(editTreeContainer,openUserViewContainer, resultLabel, bottomFourButtonsContainer);
        rightControlsContainer.setAlignment(Pos.CENTER);
        rightControlsContainer.setPadding(new Insets(20));

        HBox overallContainer = new HBox(treeContainer, rightControlsContainer);
        overallContainer.setAlignment(Pos.CENTER);
        overallContainer.setPadding(new Insets(10));

        Scene adminPanelScene = new Scene(overallContainer);
        primaryStage.setScene(adminPanelScene);
        primaryStage.show();
    }

    private VBox editTree()
    {
        Label addUserExplainLabel = new Label("Enter a user id you want to add");

        //Button and textfield for adding user
        userIdTextField = new TextField();
        Button addUserButton = new Button("Add User");
        addUserButton.setOnAction(new ButtonClickHandler());

        HBox addUserContainer = new HBox(userIdTextField, addUserButton);
        addUserContainer.setPadding(new Insets(10));

        //Button and textfield for adding user group
        userGroup = new ArrayList<>();
        userGroup.add("Root");

        Label addGroupExplainLabel = new Label("Enter a group you want to add");
        groupIdTextField = new TextField();
        Button addUserGroupButton = new Button("Add Group");
        addUserGroupButton.setOnAction(new ButtonClickHandler());

        HBox addGroupContainer = new HBox(groupIdTextField, addUserGroupButton);
        addGroupContainer.setPadding(new Insets(10));

        VBox container = new VBox(addUserExplainLabel, addUserContainer, addGroupExplainLabel, addGroupContainer);

        return container;
    }


   private class ButtonClickHandler implements EventHandler<ActionEvent>
   {

       @Override
       public void handle(ActionEvent event) {
           Button currentEvent = (Button)event.getSource();

           TreeItem<String> selectedItem = TreeViewObject.getTreeViewObject().getTreeView()
                   .getSelectionModel().getSelectedItem();

           //action for adding user
           if(currentEvent.getText().equals("Add User"))
           {
                String id = userIdTextField.getText();
                if(!id.isEmpty())
                {
                    if(selectedItem==null)
                    {
                        alert = new PopUpAlert("Please select a group to add to.");
                    }
                    if(selectedItem!= null && isGroup(selectedItem.getValue()) && !isExist(id)){
                        User newUser = new IndividualUser(id);
                        TreeViewObject.getTreeViewObject().addChild(newUser, selectedItem);
                        TreeViewObject.getTreeViewObject().addUser(newUser);
                    }
                } else
                {
                    alert = new PopUpAlert("Please enter an ID.");
                }
                userIdTextField.clear();
           }

           //Action for adding user group
           if(currentEvent.getText().equals("Add Group"))
           {
               String groupId = groupIdTextField.getText();
               if(!groupId.isEmpty())
               {
                   if(selectedItem==null)
                   {
                       alert = new PopUpAlert("Please select a group to add to.");
                   }
                   if(selectedItem!= null && isGroup(selectedItem.getValue()) && !isExist(groupId)){
                       User newUserGroup = new UserGroup(groupId);
                       userGroup.add(groupId);
                       TreeViewObject.getTreeViewObject().addChild(newUserGroup, selectedItem);
                       TreeViewObject.getTreeViewObject().addUser(newUserGroup);
                   }
               } else
               {
                   alert = new PopUpAlert("Please enter an ID.");
               }
               groupIdTextField.clear();
           }

           UserStatisticVistor result = new UserStatisticResultVisitor();

           //Action for button - show user total
           if(currentEvent.getText().equals("Show User Total"))
           {
                UserTotal userTotal = new UserTotal();
                int total = (int)userTotal.accept(result);
                resultLabel.setText("User Total: " + total);

           }

           //Action for button - Show Group Total
           if(currentEvent.getText().equals("Show Group Total"))
           {
               GroupTotal groupTotal = new GroupTotal();

               int total = (int)groupTotal.accept(result);
               resultLabel.setText("Group Total: " + total);
           }

           //Action for button - Show Messages Total
           if(currentEvent.getText().equals("Show Messages Total"))
           {
               MessageTotal messageTotal = new MessageTotal();

               int total = (int)messageTotal.accept(result);
               resultLabel.setText("Message Total: " + total);

           }

           //Action for button - Show Positive Percentage
           if(currentEvent.getText().equals("Show Positive Percentage"))
           {
               PositivePercentage positivePercentage = new PositivePercentage();
               double percent = positivePercentage.accept(result);
               String str = String.format("Positive Percentage: %.2f %%", percent);
               resultLabel.setText(str);
           }

           //Action for button - Show Positive Percentage
           if(currentEvent.getText().equals("Validate IDs"))
           {
               ValidateID validateID = new ValidateID();
               double validationResult = validateID.accept(result);
               if(validationResult == 0.0) {
                   alert = new PopUpAlert("All IDs are valid!");
               } else
               {
                   alert = new PopUpAlert("Invalid ID(s) found!");
               }
           }

           //Action for button - Show Last Updated ID
           if(currentEvent.getText().equals("Show Last Updated ID"))
           {
               String resultID = findLastUpdatedID();
               resultLabel.setText("Last Update in User: " + resultID);
           }
       }

       private String findLastUpdatedID(){
           String id = "";

           ArrayList<User> userInTree = TreeViewObject.getTreeViewObject().getListOfUser();

           if(!userInTree.isEmpty()) {

               if(userInTree.size() == 1){
                   id = "Root";
               }

               for (int i = 1; i < userInTree.size(); i++) {
                   User user = userInTree.get(i);
                   User previousUser = userInTree.get(i-1);

                   //more time means more recent update
                   if(user.getLastUpdateTime() > previousUser.getLastUpdateTime()){
                       id = user.getId();
                   }
               }
           }
           return id;
       }


       private boolean isExist(String id)
       {
           boolean found = TreeViewObject.getTreeViewObject().isTreeContains(id);
           if (found)
           {
               alert = new PopUpAlert("Already exist.");
           }
           return found;
       }

       private boolean isGroup(String target)
       {
           boolean found = false;
           User check = TreeViewObject.getTreeViewObject().getUser(target);
           if(check instanceof UserGroup)
           {
               found = true;
           }
           if (!found)
           {
               alert = new PopUpAlert("This is not a group.");
           }
           return found;
       }
   }


}
