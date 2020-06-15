package com.example.zzz;

public class Notification {
    String notifitedTo;
    String notifiedBy;
    String status;

    public Notification() {
    }

    public Notification(String notifitedTo, String notifiedBy, String status) {
        this.notifitedTo = notifitedTo;
        this.notifiedBy = notifiedBy;
        this.status = status;
    }

    public String getNotifitedTo() {
        return notifitedTo;
    }

    public void setNotifitedTo(String notifitedTo) {
        this.notifitedTo = notifitedTo;
    }

    public String getNotifiedBy() {
        return notifiedBy;
    }

    public void setNotifiedBy(String notifiedBy) {
        this.notifiedBy = notifiedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
