package org.example;

import java.util.ArrayList;

public class User{
    private int idUser;
    private String userName;
    private ArrayList<String> userList;

    private class InnerUser {
        private int id;
        private String name;
        private int timer;

        public InnerUser(int id, String name){
            this.id = id;
            this.name = name;
            this.timer = 3;
        }
        public String toString(){
            return (this.id + " " + this.name);
        }

    }
    private ArrayList<InnerUser> innerUsersList;

    public User(int idUser, String userName){
        this.idUser = idUser;
        this.userName = userName;
        this.userList = new ArrayList<>();
        this.innerUsersList = new ArrayList<>();
    }

    public static void sleep(int n){
        try{Thread.sleep(n);}
        catch (InterruptedException e) {System.err.println("Interuption of sleep");}
    }

    public void setUserId(int id){
        this.idUser = id;
    }

    public boolean setUserName(String name){
        if (name == "_"){
            this.userName = name;
            return false;
        }
        boolean absent = true;
        for (InnerUser usr : this.innerUsersList){
            absent &= !usr.name.equals(name);
        }
        if (absent) {
            this.userName = name;
        }
        return absent;
    }

    public boolean selectName(String name) {
        boolean hasName = this.setUserName(name);
        sleep(10000);
        boolean has = this.setUserName(name);
        if (!has){
            System.out.println("Name already taken.");
            if (hasName){
                hasName = this.setUserName("_");
            }
        }
        return hasName;
    }

    public void setId(){
        System.out.println("Please wait a moment");
        this.idUser = -1;
        sleep(10000);
        boolean taken = true;
        int i = 0;
        for (i = 0 ; taken ; i++) {
            taken = false;
            for (InnerUser usr : this.innerUsersList) {
                if (i == usr.id){
                    taken = true;
                }
            }
        }
        this.idUser = i-1;
    }

    public int getUserId(){
        return this.idUser;
    }

    public String getUserName(){
        return this.userName;
    }

    public ArrayList<String> getUserList(){
        return this.userList;
    }

    public ArrayList<InnerUser> getInnerUsersList(){
        return this.innerUsersList;
    }

    public void addList(String id, String name){
        if (!(userList.contains(id) || this.idUser == Integer.valueOf(id))) {
            userList.add(id);
            innerUsersList.add(new InnerUser(Integer.valueOf(id),name));
        }
    }

    public void updateList(String id, String name){
        ArrayList<String> a_suprimer = new ArrayList();
        if ( this.idUser == Integer.valueOf(id)){
            for (InnerUser usr : this.innerUsersList){
                usr.timer--;
                //System.out.println(usr.timer);
                if (usr.timer == 0){
                    a_suprimer.add(String.valueOf(usr.id));
                }
            }
        }
        if (userList.contains(id)) {
            int n=userList.indexOf(id);
            innerUsersList.get(n).name = name;
            innerUsersList.get(n).timer = 3;
        }
        for (String i : a_suprimer){
            removeList(i);
        }
    }

    public boolean removeList(String id){
        int n=userList.indexOf(id);
        innerUsersList.remove(n);
        return  userList.remove(id);
    }
}