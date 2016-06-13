package com.mindminer.intellitags

import com.mindminer.intellitags.logger.Loggable
import com.wang.web.configure.ApplicationCoreConfigure
import org.springframework.boot.{Banner, SpringApplication}
import org.springframework.context.annotation.{ComponentScan, Import}
/**
  * Created by wangji on 2016/6/13.
  */
object MainClass extends Loggable{
    def main(args: Array[String]) {
        val app = new SpringApplication(classOf[MainClass])
        app.setBannerMode(Banner.Mode.OFF)
        app.run(args: _*)

        info("Server is running...")
    }
}

@ComponentScan
@Import(value = Array(classOf[ApplicationCoreConfigure]))
class MainClass {}