package com.nazobenkyo.petvaccine.application.api.security.privileges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrivilegeConstants {
    public static final String CREATE_USERS_PRIVILEGE = "CREATE_USERS_PRIVILEGE";
    public static final String UPDATE_USERS_PRIVILEGE = "UPDATE_USERS_PRIVILEGE";
    public static final String DELETE_USERS_PRIVILEGE = "DELETE_USERS_PRIVILEGE";
    public static final String GET_USERS_PRIVILEGE = "GET_USERS_PRIVILEGE";
    public static final String CREATE_PETS_PRIVILEGE = "CREATE_PETS_PRIVILEGE";
    public static final String UPDATE_PETS_PRIVILEGE = "UPDATE_PETS_PRIVILEGE";
    public static final String DELETE_PETS_PRIVILEGE = "DELETE_PETS_PRIVILEGE";
    public static final String GET_PETS_PRIVILEGE = "GET_PETS_PRIVILEGE";

    public static final List<String> USERS_ALL = Arrays.asList(
            CREATE_USERS_PRIVILEGE, UPDATE_USERS_PRIVILEGE, DELETE_USERS_PRIVILEGE, GET_USERS_PRIVILEGE
    );

    public static final List<String> PETS_ALL = Arrays.asList(
            CREATE_PETS_PRIVILEGE, UPDATE_PETS_PRIVILEGE, DELETE_PETS_PRIVILEGE, GET_PETS_PRIVILEGE
    );

    public static final List<String> ADMIN_PRIVILEGES = Stream.concat(USERS_ALL.stream(), PETS_ALL.stream()).collect(Collectors.toList());
    public static final List<String> DOCTOR_PRIVILEGES = new ArrayList<>(PETS_ALL);
}
