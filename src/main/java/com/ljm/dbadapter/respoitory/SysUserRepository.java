package com.ljm.dbadapter.respoitory;

import com.ljm.dbadapter.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author Dominick Li
 * @CreateTime 2022/5/4 21:18
 * @Description
 **/
public interface SysUserRepository extends JpaRepository<SysUser,Integer> {
    List<SysUser> findAllByEnabled(boolean enabled);
}
