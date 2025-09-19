package es.unizar.webeng.hello.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalTime
import org.slf4j.LoggerFactory

/**
 * Returns a time-based greeting depending on the current hour.
 * - Morning: 5:00 - 11:59
 * - Afternoon: 12:00 - 18:59
 * - Evening: 19:00 - 4:59
 */
private fun getTimeBasedGreeting(): String {
    val hour = LocalTime.now().hour
    return when (hour) {
        in 5..11 -> "Good Morning"
        in 12..18 -> "Good Afternoon"
        else -> "Good Evening"
    }
}

@Controller
class HelloController(
    @param:Value("\${app.message:Hello World}") 
    private val message: String
) {
    /**
     * Handles the root web page.
     * If a name is provided, returns a time-based greeting.
     * Otherwise, returns the default message.
     */

    private val logger = LoggerFactory.getLogger(HelloController::class.java)

    @GetMapping("/")
    fun welcome(
        model: Model,
        @RequestParam(defaultValue = "") name: String
    ): String {
        val greeting = if (name.isNotBlank()) {
            "${getTimeBasedGreeting()}, $name!"
        } else message
        logger.info("Web greeting generated for name=$name with greeting='$greeting'")
        model.addAttribute("message", greeting)
        model.addAttribute("name", name)
        return "welcome"
    }
}

@RestController
class HelloApiController {
    /**
     * Handles the API endpoint.
     * Returns a time-based greeting in JSON format.
     */

    private val logger = LoggerFactory.getLogger(HelloApiController::class.java)

    @GetMapping("/api/hello", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun helloApi(@RequestParam(defaultValue = "World") name: String): Map<String, String> {
        val greeting = "${getTimeBasedGreeting()}, $name!"
        val response = mapOf(
            "message" to greeting,
            "timestamp" to java.time.Instant.now().toString()
        )
        logger.info("API greeting generated for name=$name with response=$response")
        return response
    }
}
