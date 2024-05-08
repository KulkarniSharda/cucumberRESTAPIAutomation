package com.agileautomation.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
public class CreateContactResponsePojo {

        private String firstName;
        private String lastName;
        private Long id;
        private String type;
        private List<Map<String, String>> properties;

    }

