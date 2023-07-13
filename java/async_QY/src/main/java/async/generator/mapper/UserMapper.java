package async.generator.mapper;

import async.generator.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
* @author yishiyuntuan
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-07-12 23:48:19
* @Entity generator.domain.User
*/

@Repository
public interface UserMapper extends BaseMapper<User> {

}




