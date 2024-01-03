package com.zhouyf.zhouyfbatis.mapper;

import com.zhouyf.zhouyfbatis.model.ArticlePO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ArticleMapper {

    @Select("""
            select id, user_id, title, summary, read_count, create_time, update_time\s
                    from article where id = #{id};
            """)

    @Results(id = "BaseArticleMap", value = {
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "title", property = "title"),
            @Result(column = "summary", property = "summary"),
            @Result(column = "read_count", property = "readCount"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime")
    })
    ArticlePO selectById(@Param("id") int id);

    //insert
    @Insert("""
            insert into article(user_id, title, summary,
            read_count, create_time, update_time)
            values(#{userId}, #{title}, #{summary},
            #{readCount}, #{createTime}, #{updateTime})
            """)
    int insertArticle(ArticlePO articlePO);

    //update
    @Update("""
            update article set read_count = #{readCount} where id = #{id};
            """)
    int updateReadCount(Integer id, Integer readCount);

    //delete
    @Delete("""
            delete from article where id = #{id}
            """)
    int deleteArticle(Integer id);
}
