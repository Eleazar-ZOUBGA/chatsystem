package chatsystem.contacts;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Contact {
    private int id;
    private String name;
    private InetAddress adressIP;
    private int port;
    private int timer;
    
    
    // Observer interface
    public interface MessageObserver {
        void updateMessage(Contact contact, String message);
    }
    // Observer pattern components
    private List<MessageObserver> observers = new ArrayList<>();

    public Contact(int id, String name, InetAddress address, int port) {
        this.id = id;
        this.name = name;
        this.adressIP = address;
        this.port = port;
        this.timer = 3;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public InetAddress getAdress() {
        return this.adressIP;
    }

    public int getPort() {
        return this.port;
    }

    public void setTimer(int t) {
        this.timer = t;
    }

    public int getTimer() {
        return this.timer;
    }

    public void addObserver(MessageObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(MessageObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (MessageObserver observer : observers) {
            observer.updateMessage(this, message);
        }
    }

    public void receiveMessage(String message) {
        notifyObservers(message);
    }

    @Override
    public String toString() {
        return ("Id : " + this.id + " , name : " + this.name + " , @IP : " + this.adressIP + " , port : " + this.port);
    }
}
