package com.asymt.addon.resourcesite.task;

import com.asymt.addon.resourcesite.service.IntegralAvailablesService;
import com.jfinal.aop.Aop;
import io.jboot.components.schedule.annotation.Cron;

/**
 * 自动过期用户积分的任务类
 * 每天的0点10分执行
 */
@Cron("10 0 * * *")
public class ExpireIntegralTask implements Runnable{
    @Override
    public void run() {
        IntegralAvailablesService integralAvailablesService= Aop.get(IntegralAvailablesService.class);
        integralAvailablesService.expireIntegral();
    }
}
