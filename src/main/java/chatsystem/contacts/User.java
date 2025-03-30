package chatsystem.contacts;

import java.net.InetAddress;
import java.util.ArrayList;

public class User {
    private int idUser;
    private String userName;
    private int TCPServerPort;
    private ArrayList<String> userList;
    private ArrayList<Contact> contactsList;

    public User(int idUser, String userName) {
        this.idUser = idUser;
        this.userName = userName;
        this.userList = new ArrayList<>();
        this.contactsList = new ArrayList<>();
    }

    public static void sleep(int n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            System.err.println("Interruption of sleep");
        }
    }

    public void setUserId(int id) {
        this.idUser = id;
    }

    public boolean nameIsDisponible(String name){
        boolean absent = true;
        for (Contact usr : this.contactsList){
            absent &= !usr.getName().equals(name);
        }
        return absent;
    }

    public void _setUserName(String name){
        this.userName = name;
    }

    public boolean setUserName(String name){
        if (name.equals("_")) {
            this.userName = name;
            return false;
        }
        boolean absent = true;
        for (Contact usr : this.contactsList) {
            absent &= !usr.getName().equals(name);
        }
        if (absent) {
            this.userName = name;
        }
        return absent;
    }

    public boolean selectName(String name) {
        boolean hasName = this.setUserName(name);
        sleep(1000);
        boolean has = this.setUserName(name);
        if (!has) {
            System.out.println("Name already taken.");
            if (hasName) {
                hasName = this.setUserName("_");
            }
        }
        return hasName;
    }

    public void assignUserId() {
        System.out.println("Please wait a moment");
        this.idUser = -1;
        sleep(1000);
        boolean taken = true;
        int i = 0;
        for (i = 0; taken; i++) {
            taken = false;
            for (Contact usr : this.contactsList) {
                if (i == usr.getId()) {
                    taken = true;
                }
            }
        }
        this.idUser = i - 1;
    }

    public int getUserId() {
        return this.idUser;
    }

    public String getUserName() {
        return this.userName;
    }

    public int getPort() {
        return this.TCPServerPort;
    }

    public void setPort(int p) {
        this.TCPServerPort = p;
    }

    public ArrayList<String> getUserList() {
        return this.userList;
    }

    public ArrayList<Contact> getContactsList() {
        return this.contactsList;
    }

    public void addList(String id, String name, InetAddress address, int port) throws UserAlreadyExists {
        if (userList.contains(id) || this.idUser == Integer.valueOf(id)) throw new UserAlreadyExists(name, Integer.parseInt(id));
        userList.add(id);
        contactsList.add(new Contact(Integer.valueOf(id), name, address, port));
    }

    public void updateList(String id, String name) {
        ArrayList<String> toDelete = new ArrayList<>();
        if (this.idUser == Integer.parseInt(id)) {
            for (Contact usr : this.contactsList) {
                usr.setTimer(usr.getTimer() - 1);
                if (usr.getTimer() == 0) {
                    toDelete.add(String.valueOf(usr.getId()));
                }
            }
        }
        if (userList.contains(id)) {
            int n = userList.indexOf(id);
            contactsList.get(n).setName(name);
            contactsList.get(n).setTimer(3);
        }
        for (String i : toDelete) {
            removeList(i);
        }
    }

    public boolean removeList(String id) {
        if (userList.contains(id)) {
            int n = userList.indexOf(id);
            contactsList.remove(n);
            return userList.remove(id);
        }
        return false;
    }

    public Contact getContactByName(String name) {
        for (Contact contact : contactsList) {
            if (contact.getName().equals(name)) {
                return contact;
            }
        }
        return null;
    }
}
