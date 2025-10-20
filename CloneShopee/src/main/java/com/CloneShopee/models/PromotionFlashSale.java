package com.CloneShopee.models;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "promotiondlashsale")
public class PromotionFlashSale extends Promotion {
    @NotNull(message = "Trường startTime là bắt buộc")
    private LocalTime startTime;
    @NotNull(message = "Trường endTime là bắt buộc")
    private LocalTime endTime;

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

}
