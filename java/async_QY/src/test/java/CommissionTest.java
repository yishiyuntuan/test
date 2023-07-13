import async.MainApplication;
import async.commission.demo.MissionProcess;
import async.commission.util.SpringUtil;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = MainApplication.class)
public class CommissionTest {

    @Test
    public void testAnno() throws ExecutionException, InterruptedException {
        MissionProcess missionProcess = (MissionProcess) SpringUtil.getBean("MissionProcess");
        missionProcess.run2();
    }
}
