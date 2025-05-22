package queries;


public class UserQueryCondition {

    private String account;

    private String name;

    public UserQueryCondition() {
        this.account = null;
        this.name = null;
    }

    public String getAccount(){return account;}

    public String getName() { return name; }

    public void setAccount(String account) { this.account = account; }

    public void setName(String name) { this.name = name; }
}
