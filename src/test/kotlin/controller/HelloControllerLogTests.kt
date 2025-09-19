package es.unizar.webeng.hello.controller

import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.read.ListAppender
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.ui.ExtendedModelMap

/**
 * Tests to verify that logging occurs when controller methods are called.
 */
class HelloControllerLogTests {

    /**
     * Verifies that calling HelloController.welcome logs a greeting message.
     */
    @Test
    fun `should log greeting when welcome is called`() {
        // Get the logger for HelloController
        val logger = LoggerFactory.getLogger(HelloController::class.java) as Logger
        // Create a ListAppender to capture log events in memory
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        // Attach the appender to the logger
        logger.addAppender(listAppender)

        // Call the controller method
        val controller = HelloController("Test Message")
        val model = ExtendedModelMap()
        controller.welcome(model, "LogTester")

        // Get the captured logs
        val logs = listAppender.list
        // Assert that at least one log contains the expected message and name
        assertThat(logs).anyMatch { 
            it.formattedMessage.contains("Web greeting generated") && 
            it.formattedMessage.contains("LogTester") 
        }
    }

    /**
     * Verifies that calling HelloApiController.helloApi logs a greeting message.
     */
    @Test
    fun `should log greeting when helloApi is called`() {
        // Get the logger for HelloApiController
        val logger = LoggerFactory.getLogger(HelloApiController::class.java) as Logger
        // Create a ListAppender to capture log events in memory
        val listAppender = ListAppender<ILoggingEvent>()
        listAppender.start()
        // Attach the appender to the logger
        logger.addAppender(listAppender)

        // Call the API controller method
        val apiController = HelloApiController()
        apiController.helloApi("LogTester")

        // Get the captured logs
        val logs = listAppender.list
        // Assert that at least one log contains the expected message and name
        assertThat(logs).anyMatch { 
            it.formattedMessage.contains("API greeting generated") && 
            it.formattedMessage.contains("LogTester") 
        }
    }
}
