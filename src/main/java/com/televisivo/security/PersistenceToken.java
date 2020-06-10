package com.televisivo.security;

import java.util.Date;
import java.util.Optional;

import com.televisivo.model.PersistenceTokenLogin;
import com.televisivo.repository.PersistenceTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersistenceToken implements PersistentTokenRepository {

    @Autowired
    private PersistenceTokenRepository persistenceTokenRepository;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        PersistenceTokenLogin login = new PersistenceTokenLogin();
        login.setSeries(token.getSeries());
        login.setUsername(token.getUsername());
        login.setToken(token.getTokenValue());
        login.setLestUsed(token.getDate());
        persistenceTokenRepository.save(login);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistenceTokenLogin login = persistenceTokenRepository.getOne(series);
        login.setToken(tokenValue);
        login.setLestUsed(lastUsed);
        persistenceTokenRepository.save(login);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        Optional<PersistenceTokenLogin> login = persistenceTokenRepository.findById(seriesId);
        if (login.isPresent()) {
            return new PersistentRememberMeToken(login.get().getUsername(), login.get().getSeries(), login.get().getToken(), login.get().getLestUsed());
        }
        return null;
    }

    @Override
    public void removeUserTokens(String username) {
        persistenceTokenRepository.deletePersistenceTokenLogin(username);
    }
}