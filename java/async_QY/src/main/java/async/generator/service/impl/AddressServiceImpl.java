package async.generator.service.impl;

import async.generator.domain.Address;
import async.generator.mapper.AddressMapper;
import async.generator.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author yishiyuntuan
* @description 针对表【address】的数据库操作Service实现
* @createDate 2023-07-12 23:48:19
*/
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
    implements AddressService {

}




