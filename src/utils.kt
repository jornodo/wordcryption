import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.ChaCha20ParameterSpec

const val ALGORITHM: String = "ChaCha20"

val key: SecretKey = KeyGenerator.getInstance(ALGORITHM).apply { init(256) }.generateKey()
val nonce = ByteArray(12).apply { SecureRandom().nextBytes(this) }

fun encryptRaw(data: ByteArray, n: Int = 0): ByteArray {
    val parameterSpec = ChaCha20ParameterSpec(nonce, n);
    val cipher = Cipher.getInstance(ALGORITHM)
    cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec)

    return cipher.doFinal(data)
}

fun decryptRaw(data: ByteArray, n: Int = 0): ByteArray {
    val parameterSpec = ChaCha20ParameterSpec(nonce, n);
    val cipher = Cipher.getInstance(ALGORITHM)
    cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec)

    return cipher.doFinal(data)
}

fun encryptAllRaw(data: List<ByteArray>, n: Int = 500): List<ByteArray> {
    return data.map {arr -> (1..n)
        .foldIndexed(arr) {index, acc, _ -> encryptRaw(acc, index)}
    }
}

fun decryptAllRaw(data: List<ByteArray>, n: Int = 500): List<ByteArray> {
    return data.map {arr -> (1..n)
        .foldIndexed(arr) {index, acc, _ -> decryptRaw(acc, index)}
    }
}

fun getText(): String {
    return """"
    Examples
    A Non-programming Example
        Suppose a simple scheme for a repair centre, involving a manager and a group of technicians.
    The manager is responsible for receiving articles, and assigning an article to be repaired by a
    technician. All technicians have similar skills for repairing articles, and each one is responsible
    to repair one article at a time, independent of the other technicians. When a technician finishes
    repairing his assignment, he notifies the manager; the manager then assigns him a new article
        to be repaired, and so on. In general, repairing articles represents an irregular problem: some
    articles may present a simple fix and take a little amount of time, while others may require a
    more complex repair. Also, the effectiveness of this scheme relies on the fact that the number of
    articles that arrive to the centre can be substantially larger than the number of technicians
    available.
    A Programming Example
    Consider a real-time ultrasonic imaging system [GSVOM97], designed to acquire, process and
    display a tomographic image. Data is acquired based on the reflection of an ultrasonic signal
        that excites an array of 56 ceramic sensors. Data is amplified and digitalised to form a black and
    white image of 56´ 256 pixels, each one represented by a byte. An interpolation program is
    required to process the image, enlarging it to make it clearer to the observer. The image is
    displayed on a standard resolution monitor (640´ 480 pixels) in real-time, this is, at least 25
    frames per second. In accordance with these requirements, an interpolation by a factor 3
    between columns was chosen, enlarging the information of each image three times. A
    calculation shows the volume of data to be processes per second: each frame is represented as
    168´ 256´ 1 bytes, and using 25 frames per second, makes a total of 1.075200 Mbytes per
    second. Using a manager-worker system for the cubic interpolation, the image is received a
    stream of pixels by the manager, which assigns to each worker a couple of pixels. Each worker
    uses each couple of pixels as input data, and calculates the cubic interpolation between them,
    producing other four interpolated pixels. As the number of workers is less than the total number
    of pixels, each worker requests for more work to the manager as soon as it finishes its process,
    and so on.
    Problem
    A computation is required where independent computations are performed, perhaps repeatedly,
    on all elements of some ordered data. Each computation can be performed completely,
    independent of the activity of other elements. Data is distributed among components without a

    specific order. However, an important feature is to preserve the order of data. Consider, for
    example, an imaging problem, where the image can be divided into smaller sub-images, and the
    computation on each sub-image does not require the exchange of messages between
    components. This can be carried out until completion, and then the partial results gathered. The
    overall affect is to apply the computation to the whole image. If this computation is carried out
    serially, it should be executed as a sequence of serial jobs, applying the same computation to
    each sub-image one after another. Generally, performance as execution time is the feature of
    interest.
    Forces
    Using the previous problem description and other elements of parallel design, such as
    granularity and load balance [Fos94, CT92], the following forces are found:
    Preserve the order of data. However, the specific order of data distribution and operation among
    processing elements is not important
    The same computation can be performed independently and simultaneously on different pieces
        of data.
    Data pieces may exhibit different sizes.
    Changes in the number of processing elements should be reflected by the execution time.
    Improvement in performance is achieved when execution time decreases.
    """
}
