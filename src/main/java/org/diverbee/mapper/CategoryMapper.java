package org.diverbee.mapper;

import org.apache.ibatis.annotations.*;
import org.diverbee.pojo.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //新增一条记录
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            " values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    public void add(Category category);

    //查询对应id的所有分类
    @Select("select * from category where create_user=${userid}")
    List<Category> list(Integer userid);

    @Select("select * from category where id=#{id}")
    Category findById(Integer id);

    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now() where id = #{id}")
    void update(Category category);

    @Delete("delete from category where id=#{id}")
    void delById(Integer id);
}
