package net.tinybrick.intellitags

import net.tinybrick.doc.configuration.AipDocConfigure
import net.tinybrick.web.configure.ApplicationCoreConfigure
import net.tinybrick.intellitags.logger.Loggable
import org.springframework.boot.{Banner, SpringApplication}
import org.springframework.context.annotation.{ComponentScan, Import}

/**
  * Created by wangji on 2016/6/13.
  */
object MainClass extends Loggable {
    def main(args: Array[String]) {
        val app = new SpringApplication(classOf[MainClass])
        app.setBannerMode(Banner.Mode.OFF)
        app.run(args: _*)

        info("Server is running...")
    }
}

@ComponentScan
@Import(value = Array(classOf[ApplicationCoreConfigure], classOf[AipDocConfigure]))
class MainClass {}