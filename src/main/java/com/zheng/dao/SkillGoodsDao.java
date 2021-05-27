package com.zheng.dao;

import com.zheng.entity.SkillGoods;
import org.apache.ibatis.annotations.Param;

/**
 * @author: ZhengTianLiang
 * @date: 2021/5/17  9:38
 * @desc: 秒杀商品表dao
 */

public interface SkillGoodsDao {

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  11:14
     * @desc: 通过秒杀商品的id，查询秒杀商品的数据
     */
    SkillGoods selectById(Integer gid);

    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/17  14:57
     * @desc: 通过秒杀商品的id，更新秒杀商品的数量
     */
    Integer updateSkillGoods(@Param("gid") Integer gid, @Param("count") Integer count);
}
