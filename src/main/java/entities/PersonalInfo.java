package entities;

public class PersonalInfo {
    private int personalInfoId;
    private String name;
    private String phoneNumber;

    public PersonalInfo() {}

    public PersonalInfo(int personalInfoId, String name, String phoneNumber) {
        this.personalInfoId = personalInfoId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return "PersonalInfo{" +
                "personalInfoId=" + personalInfoId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    public int getPersonalInfoId() {
        return personalInfoId;
    }

    public void setPersonalInfoId(int personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
