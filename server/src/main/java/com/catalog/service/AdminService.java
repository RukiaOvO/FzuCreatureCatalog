package com.catalog.service;

import com.catalog.dto.AdminLoginDTO;
import com.catalog.dto.RejectCardDTO;
import com.catalog.entity.Card;

import java.util.List;

public interface AdminService
{
    Boolean login(AdminLoginDTO adminLoginDTO);

    String getAccessToken();

    void deleteCard(RejectCardDTO rejectCardDTO);

    List<Card> showHomeCards();
}
