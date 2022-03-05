package com.tam.threeam.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;


/**
 * @author 전예지
 * @version 1.0
 * @Description
 * @Modification Information
 * Created 2021/12/30
 * @
 * @ 수정일       	수정자        수정내용
 * @ ———    		————    	—————————————
 * @ 2021/12/30     전예지      	최초 작성
 * @ 2021/01/27		이동은		stateless session, token validating filter 추가
 * @ 2022/02/09		이동은		jwtExceptionFilterBean() 추가
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
//	private static final String[] AUTH_WHITELIST = {
//			"/swagger-resources/**",
//			"/swagger-ui.html",
//			"/v2/api-docs",
//			"/webjars/**",
//			"/h2-console/**",
//			"/js/**", "/css/**",
//			"/image/**"
//	};


	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Autowired
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Autowired
	private PrincipalDetailService principalDetailService;

	@Bean
	public BCryptPasswordEncoder encoderPwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilterBean() {
		return new JwtAuthenticationFilter();
	}

//	@Bean
//	public JwtExceptionFilter jwtExceptionFilterBean() {return new JwtExceptionFilter();};

	@Bean
	public HttpFirewall configureFirewall() {
		StrictHttpFirewall strictHttpFirewall = new StrictHttpFirewall();
		strictHttpFirewall
				.setAllowSemicolon(true);
		return strictHttpFirewall;
	}

//	@Override
//	public void configure(WebSecurity web) {
//		web.ignoring().antMatchers(AUTH_WHITELIST)
//				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encoderPwd());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.csrf().disable()

				.authorizeRequests()
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/favicon.ico", "/img/**", "/static/**", "/*.js", "/*.png", "/index.html")
				.permitAll()
				.anyRequest().authenticated()

//				.and()
//				.formLogin()
//				.loginPage("/auth/signInForm")
//				.loginProcessingUrl("/auth/signInProc")
//				.defaultSuccessUrl("/")

				.and()
				.exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)

				//세션 미사용
				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);


		http.addFilterBefore(jwtAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class);
//		http.addFilterBefore(jwtExceptionFilterBean(), JwtAuthenticationFilter.class);
	}


}