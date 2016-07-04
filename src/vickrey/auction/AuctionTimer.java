/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vickrey.auction;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author User
 */
public class AuctionTimer {
    Timer timer;

    public AuctionTimer(int seconds) {
        timer = new Timer();
        timer.schedule(new TrialReminder(), seconds*1000);
        
    }
    
    class TrialReminder extends TimerTask{

        @Override
        public void run() {
            timer.cancel();
            Auction.end();
            Auction.finalization();
        }
        
    }

}
