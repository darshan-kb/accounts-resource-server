package com.user.account.service;

public interface EmailSender {
    void send(String to, String email, String subject);
    public String buildEmail(String otp,String user, double amount);
    public void rechargeEmail(String to, String user, String otp, double amount);
}
