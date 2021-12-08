package com.lxj.shardingjdbc;

import com.lxj.shardingjdbc.dao.AreaMapper;
import com.lxj.shardingjdbc.dao.OrderMapper;
import com.lxj.shardingjdbc.model.Area;
import com.lxj.shardingjdbc.model.AreaExample;
import com.lxj.shardingjdbc.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShardingjdbcApplicationTests {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private AreaMapper areaMapper;

//    @Test
    public void testOrder() {
        Order order = new Order();
        order.setUserId(1);
        order.setId(1);
        order.setOrderAmount(19L);
        order.setOrderStatus(1);
        // 1 % 2 == 1 -> ds1
        orderMapper.insert(order);
        order.setId(2);
        order.setUserId(2);
        order.setOrderStatus(0);
        // 2 % 2 == 0 -> ds0
        orderMapper.insert(order);
    }

    @Test
    public void testOrder1() {
        Order order = new Order();
        order.setUserId(4);
        order.setId(3);
        order.setOrderAmount(19L);
        order.setOrderStatus(1);
        // 3 % 2 == 0 -> ds1
        orderMapper.insert(order);
        order.setId(4);
        order.setUserId(3);
        order.setOrderStatus(0);
        // 4 % 2 == 0 -> ds0
        orderMapper.insert(order);
    }

    @Test
    public void queryOrder() {
        System.out.println(orderMapper.selectByPrimaryKey(2));
    }

//    @Test
    public void insertArea() {
        Area area = new Area();
        area.setId(2);
        area.setName("shanghai");
        areaMapper.insert(area);
    }

    @Test
    public void queryArea() {
        AreaExample areaExample = new AreaExample();
        areaExample.createCriteria().andIdEqualTo(2);
        List<Area> areas = areaMapper.selectByExample(areaExample);
        System.out.println(areas.size());
        areas.forEach(System.out::println);
    }
}
