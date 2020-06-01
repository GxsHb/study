package com.hb.study.controller;

import com.hb.study.pojo.ImgPO;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author huangbing
 * @date 2019/10/05 11:35
 */
@Controller
@RequestMapping("/api")
public class GetImageController {

      private static Logger logger = LoggerFactory.getLogger(GetImageController.class);

      @Autowired
      private ImgPO imgPO;
//    @Value("#{systemProperties['os.name']?:success}")

//      @Value("${image.notExist : success}")
      @Value("${hb.cs.title}")
      private String test;

      //https://movie.douban.com/subject/30413052/photos?type=S&start=0&sortby=like&size=a&subtype=o
      //https://movie.douban.com/subject/30282387/photos?type=S&start=0&sortby=like&size=a&subtype=o
      @RequestMapping("/getImage/{videoId}")
      @ResponseBody
      public Object getImage(@PathVariable("videoId") String videoId){
          String url = "https://movie.douban.com/subject/"+videoId+"/photos?type=S&start=0&sortby=like&size=a&subtype=o";

          return null;
      }

//      @RequestMapping("/queryImg/{methodName}")
        @RequestMapping("/queryImg")
      /**
       *  RequestBody将json格式请求参数转换为java对象,并进行绑定
       *  @RequestBody主要用来接收前端传递给后端的json字符串中的数据的(请求体中的数据的),
       *  GET方式无请求体,所以使用@RequestBody接收数据时,前端不能使用GET方式提交数据,
       *  而是用POST方式进行提交。在后端的同一个接收方法里，@RequestBody与@RequestParam(value="customName")可以同时使用，
       *  @RequestBody最多只能有一个，而@RequestParam()可以有多个。
       */
      public String queryImg(HttpServletRequest request, /*@PathVariable(value = "methodName")*/ String methodName){
//              logger.info(imgPO.toString());
                request.setAttribute("studyList", Lists.newArrayList("java","redis","docker","mysql"));
                //获取当前时间
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                request.setAttribute("currentDateTime", dateTimeFormatter.format(localDateTime));
                return "study";
      }

}
