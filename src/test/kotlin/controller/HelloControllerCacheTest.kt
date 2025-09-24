package es.unizar.webeng.hello.controller

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.beans.factory.annotation.Autowired

/*
* Tests to verify that caching behavior is working as expected in HelloApiController.
 */
@SpringBootTest
class HelloControllerCacheTest {

    // Injects the REST controller with the cached method
    @Autowired
    private lateinit var apiController: HelloApiController

    /**
     * Verifies that for the same parameter ("CacheTester"), the result is retrieved from the cache,
     * so both results should be equal.
     */
    @Test
    fun `should cache API response for same name`() {
        val first = apiController.helloApi("CacheTester")
        val second = apiController.helloApi("CacheTester")
        assertThat(first).isEqualTo(second)
    }

    /**
     * Verifies that for different parameters ("CacheTester1" and "CacheTester2"),
     * the result is not retrieved from the cache, so the results should be different.
     */
    @Test
    fun `should not cache API response for different names`() {
        val first = apiController.helloApi("CacheTester1")
        val second = apiController.helloApi("CacheTester2")
        assertThat(first).isNotEqualTo(second)
    }
}
