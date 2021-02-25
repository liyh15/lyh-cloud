package wibo.cloud.custom.excel;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Classname TOrderUnpaidEntity
 * @Description TODO
 * @Date 2020/11/30 16:57
 * @Created by lyh
 */
@Data
public class TOrderUnpaidExportEntity {
	/**
	 * ID主键
	 */
	private Long id;

	/**
	 * 订单号
	 */
	private String orderNo;


	/**
	 * 卡号
	 */
	private String cardSn;


	/**
	 * 订单实收金额
	 */
	private BigDecimal actualAmount;

	/**
	 * 支付状态：0未支付、1已支付
	 */
	private Integer payStatus;

	/**
	 * 锁卡时间
	 */
	private String lockTime;

	/**
	 * 锁卡站点名称
	 */
	private String lockStationName;

	/**
	 * 解锁网点名称
	 */
	private String unlockBranchName;

	/**
	 * 解锁站点名称
	 */
	private String unlockStationName;


	/**
	 * 支付时间
	 */
	private String payTime;
}
