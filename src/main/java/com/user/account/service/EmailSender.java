package com.user.account.service;

public interface EmailSender {
    void send(String to, String email, String subject);
    public String buildEmail(String otp, double amount);
    public void rechargeEmail(String to, String otp, double amount);
}
