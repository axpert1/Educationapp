package com.bibliophile.datas;

/**
 * Created by Vishal.
 */

public class AppUrls {

    // TODO: 9/28/2017 BASE URL
    public static final String BASE_URL = "http://wingstud.in/bibliophile/Api/";
  //  public static final String BASE_URL = "http://wingstud-infotech.in/bibliophile/Api/";
    public static final String TOKEN_URL = "f8dd97a64286094ad63ee33c4ce398f0";


    // TODO: 9/28/2017  CENTER
    public static final String CENTER_LOGIN = BASE_URL + "Center_controller/login/" + TOKEN_URL;//retro
    public static final String GET_COURSE = BASE_URL + "Center_controller/get_course_list/" + TOKEN_URL;//retro
    public static final String GET_STATE = BASE_URL + "Center_controller/get_state_list/" + TOKEN_URL;//retro
    public static final String GET_CITY = BASE_URL + "Center_controller/get_city_list/" + TOKEN_URL;//retro
    public static final String JOIN_US = BASE_URL + "Center_controller/add/" + TOKEN_URL;//retro
    public static final String GET_PACKAGES = BASE_URL + "Center_controller/get_packages_list/" + TOKEN_URL;//retro
    public static final String CENTER_PROFILE = BASE_URL + "Center_controller/get_center_profile/" + TOKEN_URL; //retro

    // TODO: 9/28/2017  COMMON
    public static final String FORGOT_PASSWORD = BASE_URL + "Common_controller/forgot_password/" + TOKEN_URL; //retro
    public static final String CHANGE_PASSWORD = BASE_URL + "Common_controller/change_password/" + TOKEN_URL; //loopj
    public static final String LOGOUT = BASE_URL + "Common_controller/logout/" + TOKEN_URL; //loopj
    public static final String CONTACT_US = BASE_URL + "Common_controller/contact_us/" + TOKEN_URL; //loopj
    public static final String GET_BANNER = BASE_URL + "Common_controller/get_banner/" + TOKEN_URL; //loopj

    // TODO: 9/28/2017  TEST CONTROLLER
    public static final String GET_TEST_SERIES = BASE_URL + "Test_controller/get_test_series/" + TOKEN_URL; //loopj
    public static final String CHANGE_SERIES_STATUS = BASE_URL + "Test_controller/change_series_status/" + TOKEN_URL; //loopj
    public static final String GET_QUESTIONS_BY_MAIN_SERIES = BASE_URL + "Test_controller/get_questions_by_main_series/" + TOKEN_URL; //loopj
  public static final String GET_PACKAGES_STUDENT = BASE_URL + "Test_controller/get_packages_list/" + TOKEN_URL;//retro


    // TODO: 9/28/2017  STUDENT
    public static final String GET_COURSE_FOR_STUDENT =  "Student_controller/get_center_course/" + TOKEN_URL; //retro
    public static final String ADD_STUDENT = BASE_URL + "Student_controller/add/" + TOKEN_URL; //retro
    public static final String STUDENT_LOGIN = BASE_URL + "Student_controller/login/" + TOKEN_URL; //retro
    public static final String GET_STUDENT_LIST = BASE_URL + "Student_controller/get_student_list/" + TOKEN_URL; //retro
    public static final String GET_STUDENT_DETAILS = BASE_URL + "Student_controller/get_student_details/" + TOKEN_URL; //loop









    public static final String GENERATE_OTP_URL = BASE_URL + "user/sendOtp";
    public static final String REGISTER_URL = BASE_URL + "user/register";
    public static final String LOGIN_URL = BASE_URL + "user/login";
    public static final String FORGOT_PASSWORD_URL = BASE_URL + "forgotpassword";
    public static final String CONTINUE_WITH_FB_URL = BASE_URL + "user/continueWithFacebook";
    public static final String UPDATE_LOCATION_URL = BASE_URL + "user/updateLocation";
    public static final String NEAR_BY_URL = BASE_URL + "truck/nearby";
    public static final String LOGOUT_URL = BASE_URL + "user/logout";
    public static final String EDIT_PROFILE = BASE_URL + "user/editProfile";
    public static final String GET_MENU = BASE_URL + "getMenu";
    public static final String GET_HISTORY = BASE_URL + "order/history";
    public static final String CANCEL_ORDER = BASE_URL + "order/cancel";
    public static final String CONFIRM_ORDER = BASE_URL + "order/confirm";
    public static final String EMAIL_INVOICE = BASE_URL + "email-invoice";
    public static final String GET_FILTER = BASE_URL + "filter";
    public static final String GET_HISTORY_DETAIL = BASE_URL + "order/history/detail";
    public static final String GET_NOTIFICATION = BASE_URL + "notification/list";
    public static final String ADD_TO_CART = BASE_URL + "add-to-cart";
    public static final String CHECK_CART = BASE_URL + "check-cart";
    public static final String EDIT_CART = BASE_URL + "order/edit";
    public static final String ADD_FAV_TO_CART = BASE_URL + "add-favourite-to-cart";
    public static final String GET_CART = BASE_URL + "get-cart";
    public static final String GET_MENU_ITEM = BASE_URL + "getMenuItem";
    public static final String REMOVE_ITEM_CART = BASE_URL + "remove-from-cart";
    public static final String CHECK_DELIVERY_RANGE = BASE_URL + "check-delivery-range";
    public static final String PLACE_ORDER = BASE_URL + "place-order";
    public static final String REORDER = BASE_URL + "reorder";
    public static final String ADD_CARD = BASE_URL + "user/payment-method/add";
    public static final String CLEAR_CART = BASE_URL + "clear-cart";
    public static final String CART_ITEM_COUNT = BASE_URL + "cart-item-count";
    public static final String APPLY_PROMO_CODE = BASE_URL + "promoCode/apply";
    public static final String REMOVE_PROMO_CODE = BASE_URL + "remove-promo-code";
    public static final String GIVE_RATING = BASE_URL + "give-rating";
    public static final String FOLLOW_TRUCK = BASE_URL + "user/follow-truck";
    public static final String GET_TAX_INFO = BASE_URL + "get-tax-and-fee";
    public static final String SEND_SUPPORT_EMAIL = BASE_URL + "send-support-email";
    public static final String HOT_DEAL_LIST = BASE_URL + "hot-deal/list";
    public static final String ACTIVATE_DEAL = BASE_URL + "activate-deal";
    public static final String CLIENT_TOKEN = BASE_URL + "user/client-token";
    public static final String TERMS_CONDITION_URL = "https://www.google.co.in/";
    public static final String PRIVACY_POLICY_URL = "https://www.youtube.com/";
}
