//package com.example.boot;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailService implements UserDetailsService {
//    private final MembersRepositry membersRepositry;
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        membersRepositry.findByUsername(username);
////        DB에서 username을 가진 유저를 찾아와서
//        return new Members(username, password, 권한)
//    }
//}
