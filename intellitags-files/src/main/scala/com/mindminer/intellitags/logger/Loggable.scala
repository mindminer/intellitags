package com.mindminer.intellitags.logger

import org.apache.log4j.Logger


/**
  * Created by wangji on 2016/6/13.
  */
trait Loggable {
    private lazy val logger = Logger.getLogger(getClass)
    protected def debug(msg: => AnyRef, t: => Throwable = null): Unit =
    {
        logger.debug(msg, t)
    }
    protected def info(msg: => AnyRef, t: => Throwable = null): Unit =
    {
        logger.info(msg, t)
    }
}
