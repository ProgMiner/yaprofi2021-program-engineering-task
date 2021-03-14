package ru.byprogminer.programengineeringtask

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.service.ApiInfo
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2


@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun externalApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select().apis(RequestHandlerSelectors.basePackage("ru.byprogminer.programengineeringtask"))
            .paths(PathSelectors.regex("/.+")).build()
            .apiInfo(apiInfo()).useDefaultResponseMessages(false)
            .groupName("default")
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfo(
            "RESTful API сервис для работы с заметками",
            "Задание на олимпиаду Я-Профессионал по Программной инженерии",
            "0.1",
            null,
            null,
            null,
            null,
            emptyList(),
        )
    }
}
