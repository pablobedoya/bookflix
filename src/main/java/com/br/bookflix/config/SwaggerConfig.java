package com.br.bookflix.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import org.springframework.web.bind.annotation.RequestMethod;

import com.br.bookflix.error.CustomError;
import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	private TypeResolver typeResolver;

	@Bean
	public Docket apiBooks() {
		return new Docket(DocumentationType.SWAGGER_2)
			.additionalModels(typeResolver.resolve(CustomError.class))
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.br.bookflix"))
			.paths(PathSelectors.any())
			.build()
			.securityContexts(Arrays.asList(securityContext()))
			.securitySchemes(Arrays.asList(apiKey()))
			.useDefaultResponseMessages(false)
			.globalResponseMessage(RequestMethod.GET, responseMessage(RequestMethod.GET, "Book", false))
			.globalResponseMessage(RequestMethod.POST, responseMessage(RequestMethod.GET, "Book", true))
			.globalResponseMessage(RequestMethod.PUT, responseMessage(RequestMethod.PUT, "Book", true))
			.globalResponseMessage(RequestMethod.DELETE, responseMessage(RequestMethod.DELETE, "Book", true))
			.apiInfo(apiInfo());
	}
	
	private List<ResponseMessage> responseMessage(RequestMethod method, String type, boolean requiresAuthentication) {
		return new ArrayList<ResponseMessage>() {
			private static final long serialVersionUID = 1L;
			{
				if (RequestMethod.DELETE.equals(method))
					add(getError204(type));

				add(getError401());

				if (requiresAuthentication)
					add(getError403());

				add(getError404());
				add(getError412());
				add(getError422());
				add(getError500());
			}
		};
	}
	
	private ResponseMessage getError204(String type) {
		return new ResponseMessageBuilder()
            .code(204)
            .message("No Content")
            .build();
	}
	
	private ResponseMessage getError401() {
		return new ResponseMessageBuilder()
            .code(401)
            .message("Unauthorized")
            .responseModel(new ModelRef("CustomError"))
            .build();
	}
	
	private ResponseMessage getError403() {
		return new ResponseMessageBuilder()
            .code(403)
            .message("Forbidden")
            .responseModel(new ModelRef("CustomError"))
            .build();
	}
	
	private ResponseMessage getError404() {
		return new ResponseMessageBuilder()
            .code(404)
            .message("Not Found")
            .responseModel(new ModelRef("CustomError"))
            .build();
	}
	
	private ResponseMessage getError412() {
		return new ResponseMessageBuilder()
            .code(412)
            .message("Precondition Failed")
            .responseModel(new ModelRef("CustomError"))
            .build();
	}
	
	private ResponseMessage getError422() {
		return new ResponseMessageBuilder()
            .code(422)
            .message("Unprocessable Entity")
            .responseModel(new ModelRef("CustomError"))
            .build();
	}
	
	private ResponseMessage getError500() {
		return new ResponseMessageBuilder()
            .code(500)
            .message("Internal Server Error")
            .responseModel(new ModelRef("CustomError"))
            .build();
	}

	@Bean
	public LinkDiscoverers discoverers() {
		List<LinkDiscoverer> plugins = new ArrayList<>();
		plugins.add(new CollectionJsonLinkDiscoverer());

		return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.build();
	}
	
	List<SecurityReference> defaultAuth() {
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
	    authorizationScopes[0] = authorizationScope;
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	  }
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "API Bookflix", 
	      "This API is responsible for authenticating users, employees and creating books.", 
	      "1.0.0", null, new Contact("Equipe 1", null, "equipe1@g.unicamp.br"), 
	      null, null, Collections.emptyList());
	}

}