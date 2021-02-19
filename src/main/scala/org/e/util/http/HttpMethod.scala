package org.e.util.http

sealed abstract class HttpMethod
case object GET extends HttpMethod
case object POST extends HttpMethod
case object PUT extends HttpMethod
case object DELETE extends HttpMethod