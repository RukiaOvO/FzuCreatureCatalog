package com.catalog.mapper;

import com.catalog.entity.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ImgMapper
{
    @Select("select * from img where id = #{id}")
    Img getImageById(int id);

    @Select("select count(*) from user_img where user_id = #{id}")
    int getImgNumByUserId(int id);
}

