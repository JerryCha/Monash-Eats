public class Owner extends Account {

    // Status of being verified by admin.
    private boolean isVerified;

    public Owner() {

    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
