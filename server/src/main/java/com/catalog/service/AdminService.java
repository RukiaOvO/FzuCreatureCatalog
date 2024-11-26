package com.catalog.service;

import com.catalog.dto.AdminLoginDTO;
import com.catalog.entity.Card;

import java.util.List;

public interface AdminService
{
    Boolean login(AdminLoginDTO adminLoginDTO);

    String getAccessToken();

    void deleteCard(int cardId);

    List<Card> showHomeCards();
}
