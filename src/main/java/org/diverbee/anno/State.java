package org.diverbee.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.diverbee.validation.StateValidation;

import java.lang.annotation.*;

@Documented//元注解 到帮助文档
@Target(ElementType.FIELD)// 元注解，表示注解可以用到哪些地方
@Retention(RetentionPolicy.RUNTIME)//哪个阶段被保留
@Constraint(validatedBy = {StateValidation.class}) //谁来提供校验规则
public @interface State {
    //提供校验失败后的信息
    String message() default "State参数的值只能是[已发布|草稿]";

    //制定分组
    Class<?>[] groups() default {};

    //负载，获取到state注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
