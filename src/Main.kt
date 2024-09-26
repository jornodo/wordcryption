import java.io.File

fun main() {
    val text = getText()
    val n = 5000
    val encodedText = text.split(" ").map {it.toByteArray()}

    val encryptedText = encryptAllRaw(encodedText, n)
    val decryptedText = decryptAllRaw(encryptedText, n)

    println("Original text: ${text.length}")
    println("Encrypted text: ${encryptedText.joinToString(" ") {String(it)}.length}")
    println("Decrypted text: ${decryptedText.joinToString(" ") {String(it)}.length}")
    println("Decrypted text = Original text: ${text == decryptedText.joinToString(" ") {String(it)}}")

    // write encryptedText to file
    File("encryptedText.txt").writeText(encryptedText.toString())
    println("List of individually encrypted words written to encryptedText.txt, " +
        "Size of list = ${encryptedText.size}")
}