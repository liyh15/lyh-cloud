package wibo.cloud.custom.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wibo.cloud.custom.mapper.TeacherMapper;
import wibo.cloud.custom.service.ServiceInterFace;

/**
 * @Classname ServiceImpl
 * @Description TODO
 * @Date 2020/12/28 9:18
 * @Created by lyh
 */
@Service
public class ServiceImpl implements ServiceInterFace {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public void updateTest() {
        teacherMapper.update(3);
    }
}
