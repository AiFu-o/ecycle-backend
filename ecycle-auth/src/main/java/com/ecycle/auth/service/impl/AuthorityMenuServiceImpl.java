package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.model.AuthorityMenu;
import com.ecycle.auth.service.AuthorityMenuService;
import com.ecycle.auth.mapper.AuthorityMenuMapper;
import org.springframework.stereotype.Service;

/**
* @author wangweichen
* @description 针对表【ecycle_authority_menu】的数据库操作Service实现
* @createDate 2024-04-01 10:15:14
*/
@Service
public class AuthorityMenuServiceImpl extends ServiceImpl<AuthorityMenuMapper, AuthorityMenu>
    implements AuthorityMenuService{

}




