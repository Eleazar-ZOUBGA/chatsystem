package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main 
{
    private static int [] dest = {4000,4001,4002,4003,4005,4006,4007,4008,4009};

    public static void sleep(int n){
        try{Thread.sleep(n);}
        catch (InterruptedException e) {System.err.println("Interuption of sleep");}
    }

    public static void main( String[] args ) {
        // verification des argument
        /*if (args.length != 1) {
            System.err.println("need one argument : number");
            System.exit(1);
        }
        try {
            Integer.parseInt(args[0]);
        }catch (NumberFormatException e){
            System.err.println("need a number");;
            System.exit(1);
        }*/

        //debut du code
        //int n = Integer.parseInt(args[0]);
        User user = new User(-1, "_");
        System.err.println(user.getUserName());
        ConnectionHandling.create(user/*, 2000 + n, dest[n]*/, dest);
        //ConnectionHandling.connection();
        ConnectionHandling.start();
        sleep(100);

        user.setId();
        System.out.println("ID : " + user.getUserId());

        //choix du nom
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean hasName = false;
        while (!hasName) {
            try {
                System.out.println("Choose a name : ");
                String name = reader.readLine();
                sleep(100);
                hasName = user.selectName(name);
            } catch (IOException e) {
                System.err.println("Erreur ReadLine");
            }
        }
        System.out.println("Name : " + user.getUserName());
        boolean running = true;
        while (running) {
            sleep(6000);
            System.out.println(user.getInnerUsersList());
        }

    }
}
