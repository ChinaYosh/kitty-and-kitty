package com.animals.tools;

import com.animals.entity.Course;
import com.animals.entity.CourseReservation;
import com.animals.entity.School;
import com.animals.query.CourseQuery;
import com.animals.service.ICourseReservationService;
import com.animals.service.ICourseService;
import com.animals.service.ISchoolService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class CourseTools {
    private final ICourseService courseService;
    private final ISchoolService schoolService;
    private final ICourseReservationService reservationService;
    @Tool(description = "根据条件查询课程")
    public List<Course> queryCourse(@ToolParam(description = "查询的条件") CourseQuery courseQuery) {
        log.info("queryCourse,{}",courseQuery);
        if(courseQuery == null)
        {
            return  courseService.list();
        }
        QueryChainWrapper<Course> wrapper = courseService.query().eq(courseQuery.getType() != null, "type", courseQuery.getType())
                .le(courseQuery.getEdu() != null, "edu", courseQuery.getEdu());
        if (courseQuery.getSorts() != null && !courseQuery.getSorts().isEmpty()) {
            for (CourseQuery.Sort sort : courseQuery.getSorts()) {
                wrapper.orderBy(true, sort.getAsc(), sort.getField());
            }

        }
        return  wrapper.list();
    }

    @Tool(description = "根据所有学校")
    public List<School> querySchool() {
        return schoolService.list();
    }
    @Tool(description = "创建课程预约")
    public  Integer createCourseReservation(
                                             @ToolParam(description = "课程id") String course,
                                             @ToolParam(description = "校区id") String school,
                                             @ToolParam(description = "学生姓名") String studentName,
                                             @ToolParam(description = "联系方式") String contactInfo,
                                             @ToolParam(description = "备注",required = false) String remark)
    {
        CourseReservation reservation = new CourseReservation();
        reservation.setCourse(course);
        reservation.setSchool(school);
        reservation.setStudentName(studentName);
        reservation.setContactInfo(contactInfo);
        reservation.setRemark(remark);
        reservationService.save(reservation);
        return reservation.getId();
    }

}
