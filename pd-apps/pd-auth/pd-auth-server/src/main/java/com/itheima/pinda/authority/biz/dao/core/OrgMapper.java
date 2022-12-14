package com.itheima.pinda.authority.biz.dao.core;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.pinda.authority.entity.core.Org;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * 组织
 * </p>
 */
@Repository
public interface OrgMapper extends BaseMapper<Org> {

    IPage<Org> pageLike(Page page, @Param("params") Map params);
}
