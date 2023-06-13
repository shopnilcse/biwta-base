package com.synesisit.biwta.base.service;

import com.synesisit.biwta.base.model.User;
import com.synesisit.biwta.base.service.IService.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServcie implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final EntityManagerFactory emf;
    @PersistenceContext
    EntityManager em;

    public UserServcie(final PasswordEncoder passwordEncoder, EntityManagerFactory emf) {
        this.passwordEncoder = passwordEncoder;
        this.emf = emf;
    }
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     *
     * @return List
     */
    @Override
    public List<User> findAll() {
        return null;
    }


}

