package structures;


public class Transaction{
    double amount;
    int number = 0;

    public Transaction(double amount, int number){
        this.amount = amount;
        this.number = number;
    }

    public int compare(Transaction other){
        return(this.amount > other.amount? 1: this.amount == other.amount? 0: -1);
    }

    public String getNumber(){
        return String.valueOf(number);
    }

    public String getAmount(){
        return String.valueOf(amount);
    }

    @Override
    public String toString(){
        return getAmount() + " " + getNumber();
    }
}
