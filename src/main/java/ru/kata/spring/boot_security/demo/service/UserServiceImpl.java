package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDAO = userDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> index() {
        return userDAO.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User show(long id) {

        return userDAO.findById(id).get();
    }


    @Override
    public boolean save(User user) {
        User anotherUser = this.findByUsername(user.getUsername());
        if (anotherUser != null && anotherUser.getId() != user.getId()) {
            return false;
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDAO.save(user);
        return true;
    }

    @Override
    public void delete(long id) {
        userDAO.deleteById(id);
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }
}
