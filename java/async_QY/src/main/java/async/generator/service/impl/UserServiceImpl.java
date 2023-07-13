package async.generator.service.impl;

import async.generator.domain.User;
import async.generator.mapper.UserMapper;
import async.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author yishiyuntuan
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-07-12 23:48:19
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




