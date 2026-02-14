package com.qingyunyouxiao.sbsn.controllers;

import java.net.URI;
import java.util.List;

import com.qingyunyouxiao.sbsn.dto.*;
import com.qingyunyouxiao.sbsn.services.AuthenticationService;
import com.qingyunyouxiao.sbsn.services.CommunityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/community")
public class CommunityController {

    private final CommunityService communityService;
    private final AuthenticationService authenticationService;

    public CommunityController(CommunityService communityService, AuthenticationService authenticationService) {
        this.communityService = communityService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<MessageDto>> getCommunityMessages(
            @AuthenticationPrincipal OAuth2User principal,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        UserDto user = authenticationService.findOrCreateByLogin(principal);
        return ResponseEntity.ok(communityService.getCommunityMessages(user, page));
    }

    @GetMapping("/images")
    public ResponseEntity<List<ImageDto>> getCommunityImages(
            @RequestParam(value = "page", defaultValue = "0") int page) {
        return ResponseEntity.ok(communityService.getCommunityImages(page));
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDto> postMessage(@RequestBody MessageDto messageDto) {
        return ResponseEntity.created(URI.create("/v1/community/messages"))
                .body(communityService.postMessage(messageDto));
    }

    @PostMapping("/images")
    public ResponseEntity<ImageDto> postImage(@RequestParam MultipartFile file,
                                              @RequestParam(value = "title") String title) {
        return ResponseEntity.created(URI.create("/v1/community/images"))
                .body(communityService.postImage(file, title));
    }
}
