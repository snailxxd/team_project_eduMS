package entities;

public class Teacher extends User {
    private String deptName;
    private int salary;

    public Teacher() {};

    public Teacher(int userId, String accountNumber, String password, int personalInfoId, String deptName, int salary) {
        super(userId, accountNumber, password, personalInfoId);
        this.deptName = deptName;
        this.salary = salary;
    }

    @Override
    public UserRole getUserType() {
        return UserRole.TEACHER;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                ", password='" + password + '\'' +
                ", personalInfoId=" + personalInfoId +
                ", deptName='" + deptName + '\'' +
                ", salary=" + salary +
                '}';
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}