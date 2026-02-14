package com.qingyunyouxiao.sbsn.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.qingyunyouxiao.sbsn.dto.*;

@Service
public class UserService {
    
    public ProfileDto getProfile(int userId) {
        return new ProfileDto(new UserSummaryDto(1L, "Philip", "Harder"), 
                Arrays.asList(new UserSummaryDto(2L,"Alex", "Buey")),
                Arrays.asList(new MessageDto(1L, "Message")),
                Arrays.asList(new ImageDto(1L, "Title", null)));
    }

    
    public void addFriend(Long friendId) {
        return ; // nothing to do at the moment
    }
    
    public List<UserSummaryDto> searchUsers(String term) {
        return Arrays.asList(new UserSummaryDto(1L, "Philip", "Harder"),
                new UserSummaryDto(2L,"Alex", "Buey"));
    }

    public UserDto signUp(SignUpDto user) {
        return new UserDto(1L,"qingyun", "youxiao", "login","token");
    }

    public void signOut(UserDto user) {
        return ; // nothing to do at the moment
    }
}
