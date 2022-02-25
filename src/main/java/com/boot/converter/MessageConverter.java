package com.boot.converter;
//自定义的converter(这是一个自定义的Converter,只要改请求头的Accept为"xxx"(postman可以做到),浏览器就会以我们自定义的数据格式响应数据)
import com.boot.bean.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class MessageConverter implements HttpMessageConverter<Person> {
    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return false;
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.isAssignableFrom(Person.class);
    }


    //服务器要统计所以MessageConverter都能写出哪些内容类型(application/xxx)
    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return MediaType.parseMediaTypes("application/xxx");
    }

    @Override
    public Person read(Class<? extends Person> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        return null;
    }

    //自定义协议数据的写出
    @Override
    public void write(Person person, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String data=person.getUserName()+";"+person.getAge()+";"+person.getBirth();
        OutputStream body=outputMessage.getBody();
        body.write(data.getBytes());//写出去
    }
}
