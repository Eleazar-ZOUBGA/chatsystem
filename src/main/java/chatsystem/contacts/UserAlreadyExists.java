package chatsystem.contacts;

/** Error that is thrown when a contact is added twice to the list. */
public class UserAlreadyExists extends Exception {

    private final String username;
    private final int id;

    public UserAlreadyExists(String username, int id) {
        this.username = username;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ContactAlreadyExists{" +
                "username='" + username + '\'' + "id= " + id +
                '}';
    }
}
