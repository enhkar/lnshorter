package local.ln.services;

import local.ln.entities.UserEntity;
import local.ln.data.UserData;
import local.ln.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(UserData userData) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userData, userEntity);
        encodePassword(userEntity, userData);
        userRepository.save(userEntity);
    }

    private void encodePassword(UserEntity userEntity, UserData userData){
        userEntity.setPassword(passwordEncoder.encode(userData.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Not found!");
        }

        return user;
    }
}
