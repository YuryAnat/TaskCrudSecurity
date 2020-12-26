package crud.services;

import crud.dao.UserDao;
import crud.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServicesImpl implements UserDetailsService {
    private UserDao userDao;

    public UserDetailServicesImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User userByEmail = userDao.getUserByEmail(s);
        return userByEmail;
    }
}
