/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vickrey.auction;

/**
 *
 * @author User
 */
public class AuctionState {
    public enum State{
        QUEUED, RUNNING, CANCELLED, ENDED;
    }
    State auctionState;

    public AuctionState(State auctionState) {
        this.auctionState = auctionState;
    }
    
    public void steps(){
        switch(auctionState){
            case RUNNING:
                if (AuctionType.getMainOption()==3){
                    Auction.baStart();
                }else{
                    Auction.start();
                }
                break;
            case CANCELLED: 
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
                System.out.println("The auction has now been terminated.");
                break;
            case ENDED: 
                System.out.println("-----------------------------------------"); 
                System.out.println("The auction has come to an end.");
                System.out.println("THANK YOU SO MUCH & SEE U AGAIN NEXT TIME");
                break;
        }
    }
}
