package mx.mango.pics.models;

import com.google.gson.annotations.SerializedName;

public class ApiUser {
    @SerializedName("_id")
    private String id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("token")
    private String token;

    public ApiUser(String id, String firstName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return this.id
                .concat(" -> ")
                .concat(this.firstName)
                .concat(" -> ")
                .concat(this.email);
    }
}
