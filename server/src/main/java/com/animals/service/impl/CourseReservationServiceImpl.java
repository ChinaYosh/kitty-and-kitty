package com.animals.service.impl;

import com.animals.entity.CourseReservation;
import com.animals.mapper.CourseReservationMapper;
import com.animals.service.ICourseReservationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2026-04-05
 */
@Service
@Lazy // 💡 延迟初始化 Service，让数据源有足够时间先准备好
public class CourseReservationServiceImpl extends ServiceImpl<CourseReservationMapper, CourseReservation> implements ICourseReservationService {

}
