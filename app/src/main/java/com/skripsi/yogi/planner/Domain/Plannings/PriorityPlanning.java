package com.skripsi.yogi.planner.Domain.Plannings;

import com.skripsi.yogi.planner.Common.EntityBase;

import java.math.BigDecimal;

public class PriorityPlanning extends EntityBase {
    private int priority;
    private BigDecimal recommendation;
    private String goalName;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public BigDecimal getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(BigDecimal recommendation) {
        this.recommendation = recommendation;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }
}
