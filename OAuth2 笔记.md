版本

> 官方文档<https://cloud.spring.io/spring-cloud-security/2.0.x/single/spring-cloud-security.html>

### OAuth2 简单登录

#### 开发

------



- ###### 依赖

  ```xml
      <dependencies>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-web</artifactId>
          </dependency>
          <dependency>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-starter-security</artifactId>
          </dependency>
      </dependencies>
  ```

- ###### 接口

  ```java
  @RestController
  public class TestController {
  
      @GetMapping(value = "get/{id}")
      public String get(@PathVariable("id") String id){
          return "This is " + id;
      }
  }
  ```

- ###### 配置文件

  ```java
  server.port=8888
  ```

  

- ###### 启动类

  ```java
  @SpringBootApplication
  public class Demo001Application {
  
      public static void main(String[] args) {
          SpringApplication.run(Demo001Application.class, args);
      }
  }
  ```

- ###### OAuth2 简单登录完成

#### 测试

------

- ###### 启动

  查看启动日志:默认认证密码`b8a24a96-4048-4e00-815c-06f231254457`  用户名:`user`

  ```
  20-01-07 11:10:26.256  INFO 19468 ---[main] .s.s.UserDetailsServiceAutoConfiguration : 
  Using generated security password: b8a24a96-4048-4e00-815c-06f231254457
  ```

- 接口访问 `<http://localhost:8888/get/1234>`

  - 跳转登录页面

    ![1578367474628](C:\Users\houxi\AppData\Roaming\Typora\typora-user-images\1578367474628.png)

  - 登录之后结果

    ![1578367655719](C:\Users\houxi\AppData\Roaming\Typora\typora-user-images\1578367655719.png)

#### 分析

------

- 提出问题

  1. 默认密码生产
  2. 认证步骤

- 源码

  - `UserDetailsServiceAutoConfiguration.class`

    ```jva
    @Configuration(
        proxyBeanMethods = false
    )
    @ConditionalOnClass({AuthenticationManager.class})
    @ConditionalOnBean({ObjectPostProcessor.class})
    @ConditionalOnMissingBean(
        value = {AuthenticationManager.class, AuthenticationProvider.class, UserDetailsService.class},
        type = {"org.springframework.security.oauth2.jwt.JwtDecoder", "org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector"}
    )
    public class UserDetailsServiceAutoConfiguration {
        
    }
    ```

    `@ConditionalOnClass({AuthenticationManager.class})`判断当前classpath下是否存在指定类（AuthenticationManager.class），若存在则将当前的配置装载入spring容器

    `@ConditionalOnBean({ObjectPostProcessor.class})`当前上下文中存在某个Bean时，才会实例化当前Bean

    `@ConditionalOnMissingBean({...})`上下文中不存在某个Bean时，才会实例化当前Bean

  - `UserDetailsServiceAutoConfiguration`方法`inMemoryUserDetailsManager`初始化了一个名为`InMemoryUserDetailsManager` 的内存用户管理器

    ```java
        @Bean
        @ConditionalOnMissingBean(
            type = {"org.springframework.security.oauth2.client.registration.ClientRegistrationRepository"}
        )
        @Lazy
        public InMemoryUserDetailsManager inMemoryUserDetailsManager(
            SecurityProperties properties, ObjectProvider<PasswordEncoder> 						passwordEncoder) {
            //略
        }
    ```

    - `SecurityProperties.class` 初始化提供了默认参数

      - 默认用户名user
      - 默认密码UUID

      ```java
      public class SecurityProperties {
          public static class User {
              private String name = "user";
              private String password = UUID.randomUUID().toString();
              private List<String> roles = new ArrayList();
              private boolean passwordGenerated = true;
      	}
      }
      ```

  - 扩展

    - 用户账户密码通过`SecurityProperties`类管理，及可通过配置文件的方式自定义用户名和密码

      ```properties
      spring.security.user.name=root
      spring.security.user.password=123456
      ```

    - 内存用户管理通过类`InMemoryUserDetailsManager`，及可通过重写`UserDetailsManager`实现自己的用户管理逻辑

      ![1578380807818](C:\Users\houxi\AppData\Roaming\Typora\typora-user-images\1578380807818.png)

    - 新建`MyUserDetailsManager` 实现 自定义用户管理

      ```java
          @Component
      	public class MyUserDetailsManager implements UserDetailsService {
              //略
              @Override
              public UserDetails loadUserByUsername(String username) throws 
                                                          UsernameNotFoundException {
                  //模拟数据库查询
                  User user = userDetailsDao.get(username);
                  if (null == user) {
                      log.warn("用户{}不存在", username);
                      throw new UsernameNotFoundException(username);
                  }
                  log.info("登录成功！用户: {}", user);
                  return user;
              }
          }
      ```

    - 过滤器

      - 默认登录页配置加载过程(`DefaultLoginPageGeneratingFilter` 加载)

        - ![1578556466971](C:\Users\houxi\AppData\Roaming\Typora\typora-user-images\1578556466971.png)

      - 可以通过重写`WebSecurityConfigurerAdapter`  来自定义登录页面

        ```java
        @Configurable
        @EnableWebSecurity
        public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
        
            @Override
            protected void configure(HttpSecurity http) throws Exception {
        
                http
                        .authorizeRequests().antMatchers("/get/**").authenticated()
                        .and()         .formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll()
                        .and().httpBasic()
                        .and().csrf().disable();
           }
        }
        ```

      - `SavedRequestAwareAuthenticationSuccessHandler`处理登录成功（可重写）

      - `HttpSessionRequestCache保存了回调url信息`

        - ```java
          public SavedRequest getRequest(HttpServletRequest currentRequest,
          			HttpServletResponse response) {
          		HttpSession session = currentRequest.getSession(false);
          
          		if (session != null) {
          			return (SavedRequest) session.getAttribute(this.sessionAttrName);
          		}
          
          		return null;
          	}
          ```

          

      - `BCryptPasswordEncoder` `matches` 方法进行了密码验证

        - ```java
          	public boolean matches(CharSequence rawPassword, String encodedPassword) {
          		if (encodedPassword == null || encodedPassword.length() == 0) {
          			logger.warn("Empty encoded password");
          			return false;
          		}
          
          		if (!BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
          			logger.warn("Encoded password does not look like BCrypt");
          			return false;
          		}
          
          		return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
          	}
          ```

          



​				



## OAuth2 资源权限