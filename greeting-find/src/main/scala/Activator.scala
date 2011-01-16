/*
 * Copyright 2009-2011 Weigle Wilczek GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weiglewilczek.scalamodulesexamples.greeting
package find

import com.weiglewilczek.scalamodules._
import org.osgi.framework.{ BundleActivator, BundleContext }

class Activator extends BundleActivator {

  override def start(context: BundleContext) {

    println("Find a Greeting service and print the result of calling welcome:")
    context findService withInterface[Greeting] andApply { _.welcome } match {
      case None => println("No Greeting service available!")
      case Some(welcome) => println(welcome)
    }

    println("""Find all Greeting services and print their "style" property plus the result of calling welcome:""")
    context findServices withInterface[Greeting] andApply {
      (greeting, properties) => "%s: %s".format(properties get "style" getOrElse "UNKNOWN", greeting.welcome)
    } match {
      case Nil => println("No Greeting service available!")
      case welcomes => welcomes foreach println
    }

    println("""Find all Greeting services matching the filter "style".present and print their "style" property plus the result of calling welcome:""")
    context findServices withInterface[Greeting] withFilter "style".present andApply {
      (greeting, properties) => "%s: %s".format(properties("style"), greeting.welcome)
    } match {
      case Nil => println("No Greeting service available!")
      case welcomes => welcomes foreach println
    }
  }

  override def stop(context: BundleContext) {}
}
