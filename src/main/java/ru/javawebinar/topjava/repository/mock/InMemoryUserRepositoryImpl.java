package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()){
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> userList = new ArrayList<>(repository.values());
        userList.sort((User o1, User o2) -> (o1.getName().compareTo(o2.getName())));
//        userList.sort(new Comparator<User>() {
//            @Override
//            public int compare(User o1, User o2) {
//                return o1.getName().compareTo(o2.getName());
//            }
//        });
        return userList;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        User userToFindByEmail = null;
        for (User user : getAll()){
            if (user.getEmail().equals(email)) {
                userToFindByEmail = user;
                break;
            }
        }
        return userToFindByEmail;
    }

}
