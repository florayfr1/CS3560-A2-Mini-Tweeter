package sample;

import java.util.ArrayList;
import java.util.HashMap;

public class UserStatisticResultVisitor implements UserStatisticVistor {
    @Override
    public double visitUserTotal(UserTotal userTotal) {
        double count = 0;
        ArrayList<User> userInTree = TreeViewObject.getTreeViewObject().getListOfUser();
        if(!userInTree.isEmpty()) {
            for (int i = 0; i < userInTree.size(); i++) {
                User user = userInTree.get(i);
                if (user instanceof IndividualUser) {
                    count ++;
                }
            }
        }
        return count;
    }

    @Override
    public double visitGroupTotal(GroupTotal groupTotal) {
        double count = 0;
        ArrayList<User> userInTree = TreeViewObject.getTreeViewObject().getListOfUser();
        if(!userInTree.isEmpty()) {
            for (int i = 0; i < userInTree.size(); i++) {
                User user = userInTree.get(i);
                if (user instanceof UserGroup) {
                    count ++;
                }
            }
        }
        return count;
    }

    @Override
    public double visitMessageTotal(MessageTotal messageTotal) {
        double count = 0;
        ArrayList<User> userInTree = TreeViewObject.getTreeViewObject().getListOfUser();
        if(!userInTree.isEmpty()) {
            for (int i = 0; i < TreeViewObject.getTreeViewObject().getListOfUser().size(); i++) {
                User user = userInTree.get(i);
                if (user instanceof IndividualUser) {
                    count += ((IndividualUser)user).getNewsFeed().size();
                }
            }
        }
        return count;
    }

    @Override
    public double visitPositivePercentage(PositivePercentage positivePercentage) {
        int countPositive = 0;
        int countTotal = 0;
        double result = 0;

        String[] positiveWord = positivePercentage.getPositiveWords();

        ArrayList<User> userInTree = TreeViewObject.getTreeViewObject().getListOfUser();

        if(!userInTree.isEmpty()) {
            for (int i = 0; i < userInTree.size(); i++) {
                User user = userInTree.get(i);
                if (user instanceof IndividualUser) {
                    if(!((IndividualUser)user).getNewsFeed().isEmpty()) {
                        countTotal += ((IndividualUser) user).getNewsFeed().size();
                        for (int k = 0; k < ((IndividualUser) user).getNewsFeed().size(); k++) {
                            String userMessageAtIndex = ((IndividualUser) user).getNewsFeed().get(k).trim().toLowerCase();
                            for (int j = 0; j < positiveWord.length; j++) {
                                if (userMessageAtIndex.contains(positiveWord[j])) {
                                    countPositive++;
                                }
                            }
                        }
                    }
                }
            }
            if(countTotal != 0) {
                result = countPositive * 100.0 / countTotal;
            }
        }
        return result;
    }
}
