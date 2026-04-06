package com.animals.service.impl;

import com.animals.entity.School;
import com.animals.mapper.SchoolMapper;
import com.animals.service.ISchoolService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 校区表 服务实现类
 * </p>
 *
 * @author author
 * @since 2026-04-05
 */
@Service
@Lazy // 💡 延迟初始化 Service，让数据源有足够时间先准备好
public class SchoolServiceImpl extends ServiceImpl<SchoolMapper, School> implements ISchoolService {

}
