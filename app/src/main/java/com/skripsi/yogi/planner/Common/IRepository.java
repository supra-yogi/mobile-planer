package com.skripsi.yogi.planner.Common;

import java.util.List;

/**
 * Created by Yogi on 3/5/2018.
 */

public interface IRepository<T extends EntityBase> {
    void getById(final ResponseCallBack responseCallBack, int id);
    void getAll(final ResponseCallBack responseCallBack);
    void save(final ResponseCallBack responseCallBack, T entity);
    void delete(final ResponseCallBack responseCallBack, int id);
    T createNew();
}
