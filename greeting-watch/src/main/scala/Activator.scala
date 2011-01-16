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
package watch

import com.weiglewilczek.scalamodules._
import org.osgi.framework.{ BundleActivator, BundleContext }

class Activator extends BundleActivator {

  override def start(context: BundleContext) {
    def message(property: Option[Any], s: String) =
      "ServiceEvent - %s: %s".format(property getOrElse "UNKNOWN", s)
    def styleProperty(properties: Props) = properties get "style"
    context watchServices withInterface[Greeting] withFilter "style".present andHandle {
      case AddingService(greeting, properties) => println(message(styleProperty(properties), greeting.welcome))
      case ServiceRemoved(greeting, properties) => println(message(styleProperty(properties), greeting.goodbye))
    }
  }

  override def stop(context: BundleContext) {}
}
