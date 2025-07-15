package com.book.inventory.management.bookim.model.dto;

import java.time.LocalDateTime;

public interface BaseDto {
    String getId();

    void setId(String id);

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);

    String getCreatedBy();

    void setCreatedBy(String createdBy);

    String getUpdatedBy();

    void setUpdatedBy(String updatedBy);

    boolean isDeleted();

    void setDeleted(boolean deleted);
}