package com.wego.service;

import com.wego.entity.Skin;
import com.wego.model.ResultModel;

public interface SkinService {

    ResultModel<Skin> showSkin(int uid);

    ResultModel useSkin(int uid, int pid, int sid);

    ResultModel buySkin(int uid, int sid);
}
