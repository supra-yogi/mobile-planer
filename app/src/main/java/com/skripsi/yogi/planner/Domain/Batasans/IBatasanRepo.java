package com.skripsi.yogi.planner.Domain.Batasans;

import com.skripsi.yogi.planner.Common.IRepository;
import com.skripsi.yogi.planner.Common.ResponseCallBack;

public interface IBatasanRepo extends IRepository<Batasan> {
    void getBatasanByUserId(final ResponseCallBack responseCallBack, int userId);
}
