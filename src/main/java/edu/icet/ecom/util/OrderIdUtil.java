package edu.icet.ecom.util;

public class OrderIdUtil{
    public static String formatId(String orderId) {
        try {
            int num = Integer.parseInt(orderId);
            return String.format("O%03d" , num);
        } catch (NumberFormatException e){
            return orderId;
        }

    }

}
