package net.tinybrick.intellitags.controllers

import javax.annotation.PostConstruct

import net.tinybrick.doc.annotation.ApiDocDefination
import net.tinybrick.doc.configuration.AutoApiDocEnabler
import net.tinybrick.intellitags.logger.Loggable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RestController, RequestMapping}

/**
  * Created by wangji on 2016/6/14.
  */
@RestController
@RequestMapping(Array("/v1.0"))
@ApiDocDefination()
class HomeController extends Loggable{
    @RequestMapping(value = Array("/version")) private[intellitags]
    def getDemo: String = {
        return "Hello World version 1.0"
    }
}

@RestController
@RequestMapping(Array("/v2.0"))
@ApiDocDefination()
class HomeControllerV2 extends Loggable{
    @RequestMapping(value = Array("/version")) private[intellitags]
    /*override*/ def getDemo: String = {
        return "Hello World version 2.0"
    }
}
