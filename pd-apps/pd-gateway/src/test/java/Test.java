import com.itheima.TestController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.applet.AppletContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    private AnnotationConfigApplicationContext applicationContext;

    @org.junit.Test
    public void test(){
        TestController bean = applicationContext.getBean(TestController.class);
    }


}
