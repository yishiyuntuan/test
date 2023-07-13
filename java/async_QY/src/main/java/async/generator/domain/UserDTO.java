package async.generator.domain;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends User{
    private List<Address> list;


}
