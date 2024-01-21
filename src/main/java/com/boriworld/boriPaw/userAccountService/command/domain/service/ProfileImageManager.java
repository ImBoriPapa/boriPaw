package com.boriworld.boriPaw.userAccountService.command.domain.service;

import com.boriworld.boriPaw.userAccountService.command.domain.value.ProfileImage;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageManager {
    ProfileImage uploadImage(MultipartFile multipartFile);
    void delete(ProfileImage profileImage);

}
