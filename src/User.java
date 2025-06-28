import java.util.Date;

class User {
    private final int userId;
    private int balance;
    private final Date createdAt;

    public User(int userId, int balance) {
        this.userId = userId;
        this.balance = balance;
        this.createdAt = new Date();
    }

    public void setBalance(int balance) { this.balance = balance; }
    public int getUserId() { return userId; }
    public int getBalance() { return balance; }
    public Date getCreatedAt() { return createdAt; }
}
