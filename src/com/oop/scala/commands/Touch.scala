package com.oop.scala.commands
import com.oop.scala.files.DirEntry
import com.oop.scala.filesystem.State
import com.oop.scala.files.File

class Touch(name: String) extends CreateEntry(name){
  override def createSpecyficEntry(state: State): DirEntry = File.empty(state.wd.path, name)
}
