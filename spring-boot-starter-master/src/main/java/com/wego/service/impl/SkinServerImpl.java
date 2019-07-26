package com.wego.service.impl;


import com.wego.entity.Skin;

import java.util.List;

public interface SkinServerImpl {

    //显示用户所拥有的皮肤
    /**
     * @param uid
     * @return
     */
    List<Skin> showSkin(int uid);


    // 使用皮肤
    /**
     * @param uid   用户id
     * @param pid   宠物id
     * @param sid   皮肤sid
     * 更改皮肤状态
     */
    void  useSkin(int uid,int pid,int  sid);


    //购买皮肤
    /**
     * @param uid 用户id
     * @param sid 皮肤id
     * @return   0，1  0=》健康豆余额不足
     */
    int buySkin(int uid, int  sid);

}
