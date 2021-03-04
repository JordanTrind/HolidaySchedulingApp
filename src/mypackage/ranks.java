package mypackage;

public class ranks {
    int id;
    String rank;
    int amountNeeded;

    public ranks(int id, String rank, int amountNeeded) {
        this.id = id;
        this.rank = rank;
        this.amountNeeded = amountNeeded;
    }

    public String getRankName() { return rank; }

    public int getRankID() { return id; }

    public int getAmountNeeded() { return amountNeeded; }
}
