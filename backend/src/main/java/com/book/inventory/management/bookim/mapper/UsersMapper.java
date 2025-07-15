package com.book.inventory.management.bookim.mapper;

import org.springframework.stereotype.Service;

import com.book.inventory.management.bookim.model.dto.UserDto;
import com.book.inventory.management.bookim.model.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsersMapper implements BaseMapper<User, UserDto> {

    @Override
    public UserDto toDto(User entity) {
        return new UserDto(
            entity.getId() != null ? Long.valueOf(entity.getId()) : null, // id
            null, // username (not present in User)
            entity.getEmail(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getPassword(),
            null // books (UserDto expects a single Book, User has List<Book>)
        );
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId() != null ? String.valueOf(dto.getId()) : null);
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setBooks(null); // UserDto has a single Book, User expects List<Book>
        return user;
    }

}
