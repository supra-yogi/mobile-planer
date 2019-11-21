package com.skripsi.yogi.planner.Domain.Batasans;

import com.skripsi.yogi.planner.Common.EntityBase;
import com.skripsi.yogi.planner.Domain.Users.User;

import java.math.BigDecimal;

public class Batasan extends EntityBase {
    private User user;
    // Batasan Jangka Waktu
    private BigDecimal waktuCepatFrom;
    private BigDecimal waktuCepatTo;
    private BigDecimal waktuLamaFrom;
    private BigDecimal waktuLamaTo;

    // Batasan Harga
    private BigDecimal biayaRendahFrom;
    private BigDecimal biayaRendahTo;
    private BigDecimal biayaSedangFrom;
    private BigDecimal biayaSedangTo;
    private BigDecimal biayaTinggiFrom;
    private BigDecimal biayaTinggiTo;

    // Batasan Interest
    private BigDecimal kebutuhanRendahFrom;
    private BigDecimal kebutuhanRendahTo;
    private BigDecimal kebutuhanTinggiFrom;
    private BigDecimal kebutuhanTinggiTo;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getWaktuCepatFrom() {
        return waktuCepatFrom;
    }

    public void setWaktuCepatFrom(BigDecimal waktuCepatFrom) {
        this.waktuCepatFrom = waktuCepatFrom;
    }

    public BigDecimal getWaktuCepatTo() {
        return waktuCepatTo;
    }

    public void setWaktuCepatTo(BigDecimal waktuCepatTo) {
        this.waktuCepatTo = waktuCepatTo;
    }

    public BigDecimal getWaktuLamaFrom() {
        return waktuLamaFrom;
    }

    public void setWaktuLamaFrom(BigDecimal waktuLamaFrom) {
        this.waktuLamaFrom = waktuLamaFrom;
    }

    public BigDecimal getWaktuLamaTo() {
        return waktuLamaTo;
    }

    public void setWaktuLamaTo(BigDecimal waktuLamaTo) {
        this.waktuLamaTo = waktuLamaTo;
    }

    public BigDecimal getBiayaRendahFrom() {
        return biayaRendahFrom;
    }

    public void setBiayaRendahFrom(BigDecimal biayaRendahFrom) {
        this.biayaRendahFrom = biayaRendahFrom;
    }

    public BigDecimal getBiayaRendahTo() {
        return biayaRendahTo;
    }

    public void setBiayaRendahTo(BigDecimal biayaRendahTo) {
        this.biayaRendahTo = biayaRendahTo;
    }

    public BigDecimal getBiayaSedangFrom() {
        return biayaSedangFrom;
    }

    public void setBiayaSedangFrom(BigDecimal biayaSedangFrom) {
        this.biayaSedangFrom = biayaSedangFrom;
    }

    public BigDecimal getBiayaSedangTo() {
        return biayaSedangTo;
    }

    public void setBiayaSedangTo(BigDecimal biayaSedangTo) {
        this.biayaSedangTo = biayaSedangTo;
    }

    public BigDecimal getBiayaTinggiFrom() {
        return biayaTinggiFrom;
    }

    public void setBiayaTinggiFrom(BigDecimal biayaTinggiFrom) {
        this.biayaTinggiFrom = biayaTinggiFrom;
    }

    public BigDecimal getBiayaTinggiTo() {
        return biayaTinggiTo;
    }

    public void setBiayaTinggiTo(BigDecimal biayaTinggiTo) {
        this.biayaTinggiTo = biayaTinggiTo;
    }

    public BigDecimal getKebutuhanRendahFrom() {
        return kebutuhanRendahFrom;
    }

    public void setKebutuhanRendahFrom(BigDecimal kebutuhanRendahFrom) {
        this.kebutuhanRendahFrom = kebutuhanRendahFrom;
    }

    public BigDecimal getKebutuhanRendahTo() {
        return kebutuhanRendahTo;
    }

    public void setKebutuhanRendahTo(BigDecimal kebutuhanRendahTo) {
        this.kebutuhanRendahTo = kebutuhanRendahTo;
    }

    public BigDecimal getKebutuhanTinggiFrom() {
        return kebutuhanTinggiFrom;
    }

    public void setKebutuhanTinggiFrom(BigDecimal kebutuhanTinggiFrom) {
        this.kebutuhanTinggiFrom = kebutuhanTinggiFrom;
    }

    public BigDecimal getKebutuhanTinggiTo() {
        return kebutuhanTinggiTo;
    }

    public void setKebutuhanTinggiTo(BigDecimal kebutuhanTinggiTo) {
        this.kebutuhanTinggiTo = kebutuhanTinggiTo;
    }
}
