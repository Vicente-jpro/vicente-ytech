package com.example.vicenteytech.configs;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
	/*
	String schemeName = "bearerAuth";
	String bearerFormat = "JWT";
	String scheme = "bearer";
	@Bean
	public OpenAPI caseOpenAPI() {
	        return new OpenAPI()
	                  .addSecurityItem(new SecurityRequirement()
	.addList(schemeName)).components(new Components()
	                                  .addSecuritySchemes(
	                                        schemeName, new SecurityScheme()
	                                        .name(schemeName)
	                                        .type(SecurityScheme.Type.HTTP)
	                                        .bearerFormat(bearerFormat)
	                                        .in(SecurityScheme.In.HEADER)
	                                        .scheme(scheme)
	                                  )
	                  )
	                  .info(new Info()
	                              .title("Case Management Service")
	                              .description("Claim Event Information")
	                              .version("1.0")
	                  );
	        }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.example.vicenteytech.controllers"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Vicente yteck")
                .description("order API")
                .version("1.0")
                .contact(contact())
                .build();
    }

    private Contact contact(){
        return new Contact("Vicente Simao"
                , "http://github.com/vicente-jpro",
                "vicente.simao.rails@gmail.com");
    }

    public ApiKey apiKey(){
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope(
                "global", "accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = authorizationScope;
        SecurityReference reference = new SecurityReference("JWT", scopes);
        List<SecurityReference> auths = new ArrayList<>();
        auths.add(reference);
        return auths;
    }
    */

}
