
package in.usamabinluha.www.zohousama;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String url;

    public User(int id, String firstName, String lastName, String url) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.url = url;
    }

    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getUrl() {
        return url;
    }
}
