package Utilities;

public enum Roles {

    ADMIN("1"),
    TEACHER("2"),
    TA("3"),
    STUDENT("4");

    private final String role;

    private Roles(String r){
        role = r;
    }

    public boolean equalsName(String otherRole) {
        return role.equals(otherRole);
    }

    public String toString() {
        return this.role;
    }

}
