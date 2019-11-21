package com.skripsi.yogi.planner.Domain.Users;

import com.skripsi.yogi.planner.Common.IRepository;
import com.skripsi.yogi.planner.Common.ResponseCallBack;

public interface IUserRepo extends IRepository<User> {
    void getByUsername(final ResponseCallBack responseCallBack, String username);
    void getByEmail(final ResponseCallBack responseCallBack, String email);
    void login(final ResponseCallBack responseCallBack, String email, String password);
    void checkPassword(final ResponseCallBack responseCallBack, int id, String password);
    void resetPassword(final ResponseCallBack responseCallBack, String email);
}
