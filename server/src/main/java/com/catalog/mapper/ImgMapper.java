package com.catalog.mapper;

import com.catalog.annotation.AutoFill;
import com.catalog.entity.Img;
import com.catalog.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ImgMapper
{
    Img getImageById(int id);

    @Select("select count(*) from user_img where user_id = #{id}")
    int getImgNumByUserId(int id);

    @AutoFill(OperationType.INSERT)
    int insert(Img img);
}

