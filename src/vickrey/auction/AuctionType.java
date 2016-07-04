/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vickrey.auction;

import java.util.Scanner;

/**
 *
 * @author User
 */
public class AuctionType {
    private static int mainOption = 0;
    
    public static void select(){
        Scanner input = new Scanner (System.in);
        System.out.println("-----------------------------------------");
        System.out.println("Choose type of auction: ");
        System.out.println("1. Vikrey Auction");
        System.out.println("2. First-price Sealed-bid Auction");
        System.out.println("3. Blind Auction");
        while (mainOption<1||mainOption>3){
            System.out.print("Enter: ");
            mainOption = input.nextInt();
        }
    }

    public static int getMainOption() {
        return mainOption;
    }
    
    
}
