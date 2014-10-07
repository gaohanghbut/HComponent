package cn.hang.spring.test;

import cn.hang.ioc.api.LifeCycle;
import cn.hang.ioc.api.Starter;

/**
 * Created by hang.gao on 2014/8/23.
 */
public class TestServiceImpl implements TestService, LifeCycle {
    private TestDao testDao;

    @Override
    public void done() {
        testDao.selectAll();
    }

    public TestDao getTestDao() {
        return testDao;
    }

    public void setTestDao(TestDao testDao) {
        this.testDao = testDao;
    }

    @Override
    public void init() throws Throwable {
        System.out.println("init");
    }

    @Override
    public void destroy() throws Throwable {
        System.out.println("destroy");
    }
}
