package com.studyinghome.bootshop.dto;


import com.studyinghome.bootshop.entity.Area;
import com.studyinghome.bootshop.enums.AreaStateEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AreaExecution implements Serializable {
	// 结果状态
	private int state;
	// 状态标识
	private String stateInfo;
	// 店铺数量
	private int count;
	// 操作的award（增删改商品的时候用）
	private Area area;
	// 获取的award列表(查询商品列表的时候用)
	private List<Area> areaList;

	// 失败的构造器
	public AreaExecution(AreaStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}

	// 成功的构造器
	public AreaExecution(AreaStateEnum stateEnum, Area area) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.area = area;
	}

	// 成功的构造器
	public AreaExecution(AreaStateEnum stateEnum, List<Area> areaList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.areaList = areaList;
	}
}
