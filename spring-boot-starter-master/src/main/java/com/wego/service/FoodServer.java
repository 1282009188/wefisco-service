package com.wego.service;

import com.wego.entity.Food;
import com.wego.service.impl.FoodServerImpl;

import java.util.List;

public class FoodServer  implements FoodServerImpl {

    @Override
    public List<Food> showUserfoods(int uid) {
        return null;
    }

    @Override
    public void useFood(int uid, int pid, int fid) {

    }

    @Override
    public int buyFood(int uid, int fid, int num) {
        return 0;
    }
}
