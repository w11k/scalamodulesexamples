/**
 * Copyright (c) 2009-2010 WeigleWilczek and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
    val paxExamVersion = "1.2.0"

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
  }
}
