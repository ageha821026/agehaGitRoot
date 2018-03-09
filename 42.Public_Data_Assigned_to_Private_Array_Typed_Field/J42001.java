package testbed.unsafe;

public class J42001 {
    
    private String[] userRoles;

    public void setUserRoles(String[] userRoles) {
        this.userRoles = userRoles;
    }

    public void print() {
        for (int i = 0; i < userRoles.length; i++)
            System.out.println(userRoles[i]);
    }
}
