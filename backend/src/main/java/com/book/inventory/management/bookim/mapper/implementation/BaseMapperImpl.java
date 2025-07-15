package com.book.inventory.management.bookim.mapper.implementation;

import java.time.LocalDateTime;

import com.book.inventory.management.bookim.mapper.BaseMapper;
import com.book.inventory.management.bookim.model.BaseEntity;
import com.book.inventory.management.bookim.model.dto.BaseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseMapperImpl<E extends BaseEntity, D extends BaseDto> implements BaseMapper<E, D> {

    @Override
    public D toDto(E entity) {
        if (entity == null) {
            return null;
        }
        D dto = createDto();
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setDeleted(entity.isDeleted());
        return dto;
    }

    @Override
    public E toEntity(D dto) {
        if (dto == null) {
            return null;
        }
        E entity = createEntity();
        entity.setId(dto.getId());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setDeleted(dto.isDeleted());
        return entity;
    }

    protected abstract D createDto();

    protected abstract E createEntity();
}