package structures;


public class Transaction{
    double tAmt;
    int tNum = 0;

    public Transaction(double amount){
        this.tAmt = amount;
    }

    public int compareTo(Transaction other){
        return(this.tAmt > other.tAmt? 1: this.tAmt == other.tAmt? 0: -1);
    }

    public String getNumber(){
        return String.valueOf(tNum);
    }

    public String getAmount(){
        return String.valueOf(tAmt);
    }

    @Override
    public String toString(){
        return getAmount() + " " + getNumber();
    }
}