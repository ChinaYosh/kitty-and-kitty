package com.animals.service.impl;

import com.animals.entity.Course;
import com.animals.mapper.CourseMapper;
import com.animals.service.ICourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy // 💡 延迟初始化 Service，让数据源有足够时间先准备好
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    // 可以在这里添加业务逻辑
}
