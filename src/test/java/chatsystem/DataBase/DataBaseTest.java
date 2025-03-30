package chatsystem.DataBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

public class DataBaseTest {

        @Test
    public void testDatabaseFunctionality() {
        String databaseName = "messagerie.db";
        DatabaseControllers dbController = new DatabaseControllers(databaseName);
        Connection conn = dbController.connect();
        DataBase dataBase = new DataBase(databaseName);
        dataBase.connect(conn);
        dataBase.createUserMessagesTable();

        int userId1 = 1;
        int userId2 = 2;

        String message1 = "Bonjour, comment ça va ?";
        String message2 = "Je vais bien, merci !";
        String message3 = "Que fais-tu ?";

        String time1 = "2023-10-01 10:00:00";
        String time2 = "2023-10-01 10:01:00";
        String time3 = "2023-10-01 10:02:00";
        
        // Obtenir le nombre de messages existants
        int messageCount = dataBase.getMessagesBetween(userId1, userId2).size();

        // Insérer les messages
        dataBase.insertMessage(userId1, userId2, message1, time1);
        dataBase.insertMessage(userId2, userId1, message2, time2);
        dataBase.insertMessage(userId1, userId2, message3, time3);

        int expectedMessageCount = messageCount + 3;

        // Vérifier le nombre de messages
        assertEquals(expectedMessageCount, dataBase.getMessagesBetween(userId1, userId2).size());

        // Vérifier les messages
        assertTrue(dataBase.getMessagesBetween(userId1, userId2).contains(userId1 + "::" + message1 + "::" + time1));
        assertTrue(dataBase.getMessagesBetween(userId1, userId2).contains(userId2 + "::" + message2 + "::" + time2));
        assertTrue(dataBase.getMessagesBetween(userId1, userId2).contains(userId1 + "::" + message3 + "::" + time3));

        dataBase.close();
        dbController.close(conn);
}
}
