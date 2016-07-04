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
public class Seller {
    protected static File seller = new File("Seller.txt");
    protected static File tempFile = new File("TempFile.txt");
    protected static final String[] regItem = {"L1","L2","L3","L4","L5","L6","L7","L8","L9","L10"};
    private static final String[] regNo = {"A1","A2","A3","A4","A5","A6","A7","A8","A9","A10"};
    private static int num;
    private static String verifyID;
    private static int reg;
    private String ID;
    private String name;
    private String item;
    private String itemID;
    
    
    public static void display() throws FileNotFoundException{
        Scanner input = new Scanner (System.in);
        System.out.println("1. Add seller");
        System.out.println("2. Remove seller");
        System.out.println("3. Edit details");
        System.out.println("4. Back <--");
        System.out.print("Enter: ");
        int choice = input.nextInt();
        System.out.println("-----------------------------------------");
            if (choice==1){
                System.out.println("[Enter 'Q' to quit]");
                input.nextLine();
                String assignID = assignID();
                System.out.print("Name: ");
                String i2 = input.nextLine();
                if (i2.equalsIgnoreCase("q")){
                    display();
                }
                System.out.print("Item: ");
                String i3 = input.nextLine();
                if (i3.equalsIgnoreCase("q")){
                    display();
                } 
                System.out.println("Item ID: "+regItem[reg]);
                Seller newSeller = new Seller(assignID, i2, i3, regItem[reg]);
                System.out.println("SUCCESSFULLY REGISTERED!");
                System.out.println("-----------------------------------------");
                display();
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
                System.out.println("1. Name");
                System.out.println("2. Item");
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
    
    public Seller(String a, String bb, String c, String d) {
        ID = a;
        name = bb;
        item = c;
        itemID = d;
        try{
            PrintWriter b = new PrintWriter(new FileOutputStream(seller, true)); 
            b.println(ID+", "+name+", "+item+", "+itemID);
            b.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot write to the file");         
        } 
    }
    
    public static void quit(String ID) throws FileNotFoundException {
        ArrayList all = readFile();
        //check if exist
        boolean found = false;
        for (int i=0; i<all.size(); i+=4){
            if (ID.equals(all.get(i))){
                for (int k=0; k<4; k++){
                    all.remove(i);
                }
                if (!all.isEmpty()){
                    //write file
                    try{
                    PrintWriter b = new PrintWriter(new FileOutputStream(tempFile, true));
                    for (int j=0; j<all.size();j+=4){
                        b.println(all.get(j)+", "+all.get(j+1)+", "+all.get(j+2)+", "+all.get(j+3));
                    }
                    b.close();
                    }catch(Exception e){
                    System.out.println("Cannot write to the file"); 
                    }
                }
                //rename and delete file
                forceRename(tempFile, seller);
                System.out.println("SUCCESSFULLY REMOVED!");
                found = true;
                break;
            }
        }if (!found){
            System.out.println("THIS SELLER ID DOES NOT EXIST!");
        }
        System.out.println("-----------------------------------------");       
    }
    
    public static void modify(int d, String c){
        Scanner input = new Scanner (System.in);
        ArrayList all = readFile();
        boolean found = false;
        for (int i=0; i<all.size(); i+=4){
            if (c.equals(all.get(i))){
                System.out.print("Enter new: ");
                String re = input.next();
                all.set(i+d, re);
                if (!all.isEmpty()){
                    //write file
                    try{
                    PrintWriter b = new PrintWriter(new FileOutputStream(tempFile, true));
                    for (int j=0; j<all.size();j+=4){
                        b.println(all.get(j)+", "+all.get(j+1)+", "+all.get(j+2)+", "+all.get(j+3));
                    }
                    b.close();
                    }catch(Exception e){
                    System.out.println("Cannot write to the file"); 
                    }
                }
                //rename and delete file
                forceRename(tempFile, seller);
                System.out.println("SUCCESSFULLY MODIFIED!");
                found = true;
                break;
            }
        }if (!found){
            System.out.println("THIS SELLER ID DOES NOT EXIST!");
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
        System.out.printf("%-4s%-15s%-20s%4s\n","(id)","(name)","(item)","(itemID)");
        //read file
        try{        
        Scanner a = new Scanner (new FileInputStream(seller));
        for (int i=0; a.hasNextLine(); i++){                        
            String s = a.nextLine();
            String[] details = s.split(", ");
            System.out.printf("%-4s%-15s%-20s%4s\n",details[0],details[1],details[2],details[3]);
        }    
            a.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot read from the file");         
        } 
        User.main(new String[0]);
    }
    
    public static ArrayList readFile(){
        ArrayList all = new ArrayList();
        try{        
            Scanner a = new Scanner (new FileInputStream(seller));
            for (int i=0; a.hasNextLine(); i++){                        
                String s = a.nextLine();
                String[] details = s.split(", ");
                all.addAll(Arrays.asList(details));
            }    
            a.close();
            }catch(FileNotFoundException e){
                System.out.println("Cannot read from the file");         
            }
        return all;        
    }
    
    public static String assignID(){
        String assignID = "";
                ArrayList all = readFile();
                if (all.isEmpty()){
                    assignID = regNo[0];
                    reg = 0;
                    System.out.println("ID: "+assignID);
                }else{
                    for (int i=0; i<regNo.length; i++){
                        boolean found = false;
                        for (int k=0; k<all.size(); k++){
                            if (all.get(k).equals(regNo[i])){
                                found = true;
                            }
                        }if(!found){
                            assignID = regNo[i];
                            reg = i;
                            System.out.println("ID: "+assignID);
                            break;
                        }else if (i==regNo.length-1 && found){
                            System.out.println("The registration is full.");
                        }
                    }
                }
        return assignID;        
    }

}
