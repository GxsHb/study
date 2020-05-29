package com.hb.study;

import com.hb.study.dao.Employee;
import com.hb.study.pojo.ImgPO;
import com.hb.study.service.DepartmentService;
import com.hb.study.service.EmployeeService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudySpringbootApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    RestTemplate restTemplate;
    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Autowired
    ImgPO imgPO;

    private String osName;

    private static Logger logger = LoggerFactory.getLogger(StudySpringbootApplicationTests.class);
    @Test
    public void contextLoads() {
        logger.info(osName);
//      applicationContext.getBeanDefinitionNames();
        logger.info(imgPO.toString());
    }

    @Qualifier("druid")
    @Autowired
    DataSource dateSource;

    @Test
    public void test(){
        System.out.println(dateSource.getClass());
    }

    @Autowired
    private   EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
//  @Cacheable(cacheNames = "customCache",key = "#employee.employeeName")
    public void checkCache() {
        Employee employee = (Employee) redisTemplate.opsForValue().get("customCache");
        if (StringUtils.isEmpty(employee)) {
            employee = employeeService.queryEmployeeById(170625);
            redisTemplate.opsForValue().set("customCache", employee);
        }
        logger.info("cache hit ratio");
    }
    @Test
    public void sendMessage(){
        for (int i = 0; i < 15; i++) {
            Employee employee = employeeService.queryEmployeeById(170625);
            rabbitTemplate.convertAndSend("hbfanout","", employee);
        }
    }

    @RabbitListener(bindings =
            {@QueueBinding(
                    value =@Queue(name = "topicqueues"),
                    exchange=@Exchange(name="hbfanout"),
                    key="topicqueues")}
            )
    public void  receiveMessage(Employee employee){
           logger.info(employee.toString());
    }

    @Test
    public void receiveMessage(){
        for (int i = 0; i < 5; i++) {
            logger.info(rabbitTemplate.receiveAndConvert("directqueues").toString());
        }
    }

    @Test
    public void checkSqlRun(){

//         employeeService.queryAllEmployee().forEach(employee -> {
//             logger.info(employee.toString());
//         });

           logger.info( employeeService.queryEmployeeById(170625).getEmployeeName());

//          departmentService.queryAllDepartment().forEach(department -> {
//               logger.info(department.getDepartmentName());
//               department.getEmployeeList().forEach(employee -> {
//                  logger.info(employee.toString());
//              });
//                logger.info(department.toString());
//          });
    }
    private final String HB_EMAIL_COUNT = "395628861@qq.com";
    private final String ZZX_EMAIL_COUNT = "907708282@qq.com";
    @Test
    public void testSendEmail() throws InterruptedException{
            ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            executorService.submit(() ->{
//                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//                simpleMailMessage.setFrom(HB_EMAIL_COUNT);
//                simpleMailMessage.setSubject("BlackTiger_Ab");
//                simpleMailMessage.setText("金湖海底捞");
//                simpleMailMessage.setTo(ZZX_EMAIL_COUNT);
//                javaMailSender.send(simpleMailMessage);
//                发送复杂邮件
                  MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                  MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
//                上传文件
                    File file = new File("");
                    try {
                        mimeMessageHelper.addAttachment("springboot",file);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                    javaMailSender.send(mimeMessage);
            });
        }
                executorService.awaitTermination(5000, TimeUnit.MILLISECONDS);
                getGenericType(Lists.newArrayList(Integer.MAX_VALUE),0);
    }


        public static <T>  T getGenericType(List<T> listType, int index){
               return listType.get(index);
        }

}
