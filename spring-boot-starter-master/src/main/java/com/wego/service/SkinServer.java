package com.wego.service;

import com.wego.entity.Skin;
import com.wego.entity.User;
import com.wego.model.ResultModel;
import com.wego.service.impl.SkinServerImpl;

import java.util.List;

public interface SkinServer {

    ResultModel<Skin> showSkin(int uid);

    void useSkin(int uid, int pid, int sid);

    ResultModel buySkin(int uid, int sid);
}
