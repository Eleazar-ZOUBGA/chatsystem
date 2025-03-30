package chatsystem.contacts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.InetAddress;

import org.junit.jupiter.api.Test;

public class ContactTest {

    private User user = new User(0, "test");

    @Test
    public void testAssignUserId() throws UserAlreadyExists{
        user.assignUserId();
        assertEquals(0, user.getUserId());
        user.setUserId(5);
        user.addList("0", "null", null, 1234);
        user.assignUserId();
        assertEquals(1, user.getUserId());
    }

    @Test
    public void testSetUserName() throws UserAlreadyExists{
        assertTrue(user.setUserName("newUserName"));
        assertEquals("newUserName", user.getUserName());
        user.addList("1", "contact1", InetAddress.getLoopbackAddress(), 8080);
        assertFalse(user.setUserName("contact1")); // name already taken
        assertEquals("newUserName", user.getUserName()); // should remain unchanged
    }

    @Test
    public void testAddNewContact() throws UserAlreadyExists{
        int n = user.getContactsList().size();
        user.addList("1", "test1", InetAddress.getLoopbackAddress(), 1234);
        assertEquals(n+1, user.getContactsList().size());
        Contact contact = user.getContactsList().get(0);
        assertEquals(1, contact.getId());
        assertEquals("test1", contact.getName());
        assertEquals(1234, contact.getPort());
        assertEquals(InetAddress.getLoopbackAddress(), contact.getAdress());
    }
    
    @Test
    public void testAddDuplicateContact() throws UserAlreadyExists{
        user.addList("1", "contact1", InetAddress.getLoopbackAddress(), 8080);
        
        // Test adding a user that already exists
        assertThrows(UserAlreadyExists.class, () -> {
            user.addList("1", "contact1", InetAddress.getLoopbackAddress(), 8080);
        });
    }

    @Test
    public void testUpdateContact() throws UserAlreadyExists{
        user.addList("1", "contact1", InetAddress.getLoopbackAddress(), 8080);
        user.updateList("1", "contactUpdated");
        assertEquals("contactUpdated", user.getContactsList().get(0).getName());
    }

    @Test
    public void testRemoveList() throws UserAlreadyExists {
        user.addList("1", "contact1", InetAddress.getLoopbackAddress(), 8080);
        assertTrue(user.removeList("1"));
        assertEquals(0, user.getUserList().size());

        // Test removing a non-existent user
        assertFalse(user.removeList("2")); // Should return false
    }

}
