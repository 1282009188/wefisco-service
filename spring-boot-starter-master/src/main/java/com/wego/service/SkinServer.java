package com.wego.service;

import com.wego.entity.Skin;
import com.wego.service.impl.SkinServerImpl;

import java.util.List;

public class SkinServer implements SkinServerImpl {

    @Override
    public List<Skin> showSkin(int uid) {
        return null;
    }

    @Override
    public void useSkin(int uid, int pid, int sid) {

    }

    @Override
    public int buySkin(int uid, int sid) {
        return 0;
    }
}
