package com.wego.service;

import com.wego.entity.Skin;
import com.wego.model.ResultModel;

import java.util.HashMap;
import java.util.List;

public interface SkinService {

    ResultModel<Skin> showSkin(int uid);

    ResultModel useSkin(int uid, int pid, int sid);

    ResultModel buySkin(int uid, int sid);

    ResultModel<HashMap<String, List<Skin>>> marketSkin();
}
