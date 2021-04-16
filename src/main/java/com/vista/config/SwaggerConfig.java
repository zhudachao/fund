package com.vista.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.vista.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("fund基金的api接口文档")
                .description("简单优雅的restful风格")
                .version("1.0")
                .build();
    }
    
    /**
     * 使用实例
     * 类上：
     * 	@Api(value = "基金相关接口", tags = {"基金相关接口"})
     * 方法上：
     *  @ApiImplicitParam(name = "code", value = "基金代码", required = true, dataType = "String")
	 *  @ApiOperation(value = "基金详细信息更新", notes = "基金详细信息更新", response = List.class)
	 * 	@OperationLogger(value = "新增角色信息")
	 * 参数上：
	 * 	@ApiParam(name = "username", value = "用户名或邮箱或手机号", required = false) @RequestParam(name = "username", required = false) String username...
	 * 
     */

}
