package com.skripsi.yogi.planner.Domain.Plannings;

import com.skripsi.yogi.planner.Common.EntityBase;
import com.skripsi.yogi.planner.Domain.Users.User;

import java.math.BigDecimal;

public class Planning extends EntityBase {
    private User user;
    private String goalName;
    private int timePeriod;
    private BigDecimal currentCost;
    private BigDecimal inflationRate;
    private int requiredRate;
    // Simulasi Bank
    private BigDecimal alreadyInvest;
    private BigDecimal biayaAdmin;
    private BigDecimal interestRate;
    private BigDecimal pajakBunga;

    private BigDecimal futureCost;
    private BigDecimal lumpsum;
    private BigDecimal monthlyInvest;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public BigDecimal getFutureCost() {
        return futureCost;
    }

    public void setFutureCost(BigDecimal futureCost) {
        this.futureCost = futureCost;
    }

    public BigDecimal getAlreadyInvest() {
        return alreadyInvest;
    }

    public void setAlreadyInvest(BigDecimal alreadyInvest) {
        this.alreadyInvest = alreadyInvest;
    }

    public BigDecimal getLumpsum() {
        return lumpsum;
    }

    public void setLumpsum(BigDecimal lumpsum) {
        this.lumpsum = lumpsum;
    }

    public BigDecimal getMonthlyInvest() {
        return monthlyInvest;
    }

    public void setMonthlyInvest(BigDecimal monthlyInvest) {
        this.monthlyInvest = monthlyInvest;
    }

    public int getRequiredRate() {
        return requiredRate;
    }

    public void setRequiredRate(int requiredRate) {
        this.requiredRate = requiredRate;
    }

    public BigDecimal getInflationRate() {
        return inflationRate;
    }

    public void setInflationRate(BigDecimal inflationRate) {
        this.inflationRate = inflationRate;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getCurrentCost() {
        return currentCost;
    }

    public void setCurrentCost(BigDecimal currentCost) {
        this.currentCost = currentCost;
    }

    public int getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(int timePeriod) {
        this.timePeriod = timePeriod;
    }

    public BigDecimal getBiayaAdmin() {
        return biayaAdmin;
    }

    public void setBiayaAdmin(BigDecimal biayaAdmin) {
        this.biayaAdmin = biayaAdmin;
    }

    public BigDecimal getPajakBunga() {
        return pajakBunga;
    }

    public void setPajakBunga(BigDecimal pajakBunga) {
        this.pajakBunga = pajakBunga;
    }
}
