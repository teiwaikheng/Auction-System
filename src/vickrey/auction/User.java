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
import java.util.Scanner;
import java.util.Timer;
import static vickrey.auction.Auction.baDuration;
import vickrey.auction.AuctionState.State;

/**
 *
 * @author User
 */
public class User {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner (System.in);
        System.out.println("Welcome to Jass's Vintage Auction System!");
        AuctionType.select();
        System.out.println("-----------------------------------------");
        System.out.println("1. Members' information portal");
        System.out.println("2. Begin Auction");
        System.out.println("3. Terminate auction");
        System.out.print("Choose your action: ");
        int number = input.nextInt();
        if (number==1){
            System.out.println("1. Seller");
            System.out.println("2. Bidder");
            System.out.println("3. Staff login");
            System.out.print("Are you a seller or a bidder? ");
            int num = input.nextInt();
            if (num==1){
                if (AuctionType.getMainOption()==3){
                    BAseller.display();
                }else{
                    Seller.display();
                }
            }else if (num==2){
                Bidder.display();
            }else if (num==3){
                staffLogin();
            }else{
                main(new String[0]);
            }
        }else if (number==2){
            option2();
        }else if (number==3)
            System.exit(0);
    }
    
    public static void option2() throws FileNotFoundException{
        Scanner input = new Scanner(System.in);
        System.out.println("1. Set auction duration");
        System.out.println("2. Start auction");
        System.out.print("Enter: ");
        int n = input.nextInt();
            if (n==1){
                Auction.setTimer();
                option2();
            }else if (n==2){
                if (AuctionType.getMainOption()==3&&baDuration==0){
                    System.out.println("!!!!!Duration cannot be zero!!!!!");
                    Auction.setTimer();
                }
                if (AuctionType.getMainOption()==3){
                    Auction.baDescription();
                }else{
                    Auction.description();
                }
                System.out.print("Press E then ENTER to start bidding: ");
                String s = input.next();
                if (s.equalsIgnoreCase("e")){
                    AuctionState a = new AuctionState(State.RUNNING);
                    a.steps();
                }else{
                    main(new String[0]);
                    }
            }else{
                main(new String[0]);
            }
        
    }
    public static void staffLogin() throws FileNotFoundException{
        Timer timer = new Timer();
        File ver = new File("Verification.txt");
        Scanner input = new Scanner (System.in);
        System.out.print("Verification passcode: ");
        String str = input.next();
        if (str.equals("Jass95MyHome")){
            System.out.println("1. Seller details");
            System.out.println("2. Bidder details");
            System.out.print("Enter: ");
            int de = input.nextInt();
            if (de==1){
                if (AuctionType.getMainOption()==3){
                    BAseller.print();
                }else{
                    Seller.print();
                }
            }else if (de==2){
                Bidder.print();
            }else{
                main(new String[0]);
            }
        }else{
            System.out.println("WRONG PASSCODE!");
            int trial=0;
            //read file
            try{        
                Scanner a = new Scanner (new FileInputStream(ver));
                if(!a.hasNextInt()){
                    try{
                        PrintWriter b = new PrintWriter(new FileOutputStream(ver)); 
                        b.println(1);
                        b.close();
                    }catch(FileNotFoundException e){
                        System.out.println("Cannot write to the file");         
                    } 
                }else{
                    try{
                        PrintWriter b = new PrintWriter(new FileOutputStream(ver));
                        trial = a.nextInt()+1;
                        b.println(trial);
                        b.close();
                    }catch(FileNotFoundException e){
                        System.out.println("Cannot write to the file");         
                    }
                    if (trial==3){
                        System.out.println("YOU WILL BE DIRECTED BACK TO MAIN PAGE AFTER 5 MINUTES!");
                        try{
                            PrintWriter b = new PrintWriter(new FileOutputStream(ver));
                            b.println(0);
                            b.close();
                        }catch(FileNotFoundException e){
                            System.out.println("Cannot write to the file");         
                    }
                        TrialTimer trialTimer = new TrialTimer (15);
                    }else{
                        main(new String[0]);
                    }
                }
                a.close();
            }catch(FileNotFoundException e){
                System.out.println("Cannot read from the file");         
            } 
        }
    }
}
