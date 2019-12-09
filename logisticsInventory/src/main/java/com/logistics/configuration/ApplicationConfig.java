package com.logistics.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//import com.logistics.security.CustomBasicAuthenticationEntryPoint;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebSecurity
//@EnableWebMvc
//@ComponentScan(basePackages = {"com.logistics.security","com.logistics.controller"})
public class ApplicationConfig extends WebSecurityConfigurerAdapter {
	
	@Value("${basicUseName}")
    private String basicUseName;
	
	@Value("${basicAuth}")
    private String basicAuth;

   @Autowired
    private BasicAuthenticationConfig basicAuthenticationConfig;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.logistics.controller"))
                .paths(PathSelectors.any())
                .build();
    }
    
    private ApiInfo getApiInfo() {
        Contact contact = new Contact("Artissol", "", "");
        return new ApiInfoBuilder()
                .title("Logisticsappplication")
                .description("Documentation of Logistics API's")
                .version("1.0.0")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
                .contact(contact)
                .build();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
       
    }

    @Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("artis").password("artis123").roles("ADMIN");
		//.and().withUser("project").password("prismadmin").roles("PROJECT");
		
	}
	
	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception {
	 * 
	 * http.csrf().disable() .authorizeRequests()
	 * //.antMatchers("/rest/project/**").access("hasRole('PROJECT')")
	 * .antMatchers("/rest/**").hasRole("ADMIN")
	 * .and().httpBasic().authenticationEntryPoint(getBasicAuthEntryPoint()); }
	 */
	
	@Bean
	public BasicAuthenticationConfig getBasicAuthEntryPoint(){
		return new BasicAuthenticationConfig();
	}
}
