/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vickrey.auction;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class TrialTimer {
    Timer timer;

    public TrialTimer(int seconds) {
        timer = new Timer();
        timer.schedule(new TrialReminder(), seconds*1000);
    }
    
    class TrialReminder extends TimerTask{

        @Override
        public void run() {
            timer.cancel();
            try {
                User.main(new String [0]);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TrialTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
