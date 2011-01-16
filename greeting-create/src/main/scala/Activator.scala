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
package create

import java.io.Serializable
import com.weiglewilczek.scalamodules._
import org.osgi.framework.{ BundleActivator, BundleContext }

class Activator extends BundleActivator {

  override def start(context: BundleContext) {
    val greeting = new Greeting {
      override def welcome = "Hello!"
      override def goodbye = "Bye!"
    }
    context createService greeting

    val coolGreeting = new Greeting {
      override def welcome = "Hey!"
      override def goodbye = "See you!"
    }
    context.createService(coolGreeting, Style -> "cool", interface[Greeting])

    val politeGreeting = new Greeting with Serializable {
      override def welcome = "Welcome!"
      override def goodbye = "Good-bye!"
    }
    context.createService(
      politeGreeting,
      interface1 = interface[Greeting],
      interface2 = interface[Serializable],
      properties = Map(Style -> "polite", "priority" -> 1))
  }

  override def stop(context: BundleContext) {}

  private val Style = "style"
}
