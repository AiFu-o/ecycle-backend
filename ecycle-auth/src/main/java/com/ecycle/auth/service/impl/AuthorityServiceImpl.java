package com.ecycle.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ecycle.auth.model.Authority;
import com.ecycle.auth.service.AuthorityService;
import com.ecycle.auth.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

/**
* @author wangweichen
* @description 针对表【ecycle_authority】的数据库操作Service实现
* @createDate 2024-04-01 10:15:14
*/
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
    implements AuthorityService{

}




