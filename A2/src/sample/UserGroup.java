package sample;

import javafx.scene.control.TreeItem;

public class UserGroup implements User{
    private String groupId;
    private String id;

    public UserGroup(String id){
        groupId = id;
    }

    @Override
    public void setId(String id) {
        groupId = id;
    }

    @Override
    public String getId() {
        return groupId;
    }

}
