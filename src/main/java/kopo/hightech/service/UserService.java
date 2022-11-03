package kopo.hightech.service;


import kopo.hightech.domain.Authority;
import kopo.hightech.domain.User;
import kopo.hightech.repository.AuthorityMapper;
import kopo.hightech.repository.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Inject
    private UserMapper userMapper;
    @Inject
    private AuthorityMapper authorityMapper;
    @Inject
    private PasswordEncoder passwordEncoder;

    public int countUsers() {
        return userMapper.count();
    }
    public int countAuthorities() {
        return authorityMapper.count();
    }

    public Boolean signup(User user) {
        if(user.getEmail() == null || user.getPassword() ==  null)
            return false;

        user.setPassword(passwordEncoder.encode(user.getPassword()));	// 사용자의 실제 비밀번호를 sha-256로 암호화함
        userMapper.insert(user);

        if(user.getEmail().contains("admin")) {
            Authority adminAuthority = new Authority();
            adminAuthority.setUserId(user.getId());
            adminAuthority.setRole("ROLE_ADMIN");
            authorityMapper.insert(adminAuthority);
        }
        else {

            Authority authority = new Authority();
            authority.setUserId(user.getId());
            authority.setRole("ROLE_USER");
            authorityMapper.insert(authority);
        }


        System.out.println("user created :" + new Date());
        return true;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByEmail(username); //DB에서 아이디를 바탕으로 검색
        if (user == null) { // 그런 아이디를 가진 유저가 없다면 에러 발생
            throw new UsernameNotFoundException("Invalid username/password.");
        }

        //그런 아이디를 가진 유저가 있다면 
        // 설정한 역할도 같이 불러옴
        // ROLE_USER, ROLE_ADMIN ...
        List<Authority> authorities = authorityMapper.findByUserId(user.getId());
        user.setAuthorities(authorities);
        System.out.println("user = " + user);
        return user;
    }

}
