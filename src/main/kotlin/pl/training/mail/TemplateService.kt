package pl.training.mail

import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class TemplateService(
    private val templateEngine: TemplateEngine
) {

    fun process(templateBaseName: String, data: Map<String, Any>, language: String): String {
        val templateName = getTemplateName(templateBaseName, language)
        val context = Context()
        context.setVariables(data)
        return templateEngine.process(templateName, context)
    }

    private fun getTemplateName(baseName: String, language: String) = "$baseName$LANGUAGE_SEPARATOR$language"

    companion object {
        private const val LANGUAGE_SEPARATOR = "_"
    }

}
