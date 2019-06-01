package com.bingo.spring.jdbc.demo.dao;

import com.bingo.spring.jdbc.demo.entity.User;
import com.bingo.spring.jdbc.orm.BaseDaoSupport;
import com.bingo.spring.jdbc.orm.QueryRule;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author Bingo
 * @title: UserDao
 * @projectName java-hub
 * @description: TODO
 * @date 2019/6/1  23:35
 */
public class UserDao extends BaseDaoSupport<User,Integer> {
    @Override
    public String getPKColumn() {
        return "userid";
    }

    public void setDataSource(DataSource dataSource){
        super.setDatasource(dataSource);
    }

    public List<User> selectAll() throws Exception {
        QueryRule queryRule = new QueryRule(User.class);
        queryRule.setSql("select * from t_user");
        return super.select(queryRule);
    }

}
