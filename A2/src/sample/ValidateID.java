package sample;

public class ValidateID implements UserStatistic {
    @Override
    public double accept(UserStatisticVistor visitor) {
        return visitor.visitValidateID(this);
    }
}
