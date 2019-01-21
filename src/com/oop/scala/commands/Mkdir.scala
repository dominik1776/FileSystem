package com.oop.scala.commands

import com.oop.scala.files.{DirEntry, Directory}
import com.oop.scala.filesystem.State

class Mkdir(name: String) extends CreateEntry(name) {
  override def createSpecyficEntry(state: State): DirEntry = Directory.empty(state.wd.path, name)
}
