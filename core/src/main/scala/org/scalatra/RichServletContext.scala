package org.scalatra

import java.net.{MalformedURLException, URL}
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest

/**
 * Extension methods to the standard ServletContext.
 */
class RichServletContext(sc: ServletContext) extends AttributesMap {
  protected def attributes = sc

  /**
   * Optionally returns a URL to the resource mapped to the given path.  This
   * is a wrapper around `getResource`.
   *
   * @param path the path to the resource
   * @return the resource located at the path, or None if there is no resource
   * at that path.
   */
  def resource(path: String): Option[URL] =
    try {
      Option(sc.getResource(path))
    }
    catch {
      case e: MalformedURLException => throw e
    }

  /**
   * Optionally returns the resource mapped to the request's path.
   *
   * @param the request
   * @return the resource located at the result of concatenating the request's
   * servlet path and its path info, or None if there is no resource at that path.
   */
  def resource(req: HttpServletRequest): Option[URL] = {
    val path = req.getServletPath + (Option(req.getPathInfo) getOrElse "")
    resource(path)
  }
}

