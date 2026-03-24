package kz.safetrip.safetrip.repository.impl;

import kz.safetrip.safetrip.enumeration.UserRole;
import kz.safetrip.safetrip.model.entity.User;
import kz.safetrip.safetrip.repository.UserRepository;
import kz.safetrip.safetrip.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    @Override @Transactional public User save(User user) { return userJpaRepository.save(user); }
    @Override public Optional<User> findById(Long id) { return userJpaRepository.findById(id); }
    @Override public boolean existsById(Long id) { return userJpaRepository.existsById(id); }
    @Override public Page<User> findAll(Pageable pageable) { return userJpaRepository.findAll(pageable); }
    @Override public Page<User> findByRole(UserRole role, Pageable pageable) { return userJpaRepository.findByRole(role, pageable); }
    @Override public Page<User> findByIsActive(Boolean isActive, Pageable pageable) { return userJpaRepository.findByIsActive(isActive, pageable); }
    @Override @Transactional public void deleteById(Long id) { userJpaRepository.deleteById(id); }
    @Override public List<User> findAll() { return userJpaRepository.findAll(); }
    @Override public Optional<User> findByEmail(String email) { return userJpaRepository.findByEmail(email); }
    @Override public boolean existsByEmail(String email) { return userJpaRepository.existsByEmail(email); }
    @Override public List<User> findAllByRole(UserRole role) { return userJpaRepository.findAllByRole(role); }
}
