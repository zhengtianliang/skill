package com.zheng;

import com.zheng.entity.SkillGoods;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootTest
class SkillApplicationTests {


    /**
     * @author: ZhengTianLiang
     * @date: 2021/5/20  9:30
     * @desc: 获取list当中，时间最大的数据
     */
    @Test
    void contextLoads() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date parse1 = null;
        Date parse2 = null;
        Date parse3 = null;
        try {
            parse1 = simpleDateFormat.parse("2010-10-19 11:12:13");
            parse2 = simpleDateFormat.parse("1998-10-17 11:12:13");
            parse3 = simpleDateFormat.parse("1998-10-19 11:15:13");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SkillGoods skillGoods = new SkillGoods();
        skillGoods.setSeckillId(1);
        skillGoods.setEndTime(parse1);

        SkillGoods skillGoods2 = new SkillGoods();
        skillGoods2.setSeckillId(2);
        skillGoods2.setEndTime(parse2);

        SkillGoods skillGoods3 = new SkillGoods();
        skillGoods3.setSeckillId(3);
        skillGoods3.setEndTime(parse3);

        List<SkillGoods> list = new ArrayList();
        list.add(skillGoods);
        list.add(skillGoods2);
        list.add(skillGoods3);

        Date max = Collections.max(list.stream().map(SkillGoods::getEndTime).collect(Collectors.toList()));

        System.out.println("123");
    }

}
