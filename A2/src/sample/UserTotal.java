package sample;

public class UserTotal implements  UserStatistic{


    @Override
    public double accept(UserStatisticVistor visitor) {
        return visitor.visitUserTotal(this);
    }

}
