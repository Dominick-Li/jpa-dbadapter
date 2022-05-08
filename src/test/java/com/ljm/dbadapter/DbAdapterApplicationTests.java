package com.ljm.dbadapter;

import com.ljm.dbadapter.adaptation.DataBaseNativeSql;
import com.ljm.dbadapter.model.SysUser;
import com.ljm.dbadapter.respoitory.SysUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class DbAdapterApplicationTests {

    @Autowired
    private SysUserRepository sysUserRepository;

    private EntityManagerFactory emf;

    @Resource
    private DataBaseNativeSql dataBaseNativeSql;

    @PersistenceUnit
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Test
    void contextLoads() {
        if (false) {
            SysUser sysUser = new SysUser();
            sysUser.setCreateTime(new Date());
            sysUser.setUsername("张三");
            sysUser.setEnabled(false);
            sysUserRepository.save(sysUser);
            sysUser = new SysUser();
            sysUser.setCreateTime(new Date());
            sysUser.setUsername("李四");
            sysUser.setEnabled(true);
            sysUserRepository.save(sysUser);
            List<SysUser> sysUserList = sysUserRepository.findAllByEnabled(false);
            System.out.println(sysUserList.size());
        }

        {
            EntityManager em = emf.createEntityManager();
            //1=根据年分组,2=根据年月分组,3=根据年月日分组
            Integer groupType = 1;
            try {
                Query query;
                String groupBy;
                if (groupType == 1) {
                    //年分组
                    groupBy = dataBaseNativeSql.format_year().replace("${field}", "createTime");
                } else if (groupType == 2) {
                    //月分组
                    groupBy = dataBaseNativeSql.format_year_month().replace("${field}", "createTime");
                } else  {
                    //按日如果没传时间参数,默认查最近一个月的
                    groupBy = dataBaseNativeSql.format_year_month_day().replace("${field}", "createTime");
                }
                StringBuilder sql = new StringBuilder("select ");
                sql.append(groupBy);
                sql.append(" gb,count(id)  from sys_user group by ");
                sql.append(groupBy);
                //创建query对象
                query = em.createNativeQuery(sql.toString());
                List<Object[]> lists = query.getResultList();
                for (Object[] data : lists) {
                    System.out.println(data[0] + "注册的用户数为=" + data[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (em != null) {
                    em.close();
                }
            }
        }
    }

}
