package pl.training.mail

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.mail.javamail.MimeMessagePreparator

// @Service
class EmailService(
    private val mailSender: JavaMailSender,
    private val templateService: TemplateService
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        val text = templateService.process("ChatNotification", mapOf("value" to "Hello"), "pl")
        val message = createMessage("user@training.pl", arrayOf("client@training.pl"), "Training", text)
        mailSender.send(message)
    }

    private fun createMessage(
        sender: String,
        recipients: Array<String>,
        subject: String,
        text: String
    ): MimeMessagePreparator {
        return MimeMessagePreparator { mimeMessage ->
            val message = MimeMessageHelper(mimeMessage)
            message.setFrom(sender)
            message.setTo(recipients)
            message.setSubject(subject)
            message.setText(text, true)
        }
    }

}
