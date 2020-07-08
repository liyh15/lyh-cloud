package wibo.cloud.common.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class TastJob implements Serializable {

    private String taskCode;

    private String taskUrl;

    private String cron;
}
