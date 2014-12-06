package hackit

import hackit.game.{SpaceObject, Pilot, Rocket}
import org.scalajs.dom.{CanvasRenderingContext2D, HTMLCanvasElement}
import org.scalajs.jquery.jQuery

import scala.util.Random


class World(canvasId: String) extends CollisionSpace {

  private val element = jQuery(s"#$canvasId")(0)
  private val canvas = element.asInstanceOf[HTMLCanvasElement]
  private val ctx = canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

  private var rockets: List[Rocket] = List.empty

  def addRocket(pilot: Pilot): Rocket = {
    rockets ::= new Rocket(Random.nextInt(canvas.width), Random.nextInt(canvas.height), Random.nextInt(360), pilot, this)
    rockets.head
  }

  def drawScene(): Unit = {
    ctx.clearRect(0, 0, canvas.width, canvas.height)
    ctx.strokeRect(0, 0, canvas.width, canvas.height)
    rockets.foreach(rocket => {
      rocket.step()
      rocket.draw(ctx)
    })
  }

  override def toString = {
    s"""World(
       |  ${rockets.mkString(",\n  ")}
       |)""".stripMargin
  }

  override def spaceObjects: List[SpaceObject] = rockets

  override def spaceRect: (Int, Int, Int, Int) = (0, 0, canvas.width, canvas.height)
}
