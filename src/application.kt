package KtorAPI

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.auth.*
import com.fasterxml.jackson.databind.*
import io.ktor.jackson.*
import io.ktor.features.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.DevelopmentEngine.main(args)

fun Application.module() {
	install(Authentication) {
	}

	install(ContentNegotiation) {
		jackson {
			enable(SerializationFeature.INDENT_OUTPUT)
		}
	}

	install(CORS) {
		method(HttpMethod.Options)
		method(HttpMethod.Put)
		method(HttpMethod.Delete)
		method(HttpMethod.Patch)
		header(HttpHeaders.Authorization)
		header("MyCustomHeader")
		allowCredentials = true
		anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
	}

	routing {
		// http://127.0.0.1:8080 or http://localhost:8080
		get("/") {
			call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
		}

		// http://127.0.0.1:8080/json/jackson or http://localhost:8080/json/jackson
		get("/json/jackson") {
			call.respond(mapOf("hello" to "world"))
		}
	}
}

