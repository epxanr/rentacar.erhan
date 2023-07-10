package com.erhan.rentacar.service;

import com.erhan.rentacar.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.erhan.rentacar.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public Iterable<User> getAll()
    {
        return repository.findAll();
    }

    public Optional<User> getById(Long id)
    {
        return repository.findById(id);
    }

    public Optional<User> getByEmail(String email)
    {
        return repository.findByEmail(email);
    }

    public Optional<User> create(String email, String password)
    {
        if(email == null || password == null) return Optional.empty();

        if(repository.existsByEmail(email))
            return Optional.empty();

        var user = new User(email, password);
        return Optional.of(repository.save(user));
    }

    public Optional<User> update(long id, String email, String password)
    {
        var userOpt = repository.findById(id);
        if(userOpt.isEmpty()) return Optional.empty();

        var user = userOpt.get();

        if(email != null)
            user.setEmail(email);
        if(password != null)
            user.setPassword(password);

        return Optional.of(repository.save(user));
    }

    public boolean deleteById(Long id)
    {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
