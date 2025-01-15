package org.diverbee.mapper;

import org.apache.ibatis.annotations.*;
import org.diverbee.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    public void add(Article article);



    List<Article> list(Integer userid, Integer categoryId, String state);


    @Select("select * from article where id = #{articleId} and create_user = #{userId}")
    Article detail(Integer userId, Integer articleId);

    @Update("update article set title = #{title},content = #{content},cover_img=#{coverImg}," +
            "state=#{state},update_time=#{updateTime} where id = #{id}")
    void update(Article article);

    @Delete("delete from article where id = #{id}")
    void del(Integer id);
}
