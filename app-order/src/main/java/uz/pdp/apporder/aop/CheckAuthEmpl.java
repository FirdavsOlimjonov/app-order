package uz.pdp.apporder.aop;

import uz.pdp.apporder.entity.enums.PermissionEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckAuthEmpl {

    PermissionEnum[] permissions() default {};

}
