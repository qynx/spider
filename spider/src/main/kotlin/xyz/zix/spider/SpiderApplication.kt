package xyz.zix.spider

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SpiderApplication

fun main(args: Array<String>) {
    runApplication<SpiderApplication>(*args)
}
