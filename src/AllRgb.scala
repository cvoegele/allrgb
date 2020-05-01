import java.awt.image.BufferedImage
import java.io.{BufferedOutputStream, FileInputStream, FileOutputStream}

import javax.imageio.ImageIO

import scala.collection.mutable.ListBuffer
import scala.util.Random

class RGB(val red: Int, val green: Int, val blue: Int) {
  def ==(other: RGB): Boolean = {
    red == other.red && blue == other.blue && green == other.green
  }

  def difference(o: RGB) : Int = {
    val redD = Math.abs(red - o.red)
    val greenD = Math.abs(green - o.green)
    val blueD = Math.abs(blue - o.blue)
    redD + greenD + blueD
  }
}

object AllRgb {

  def main(args: Array[String]): Unit = {
    val output = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_RGB)


        val colors = createRGBList
        val rColors = Random.shuffle(colors);

        var i = 0
        for (color <- rColors) {
          val x = i % 4096
          val y = i / 4096
          val value = (color.red << 8 | color.green) << 8 | color.blue
          output.setRGB(x, y, value)
          i += 1
        }
        println("Random Image created")

    val input = ImageIO.read(new FileInputStream("./mona.png"))

    //    val rbg  = input.getRGB(1000,1001)

    println("start swapping")
    var i2 = 0
    var gen = 0;
    while (true) {

      val x1 = Random.between(0, 4096)
      val y1 = Random.between(0, 4096)
      val x2 = Random.between(0, 4096)
      val y2 = Random.between(0, 4096)

      val outputValue = output.getRGB(x1, y1)
      val correctValue = input.getRGB(x1, y1)
      val candidateValue = output.getRGB(x2, y2)

      if (Math.abs(candidateValue - correctValue) < Math.abs(outputValue - correctValue)) {
        output.setRGB(x2, y2, outputValue)
        output.setRGB(x1, y1, candidateValue)
      }
      i2 += 1

      if (i2 % 100_000_000 == 0) {
        println("Generation "+ gen + " born")
        ImageIO.write(output, "png", new BufferedOutputStream(new FileOutputStream("RGBRandomMona"+gen+".png")))
        gen+=1;
      }

    }
    println("stop swapping")

    ImageIO.write(output, "png", new BufferedOutputStream(new FileOutputStream("RGBRandomMona.png")))
  }

  def createRGBFromInt(rbg: Int): RGB = {
    val red = (rbg >> 16) & 0xFF
    val green = (rbg >> 8) & 0xFF
    val blue = rbg & 0xFF
    new RGB(red,green,blue)
  }

  private def createRGBList: ListBuffer[RGB] = {
    var rgbs = ListBuffer[RGB]()

    var i = 0;
    var r = 0;
    while (r < 256) {
      var b = 0;
      while (b < 256) {
        var g = 0;
        while (g < 256) {
          //img.setRGB(x, y, (r << 8 | g) << 8 | b);
          rgbs += new RGB(r, g, b)
          i += 1
          g += 1
        }
        b += 1
      }
      r += 1
    }
    println("palette Created!")
    rgbs
  }
}
