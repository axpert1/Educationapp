package com.bibliophile.service;

/**
 * Created by wingstud on 21-04-2017.
 */
public class Config {
    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";
    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE = 101;
    public static final String SHARED_PREF = "ah_firebase";    //
    public static final String SHARED_NC_PREF = "nc_data";
    public static final String NC_TYPE_ID = "nctypeid";
    public static final String NC_DEPARTMENT_ID = "nc_department_id";
    public static final String NC_EMPL0YED_ID = "nc_employed_id";
    public static final String NC_ROOM_NO = "nc_room_no";
    public static final String NC_BOOKING_NO = "nc_booking_no";
    public static final String FCM_REG_ID = "fcm_reg_id";
}
