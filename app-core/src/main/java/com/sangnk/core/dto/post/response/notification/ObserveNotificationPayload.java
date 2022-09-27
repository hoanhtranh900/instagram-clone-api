package com.sangnk.core.dto.post.response.notification;

public class ObserveNotificationPayload extends NotificationPayload {
    private boolean isObserved;

    public boolean isObserved() {
        return isObserved;
    }

    public void setObserved(boolean observed) {
        isObserved = observed;
    }
}
