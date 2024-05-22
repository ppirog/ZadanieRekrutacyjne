package org.zadanierekrutacyjne.domain.loginandregister;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

class UserRepoTestImpl implements UserRepository {

    private final HashMap<Long, User> users = new HashMap<>();

    @Override
    public Optional<User> findByLogin(final String login) {
        return users.values().stream()
                .filter(user -> user.getLogin().equals(login))
                .findFirst();
    }

    @Override
    public void updateLoginAndPasswordById(final String login, final String password, final Long id) {
        final User user = users.get(id);
        user.setLogin(login);
        user.setPassword(password);
        users.put(id, user);
    }

    @Override
    public void deleteByLogin(final String login) {
        users.values().removeIf(user -> user.getLogin().equals(login));
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends User> S saveAndFlush(final S entity) {
        return null;
    }

    @Override
    public <S extends User> List<S> saveAllAndFlush(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(final Iterable<User> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(final Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public User getOne(final Long aLong) {
        return null;
    }

    @Override
    public User getById(final Long aLong) {
        return null;
    }

    @Override
    public User getReferenceById(final Long aLong) {
        return null;
    }

    @Override
    public <S extends User> Optional<S> findOne(final Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends User> List<S> findAll(final Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends User> List<S> findAll(final Example<S> example, final Sort sort) {
        return List.of();
    }

    @Override
    public <S extends User> Page<S> findAll(final Example<S> example, final Pageable pageable) {
        return null;
    }

    @Override
    public <S extends User> long count(final Example<S> example) {
        return 0;
    }

    @Override
    public <S extends User> boolean exists(final Example<S> example) {
        return false;
    }

    @Override
    public <S extends User, R> R findBy(final Example<S> example, final Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends User> S save(final S entity) {
        entity.setId((long) users.size() + 1);
        users.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends User> List<S> saveAll(final Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<User> findById(final Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(final Long aLong) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public List<User> findAllById(final Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return users.size();
    }

    @Override
    public void deleteById(final Long aLong) {

    }

    @Override
    public void delete(final User entity) {

    }

    @Override
    public void deleteAllById(final Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(final Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<User> findAll(final Sort sort) {
        return List.of();
    }

    @Override
    public Page<User> findAll(final Pageable pageable) {
        return null;
    }
}
