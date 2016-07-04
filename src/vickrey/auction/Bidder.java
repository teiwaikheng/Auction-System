/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vickrey.auction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author User
 */
public class Bidder {
    protected static File bidder = new File("Bidder.txt");
    protected static File tempFile = new File("TempFile.txt");
    protected static File sample = new File("Sample.txt");
    private static final String[] regNo = {"B1","B2","B3","B4","B5","B6","B7","B8","B9","B10"};
    private static int num;
    private static String verifyID;
    private static int reg;
    private String ID;
    private String firstname;
    private String lastname;
    
    public static void display() throws FileNotFoundException{
        Scanner input = new Scanner (System.in);
        System.out.println("1. Add bidder");
        System.out.println("2. Remove bidder");
        System.out.println("3. Edit details");
        System.out.println("4. Back <--");
        System.out.print("Enter: ");
        int choice = input.nextInt();
        System.out.println("-----------------------------------------");
            if (choice==1){
                add();
            }else if (choice==2){
                System.out.println("[Enter 'Q' to quit]");
                input.nextLine();
                System.out.print("Enter ID: ");
                String id = input.nextLine();
                if (id.equalsIgnoreCase("q")){
                    display();
                }
                quit(id);
                display();
            }else if (choice==3){
                System.out.println("1. First Name");
                System.out.println("2. Last Name");
                System.out.print("Choose part of details: ");
                num = input.nextInt();
                System.out.println("[Enter 'Q' to quit]");
                System.out.print("Enter ID: ");
                verifyID = input.next();
                if (verifyID.equalsIgnoreCase("q")){
                    display();
                }
                modify(num, verifyID);
                display();
            }else if (choice==4){
                User.main(new String[0]);
            }else{
                display();
            }
    }

    public Bidder(String a, String bb, String c) {
        ID = a;
        firstname = bb;
        lastname = c;
        try{
            PrintWriter b = new PrintWriter(new FileOutputStream(bidder, true)); 
            b.println(ID+", "+firstname+", "+lastname);
            b.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot write to the file");         
        } 
    }
    
    public static void add() throws FileNotFoundException{
        Scanner input = new Scanner(System.in);
                System.out.println("[Enter 'Q' to quit]");
                input.nextLine();
                String assignID = assignID();
                
                System.out.print("First Name: ");
                String i2 = input.nextLine();
                if (i2.equalsIgnoreCase("q")){
                    display();
                }
                System.out.print("Last Name: ");
                String i3 = input.nextLine();
                if (i3.equalsIgnoreCase("q")){
                    display();
                } 
                Bidder newBidder = new Bidder(assignID, i2, i3);
                System.out.println("SUCCESSFULLY REGISTERED!");
                //create each bidding sheet
                File own = new File (assignID+".txt");
                try{
                    PrintWriter b = new PrintWriter(new FileOutputStream(own));
                        b.println("ID: "+assignID);
                    for (String regItem : Seller.regItem) {
                        b.println("Item ID: " + regItem);
                        b.println("Bidding amount: 0.00");
                    }
                    
                    b.close();
                }catch(Exception e){
                    System.out.println("Cannot write to the file"); 
                    }
                System.out.println("-----------------------------------------");
                display();
    }

    public static void quit(String ID) throws FileNotFoundException {
        ArrayList all = new ArrayList();
        //read file
        try{        
        Scanner a = new Scanner (new FileInputStream(bidder));
        for (int i=0; a.hasNextLine(); i++){                        
            String s = a.nextLine();
            String[] details = s.split(", ");
            all.addAll(Arrays.asList(details));
        }    
            a.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot read from the file");         
        } 
        //check if exist
        boolean found = false;
        for (int i=0; i<all.size(); i+=3){
            if (ID.equals(all.get(i))){
                for (int k=0; k<3; k++){
                    all.remove(i);
                }
                if (!all.isEmpty()){
                    //write file
                    try{
                    PrintWriter b = new PrintWriter(new FileOutputStream(tempFile, true));
                    for (int j=0; j<all.size();j+=3){
                        b.println(all.get(j)+", "+all.get(j+1)+", "+all.get(j+2));
                    }
                    b.close();
                    }catch(Exception e){
                    System.out.println("Cannot write to the file"); 
                    }
                }
                //rename and delete file
                forceRename(tempFile, bidder);
                System.out.println("SUCCESSFULLY REMOVED!");
                found = true;
                break;
            }
        }if (!found){
            System.out.println("THIS BIDDER ID DOES NOT EXIST!");
        }
        System.out.println("-----------------------------------------");       
    }
    
    public static void modify(int d, String c){
        Scanner input = new Scanner (System.in);
        ArrayList all = new ArrayList();
        try{        
        Scanner a = new Scanner (new FileInputStream(bidder));
        for (int i=0; a.hasNextLine(); i++){                        
            String s = a.nextLine();
            String[] details = s.split(", ");
            all.addAll(Arrays.asList(details));
        }    
            a.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot read from the file");         
        } 
        
        boolean found = false;
        for (int i=0; i<all.size(); i+=3){
            if (c.equals(all.get(i))){
                System.out.print("Enter new: ");
                String re = input.next();
                all.set(i+d, re);
                if (!all.isEmpty()){
                    //write file
                    try{
                    PrintWriter b = new PrintWriter(new FileOutputStream(tempFile, true));
                    for (int j=0; j<all.size();j+=3){
                        b.println(all.get(j)+", "+all.get(j+1)+", "+all.get(j+2));
                    }
                    b.close();
                    }catch(Exception e){
                    System.out.println("Cannot write to the file"); 
                    }
                }
                //rename and delete file
                forceRename(tempFile, bidder);
                System.out.println("SUCCESSFULLY MODIFIED!");
                found = true;
                break;
            }
        }if (!found){
            System.out.println("THIS BIDDER ID DOES NOT EXIST!");
        }
        System.out.println("-----------------------------------------");
    }
    public static boolean forceRename(File a, File b){
        boolean bb = false;
        if (b.exists()){
            b.delete();
            a.renameTo(b);
            bb = true;
        }
        return bb;
    }
    
   public static void print() throws FileNotFoundException{
        System.out.println("Bidder Details");
        System.out.println("-----------------------------------------");
        System.out.printf("%-4s%-15s%-20s\n","(id)","(firstName)","(lastName)");
        //read file
        try{        
        Scanner a = new Scanner (new FileInputStream(bidder));
        for (int i=0; a.hasNextLine(); i++){                        
            String s = a.nextLine();
            String[] details = s.split(", ");
            System.out.printf("%-4s%-15s%-20s\n",details[0],details[1],details[2]);
        }    
            a.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot read from the file");         
        } 
        User.main(new String[0]);
    }
   
   public static String assignID(){
        String assignID = "";
        ArrayList all = new ArrayList();
        //read file
        try{        
            Scanner a = new Scanner (new FileInputStream(bidder));
            for (int i=0; a.hasNextLine(); i++){                        
                String s = a.nextLine();
                String[] details = s.split(", ");
                all.addAll(Arrays.asList(details));
            }    
            a.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot read from the file");         
        } 
        if (all.isEmpty()){
            assignID = regNo[0];
            reg = 0;
            System.out.println("ID: "+assignID);
        }else{
            for (int i=0; i<regNo.length; i++){
                boolean found = false;
                for (Object all1 : all) {
                    if (all1.equals(regNo[i])) {
                        found = true;
                    }
                }
if(found==false){
                    assignID = regNo[i];
                    reg = i;
                    System.out.println("ID: "+assignID);
                    break;
                }
            }
        }
        return assignID;        
    }
}
