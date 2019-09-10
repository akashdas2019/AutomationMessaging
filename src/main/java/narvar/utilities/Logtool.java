package narvar.utilities;


import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.*;

public class Logtool {

    static Logger logger;
    public Handler fileHandler;


    private Logtool() throws IOException {
        //instance the logger
        logger = Logger.getLogger(Logtool.class.getName());
        logger.setLevel(Level.ALL);

        //instance the filehandler
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String logfileName = "log/automation"+timestamp+".log";
        fileHandler = new FileHandler(logfileName,true);
        fileHandler.setLevel(Level.ALL);
        fileHandler.setFormatter(new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] [%2$-7sgit ] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        });

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
