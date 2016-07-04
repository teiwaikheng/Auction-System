/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vickrey.auction;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import vickrey.auction.AuctionState.State;
import static vickrey.auction.BAseller.baSeller;
import static vickrey.auction.Bidder.bidder;
import static vickrey.auction.Seller.seller;

/**
 *
 * @author User
 */
public class Auction {
    private static final List<Double> B1 = new LinkedList<>();
    private static final List<Double> B2 = new LinkedList<>();
    private static final List<Double> B3 = new LinkedList<>();
    private static final List<Double> B4 = new LinkedList<>();
    private static final List<Double> B5 = new LinkedList<>();
    private static final List<Double> B6 = new LinkedList<>();
    private static final List<Double> B7 = new LinkedList<>();
    private static final List<Double> B8 = new LinkedList<>();
    private static final List<Double> B9 = new LinkedList<>();
    private static final List<Double> B10 = new LinkedList<>();
    private static final List<List> usableBidder = new LinkedList<>();
    private static final List<Object> itemID = new LinkedList<>();
    private static final List<Object> item = new LinkedList<>();
    private static final List<Object> value = new LinkedList<>();
    private static final List<Object> minInc = new LinkedList<>();
    private static final List<Object> minBid = new LinkedList<>();
    
    private static final    ArrayList id = new ArrayList();
    private static final    ArrayList firstName = new ArrayList();
    private static final    ArrayList lastName = new ArrayList();
    private static int duration = 5;
    protected static int baDuration = 0;
    private static int baDuration1;
    
    public static void description(){
        ArrayList all = new ArrayList();
        //read file
        try{        
        Scanner a = new Scanner (new FileInputStream(seller));
        for (int i=0; a.hasNextLine(); i++){                        
            String s = a.nextLine();
            String[] details = s.split(", ");
            item.add(details[2]);
            all.addAll(Arrays.asList(details));
        }    
            a.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot read from the file");         
        } 
        //print description
        System.out.println("Bidding Item");
        System.out.println("-----------------------------------------");  
        System.out.printf("%-8s%20s\n","(itemID)","(item)");
        for (int i=0; i<all.size(); i+=4){
            itemID.add(all.get(i+3));
            System.out.printf("%-8s%20s\n",all.get(i+3),all.get(i+2));
        }
       
    }
    
    public static void baDescription() {
        ArrayList all = new ArrayList();
        //read file
        try {
            Scanner a = new Scanner(new FileInputStream(baSeller));
            for (int i = 0; a.hasNextLine(); i++) {
                String s = a.nextLine();
                String[] details = s.split(", ");
                all.addAll(Arrays.asList(details));
                item.add(details[2]);
                value.add(details[3]);
                minBid.add(details[4]);
                minInc.add(details[5]);
                itemID.add(details[6]);
            }
            a.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read from the file");
        }
        //print description
        System.out.println("Bidding Item");
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-8s%20s%15s%20s%20s\n", "(itemID)", "(item)", "(itemValue)", "(minimumBid)", "(minimumIncrement)");
        for (int i = 0; i < all.size(); i += 7) {
            System.out.printf("%-8s%20s%15s%20s%20s\n", all.get(i+6), all.get(i+2), all.get(i+3), all.get(i+4), all.get(i+5));
        }
        readBidder();
    }
    
    public static void start(){
        System.out.println(" ------------- ");
        System.out.println("|AUCTION START|");
        System.out.println(" ------------- ");
        Toolkit.getDefaultToolkit().beep();
        Date date = new Date();
        System.out.println(date.toString());
        System.out.println("--You're given "+duration+" minutes to review before we end the bidding session--");
        AuctionTimer auctionTimer = new AuctionTimer(duration);
    }
    
    public static void baStart() {
        Scanner input = new Scanner(System.in);
        System.out.println(" ------------- ");
        System.out.println("|AUCTION START|");
        System.out.println(" ------------- ");
        Toolkit.getDefaultToolkit().beep();
        Date date = new Date();
        System.out.println(date.toString());
        BAAuctionTimer auctionTimer = new BAAuctionTimer(baDuration);
        BAAuctionTimer1 auctionTimer1 = new BAAuctionTimer1(baDuration1);
        while(auctionTimer.timer!=null){
            System.out.print("ID: ");
            String in1 = input.next();
            //if bidder exist
            if (id.contains(in1)){
                System.out.println("First Name: "+firstName.get(id.indexOf(in1)));
                System.out.print("Item ID: ");
                String in2 = input.next();
                //if item exist
                if (itemID.contains(in2)){
                    System.out.println("Item: "+item.get(itemID.indexOf(in2)));
                    System.out.print("Bidding amount: ");
                    double in3 = input.nextDouble();
                    File aa = new File (in2+".txt");
                    //if item file exist
                    if (aa.exists()){
                        double min = Double.parseDouble((String) minBid.get(itemID.indexOf(in2)));
                        int frequency = 1;
                        try{        
                            Scanner a = new Scanner (new FileInputStream(aa));     
                            for (int i=0; a.hasNextLine(); i++){
                                String s = a.nextLine();
                                String[] details = s.split(", ");
                                if (in1.equals(details[1])){
                                    min = Double.parseDouble(details[3]);
                                    frequency++;
                                }
                            } 
                            a.close();
                        }catch(FileNotFoundException e){
                            System.out.println("Cannot read from the file");         
                        }
                        //if amount>minBid
                                if (in3>min){
                                    //if increment>minInc
                                    if (in3>min+Double.parseDouble((String) minInc.get(itemID.indexOf(in2)))){
                                        bid(in2, in1, firstName.get(id.indexOf(in1)), in3, frequency);
                                        System.out.println("-------You have bid successfully-------");
                                    }else{
                                        System.out.println("Bidding amount does not reach the minimum increment");
                                    }
                                }else{
                                    System.out.println("Bidding amount does not exceed mimimum bidding amount");
                                }
                    }else{
                        if (in3>Double.parseDouble((String) minBid.get(itemID.indexOf(in2)))){
                            if (in3>Double.parseDouble((String) minBid.get(itemID.indexOf(in2)))+Double.parseDouble((String) minInc.get(itemID.indexOf(in2)))){
                                bid(in2, in1, firstName.get(id.indexOf(in1)), in3, 1);
                                System.out.println("-------You have bid successfully-------");
                            }else{
                                System.out.println("Bidding amount does not reach the minimum increment");
                            }
                        }else{
                            System.out.println("Bidding amount does not exceed mimimum bidding amount");
                        }
                    }
                }else{
                    System.out.println("Item not exist");
                }
            }else{
                System.out.println("Bidder not exist");
            }
        }
    }
    
    public static void bid(String b, String id, Object name, double amount, int frequency){
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
        //write file
        try{
            PrintWriter d = new PrintWriter(new FileOutputStream(b+".txt", true));
            d.println(ft.format(now)+", "+id+", "+name+", "+amount+", "+frequency);
            d.close();
        }catch(Exception e){
            System.out.println("Cannot write to the file"); 
        }
    }
    
    public static void readBidder(){
        //read file
        try{        
        Scanner a = new Scanner (new FileInputStream(bidder));
        for (int i=0; a.hasNextLine(); i++){                        
            String s = a.nextLine();
            String[] details = s.split(", ");
            id.add(details[0]);
            firstName.add(details[1]);
            lastName.add(details[2]);
        }    
            a.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot read from the file");         
        }
    }    
    public static void setTimer(){
        Scanner input = new Scanner (System.in);
        System.out.print("Enter the duration for this auction in unit of minute(s): ");
        if (AuctionType.getMainOption()!=3){
            duration = input.nextInt();
        }else{
            baDuration = input.nextInt();
            baDuration1 = baDuration+20;
        }
    }
    public static void finalization(){
        Scanner input = new Scanner (System.in);
        if (AuctionType.getMainOption()!=3){
            System.out.println("--The bidding sheet is being collected now--");
        }
        System.out.println("1. Show bidding result");
        System.out.println("2. Cancel auction");
        System.out.print("Enter: ");
        int in = input.nextInt();
        if (in==1){
            if (AuctionType.getMainOption()==1){
                Vickreyresult();
            }else if (AuctionType.getMainOption()==2){
                FPSBresult();
            }else if (AuctionType.getMainOption()==3){
                BAresult();
            }
        }else if (in==2){
            AuctionState c = new AuctionState(State.CANCELLED);
            c.steps();
        }else{
            finalization();
        }
        
    }
    
    public static void BAresult(){
        for (int i=0; i<itemID.size(); i++){
            File b = new File (itemID.get(i)+".txt");
            if (b.exists()){
                //read file
                double winnerAmount = 0;
                String winnerID = null;
                String winner = null;
                String winnerF = null;
                System.out.println("Item: "+itemID.get(i)+"("+item.get(i)+")");
                System.out.printf("%15s%10s%10s%10s\n", "(time)", "(id)", "(name)", "(amount)");
                int count = 0;
                try{        
                    Scanner a = new Scanner (new FileInputStream(b));
                    for (int j=0; a.hasNextLine(); j++){      
                        String s = a.nextLine();
                        String[] details = s.split(", ");
                        System.out.printf("%15s%10s%10s%10s\n", details[0], details[1], details[2], details[3]);
                        if (winnerAmount<Double.parseDouble(details[3])){
                            winnerAmount = Double.parseDouble(details[3]);
                            winner = details[2];
                            winnerID = details[1];
                            winnerF = details[4];
                        }
                        count++;
                    }    
                    a.close();
                }catch(FileNotFoundException e){
                    System.out.println("Cannot read from the file");         
                } 
                System.out.println(".....................................................");
                System.out.println("Winner: "+winnerID+"("+winner+")");
                System.out.println("Amount: "+winnerAmount);
                if (Integer.parseInt(winnerF)<11){
                    System.out.println("Winner status: Newbie");
                }else if (Integer.parseInt(winnerF)<21){
                    System.out.println("Winner status: Intermediate");
                }else{
                    System.out.println("Winner status: Pro");
                }
                System.out.println("Frequency of item bidding: "+count);
                System.out.println("-----------------------------------------------------");
            }
        }
    }
    
    public static void Vickreyresult(){
        String[] regNo = {"B1","B2","B3","B4","B5","B6","B7","B8","B9","B10"};
        List[] b = {B1, B2, B3, B4, B5, B6, B7, B8, B9, B10};
        List<String> name = new LinkedList<>();
        List<Integer> statusArray = new LinkedList();
        for (int i=0; i<regNo.length; i++) {
            File a = new File(regNo[i] + ".txt");
            if (a.exists()){
                b[i].addAll(sort(a));
                usableBidder.add(b[i]);
                name.add(regNo[i]);
                int status = 0;
                for (int j=0; j<b[i].size(); j++){
                    if ((Double)(b[i].get(j))!=0){
                        status++;
                    }
                }statusArray.add(status);
            }
        }  
        System.out.println("----------------RESULT------------------");  
        String[] regItem = {"L1","L2","L3","L4","L5","L6","L7","L8","L9","L10"};
        for (int i=0; i<itemID.size(); i++){
            for (int j=0; j<regItem.length; j++){
                //for one item, eg: L1
                int frequency = 0;
                if (itemID.get(i).equals(regItem[j])){
                    List<Double> t = new LinkedList<>();
                    for (int k=0; k<usableBidder.size(); k++){
                        t.add((Double) (usableBidder.get(k)).get(j));
                        if ((Double) (usableBidder.get(k)).get(j)!=0){
                            frequency++;
                        }
                    }
                    List<Double> tt = new LinkedList<>();
                    tt.addAll(t);
                    Collections.sort(tt);
                    for (int l=0; l<t.size(); l++){
                        if (tt.get(tt.size()-1).equals(t.get(l))){
                            System.out.println("Bidding Item: "+regItem[j]+"["+item.get(i)+"]");
                            if (tt.get(tt.size()-2)==0){
                                System.out.println("No winner");
                            }else{
                                //read file
                                try{        
                                    Scanner a = new Scanner (new FileInputStream(bidder));
                                        for (int m=0; a.hasNextLine(); m++){                        
                                            String s = a.nextLine();
                                            String[] details = s.split(", ");
                                            if (name.get(l).equals(details[0])){
                                                System.out.println("Winner: "+details[0]+"["+details[1]+" "+details[2]+']');
                                                System.out.println("Bidder status: "+statusArray.get(l));
                                                System.out.println("Frequency of bidding: "+frequency);
                                            }
                                        }    
                                    a.close();
                                }catch(FileNotFoundException e){
                                    System.out.println("Cannot read from the file");         
                                } 
                                System.out.println("Price: "+tt.get(tt.size()-2));
                            }    
                        }
                    }System.out.println("________________________________________");
                }
            }
        }
        AuctionState e = new AuctionState(State.ENDED);
        e.steps();
    }
    
    public static void FPSBresult(){
        String[] regNo = {"B1","B2","B3","B4","B5","B6","B7","B8","B9","B10"};
        List[] b = {B1, B2, B3, B4, B5, B6, B7, B8, B9, B10};
        List<String> name = new LinkedList<>();
        for (int i=0; i<regNo.length; i++) {
            File a = new File(regNo[i] + ".txt");
            if (a.exists()){
                b[i].addAll(sort(a));
                usableBidder.add(b[i]);
                name.add(regNo[i]);
            }
        }  
        System.out.println("----------------RESULT------------------");  
        String[] regItem = {"L1","L2","L3","L4","L5","L6","L7","L8","L9","L10"};
        for (int i=0; i<itemID.size(); i++){
            for (int j=0; j<regItem.length; j++){
                //for one item, eg: L1
                int frequency = 0;
                if (itemID.get(i).equals(regItem[j])){
                    List<Double> t = new LinkedList<>();
                    for (int k=0; k<usableBidder.size(); k++){
                        t.add((Double) (usableBidder.get(k)).get(j));
                        if ((Double) (usableBidder.get(k)).get(j)!=0){
                            frequency++;
                        }
                    }
                    List<Double> tt = new LinkedList<>();
                    tt.addAll(t);
                    Collections.sort(tt);
                    for (int l=0; l<t.size(); l++){
                        if (tt.get(tt.size()-1).equals(t.get(l))){
                            System.out.println("Bidding Item: "+regItem[j]+"["+item.get(i)+"]");
                            if (tt.get(tt.size()-1)==0){
                                System.out.println("No winner");
                            }else{
                                //read file
                                try{        
                                    Scanner a = new Scanner (new FileInputStream(bidder));
                                        for (int m=0; a.hasNextLine(); m++){                        
                                            String s = a.nextLine();
                                            String[] details = s.split(", ");
                                            if (name.get(l).equals(details[0])){
                                                System.out.println("Winner: "+details[0]+"["+details[1]+" "+details[2]+']');
                                                System.out.println("Frequency of bidding: "+frequency);
                                            }
                                        }    
                                    a.close();
                                }catch(FileNotFoundException e){
                                    System.out.println("Cannot read from the file");         
                                } 
                                System.out.println("Price: "+tt.get(tt.size()-1));
                                
                            }    
                        }
                    }System.out.println("________________________________");
                }
            }
        }
        AuctionState e = new AuctionState(State.ENDED);
        e.steps();
    }
    
    public static List<Double> sort(File b){
        List<Double> temp = new LinkedList();
        //read file
        try{        
        Scanner a = new Scanner (new FileInputStream(b));
        for (int i=0; a.hasNextLine(); i++){   
            if (i>0 && i%2==0){
                String s = a.nextLine();
                String[]details = s.split(": ");
                temp.add(Double.parseDouble(details[1]));
            }else{
                a.nextLine();
            }
        }    
            a.close();
        }catch(FileNotFoundException e){
            System.out.println("Cannot read from the file");         
        } 
        return temp;
    }
    
    public static void end(){
        for (int i=0; i<5; i++){
            System.out.println(".");
        }
        System.out.println("!!!!!!!!!!!!!!!Time's Up!!!!!!!!!!!!!!!!");
        System.out.println("______________AUCTION END_______________");
    }
    
    
    
}
