package mx.mango.pics.models;

import io.realm.RealmObject;

public class User extends RealmObject {
    private String id;
    private String firstName;
    private String email;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return this.id + " ---> " + this.firstName + " ---> " + this.email + " ---> " + this.token;
    }
}
