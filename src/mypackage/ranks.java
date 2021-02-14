package mypackage;

public class ranks {
    int id;
    String rank;
    int importance;

    public ranks(int id, String rank, int importance) {
        this.id = id;
        this.rank = rank;
        this.importance = importance;
    }

    public String getRankName() { return rank; }

    public int getRankID() { return id; }
}
