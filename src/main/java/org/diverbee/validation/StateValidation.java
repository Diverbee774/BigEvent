package org.diverbee.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.diverbee.anno.State;

/**
 * 用来判断String是否满足State的限制
 */
public class StateValidation implements ConstraintValidator<State,String> {

    /**
     *
     * @param value 将来要判断的数据
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if(value==null){
            return false;
        }
        if(value.equals("已发布")||value.equals("草稿")){
            return true;
        }
        return false;
    } //给state提供校验规则，校验的类型是String

}
