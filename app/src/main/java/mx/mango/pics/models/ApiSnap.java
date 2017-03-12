package mx.mango.pics.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiSnap {
    @SerializedName("_id")
    private String id;
    @SerializedName("user")
    private String user;
    @SerializedName("pics")
    private List<String> pics;
    @SerializedName("location")
    private String location;
    @SerializedName("cause")
    private String cause;
    @SerializedName("description")
    private String description;
    @SerializedName("ph_os_version")
    private String phoneOSVersion;
    @SerializedName("ph_brand")
    private String phoneBrand;
    @SerializedName("ph_manufacturer")
    private String phoneManufacturer;
    @SerializedName("ph_model")
    private String phoneModel;
    @SerializedName("ph_serial")
    private String phoneSerial;
    @SerializedName("ph_device")
    private String phoneDevice;

    public ApiSnap() {}
    public ApiSnap(String id, String user, List<String> pics, String location, String cause,
                   String description, String phoneOSVersion, String phoneBrand,
                   String phoneManufacturer, String phoneModel, String phoneSerial,
                   String phoneDevice) {
        this.id = id;
        this.user = user;
        this.pics = pics;
        this.location = location;
        this.cause = cause;
        this.description = description;
        this.phoneOSVersion = phoneOSVersion;
        this.phoneBrand = phoneBrand;
        this.phoneManufacturer = phoneManufacturer;
        this.phoneModel = phoneModel;
        this.phoneSerial = phoneSerial;
        this.phoneDevice = phoneDevice;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<String> getPics() {
        return pics;
    }

    public void setPics(List<String> pics) {
        this.pics = pics;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneOSVersion() {
        return phoneOSVersion;
    }

    public void setPhoneOSVersion(String phoneOSVersion) {
        this.phoneOSVersion = phoneOSVersion;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getPhoneManufacturer() {
        return phoneManufacturer;
    }

    public void setPhoneManufacturer(String phoneManufacturer) {
        this.phoneManufacturer = phoneManufacturer;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneSerial() {
        return phoneSerial;
    }

    public void setPhoneSerial(String phoneSerial) {
        this.phoneSerial = phoneSerial;
    }

    public String getPhoneDevice() {
        return phoneDevice;
    }

    public void setPhoneDevice(String phoneDevice) {
        this.phoneDevice = phoneDevice;
    }

    @Override
    public String toString() {
        return user + " ---> " + this.cause + " ---> " + this.description +
                " ---> " + " ---> " + this.phoneBrand + " ---> " +
                this.phoneDevice + " ---> " + this.phoneManufacturer + " ---> " +
                this.phoneModel + " ---> " + this.phoneOSVersion + " ---> " +
                this.phoneSerial;
    }
}
