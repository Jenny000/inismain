package ie.my353.inis.Aop.AnnotationInterfaces;

import ie.my353.inis.utils.JsoupGetKAndP;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
//自定义一个注解
//@interface 这是一个注解类型，@Retention用来定义当前注解的作用范围，
//@Target注解用来约束自定义注解可以注解Java的哪些元素
public @interface ProcessTimeDuration {

}
