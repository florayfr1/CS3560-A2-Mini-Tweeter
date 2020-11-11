package sample;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Iterator;

//The message update uses the observable pattern
public class IndividualUser extends Subject implements User, Observer {

    private String id;

    //This will populate a ListView in JavaFX
    private ObservableList<Observer> follower;
    private ObservableList<Subject> following;
    private ObservableList<String> newsFeed;

    private HashMap<Subject, String> follwingSubjectToId;

    private String message;

    /*constructor for creating a user.
        A user has 1) an unique ID;
                   2) a list of user IDs that are following this user (followers);
                   3) a list of user IDs being followed by this user (followings);
                   4) a news feed list containing a list of Twitter messages.
    */
    public IndividualUser(String id){
        this.id = id;
        follower = FXCollections.observableArrayList();
        following = FXCollections.observableArrayList();
        newsFeed = FXCollections.observableArrayList();
        follwingSubjectToId = new HashMap<>();
    }

    public ObservableList<String> getNewsFeed() {
        return newsFeed;
    }

    public ObservableList<String> getFollowing() {
        ObservableList<String> stringFollowingList = FXCollections.observableArrayList();
        for(int i = 0; i < following.size(); i++)
        {
            Subject temp = following.get(i);
            stringFollowingList.add(getSubjectId(temp));
        }
        return stringFollowingList;
    }

    public void setFollowing(IndividualUser user){

        follwingSubjectToId.put((Subject)user, user.getId());
        setSubject(user);
        user.attach(this);
    }

    public void tweet(String message)
    {
        this.message = message;
        newsFeed.add("- Me: " + message);
        notifyObservers();
    }

    public String getSubjectId(Subject subject){
        return follwingSubjectToId.get(subject);
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public ObservableList<Observer> getFollower() {
        return follower;
    }

    //Subject function
    @Override
    public void attach(Observer observer) {
        follower.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        follower.remove(observer);
    }

    public void notifyObservers() {
        Iterator var2 = this.follower.iterator();

        while(var2.hasNext()) {
            Observer observer = (Observer)var2.next();
            observer.update(this);
        }
    }



    //Observer function

    @Override
    public void update(Subject subject) {
        if(subject instanceof IndividualUser)
        {
            String newMessage = ((IndividualUser) subject).message;

            newsFeed.add("- "+((IndividualUser) subject).id + ": " + newMessage);
        }
    }

    @Override
    public void setSubject(Subject subject) {
        following.add(subject);
    }
}
