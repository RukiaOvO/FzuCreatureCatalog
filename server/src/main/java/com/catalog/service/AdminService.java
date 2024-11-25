package com.catalog.service;

import com.catalog.dto.AdminLoginDTO;

public interface AdminService
{
    Boolean login(AdminLoginDTO adminLoginDTO);

    String getAccessToken();
}
