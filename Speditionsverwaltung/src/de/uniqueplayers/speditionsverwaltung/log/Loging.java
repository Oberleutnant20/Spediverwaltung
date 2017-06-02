/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.uniqueplayers.speditionsverwaltung.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Oberleutnant20
 */
public class Loging {
    private Logger logger = Logger.getLogger("MyLog");  
    FileHandler fh;  

    public Loging() {
        SimpleDateFormat formatDay = new SimpleDateFormat();
        try {
            fh = new FileHandler("C:/temp/test/MyLogFile_"
                + formatDay.format(Calendar.getInstance().getTime()) + ".log");
            logger.addHandler(fh);
            SimpleFormatter format = new SimpleFormatter();
            fh.setFormatter(format);
            logger.addHandler(fh);
            logger.info("Test Message :D");
        } catch (SecurityException | IOException ex) {
            Logger.getLogger(Loging.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void doLogging() {
        logger.info("info msg");
        logger.severe("error message");
        logger.fine("fine message"); //won't show because to high level of logging
    }
}
