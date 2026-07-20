/**
 * Constructor
 */
public class Constructor {

    public static void main(String[] args) {
        BankAccount a = new BankAccount(21);
        a.deposit(19, a);
        System.out.println(a.getBalance());
    }
}

/**
 * BankAcount
 */
class BankAccount {
    private int balance;
    String userName;
    String address;

    int getBalance() {
        return balance;
    }

    public BankAccount(int balance, String name) {
        this.balance = balance;
        userName = name;
    }

    public BankAccount(int balance) {
        this(balance, "abc");
    }

    public void deposit(int amount, BankAccount b) {
        balance += amount;
        b.balance -= 2;
    }

}

class BankAccountUrban extends BankAccount {
    private double tax;

    public BankAccountUrban() {
        super(2);
    }
}

class Food {
}

class Meat extends Food {
}

class Animal {
    Food getFood() {
        return new Food();
    }
}

class Tiger extends Animal {
    @Override
    Food getFood() {
        return new Meat();
    }
} // Kiểu trả về đồng biến (Meat is Food)