package sample;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeViewObject implements Widget {

    private VBox treeViewContainer;

    private static TreeViewObject treeViewObject;

    private TreeView<String> treeView;

    private TreeItem<String> root;

    private HashMap<String, User> idToUser;

    private ArrayList<User> listOfUser;

    private ImageView folderImageView = new ImageView(new Image("file:./res/folder.jpg"));
    private Node folderImage = folderImageView;

    private TreeViewObject(){
        listOfUser = new ArrayList<>();

        folderImageView.setFitHeight(15);
        folderImageView.setFitWidth(15);

        root = new TreeItem<>("Root");
        root.setExpanded(true);
        root.setGraphic(folderImage);
        User rootGroup = new UserGroup("Root");

        idToUser = new HashMap<>();
        idToUser.put("Root", rootGroup);
        listOfUser.add(rootGroup);

        treeView = new TreeView<>(root);
        treeViewContainer = new VBox(treeView);
    }

    public void addUser(User user){
        listOfUser.add(user);
    }

    public ArrayList<User> getListOfUser()
    {
        return listOfUser;
    }

    //Singleton
    public static TreeViewObject getTreeViewObject()
    {
        if(treeViewObject == null)
        {
            treeViewObject = new TreeViewObject();
        }
        return treeViewObject;
    }

    public TreeItem<String> getRoot() {
        return root;
    }

    public void addChild(User child, TreeItem<String> parent)
    {
        TreeItem<String> childNode = new TreeItem<>(child.getId());
        if(parent.getValue().equals("Root"))
        {
            root.getChildren().add(childNode);

        }else {
            parent.getChildren().add(childNode);
        }
        idToUser.put(child.getId(), child);
        if(child instanceof UserGroup) {
            childNode.setExpanded(true);
            childNode.setGraphic(folderImage);
        }
    }

    public TreeView<String> getTreeView()
    {
        return treeView;
    }

    @Override
    public VBox render() {
        return treeViewContainer;
    }

    public HashMap<String, User> getIdToUser() {
        return idToUser;
    }

    public User getUser(String id)
    {
        return idToUser.get(id);
    }

    public boolean isTreeContains(String id)
    {
        return idToUser.containsKey(id);
    }
}
