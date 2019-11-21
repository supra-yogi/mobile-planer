package com.skripsi.yogi.planner.Domain.Plannings;

import com.skripsi.yogi.planner.Common.IRepository;
import com.skripsi.yogi.planner.Common.ResponseCallBack;

public interface IPlanningRepo extends IRepository<Planning> {
    void getPlanningByUserId(final ResponseCallBack responseCallBack, int userId);
    void getRincianTabungan(final ResponseCallBack responseCallBack, int planningId);
    void getPriorityPlanningByUserId(final ResponseCallBack responseCallBack, int userId);
}
