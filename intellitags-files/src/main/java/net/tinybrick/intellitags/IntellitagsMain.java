package net.tinybrick.intellitags;

/**
 * Created by wangji on 2016/6/15.
 */
import net.tinybrick.doc.configuration.AipDocConfigure;
import net.tinybrick.web.configure.ApplicationCoreConfigure;
import org.apache.log4j.Logger;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan
@Import(value = {ApplicationCoreConfigure.class, AipDocConfigure.class})
public class IntellitagsMain {
    private static Logger logger = Logger.getLogger(IntellitagsMain.class);

    public static void main( String[] args) {
        SpringApplication app = new SpringApplication(IntellitagsMain.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);

        logger.info("Server is running...");
    }
}
