package org.pruden.tablero.components.leftSideContent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import org.pruden.tablero.globals.Colors

@Composable
fun HtmlNative(html: String, modifier: Modifier = Modifier) {
    val doc = remember(html) { Jsoup.parse(html) }
    val scroll = rememberScrollState()
    SelectionContainer {
        Column(modifier.verticalScroll(scroll).padding(8.dp)) {
            doc.body().children().forEach { RenderBlock(it) }
        }
    }
}

@Composable
private fun RenderBlock(el: Element) {
    when (el.tagName().lowercase()) {
        "h1" -> Text(text = el.text(), style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(bottom = 8.dp), color = Colors.textColor)
        "h2" -> Text(text = el.text(), style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 6.dp), color = Colors.textColor)
        "h3" -> Text(text = el.text(), style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 6.dp), color = Colors.textColor)
        "ul" -> el.children().forEach { RenderBlock(it) }
        "ol" -> el.children().forEach { RenderBlock(it) }
        "li" -> Text(text = "• " + inlineText(el), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 4.dp), color = Colors.textColor)
        "p" -> Text(text = inlineText(el), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(bottom = 8.dp), color = Colors.textColor)
        "br" -> Text(text = "", color = Colors.textColor)
        else -> if (el.children().isEmpty()) Text(text = el.text(), style = MaterialTheme.typography.bodyMedium) else el.children().forEach { RenderBlock(it) }
    }
}

private fun inlineText(el: Element) = buildAnnotatedString { appendInline(el) }

private fun androidx.compose.ui.text.AnnotatedString.Builder.appendInline(node: Node) {
    when (node) {
        is TextNode -> append(node.text())
        is Element -> {
            when (node.tagName().lowercase()) {
                "b", "strong" -> withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { node.childNodes().forEach { appendInline(it) } }
                "i", "em" -> withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { node.childNodes().forEach { appendInline(it) } }
                "u" -> withStyle(SpanStyle(textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline)) { node.childNodes().forEach { appendInline(it) } }
                "br" -> append("\n")
                else -> node.childNodes().forEach { appendInline(it) }
            }
        }
    }
}