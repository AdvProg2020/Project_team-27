package model.log;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;


public abstract class Log {

    String logId;
    public  LocalDateTime localDateTimeForLog;
    DeliveryStatus deliveryStatus = DeliveryStatus.UNDER_VIEW;
    private boolean ItsFinal;

    private static ArrayList<Log> allLogs = new ArrayList<>();


    public Log(String logId) throws IOException {
        this.logId = logId;
        allLogs.add(this);
        localDateTimeForLog = LocalDateTime.now();
    }


    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public LocalDateTime getLocalDateTimeForLog() {
        return localDateTimeForLog;
    }

    public void setLocalDateTimeForLog(LocalDateTime localDateTimeForLog) {
        this.localDateTimeForLog = localDateTimeForLog;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public boolean isItsFinal() {
        return ItsFinal;
    }

    public static ArrayList<Log> getAllLogs() {
        return allLogs;
    }

    public static void setAllLogs(ArrayList<Log> allLogs) {
        Log.allLogs = allLogs;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }


    public void setItsFinal(boolean itsFinal) {
        ItsFinal = itsFinal;
    }


    public void deleteLog(String id) {
        allLogs.remove(BuyLog.getLogWithId(id));
    }


    public static Log getLogWithId(String logId) {
        for (Log allLog : allLogs) {
            if (allLog.logId.equals(logId)) {
                return allLog;
            }
        }
        return null;
    }


    public static boolean isThereLogWithID(String logId) {
        for (Log allLog : allLogs) {
            if (allLog.logId.equals(logId)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return "Log{" +
                "logId='" + logId + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                ", ItsFinal=" + ItsFinal +
                '}';
    }


}