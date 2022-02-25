package com.boot.config;
//定制化
import com.boot.bean.Pet;
import com.boot.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
public class WebConfig {


    //@Bean  自定义hidden的name,本来是_method,改成_m,只需不用boot给我们提供的规律器,自己创建一个放进容器里就行
    public HiddenHttpMethodFilter hiddenHttpMethodFilter(){
        HiddenHttpMethodFilter methodFilter=new HiddenHttpMethodFilter();
        methodFilter.setMethodParam("_m");
        return methodFilter;
    }


    //WebMvcConfigurer定制化SpringMVC的功能
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {

            //自定义内容协商策略(当请求路径后加"?format=...",根据...浏览器返回对应的数据格式,是?format=gb,浏览器就显示我们自定义的数据格式)
            @Override
            public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
                Map<String, MediaType> mediaTypeMap=new HashMap<>();
                mediaTypeMap.put("json",MediaType.APPLICATION_JSON);
                mediaTypeMap.put("xml",MediaType.APPLICATION_XML);
                mediaTypeMap.put("gb",MediaType.parseMediaType("application/xxx"));
                /*指定支持解析器哪些参数对应的哪些媒体类型*/
                ParameterContentNegotiationStrategy parameterStrategy=new ParameterContentNegotiationStrategy(mediaTypeMap);
                /*如果自定义了一个converter,在postman中改Accept,将不会起效果,那么我们就需要添加"改变请求头也可以改响应的数据格式"*/
                HeaderContentNegotiationStrategy headerStrategy = new HeaderContentNegotiationStrategy();
                configurer.strategies(Arrays.asList(parameterStrategy,headerStrategy));//基于参数和请求头都可以改变响应的数据格式
            }

            //添加一个自己自定义的Converter,只要改请求头的Accept为"xxx",浏览器就会以我们自定义的数据格式响应数据
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                converters.add(new MessageConverter());
            }

            //自己创建一个WebMvcConfigurer对象放进容器中,此对象在这个方法开启了矩阵变量的功能(也可以此配置类实现WebMvcConfigurer接口,实现方法)
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper urlPathHelper = new UrlPathHelper();
                // "不" 移除分号后面的内容,矩阵变量功能就可以生效
                urlPathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(urlPathHelper);
            }
            //在这个方法让<input>发来的"阿毛,3"以逗号分隔,赋给Pet的各个属性
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, Pet>() {

                    @Override
                    public Pet convert(String source) {
                        // 阿毛,3
                        if(!StringUtils.isEmpty(source)){
                            Pet pet = new Pet();
                            String[] split = source.split(",");
                            pet.setName(split[0]);
                            pet.setAge(Integer.parseInt(split[1]));
                            return pet;
                        }
                        return null;
                    }
                });
            }
        };
    }
}
