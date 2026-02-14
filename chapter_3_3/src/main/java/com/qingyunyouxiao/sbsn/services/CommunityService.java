package com.qingyunyouxiao.sbsn.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qingyunyouxiao.sbsn.dto.UserDto;
import com.qingyunyouxiao.sbsn.dto.ImageDto;
import com.qingyunyouxiao.sbsn.dto.MessageDto;

@Service
public class CommunityService {

    public List<MessageDto> getCommunityMessages(UserDto user, int page) {
        return Arrays.asList(new MessageDto(1L, "First message " + user.getName()),
                new MessageDto(2L, "Second message " + user.getName()));
    }

    public List<ImageDto> getCommunityImages(int page) {
        return Arrays.asList(new ImageDto(1L, "First title", null),
                new ImageDto(2L, "Second title", null));
    }

    public MessageDto postMessage(MessageDto messageDto) {
        return new MessageDto(3L, "New message");
    }

    public ImageDto postImage(MultipartFile file, String title) {
        return new ImageDto(3L, "New Title", null);
    }
}