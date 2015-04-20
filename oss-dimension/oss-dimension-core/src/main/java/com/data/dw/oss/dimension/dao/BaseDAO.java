package com.data.dw.oss.dimension.dao;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;

public class BaseDAO {
	
	@Resource
	private SqlSessionTemplate sqlSession;

	public void setSqlSession(SqlSessionTemplate sqlSession) {
		this.sqlSession = sqlSession;
	}

	/**
	 * 方法说明：返回单个对象信息
	 */
	public Object queryOne(String key, Object obj) {
		return sqlSession.selectOne(key, obj);
	}

	/**
	 * 方法说明：返回集合信息
	 */
	public Object queryList(String key, Object obj) {
		return sqlSession.selectList(key, obj);
	}

	public Object queryList(String key, Object obj, int offset, int count) {
		RowBounds rowBounds = new RowBounds(offset, count);
		return sqlSession.selectList(key, obj, rowBounds);
	}

	/**
	 * 方法说明：信息统计
	 */
	public Object countQuery(String key, Object obj) {
		return sqlSession.selectOne(key, obj);
	}

    /**
     * 方法说明：插入数据
     */
    public Object save(String key, Object obj) {
        return sqlSession.insert(key, obj);
    }

}
