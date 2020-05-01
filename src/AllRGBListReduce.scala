import java.awt.image.BufferedImage
import java.io.{BufferedOutputStream, FileInputStream, FileOutputStream}
import java.util.Calendar

import AllRgb.createRGBList
import javax.imageio.ImageIO
import AllRgb.createRGBFromInt

import scala.collection.mutable.ListBuffer


object AllRGBListReduce {


  def main(args: Array[String]): Unit = {
    val output = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_RGB)

    var colors = createRGBList

    val input = ImageIO.read(new FileInputStream("./largehead.png"))

    var gen = 0
    var i = 0;
    while (i < 16777216) {
      val x = i % 4096
      val y = i / 4096

      val inputPixel = input.getRGB(x, y)
      val inputRGB = createRGBFromInt(inputPixel)
      val newValue = findClosestInPalette(inputRGB,colors)
      colors = colors.subtractOne(newValue)

      output.setRGB(x,y,(newValue.red << 8 | newValue.green) << 8 | newValue.blue)


      if (i % 10000 == 0) {
        println(Calendar.getInstance().getTime()+" generation "+gen+" born")
        ImageIO.write(output, "png", new BufferedOutputStream(new FileOutputStream("reduceList"+gen+".png")))
        gen += 1
      }
      i += 1;
    }

    ImageIO.write(output, "png", new BufferedOutputStream(new FileOutputStream("reduceListFinal.png")))

  }

  def findClosestInPalette(rgb: RGB, rgbs: ListBuffer[RGB]): RGB = {
    var closest: RGB = null

    for (color <- rgbs) {
      if (null == closest) closest = color
      if (color.difference(rgb) < closest.difference(rgb)) {
        closest = color
      }
    }
    closest
  }

}
