package com.exercise.loans

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
class LoansApplication

fun main(args: Array<String>) {
	runApplication<LoansApplication>(*args)
}
