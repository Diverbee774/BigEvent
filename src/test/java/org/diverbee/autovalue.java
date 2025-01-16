package org.diverbee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class autovalue {

    @Value("lala")
    private String st;

    @Test
    void test(){
        System.out.println(st);
    }
}
