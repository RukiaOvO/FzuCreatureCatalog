package com.catalog.service;

import com.catalog.dto.PageDTO;
import com.catalog.entity.Page;

import java.util.List;

public interface PageService
{
    void uploadPage(PageDTO pageDTO);

    Page getOnePageById(int pageId);

    List<Page> getPages();
}
