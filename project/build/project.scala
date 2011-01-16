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
import com.weiglewilczek.bnd4sbt.BNDPlugin
import sbt._

class ScalaModulesExamplesParentProject(info: ProjectInfo) extends ParentProject(info) {

  // ===================================================================================================================
  // Dependencies
  // ===================================================================================================================

  object Dependencies {

    // Versions
    val osgiVersion = "4.2.0"

    // Compile
    val scalaModulesCore = "com.weiglewilczek.scalamodules" %% "scalamodules-core" % projectVersion.value.toString

    // Provided
    val osgiCore = "org.osgi" % "org.osgi.core" % osgiVersion % "provided"
  }

  // ===================================================================================================================
  // greeting-api subproject
  // ===================================================================================================================

  val greetingAPIProject = project("greeting-api", "scalamodulesexamples-greeting-api", new GreetingAPIProject(_))

  class GreetingAPIProject(info: ProjectInfo) extends DefaultProject(info) with BNDPlugin {
    override def bndExportPackage =
      "com.weiglewilczek.scalamodulesexamples.greeting;version=%s".format(projectVersion.value) :: Nil
    override def bndVersionPolicy = Some("[$(@),$(version;=+;$(@)))")
  }

  // ===================================================================================================================
  // greeting-create subproject
  // ===================================================================================================================

  val greetingCreateProject =
    project("greeting-create", "scalamodulesexamples-greeting-create", new GreetingCreateProject(_), greetingAPIProject)

  class GreetingCreateProject(info: ProjectInfo) extends DefaultProject(info) with BNDPlugin {
    import Dependencies._
    override def libraryDependencies = Set(scalaModulesCore, osgiCore)
    override def bndBundleActivator = Some("com.weiglewilczek.scalamodulesexamples.greeting.create.Activator")
    override def bndImportPackage =
      "com.weiglewilczek.scalamodulesexamples.greeting.*;version=\"[%1$s,%1$s]\"".format(projectVersion.value) ::
      super.bndImportPackage.toList
    override def bndVersionPolicy = Some("[$(@),$(version;=+;$(@)))")
  }

  // ===================================================================================================================
  // greeting-find subproject
  // ===================================================================================================================

  val greetingFindProject =
    project("greeting-find", "scalamodulesexamples-greeting-find", new GreetingFindProject(_), greetingAPIProject)

  class GreetingFindProject(info: ProjectInfo) extends DefaultProject(info) with BNDPlugin {
    import Dependencies._
    override def libraryDependencies = Set(scalaModulesCore, osgiCore)
    override def bndBundleActivator = Some("com.weiglewilczek.scalamodulesexamples.greeting.find.Activator")
    override def bndImportPackage =
      "com.weiglewilczek.scalamodulesexamples.greeting.*;version=\"[%1$s,%1$s]\"".format(projectVersion.value) ::
      super.bndImportPackage.toList
    override def bndVersionPolicy = Some("[$(@),$(version;=+;$(@)))")
  }

  // ===================================================================================================================
  // greeting-watch subproject
  // ===================================================================================================================

  val greetingWatchProject =
    project("greeting-watch", "scalamodulesexamples-greeting-watch", new GreetingWatchProject(_), greetingAPIProject)

  class GreetingWatchProject(info: ProjectInfo) extends DefaultProject(info) with BNDPlugin {
    import Dependencies._
    override def libraryDependencies = Set(scalaModulesCore, osgiCore)
    override def bndBundleActivator = Some("com.weiglewilczek.scalamodulesexamples.greeting.watch.Activator")
    override def bndImportPackage =
      "com.weiglewilczek.scalamodulesexamples.greeting.*;version=\"[%1$s,%1$s]\"".format(projectVersion.value) ::
      super.bndImportPackage.toList
    override def bndVersionPolicy = Some("[$(@),$(version;=+;$(@)))")
  }
}
