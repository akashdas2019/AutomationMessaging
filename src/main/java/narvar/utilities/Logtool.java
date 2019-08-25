package narvar.utilities;


import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.logging.*;

public class Logtool {

    static Logger logger;
    public Handler fileHandler;
    Formatter plainText;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    private Logtool() throws IOException {
        //instance the logger
        logger = Logger.getLogger(Logtool.class.getName());

        //instance the filehandler
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String logfileName = "log/automation"+timestamp+".log";
        //fileHandler = new FileHandler("log/automation.log",true);
        fileHandler = new FileHandler(logfileName,true);

        //instance formatter, set formatting, and handler
        plainText = new SimpleFormatter();

        fileHandler.setFormatter(plainText);
        logger.addHandler(fileHandler);
    }

    private static Logger getLogger(){
        if(logger == null){
            try {
                new Logtool();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return logger;
    }

    public static void log(Level level, String msg){
        getLogger().log(level, msg);
        System.out.println(msg);
    }
}
