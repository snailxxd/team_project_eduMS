package entities;

public class Student extends User {
    private String deptName;
    private int totalCredit;

    public Student() {}

    public Student(int userId, String accountNumber, String password, int personalInfoId, String deptName, int totalCredit) {
        super(userId, accountNumber, password, personalInfoId);
        this.deptName = deptName;
        this.totalCredit = totalCredit;
    }

    @Override
    public UserRole getUserType() {
        return UserRole.STUDENT;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userId=" + userId +
                ", accountNumber='" + accountNumber + '\'' +
                ", password='" + password + '\'' +
                ", personalInfoId=" + personalInfoId +
                ", deptName='" + deptName + '\'' +
                ", totalCredit=" + totalCredit +
                '}';
    }

    public int getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(int totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}