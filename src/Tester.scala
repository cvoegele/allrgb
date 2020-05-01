import java.io.FileInputStream

import javax.imageio.ImageIO


object Tester {
  def main(args: Array[String]): Unit = {
    val input = ImageIO.read(new FileInputStream("./RedMonas/invertedRed.png"))

    val width = input.getWidth()
    val height = input.getHeight()

    var colors : Set[String] = Set()

    var wc = 0;
    while (wc < width) {
      var hc = 0
      while (hc < height) {
        colors = colors + String.valueOf(input.getRGB(wc, hc))
        hc += 1
      }
      wc += 1
    }

    println(colors.size)
    println(colors.size == 16777216)
  }
}
