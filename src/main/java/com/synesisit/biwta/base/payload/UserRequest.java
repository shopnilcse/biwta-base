package com.synesisit.biwta.base.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequest {

        private Long id;
        private String contactNo;
        private String name;
        private String userName;
        private String password;
        private String email;
        private String imageUrl;
        private boolean isDisabled;
}


