package com.catalog.service.Impl;

import com.catalog.dto.PageDTO;
import com.catalog.entity.Page;
import com.catalog.mapper.PageMapper;
import com.catalog.service.PageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PageServiceImpl implements PageService
{
    @Autowired
    private PageMapper pageMapper;

    @Override
    public void uploadPage(PageDTO pageDTO)
    {
        Page page = new Page();
        BeanUtils.copyProperties(pageDTO, page);
        pageMapper.insert(page);
    }

    @Override
    public Page getOnePageById(int pageId)
    {
        return pageMapper.getPageById(pageId);
    }

    @Override
    public List<Page> getPages() {
        return pageMapper.getAllPages();
    }
}
