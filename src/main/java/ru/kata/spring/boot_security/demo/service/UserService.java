package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    public List<User> index();
    public User show(long id);
    public boolean save(User user);
    public void delete(long id);

    public User findByUsername(String username);
}
