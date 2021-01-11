package wibo.cloud.custom.tiexin;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Classname SickTypeParent
 * @Description TODO
 * @Date 2021/1/11 15:59
 * @Created by lyh
 */
@Data
@ToString
public class SickTypeParent {

    private List<SickType> sickTypeList;
}
