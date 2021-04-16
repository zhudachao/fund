package com.vista.utils.sequence;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SeqMapper {	
	
	@Results({ @Result(property = "moduleName", column = "module_name"),
		@Result(property = "moduleCode", column = "module_code"),
		@Result(property = "templet", column = "templet"),
		@Result(property = "maxSerial", column = "max_serial"),
		@Result(property = "preMaxNum", column = "pre_max_num"),
		@Result(property = "icreNum", column = "icre_num"),	
		@Result(property = "seqLength", column = "seq_length"),	
	})
	@Select("select * from sys_sequence where module_code= #{moduleCode}")
	public Sequence find(String moduleCode);
	
	@Select("select templet from sys_sequence where module_code= #{moduleCode}")
	public String getTemplet(String moduleCode);
	
	@Update("update sys_sequence set max_serial=#{maxSerial} where module_code=#{moduleCode}")
	public int save(@Param("maxSerial") String maxSerial,@Param("moduleCode") String moduleCode);
	
	

}
