package com.catalog.mapper;

import com.catalog.annotation.AutoFill;
import com.catalog.entity.Page;
import com.catalog.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PageMapper
{
    @AutoFill(OperationType.INSERT)
    void insert(Page page);

    @Select("select * from page where id = #{pageId}")
    Page getPageById(int pageId);

    @Select("select * from page")
    List<Page> getAllPages();
}
